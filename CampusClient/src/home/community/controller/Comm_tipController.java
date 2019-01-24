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
import javafx.beans.property.Property;
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
import vo.AdminVO;
import vo.Free_boardVO;
import vo.LcsVO;
import vo.MemberVO;
import vo.Tip_boardVO;

public class Comm_tipController {

	private ICommunityService service;

	private List<Tip_boardVO> listall;
	private int tipSize;
	private int rowPerPage = 15;
	private List<Tip_boardVO> numList;
	private ObservableList<Tip_boardVO> Data;
	List<Tip_boardVO> list = new ArrayList<>();

	@FXML
	private Pagination page;

	@FXML
	private TableColumn<?, ?> tbd_id;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<Tip_boardVO> tableTip;

	@FXML
	private TableColumn<?, ?> tcName;

	@FXML
	private TableColumn<?, ?> tcDate;

	@FXML
	private TableColumn<?, ?> tbd_memid;

	@FXML
	private TableColumn<?, ?> tbd_wrr;

	@FXML
	private Button btnAdd;
	
	@FXML
	private Label label;

	@FXML
	private AnchorPane pane;

	@FXML
	private TextField tfSearch;

	private List<Tip_boardVO> tvo;

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	// Tip 작성 화면으로 이동
	@FXML
	void add(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip_write.fxml"));
		Parent root = loader.load();

		Comm_tip_writeController tipwriteController = loader.getController();
		tipwriteController.setMvo(mvo);

		pane.getChildren().setAll(root);
	}

	// 제목으로 검색
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

	// 목록
	@FXML
	void showContent(MouseEvent event) throws IOException {
		Tip_boardVO tvo = tableTip.getSelectionModel().getSelectedItem();
		if (event.getClickCount() == 2 && !tableTip.getSelectionModel().isEmpty()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip_view.fxml"));
			Parent root = loader.load();

			Comm_tip_viewController tipView = loader.getController();
			System.out.println(mvo.getMem_id());
			tipView.setMvo(mvo);
			tipView.setTvo(tvo);
			tipView.viewBoard();
			tipView.memChk();
			tipView.viewReply();

			pane.getChildren().setAll(root);
		}
	}

	ObservableList<Tip_boardVO> data;

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert tableTip != null : "fx:id=\"tableTip\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tcName != null : "fx:id=\"tcName\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tcDate != null : "fx:id=\"tcDate\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tbd_memid != null : "fx:id=\"tbd_memid\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tbd_wrr != null : "fx:id=\"tbd_wrr\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'comm_tip.fxml'.";
		assert tbd_id != null : "fx:id=\"tbd_id\" was not injected: check your FXML file 'comm_tip.fxml'.";
		tableTip.setFocusTraversable(false);
		
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'comm_tip.fxml'.";

		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");
		list = service.getTipBoard();
		numList = service.getTipBoard();
		
		final Font font = Font.loadFont(getClass().getResourceAsStream("NanumSquareB_0.ttf"), 36);
		label.setFont(font);

		getIndex();
		getTableList();

		tbd_id.setCellValueFactory(new PropertyValueFactory<>("tbd_id"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("tbd_ti"));
		tcDate.setCellValueFactory(new PropertyValueFactory<>("tbd_dt"));
		tbd_memid.setCellValueFactory(new PropertyValueFactory<>("tbd_memID"));
		tbd_wrr.setCellValueFactory(new PropertyValueFactory<>("tbd_wrr"));
		tipSize = numList.size();
		paging();

		btnAdd.setFocusTraversable(false);
	}

	void table(List<Tip_boardVO> vo) {
		ObservableList<Tip_boardVO> ob;
		ob = FXCollections.observableArrayList(vo);
		tableTip.setItems(ob);

		tcName.setCellValueFactory(new PropertyValueFactory<>("tbd_ti"));
		tcDate.setCellValueFactory(new PropertyValueFactory<>("tbd_dt"));
		tbd_memid.setCellValueFactory(new PropertyValueFactory<>("tbd_memID"));
		tbd_wrr.setCellValueFactory(new PropertyValueFactory<>("tbd_wrr"));
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

		tableTip.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
	}

	private void getTableList() {
		Data = FXCollections.observableArrayList(list);
		tableTip.setItems(Data);
	}

}
