package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import classRoom.board.service.IBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Qna_boardVO;

public class QnaBoardUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private TextField tfTi;

    @FXML
    private TextArea taCon;

    @FXML
    private TextField tfPass;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    private MemberVO mvo;
    
    private Qna_boardVO qvo;
    
    private IBoardService service;
    
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setQvo(Qna_boardVO qvo) {
		this.qvo = qvo;
	}

	@FXML
    void close(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("qnaBoardView.fxml"));
    	Parent root = loader.load();
    	
    	QnABoardViewController viewController = loader.getController();
    	viewController.setMvo(mvo);
    	viewController.setQvo(qvo);
    	viewController.viewBoard();
    	viewController.memBoardChk();
    	Pane qnaPane = (AnchorPane)pane.getParent();
    	qnaPane.getChildren().setAll(root);
    }

    @FXML
    void write(ActionEvent event) throws IOException {
    	if (tfTi.getText().isEmpty()) {
			alert(null, "제목을 입력해 주세요.");
			tfTi.requestFocus();
			return;
    	}
    	if (taCon.getText().isEmpty()) {
    		alert(null, "본문을 입력해 주세요.");
    		tfTi.requestFocus();
    		return;
    	}
    	qvo.setQabd_ti(tfTi.getText());
    	qvo.setQabd_con(taCon.getText());
    	int cnt = 0;
    	try {
			cnt = service.qnaUpdate(qvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt > 0) {
			infoAlert(null, "게시글 수정 성공");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("qnaBoardView.fxml"));
	    	Parent root = loader.load();
	    	QnABoardViewController viewController = loader.getController();
	    	viewController.setMvo(mvo);
	    	viewController.setQvo(qvo);
	    	viewController.viewBoard();
	    	viewController.memBoardChk();
	    	Pane qnaPane = (AnchorPane)pane.getParent();
	    	qnaPane.getChildren().setAll(root);
		}else {
			alert(null, "게시글 수정 실패");
		}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'qnaBoardUp.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        tfPass.setDisable(true);
        
        btnAdd.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
    }
    public void viewBoard() {
		tfTi.setText(qvo.getQabd_ti());
		taCon.setText(qvo.getQabd_con());
	}
    public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public void infoAlert(String header, String msg) {
    	Alert warning = new Alert(AlertType.INFORMATION);
    	warning.setTitle("알림");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public ButtonType confirm(String header, String msg){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	
    	ButtonType comfirmResult = alert.showAndWait().get();
    	return comfirmResult;
    }
}
