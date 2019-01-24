package admin.classroom_manage.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vo.AdminVO;

public class Admin_classMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField tfSearch;

    @FXML
    private TreeView<?> tree;

    @FXML
    private Button btn_video;

    @FXML
    private Button btn_homework;

    @FXML
    private Button btn_comm;

    @FXML
    private HBox exitbox;

    @FXML
    private AnchorPane conPane;
    
    private AdminVO avo;
    
    public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	@FXML
    private Button btnCancel;

    private Stage stage;
    
    @FXML
    void btn_comm_click(ActionEvent event) throws IOException {
       conPane.getChildren().clear();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../../comm/controller/admClassroom_Board.fxml"));
       Parent root = loader.load();
       
       conPane.getChildren().setAll(root);
    }

    @FXML
    void btn_homework_click(ActionEvent event) throws IOException {
       conPane.getChildren().clear();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../../question/controller/quizManList.fxml"));
       Parent root = loader.load();
       
       conPane.getChildren().setAll(root);

    }

    @FXML
    void btn_video_click(ActionEvent event) throws IOException {
       conPane.getChildren().clear();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../../video/controller/admin_classVideo.fxml"));
       Parent root = loader.load();
       
       conPane.getChildren().setAll(root);
    }


    @FXML
    void cancel(ActionEvent event) throws IOException {
       stage = (Stage) btn_comm.getScene().getWindow();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../home/main/controller/adminMain.fxml"));
       Parent root = loader.load();
       
       Scene scene = new Scene(root);
       stage.setScene(scene);
    }    

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert conPane != null : "fx:id=\"conPane\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert tree != null : "fx:id=\"tree\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert btn_video != null : "fx:id=\"btn_video\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert btn_homework != null : "fx:id=\"btn_homework\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert btn_comm != null : "fx:id=\"btn_comm\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert exitbox != null : "fx:id=\"exitbox\" was not injected: check your FXML file 'admin_classMain.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'admin_classMain.fxml'.";

        btn_comm.setFocusTraversable(false);
        btn_homework.setFocusTraversable(false);
        btn_video.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
    }
}