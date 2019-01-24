package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.reactfx.collection.SuspendableList;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vo.AdminVO;
import vo.MemberVO;
import vo.Post_boardVO;

public class LecPostBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

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
    private Button btnWrite;

    private AdminVO avo;
    
    
    public void setAvo(AdminVO avo) {
		this.avo = avo;
		if(avo==null) {
			btnWrite.setVisible(false);
		}
	}
	@FXML
    private Pagination page;

    private String[] subList = {"전체", "초급자바", "Oracle", "고급자바", "HTML", "JSP"};
   
    private String[] searchCol = {"제목", "내용", "작성자"};
    
    private ObservableList<String> subData;
    
    private ObservableList<String> searchData;
    
    private IBoardService service;
    
    private List<Post_boardVO> postList;
    
    private ObservableList<Post_boardVO> postData;
    
    private Stage stage;
    
    private MemberVO memvo;
    
    private int postSize;
    
    private int rowPerPage = 15;
    
    public void setMemvo(MemberVO memvo) {
    	this.memvo = memvo;
    	if(memvo!=null) {
    		btnWrite.setVisible(true);
    	}
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
    void subView(ActionEvent event) throws RemoteException{
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
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoardView.fxml"));
    	Parent root = loader.load();
    	
    	LecPostBoardViewController viewController = loader.getController();
    	viewController.setNode(root);
    	viewController.setMemvo(memvo);
    	viewController.setVo(vo);
    	viewController.viewBoard();
    	viewController.memBoardChk();
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(root);
    	viewController.viewReply();
    	
    	}
    }

    @FXML
    void write(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoardAdd.fxml"));
    	Parent root = loader.load();
    	
    	LecPostBoardAddController addController = loader.getController();
    	addController.setNode(root);
    	addController.setMemvo(memvo);
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(root);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert comSearch != null : "fx:id=\"comSearch\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert tableSub != null : "fx:id=\"tableSub\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert colSubNM != null : "fx:id=\"colSubNM\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert colNo != null : "fx:id=\"colNo\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert colTi != null : "fx:id=\"colTi\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert colWrr != null : "fx:id=\"colWrr\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert btnWrite != null : "fx:id=\"btnWrite\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'lecPostBoard.fxml'.";
        btnWrite.setVisible(false);
        tableSub.setFocusTraversable(false);
        
        Registry reg;
        try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
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
        
        btnSearch.setFocusTraversable(false);
        btnWrite.setFocusTraversable(false);
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
