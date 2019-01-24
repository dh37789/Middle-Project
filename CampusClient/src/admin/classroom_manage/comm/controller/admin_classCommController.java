package admin.classroom_manage.comm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classRoom.board.controller.Classroom_boardController;
import classRoom.board.controller.LecPostBoardController;
import classRoom.board.controller.QnABoardController;
import classRoom.board.controller.SubBoardController;
import classRoom.board.controller.classroom_boardViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import vo.AdminVO;

public class admin_classCommController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button noticeBoard;

    @FXML
    private Button subBoaed;

    @FXML
    private Button qnaBoard;

    @FXML
    private Button postBoard;

    @FXML
    private Pane conPane;

    
    private AdminVO avo;
    
    
    
    public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	@FXML
    void notice(ActionEvent event) throws IOException {
		conPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_Board.fxml"));
        Parent root = loader.load();
        
        Classroom_boardController  cb= loader.getController();
        cb.setMvo(avo);
        
        conPane.getChildren().setAll(root);

    }

    @FXML
    void post(ActionEvent event) throws IOException {
    	conPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admLecPostBoard.fxml"));
        Parent root = loader.load();
        
        LecPostBoardController lcp = loader.getController();
        lcp.setAvo(avo);
        conPane.getChildren().setAll(root);
    }

    @FXML
    void qna(ActionEvent event) throws IOException {
    	conPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admQnaBoard.fxml"));
        Parent root = loader.load();
        conPane.getChildren().setAll(root);
    	
    	QnABoardController commController  = loader.getController();
    	commController.setAvo(avo);
    	pane.getChildren().setAll(root);
    }

    @FXML
    void subject(ActionEvent event) {
    	conPane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../classRoom/board/controller/subBoard.fxml"));
    	
    	Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	SubBoardController commController  = loader.getController();
    	commController.setAvo(avo);
    	conPane.getChildren().setAll(root);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admin_classComm.fxml'.";
        assert noticeBoard != null : "fx:id=\"noticeBoard\" was not injected: check your FXML file 'admin_classComm.fxml'.";
        assert subBoaed != null : "fx:id=\"subBoaed\" was not injected: check your FXML file 'admin_classComm.fxml'.";
        assert qnaBoard != null : "fx:id=\"qnaBoard\" was not injected: check your FXML file 'admin_classComm.fxml'.";
        assert postBoard != null : "fx:id=\"postBoard\" was not injected: check your FXML file 'admin_classComm.fxml'.";
        assert conPane != null : "fx:id=\"conPane\" was not injected: check your FXML file 'admin_classComm.fxml'.";

        noticeBoard.setFocusTraversable(false);
        qnaBoard.setFocusTraversable(false);
        subBoaed.setFocusTraversable(false);
        postBoard.setFocusTraversable(false);
    }
}
