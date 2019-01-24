package admin.mem_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MemManageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane paneA;

    @FXML
    private Button btnApply;

    @FXML
    private Button memInfo;

    @FXML
    private Button btnBlack;

    @FXML
    private Pane pane;
    
    @FXML
    void apply(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memApply.fxml"));
		Parent memApply = loader.load();
		
		MemApplyController applyCon = loader.getController();
		
		pane.getChildren().setAll(memApply);
    }

    @FXML
    void black(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memBlack.fxml"));
		Parent memBlack = loader.load();
		
		MemBlackController BlackCon = loader.getController();
		BlackCon.getBlackList();
		pane.getChildren().setAll(memBlack);
    }

    @FXML
    void info(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfoList.fxml"));
		Parent memBlack = loader.load();
		
		MemInfoListController infoListCon = loader.getController();
		infoListCon.getAllMember();
		pane.getChildren().setAll(memBlack);
    }

    @FXML
    void initialize() {
        assert paneA != null : "fx:id=\"paneA\" was not injected: check your FXML file 'memManage.fxml'.";
        assert btnApply != null : "fx:id=\"btnApply\" was not injected: check your FXML file 'memManage.fxml'.";
        assert memInfo != null : "fx:id=\"memInfo\" was not injected: check your FXML file 'memManage.fxml'.";
        assert btnBlack != null : "fx:id=\"btnBlack\" was not injected: check your FXML file 'memManage.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memManage.fxml'.";

        btnApply.setFocusTraversable(false);
        btnBlack.setFocusTraversable(false);
        memInfo.setFocusTraversable(false);
    }
    public void viewMain() throws IOException{
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memApply.fxml"));
		Parent memApply = loader.load();
		
		MemApplyController applyCon = loader.getController();
		applyCon.getList();
		
		pane.getChildren().setAll(memApply);
    } 
}
