package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Qna_boardVO;

public class QnABoardPassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane pane;

    @FXML
    private PasswordField tfPass;

    @FXML
    private Label lbChk;

    @FXML
    private Button btnIn;

    @FXML
    private Button btnCancel;

    private MemberVO mvo;
    
    private Qna_boardVO qvo;
    
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setQvo(Qna_boardVO qvo) {
		this.qvo = qvo;
	}

	@FXML
    void cancel(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
    	Parent homework = loader.load();
    	
    	QnABoardController subCon = loader.getController();
    	subCon.setMvo(mvo);
    	
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(homework);
    }

	@FXML
    void passReset(MouseEvent event) {
		tfPass.clear();
		lbChk.setText("");
    }
	
    @FXML
    void input(ActionEvent event) throws IOException {
    	String pass = tfPass.getText();
    	if (pass.equals(qvo.getQabd_ps())|| mvo ==null) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("qnaBoardView.fxml"));
	    	Parent root = loader.load();
	    	
	    	QnABoardViewController viewController = loader.getController();
	    	viewController.setMvo(mvo);
	    	viewController.setQvo(qvo);
	    	viewController.viewBoard();
	    	viewController.memBoardChk();
	    	viewController.viewReply();
	    	Pane qnaPane = (AnchorPane)pane.getParent();
	    	qnaPane.getChildren().setAll(root);
		}else {
			lbChk.setText("비밀번호가 올바르지 않습니다.");
			lbChk.setStyle("-fx-text-fill : RED");
		}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'qnaBoardPass.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'qnaBoardPass.fxml'.";
        assert lbChk != null : "fx:id=\"lbChk\" was not injected: check your FXML file 'qnaBoardPass.fxml'.";
        assert btnIn != null : "fx:id=\"btnIn\" was not injected: check your FXML file 'qnaBoardPass.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'qnaBoardPass.fxml'.";

        btnCancel.setFocusTraversable(false);
        btnIn.setFocusTraversable(false);
    }
}
