package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import admin.comm_manage.service.IComm_manageService;
import home.community.controller.Comm_tip_viewController;
import home.community.service.ICommunityService;
import home.community.service.IFreeBoardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import vo.AdminVO;
import vo.Tip_boardVO;

public class Comm_TipController {

	private ICommunityService service;

	private List<Tip_boardVO> listall;
	private int tipSize;
	private int rowPerPage = 15;
	private List<Tip_boardVO> numList;
	private ObservableList<Tip_boardVO> Data;
	List<Tip_boardVO> list = new ArrayList<>();

	@FXML
	private TextField tfSearch;

	@FXML
	private Pagination page;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<Tip_boardVO> tableList;

	@FXML
	private TableColumn<?, ?> tcNo;

	@FXML
	private TableColumn<?, ?> tcTitle;

	@FXML
	private TableColumn<?, ?> tcDate;
	
	private List<Tip_boardVO> tvo;

	@FXML
	void search(KeyEvent event) {
		List<Tip_boardVO> list = null;
		String tbd_ti = "%" + tfSearch.getText().trim() + "%";
		try {
			list = service.tipSearch(tbd_ti);
			if ("".equals(tbd_ti)) {
				table(tvo);
			} else {
				table(list);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	void table(List<Tip_boardVO> vo) {
		ObservableList<Tip_boardVO> ob;
		ob = FXCollections.observableArrayList(vo);
		tableList.setItems(ob);
		
		
		tcNo.setCellValueFactory(new PropertyValueFactory<>("tbd_id"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("tbd_ti"));
		tcDate.setCellValueFactory(new PropertyValueFactory<>("tbd_dt"));
	}

	@FXML
	void showContent(MouseEvent event) throws IOException {
		Tip_boardVO tvo = tableList.getSelectionModel().getSelectedItem();
		if (event.getClickCount() == 2 && !tableList.getSelectionModel().isEmpty()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip_view.fxml"));
			Parent root = loader.load();

			Comm_Tip_viewController tipView = loader.getController();
			tipView.setAvo(avo);
			tipView.setTvo(tvo);
			tipView.viewBoard();

			pane.getChildren().setAll(root);
		}
	}

	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	ObservableList<Tip_boardVO> data;

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_Tip.fxml'.";
		assert tableList != null : "fx:id=\"tableList\" was not injected: check your FXML file 'comm_lcsTip.fxml'.";
		assert tcNo != null : "fx:id=\"tcNo\" was not injected: check your FXML file 'comm_lcsTip.fxml'.";
		assert tcTitle != null : "fx:id=\"tcTitle\" was not injected: check your FXML file 'comm_lcsTip.fxml'.";
		assert tcDate != null : "fx:id=\"tcDate\" was not injected: check your FXML file 'comm_lcsTip.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'comm_Tip.fxml'.";
		assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'comm_Tip.fxml'.";
		tableList.setFocusTraversable(false);
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");

		list = service.getTipBoard();
		numList = service.getTipBoard();
		getIndex();
		getTableList();

		tcNo.setCellValueFactory(new PropertyValueFactory<>("tbd_id"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("tbd_ti"));
		tcDate.setCellValueFactory(new PropertyValueFactory<>("tbd_dt"));
		tipSize = numList.size();
		paging();
	}

	public void getIndex() {
		for (int i = 0; i < numList.size(); i++) {
			Tip_boardVO vo = new Tip_boardVO();
			vo.setTbd_con(numList.get(i).getTbd_con());
			vo.setTbd_dt(numList.get(i).getTbd_dt());
			vo.setTbd_id(numList.get(i).getTbd_id());
			vo.setTbd_iNum(numList.get(i).getTbd_iNum());
			vo.setTbd_memID(numList.get(i).getTbd_memID());
			vo.setTbd_ti(numList.get(i).getTbd_ti());
			vo.setTbd_wrr(numList.get(i).getTbd_wrr());
			numList.set(i, vo);
		}
	}

	private void paging() {
		int pageCount = tipSize / rowPerPage + (tipSize % rowPerPage == 0 ? 0 : 1);
		page.setPageCount(pageCount);
		page.setCurrentPageIndex(0);

		changeTableView(0);

		page.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				changeTableView(newValue.intValue());
			}
		});
	}

	public void changeTableView(int index) {
		int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
		int toIndex = Math.min(fromIndex + rowPerPage, tipSize);

		tableList.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
	}

	private void getTableList() {
		Data = FXCollections.observableArrayList(list);
		tableList.setItems(Data);
	}
}
