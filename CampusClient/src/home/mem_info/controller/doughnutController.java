package home.mem_info.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class doughnutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfDon;

    private Stage stage;
    
    public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
    void CouponNum(ActionEvent event) {
    }

    
    @FXML
    void initialize() {
        assert tfDon != null : "fx:id=\"tfDon\" was not injected: check your FXML file 'doughnut.fxml'.";
        tfDon.setEditable(false);
        tfDon.setFocusTraversable(false);
        tfDon.setMouseTransparent(true);
    }
    public void Coupon(){
    	String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거 
		uuid = uuid.substring(0, 8); //uuid를 앞에서부터 8자리 잘라줌.
		tfDon.setText(uuid);
    }
    public void stageResize(){
    	stage.setResizable(false);
    }
}
