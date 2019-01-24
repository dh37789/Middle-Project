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

import classRoom.board.controller.SubBoardController;
import home.community.service.IFreeBoardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import vo.AdminVO;
import vo.Free_boardVO;
import vo.MemberVO;
import vo.NbdVO;

public class FreeBoardController {
	private IFreeBoardService service;
	Free_boardVO vo;
	private List<Free_boardVO> listall;

	private int fbSize;
	private int rowPerPage = 15;
	private List<Free_boardVO> numList;
	private ObservableList<Free_boardVO> Data;
	List<Free_boardVO> list = new ArrayList<>();

	@FXML
	private Pagination page;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<Free_boardVO> frbd;

	@FXML
	private TableColumn<?, ?> frbd_id;

	@FXML
	private TableColumn<?, ?> frbd_ti;

	@FXML
	private TableColumn<?, ?> frbd_wrr;

	@FXML
	private TableColumn<?, ?> frbd_dt;

	@FXML
	private TableColumn<?, ?> frbd_iNum;

	@FXML
	private Button btn_exit;

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	@FXML
	private TextField tf_search;

	// 검색
	@FXML
	void search(KeyEvent event) {
		List<Free_boardVO> list = null;
		String str = "%" + tf_search.getText().trim() + "%";
		try {
			list = service.freeBoardSearch(str);
			if ("".equals(str)) {
				table(listall);
			} else {
				table(list);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	void table(List<Free_boardVO> vo) {
		ObservableList<Free_boardVO> ob;
		ob = FXCollections.observableArrayList(vo);
		frbd.setItems(ob);
		
		frbd_id.setCellValueFactory(new PropertyValueFactory<>("frbd_id"));
		frbd_ti.setCellValueFactory(new PropertyValueFactory<>("frbd_ti"));
		frbd_wrr.setCellValueFactory(new PropertyValueFactory<>("frbd_wrr"));
		frbd_dt.setCellValueFactory(new PropertyValueFactory<>("frbd_dt"));
		frbd_iNum.setCellValueFactory(new PropertyValueFactory<>("frbd_iNum"));
	}

	// 리스트에서 항목을 클릭했을때 자세히 보기 뷰
	@FXML
	void frbd_view(MouseEvent event) throws IOException {

		vo = frbd.getSelectionModel().getSelectedItem();
		if (vo == null) {
			return;
		}
		if (event.getClickCount() == 2 && !frbd.getSelectionModel().isEmpty()) {
		// 클릭하면 조회수 1씩 증가
		service.iNum(vo.getFrbd_id());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard_view.fxml"));
		Parent root = loader.load();

		FreeBoard_ViewController viewController = loader.getController();
		viewController.setFvo(vo);
		viewController.setTable(vo);
		viewController.setAvo(avo);
		viewController.viewReply();

		pane.getChildren().setAll(root);
		}

	}

	ObservableList<Free_boardVO> data;

	private TableView<Free_boardVO> fbvo;

	public void setFbVo(TableView<Free_boardVO> frbd) {
		this.fbvo = frbd;
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd != null : "fx:id=\"frbd\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd_id != null : "fx:id=\"frbd_id\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd_ti != null : "fx:id=\"frbd_ti\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd_wrr != null : "fx:id=\"frbd_wrr\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd_dt != null : "fx:id=\"frbd_dt\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert frbd_iNum != null : "fx:id=\"frbd_iNum\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert tf_search != null : "fx:id=\"tf_search\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'community_freeBoard.fxml'.";
		frbd.setFocusTraversable(false);
		
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");

		list = service.getFreeBoard();
		numList = service.getFreeBoard();

		getIndex();
		getTableList();

		frbd_id.setCellValueFactory(new PropertyValueFactory<>("frbd_id"));
		frbd_ti.setCellValueFactory(new PropertyValueFactory<>("frbd_ti"));
		frbd_wrr.setCellValueFactory(new PropertyValueFactory<>("frbd_wrr"));
		frbd_dt.setCellValueFactory(new PropertyValueFactory<>("frbd_dt"));
		frbd_iNum.setCellValueFactory(new PropertyValueFactory<>("frbd_iNum"));
		
		fbSize = numList.size();
		paging();
		
//		btn_exit.setFocusTraversable(false);
	}

	public void getIndex() {
		for (int i = 0; i < numList.size(); i++) {
			Free_boardVO vo = new Free_boardVO();
			vo.setFrbd_con(numList.get(i).getFrbd_con());
			vo.setFrbd_dt(numList.get(i).getFrbd_dt());
			vo.setFrbd_id(numList.get(i).getFrbd_id());
			vo.setFrbd_iNum(numList.get(i).getFrbd_iNum());
			vo.setFrbd_memId(numList.get(i).getFrbd_memId());
			vo.setFrbd_ti(numList.get(i).getFrbd_ti());
			vo.setFrbd_wrr(numList.get(i).getFrbd_wrr());
			numList.set(i, vo);
		}
	}

	private void paging() {
		int pageCount = fbSize / rowPerPage + (fbSize % rowPerPage == 0 ? 0 : 1);
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
		int toIndex = Math.min(fromIndex + rowPerPage, fbSize);

		frbd.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
	}

	private void getTableList() {
		Data = FXCollections.observableArrayList(list);
		frbd.setItems(Data);
	}
}
