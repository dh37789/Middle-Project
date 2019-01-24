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
import classRoom.board.controller.LecPostBoardViewController;
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
import javafx.stage.Stage;
import vo.MemberVO;
import vo.Post_boardVO;

public class AdmLecPostBoardController {

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
    private TableView<Post_boardVO> tableSub;

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

    @FXML
    private Pagination page;

    private String[] subList = {"전체", "초급자바", "Oracle", "고급자바", "HTML", "JSP"};
    
    private String[] searchCol = {"제목", "내용", "작성자"};
    
    private ObservableList<String> subData;
    
    private ObservableList<String> searchData;
    
	private IadminClassCommSerivce service;
    
    private List<Post_boardVO> postList;
    
    private ObservableList<Post_boardVO> postData;
    
    private Stage stage;
    
    private MemberVO memvo;
    
    private int postSize;
    
    private int rowPerPage = 15;
    
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
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
         Parent root = loader.load();
         Pane aPane = (Pane) pane.getParent();
         aPane.getChildren().setAll(root);
    }


    @FXML
    void search(ActionEvent event) {
    	Map<String, String> searchMap = new HashMap<>();
    	String sub = comSub.getSelectionModel().getSelectedItem();
    	if(sub.equals("전체")) {
    		sub = "%%";
    		searchMap.put("sub", sub);
    	}else { 
    		sub = "%" + sub + "%";
    		searchMap.put("sub", sub);
    	}
    	String col = comSearch.getSelectionModel().getSelectedItem();
    	String colStr = "";
    	String SearchStr = "%" + tfSearch.getText() + "%";
    	if(col.equals("제목")) {
    		colStr = "popbd_ti";
    	}else if(col.equals("내용")) {
    		colStr = "popbd_con";
    	}else if(col.equals("작성자")) {
    		colStr = "popbd_wrr";
    	}
    	
    	searchMap.put("colStr", colStr);
    	searchMap.put("SearchStr", SearchStr);
    	
    	try {
			postList = service.searchBoard(searchMap);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	postSize = postList.size();
    	indexNo();
    	getTableList();
    	paging();
    }

    @FXML
    void subView(ActionEvent event) throws RemoteException {
    	String sub = comSub.getSelectionModel().getSelectedItem();
    	if(sub.equals("전체")) {
    		postList = service.getBoardList();
    	}else {
    		postList = service.subBoard(sub);
    	}
//    	indexNo();
    	postSize = postList.size();
    	getTableList();
    	paging();
    }


    @FXML
    void viewBoard(MouseEvent event) throws IOException {
    	if(event.getClickCount() == 2 && (! tableSub.getSelectionModel().isEmpty())) {
        	Post_boardVO vo = tableSub.getSelectionModel().getSelectedItem();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("admLecPostBoardView.fxml"));
        	Parent root = loader.load();
        	
        	AdmLecPostBoardViewController viewController = loader.getController();
        	viewController.setVo(vo);
        	viewController.viewBoard();
        	viewController.viewReply();
        	AnchorPane boardPane = (AnchorPane)pane.getParent();
        	boardPane.getChildren().setAll(root);
        	
        	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert btnNotice != null : "fx:id=\"btnNotice\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert btnSubject != null : "fx:id=\"btnSubject\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert btnQna != null : "fx:id=\"btnQna\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert brnPost != null : "fx:id=\"brnPost\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert comSearch != null : "fx:id=\"comSearch\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert tableSub != null : "fx:id=\"tableSub\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert colSubNM != null : "fx:id=\"colSubNM\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert colNo != null : "fx:id=\"colNo\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert colTi != null : "fx:id=\"colTi\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert colWrr != null : "fx:id=\"colWrr\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'admLecPostBoard.fxml'.";
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
			postList = service.getBoardList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		btnNotice.setFocusTraversable(false);
		btnQna.setFocusTraversable(false);
		btnSearch.setFocusTraversable(false);
		btnSubject.setFocusTraversable(false);
        
//        indexNo();
        getTableList();
        
        colSubNM.setCellValueFactory(
        		new PropertyValueFactory<>("popbd_sub"));
        colNo.setCellValueFactory(
        		new PropertyValueFactory<>("popbd_id"));
        colTi.setCellValueFactory(
        		new PropertyValueFactory<>("popbd_ti"));
        colWrr.setCellValueFactory(
        		new PropertyValueFactory<>("popbd_wrr"));
        colDate.setCellValueFactory(
        		new PropertyValueFactory<>("popbd_dt"));
        
        postSize = postList.size();
        
        paging();
        
        subData = FXCollections.observableArrayList(subList);
        searchData = FXCollections.observableArrayList(searchCol);
        		
        comSearch.setItems(searchData);
        comSearch.setValue(searchData.get(0));
        
        comSub.setItems(subData);
        comSub.setValue(subData.get(0));
        
        tableSub.setFocusTraversable(false);
    }
    public void indexNo() {
    	for(int i = 0; i < postList.size(); i++) {
    		Post_boardVO vo = new Post_boardVO();
    		vo.setPopbd_id(postList.get(i).getPopbd_id().substring(2));
    		vo.setPopbd_con(postList.get(i).getPopbd_con());
    		vo.setPopbd_dt(postList.get(i).getPopbd_dt());
    		vo.setPopbd_memId(postList.get(i).getPopbd_memId());
    		vo.setPopbd_sub(postList.get(i).getPopbd_sub());
    		vo.setPopbd_wrr(postList.get(i).getPopbd_wrr());
    		vo.setPopbd_ti(postList.get(i).getPopbd_ti());
    		postList.set(i, vo);
    	}
    }
    private void paging() {
    	int pageCount = postSize / rowPerPage +
    					(postSize%rowPerPage==0 ? 0 : 1);
    	page.setPageCount(pageCount);
    	page.setCurrentPageIndex(0);
    	
    	changeTableView(0);
    	
    	page.currentPageIndexProperty().addListener(
    			new ChangeListener<Number>() {
    				
    				@Override
    				public void changed(ObservableValue<? extends Number> observable,
    					Number oldValue, Number newValue){
    				changeTableView(newValue.intValue());
    			}
    		}
    	);
    }
    public void changeTableView(int index) {
    	int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
    	int toIndex = Math.min(fromIndex + rowPerPage, postSize);
    	
    	tableSub.setItems(FXCollections.observableArrayList(postList.subList(fromIndex, toIndex)));
	}
	private void getTableList() {
		postData = FXCollections.observableArrayList(postList);
		tableSub.setItems(postData);
	}
}
