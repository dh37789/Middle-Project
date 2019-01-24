package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import home.community.service.ICommunityService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import vo.Free_boardVO;
import vo.LcsVO;

public class Comm_lcsController {

	private ICommunityService service;
	private List<LcsVO> listall;

	private int lcsSize;
	private int rowPerPage = 15;
	private List<LcsVO> numList;
	private ObservableList<LcsVO> Data;
	List<LcsVO> list = new ArrayList<>();

	@FXML
	private Label label;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableColumn<?, ?> lcs_id;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<LcsVO> tableLicense;

	@FXML
	private TableColumn<?, ?> lcs_tdt;

	@FXML
	private TableColumn<?, ?> tcTitle;

	@FXML
	private TableColumn<?, ?> lcs_admid;

	@FXML
	private TextField tf_search;

	@FXML
	private Pagination page;

	@FXML
	void search(KeyEvent event) {
		List<LcsVO> list = null;
		String str = "%" + tf_search.getText().trim() + "%";
		try {
			list = service.lcsSearch(str);
			if ("".equals(str)) {
				table(listall);
			} else {
				table(list);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	void table(List<LcsVO> vo) {
		ObservableList<LcsVO> ob;
		ob = FXCollections.observableArrayList(vo);
		tableLicense.setItems(ob);

		tcTitle.setCellValueFactory(new PropertyValueFactory<>("lcs_nm"));
		lcs_tdt.setCellValueFactory(new PropertyValueFactory<>("lcs_tdt"));
	}

	// 게시글 목록
	@FXML
	void showContents(MouseEvent event) throws IOException {
		LcsVO lvo = tableLicense.getSelectionModel().getSelectedItem();
		if (event.getClickCount() == 2 && !tableLicense.getSelectionModel().isEmpty()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs_view.fxml"));
			Parent root = loader.load();

			home.community.controller.Comm_lcs_viewController lcsView = loader.getController();
			lcsView.setLcvo(lvo);
			lcsView.viewBoard();

			pane.getChildren().setAll(root);
		}
	}

	// 나가기 버튼
	@FXML
	void exit(ActionEvent event) throws IOException {
		pane.getChildren().clear();
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert tableLicense != null : "fx:id=\"tableLicense\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert tcTitle != null : "fx:id=\"tcTitle\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert lcs_tdt != null : "fx:id=\"lcs_tdt\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert tf_search != null : "fx:id=\"tf_search\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert lcs_admid != null : "fx:id=\"lcs_admid\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'comm_lcs.fxml'.";
		assert lcs_id != null : "fx:id=\"lcs_id\" was not injected: check your FXML file 'comm_tip.fxml'.";
		
		tableLicense.setFocusTraversable(false);
		final Font font = Font.loadFont(getClass().getResourceAsStream("NanumSquareB_0.ttf"), 36);
		label.setFont(font);

		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");

		list = service.getLicenseBoard();
		numList = service.getLicenseBoard();

		getIndex();
		getTableList();
			
		lcs_id.setCellValueFactory(new PropertyValueFactory<>("lcs_id"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("lcs_nm"));
		lcs_tdt.setCellValueFactory(new PropertyValueFactory<>("lcs_tdt"));
		lcs_admid.setCellValueFactory(new PropertyValueFactory<>("lcs_admId"));

		lcsSize = numList.size();
		paging();

	}

	public void getIndex() {
		for (int i = 0; i < numList.size(); i++) {
			LcsVO vo = new LcsVO();

			vo.setLcs_admId(numList.get(i).getLcs_admId());
			vo.setLcs_con(numList.get(i).getLcs_con());
			vo.setLcs_id(numList.get(i).getLcs_id());
			vo.setLcs_nm(numList.get(i).getLcs_nm());
			vo.setLcs_tdt(numList.get(i).getLcs_tdt());
			numList.set(i, vo);
		}
	}

	private void paging() {
		int pageCount = lcsSize / rowPerPage + (lcsSize % rowPerPage == 0 ? 0 : 1);
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
		int toIndex = Math.min(fromIndex + rowPerPage, lcsSize);

		tableLicense.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
	}

	private void getTableList() {
		Data = FXCollections.observableArrayList(list);
		tableLicense.setItems(Data);
	}
}
