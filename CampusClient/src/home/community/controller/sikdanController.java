package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class sikdanController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnleft;

    @FXML
    private Button btnright;
    
    @FXML
    private AnchorPane pane;

    @FXML
    private Button btn_exit;

    @FXML
    void exit(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_sikdan.fxml"));
    	Parent root = loader.load();
    	
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.setTitle("대전도시락 식단");
    	stage.close();
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'manager_sikdan.fxml'.";
        assert btn_exit != null : "fx:id=\"btn_exit\" was not injected: check your FXML file 'manager_sikdan.fxml'.";

        btn_exit.setFocusTraversable(false);
    }
}
