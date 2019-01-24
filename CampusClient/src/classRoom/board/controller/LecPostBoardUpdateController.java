package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import classRoom.board.service.IBoardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Post_boardVO;

public class LecPostBoardUpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private TextField tfTi;

    @FXML
    private ComboBox<String> comSub;

    @FXML
    private TextArea taCon;

    @FXML
    private Button btnUp;

    @FXML
    private Button btnCancel;

    private Post_boardVO vo;
    
    private MemberVO memvo;
    
    private String[] subList = {"초급자바", "Oracle", "고급자바", "HTML", "JSP"};
    
    private ObservableList<String> subData;
    
    private IBoardService service;
    
    public void setVo(Post_boardVO vo) {
    	this.vo = vo;
    }
    public void setMemVo(MemberVO memvo) {
    	this.memvo = memvo;
    }
    
    @FXML
    void close(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoard.fxml"));
    	Parent root = loader.load();
    	
    	LecPostBoardController postController = loader.getController();
    	postController.setMemvo(memvo);
    	
    	AnchorPane boardPane = (AnchorPane) pane.getParent();
    	boardPane.getChildren().setAll(root);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	if(tfTi.getText().isEmpty()) {
    		alert(null, "제목을 입력해주세요.");
    		tfTi.requestFocus();
    		return;
    	}
    	if(taCon.getText().isEmpty()) {
    		alert(null, "내용을 입력해주세요");
    		taCon.requestFocus();
    		return;
    	}
    	Post_boardVO vo = new Post_boardVO();
    	vo.setPopbd_id(this.vo.getPopbd_id());
    	vo.setPopbd_ti(tfTi.getText());
    	vo.setPopbd_con(taCon.getText());
    	vo.setPopbd_sub(comSub.getSelectionModel().getSelectedItem());
    	vo.setPopbd_wrr(memvo.getMem_nm());
    	vo.setPopbd_memId(memvo.getMem_id());
    	
    	int cnt = service.updateBoard(vo);
    	if(cnt > 0) {
    		infoAlert(null, "게시글이 수정되었습니다.");
    		pane.getChildren().clear();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoardView.fxml"));
    		Parent root = loader.load();
    		
    		LecPostBoardViewController viewController = loader.getController();
    		viewController.setMemvo(memvo);
    		viewController.setVo(vo);
    		viewController.viewBoard();
    		viewController.memBoardChk();
    		
    		AnchorPane boardPane = (AnchorPane) pane.getParent();
	    	boardPane.getChildren().setAll(root);
    	}else {
    		alert(null, "게시글 수정에 실패했습니다.");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";
        assert btnUp != null : "fx:id=\"btnUp\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'lecPostBoardUpdate.fxml'.";

        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        subData = FXCollections.observableArrayList(subList);
        comSub.setItems(subData);
        
        btnCancel.setFocusTraversable(false);
        btnUp.setFocusTraversable(false);
    }
    public void viewBoard() {
    	tfTi.setText(vo.getPopbd_ti());
    	taCon.setText(vo.getPopbd_con());
    	comSub.setValue(vo.getPopbd_sub());
    }
    
    public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public void infoAlert(String header, String msg) {
    	Alert info = new Alert(AlertType.INFORMATION);
    	info.setTitle("알림");
    	info.setHeaderText(header);
    	info.setContentText(msg);
    	
    	info.showAndWait();
    }
}
