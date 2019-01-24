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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Post_boardVO;

public class LecPostBoardAddController {

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
    private Button btnAdd;

    @FXML
    private Button btnCancel;
    
    private String[] subList = {"초급자바", "Oracle", "고급자바", "HTML", "JSP"};
    
    private ObservableList<String> subData;

	private Node node;
	
	private MemberVO memvo;
	
	private IBoardService service;

    public void setNode(Node node) {
    	this.node = node;
	}

	public void setMemvo(MemberVO memvo) {
		this.memvo = memvo;
	}
    
    @FXML
    void close(ActionEvent event) throws IOException {
    	//pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoard.fxml"));
    	Parent root = loader.load();
    	
    	LecPostBoardController postController = loader.getController();
    	postController.setMemvo(memvo);
    	
    	AnchorPane boardPane = (AnchorPane) pane.getParent();
    	pane.getChildren().setAll(root);
    }

    @FXML
    void write(ActionEvent event) throws IOException {
    	if(tfTi.getText().isEmpty()) {
    		alert(null, "제목을 입력해주세요.");
    		tfTi.requestFocus();
    		return;
    	}
    	if(taCon.getText().isEmpty()) {
    		alert(null, "내용을 입력해주세요.");
    		taCon.requestFocus();
    		return;
    	}
    	Post_boardVO vo = new Post_boardVO();
    	vo.setPopbd_ti(tfTi.getText());
    	vo.setPopbd_con(taCon.getText());
    	vo.setPopbd_sub(comSub.getSelectionModel().getSelectedItem());
    	vo.setPopbd_wrr(memvo.getMem_nm());
    	vo.setPopbd_memId(memvo.getMem_id());
    	int cnt = 0;
    	try {
			cnt = service.inPostBoard(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if(cnt == 1) {
    		infoAlert(null, "게시글이 등록되었습니다.");
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoard.fxml"));
    		Parent root = loader.load();
    		
    		LecPostBoardController postController = loader.getController();
    		postController.setMemvo(memvo);
    		
    		AnchorPane boardPane = (AnchorPane) pane.getParent();
    		boardPane.getChildren().setAll(root);
    	}else {
    		alert(null, "게시글 등록에 실패했습니다.");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'lecPostBoardAdd.fxml'.";

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
        comSub.setValue(subData.get(0));
        
        btnAdd.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
    }
    
    public void wrrWrite() {
    	tfTi.setText(memvo.getMem_nm());
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
