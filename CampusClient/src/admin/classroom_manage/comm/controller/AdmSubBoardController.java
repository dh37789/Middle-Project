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
import classRoom.board.controller.SubBoardViewController;
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
import vo.AdminVO;
import vo.Homework_boardVO;

public class AdmSubBoardController {

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
    private ComboBox<String> comSub;

    @FXML
    private ComboBox<String> comSearch;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<Homework_boardVO> tableSub;

    @FXML
    private TableColumn<?, ?> colSubNM;

    @FXML
    private TableColumn<?, ?> colNo;

    @FXML
    private TableColumn<?, ?> colTi;

    @FXML
    private TableColumn<?, ?> colWrr;

    @FXML
    private TableColumn<?, ?> colDate;

	private IadminClassCommSerivce service;
    
    @FXML
    private Pagination page;

    private String[] subList = {"전체","초급자바","Oracle","고급자바","HTML","JSP"};
    
    private String[] searchCol = {"제목", "내용", "작성자"};
    
    private ObservableList<String> subData;
    
    private ObservableList<String> searchData;
    
    private List<Homework_boardVO> homeList;
    
    private ObservableList<Homework_boardVO> homeData;

    private int homeSize;
    
    private int rowPerPage = 15;
    
    private AdminVO avo;
    
    @FXML
    void notice(ActionEvent event) throws IOException {

    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_Board.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    	
    }

    @FXML
    void post(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admLecPostBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void qna(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admQnaBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }
    @FXML
    void subject(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pane.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void search(ActionEvent event) {
    	Map<String, String> searchMap = new HashMap<>();
    	String sub = comSub.getSelectionModel().getSelectedItem();
    	if (sub.equals("전체")) {
    		sub = "%%";
    		searchMap.put("sub", sub);
    	}else {
    		sub = "%" + sub + "%";
    		searchMap.put("sub", sub);
    	}
    	String col = comSearch.getSelectionModel().getSelectedItem();
    	String colStr = "";
    	String SearchStr = "%" + tfSearch.getText() + "%";
    	if (col.equals("제목")) {
			colStr = "hwbd_ti";
		} else if (col.equals("내용")) {
			colStr = "hwbd_con";
		} else if (col.equals("작성자")) {
			colStr = "hwbd_wrr";
		}
    	System.out.println("3");
    	searchMap.put("colStr", colStr);
    	searchMap.put("SearchStr", SearchStr);
    	System.out.println("4");
    	try {
			homeList = service.searchHomeList(searchMap);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	homeSize = homeList.size();
    	System.out.println("5");
    	indexNo();
    	getTableList();
    	paging();
    }

    @FXML
    void subView(ActionEvent event) throws RemoteException {
    	String sub = comSub.getSelectionModel().getSelectedItem();
    	if (sub.equals("전체")) {
    		homeList = service.getHomeBoardList();
		}else {
			homeList = service.getSubList(sub);
		}
    	indexNo();
    	homeSize = homeList.size();
    	getTableList();
    	paging();
    }



    @FXML
    void viewBoard(MouseEvent event) throws IOException {
    	if (event.getClickCount() == 2 && (! tableSub.getSelectionModel().isEmpty()) ) {
	    	Homework_boardVO vo = tableSub.getSelectionModel().getSelectedItem();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoardView.fxml"));
	    	Parent root = loader.load();
	    	
	    	AdmSubBoardViewController viewController = loader.getController();
	    	viewController.setVo(vo);
	    	viewController.viewBoard();
	    	//pane.getChildren().clear();
	    	//boardPane.getChildren().clear();
	    	//root.setLayoutX(0);
	    	//root.setLayoutY(0);
	    	AnchorPane boardPane = (AnchorPane)pane.getParent();
	    	boardPane.getChildren().setAll(root);
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert btnNotice != null : "fx:id=\"btnNotice\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert btnSubject != null : "fx:id=\"btnSubject\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert btnQna != null : "fx:id=\"btnQna\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert brnPost != null : "fx:id=\"brnPost\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert comSearch != null : "fx:id=\"comSearch\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert tableSub != null : "fx:id=\"tableSub\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert colSubNM != null : "fx:id=\"colSubNM\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert colNo != null : "fx:id=\"colNo\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert colTi != null : "fx:id=\"colTi\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert colWrr != null : "fx:id=\"colWrr\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'admSubBoard.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'admSubBoard.fxml'.";
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
			homeList = service.getHomeBoardList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        indexNo();
        getTableList();
        colSubNM.setCellValueFactory(
        		new PropertyValueFactory<>("hwbd_sub"));
        colNo.setCellValueFactory(
        		new PropertyValueFactory<>("hwbd_id"));
        colTi.setCellValueFactory(
        		new PropertyValueFactory<>("hwbd_ti"));
        colWrr.setCellValueFactory(
        		new PropertyValueFactory<>("hwbd_wrr"));
        colDate.setCellValueFactory(
        		new PropertyValueFactory<>("hwbd_dt"));
        
        homeSize = homeList.size();
        
        subData = FXCollections.observableArrayList(subList);
        searchData = FXCollections.observableArrayList(searchCol);
        
        comSearch.setItems(searchData);
        comSearch.setValue(searchData.get(0));
        
        comSub.setItems(subData);
        comSub.setValue(subData.get(0));
        
        tableSub.setFocusTraversable(false);
        paging();
        btnNotice.setFocusTraversable(false);
        btnQna.setFocusTraversable(false);
        btnSearch.setFocusTraversable(false);
        btnSubject.setFocusTraversable(false);
    }
    public void indexNo(){
    	for (int i = 0; i < homeList.size(); i++) {
        	Homework_boardVO vo = new Homework_boardVO();
        	vo.setHwbd_id(homeList.get(i).getHwbd_id().substring(2));
        	vo.setHwbd_con(homeList.get(i).getHwbd_con());
        	vo.setHwbd_dt(homeList.get(i).getHwbd_dt());
        	vo.setHwbd_frt(homeList.get(i).getHwbd_frt());
        	vo.setHwbd_memId(homeList.get(i).getHwbd_memId());
        	vo.setHwbd_sub(homeList.get(i).getHwbd_sub());
        	vo.setHwbd_wrr(homeList.get(i).getHwbd_wrr());
        	vo.setHwbd_ti(homeList.get(i).getHwbd_ti());
			homeList.set(i, vo);
		}
    }
    public void paging(){
    	int pageCount = homeSize / rowPerPage + 
				(homeSize%rowPerPage==0 ? 0 : 1);
		page.setPageCount(pageCount);
		page.setCurrentPageIndex(0);
		
		changeTableView(0);
		
		page.currentPageIndexProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, 
							Number oldValue, Number newValue) {
						changeTableView(newValue.intValue());
					}
				}
			);
    }
    public void changeTableView(int index) {
		int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
		int toIndex = Math.min(fromIndex + rowPerPage, homeSize);

		tableSub.setItems(FXCollections.observableArrayList(homeList.subList(fromIndex, toIndex)));
	}
	private void getTableList() {
		homeData = FXCollections.observableArrayList(homeList);
		tableSub.setItems(homeData);
	}
}
