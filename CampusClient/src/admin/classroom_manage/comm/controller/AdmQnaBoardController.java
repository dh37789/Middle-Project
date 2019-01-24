package admin.classroom_manage.comm.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.service.IadminClassCommSerivce;
import classRoom.board.controller.QnABoardPassController;
import classRoom.board.controller.QnABoardViewController;
import classRoom.board.service.IBoardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Qna_boardVO;

public class AdmQnaBoardController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox pane;

    @FXML
    private Button btnNotice;

    @FXML
    private Button btnSubject;

    @FXML
    private Button btnQna;

    @FXML
    private Button brnPost;
	
	@FXML
	private ComboBox<String> comSearch;

	@FXML
	private TextField tfSearch;

	@FXML
	private Button btnSearch;

	@FXML
	private TableView<Qna_boardVO> tableSub;

	@FXML
	private TableColumn<?, ?> colNo;

	@FXML
	private TableColumn<?, ?> colTi;

	@FXML
	private TableColumn<?, ?> colWrr;

	@FXML
	private TableColumn<?, ?> colDate;

	@FXML
	private Pagination page;

	private List<Qna_boardVO> numList;

	private IadminClassCommSerivce service;

	private MemberVO mvo;

	private List<Qna_boardVO> qnaList;

	private ObservableList<Qna_boardVO> qnaData;

	private int qnaSize;

	private String[] searchCol = { "제목", "내용", "작성자" };

	private ObservableList<String> searchData;

	private int rowPerPage = 15;
	
	@FXML
    void notice(ActionEvent event) throws IOException {
		pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../comm/controller/admClassroom_Board.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        pane.getChildren().setAll(root);
    }

    @FXML
    void post(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../comm/controller/admLecPostBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void qna(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../comm/controller/admQnaBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void search(ActionEvent event) {
    	String col = comSearch.getSelectionModel().getSelectedItem();
    	String colStr = "";
    	String SearchStr = "%" + tfSearch.getText() + "%";
    	if (col.equals("제목")) {
			colStr = "qabd_ti";
		} else if (col.equals("내용")) {
			colStr = "qabd_con";
		} else if (col.equals("작성자")) {
			colStr = "qabd_wrr";
		}
    	Map<String, String> searchMap = new HashMap<>();
    	searchMap.put("colStr", colStr);
    	searchMap.put("SearchStr", SearchStr);
    	
    	try {
    		numList = service.searchQna(searchMap);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	qnaSize = numList.size();
    	getIndex();
    	getTableList();
    	paging();
    }

    @FXML
    void subject(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }

	@FXML
	void viewBoard(MouseEvent event) throws IOException {
		if (event.getClickCount() == 2 && (!tableSub.getSelectionModel().isEmpty())) {
			Qna_boardVO vo = tableSub.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("admQnaBoardView.fxml"));
			Parent root = loader.load();

			AdmQnaBoardViewController viewController = loader.getController();
    	    	viewController.setQvo(vo);
    	    	viewController.viewBoard();
    	    	viewController.viewReply();
			Pane qnaPane = (Pane) pane.getParent();
			qnaPane.getChildren().setAll(root);
		}
	}

	@FXML
	void initialize() {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert btnNotice != null : "fx:id=\"btnNotice\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
        assert btnSubject != null : "fx:id=\"btnSubject\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
        assert btnQna != null : "fx:id=\"btnQna\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
        assert brnPost != null : "fx:id=\"brnPost\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert comSearch != null : "fx:id=\"comSearch\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert tableSub != null : "fx:id=\"tableSub\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert colNo != null : "fx:id=\"colNo\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert colTi != null : "fx:id=\"colTi\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert colWrr != null : "fx:id=\"colWrr\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'admQnaBoard.fxml'.";
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IadminClassCommSerivce) reg.lookup("adminClass");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		try {
			qnaList = service.getQnaList();
			numList = service.getQnaList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		getIndex();
		getTableList();
		colNo.setCellValueFactory(new PropertyValueFactory<>("qabd_id"));
		colTi.setCellValueFactory(new PropertyValueFactory<>("qabd_ti"));
		colWrr.setCellValueFactory(new PropertyValueFactory<>("qabd_wrr"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("qabd_dt"));
		qnaSize = numList.size();

		paging();
		searchData = FXCollections.observableArrayList(searchCol);

		comSearch.setItems(searchData);
		comSearch.setValue(searchData.get(0));

		tableSub.setFocusTraversable(false);
		
		btnNotice.setFocusTraversable(false);
		btnQna.setFocusTraversable(false);
		btnSearch.setFocusTraversable(false);
		btnSubject.setFocusTraversable(false);
		brnPost.setFocusTraversable(false);
	}

	public void getIndex() {
		for (int i = 0; i < numList.size(); i++) {
			Qna_boardVO vo = new Qna_boardVO();
			vo.setQabd_id(numList.get(i).getQabd_id().substring(1));
			System.out.println(vo.getQabd_id());
			vo.setQabd_ti(numList.get(i).getQabd_ti());
			vo.setQabd_dt(numList.get(i).getQabd_dt());
			vo.setQabd_memid(numList.get(i).getQabd_memid());
			vo.setQabd_ps(numList.get(i).getQabd_ps());
			vo.setQabd_wrr(numList.get(i).getQabd_wrr());
			vo.setQabd_con(numList.get(i).getQabd_con());
			numList.set(i, vo);
		}
	}

	private void paging() {
		int pageCount = qnaSize / rowPerPage + (qnaSize % rowPerPage == 0 ? 0 : 1);
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
		int toIndex = Math.min(fromIndex + rowPerPage, qnaSize);

		tableSub.setItems(FXCollections.observableArrayList(numList.subList(fromIndex, toIndex)));
	}

	private void getTableList() {
		qnaData = FXCollections.observableArrayList(numList);
		tableSub.setItems(qnaData);
	}
}
