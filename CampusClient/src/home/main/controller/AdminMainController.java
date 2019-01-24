package home.main.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.controller.admin_classCommController;
import admin.classroom_manage.main.controller.Admin_classMainController;
import admin.comm_manage.controller.HomeCommunityController;
import admin.mem_manage.controller.MemManageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vo.AdminVO;

public class AdminMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnMem;

    @FXML
    private Button btnClass;

    @FXML
    private Button btnLectInfo;

    @FXML
    private Button btnCommu;
    
    @FXML
    private Button btnLogout;

    @FXML
    private Pane pane;

    private Stage stage;
    
    @FXML
    void classroom(ActionEvent event) throws IOException {
    	stage = (Stage) btnClass.getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/classroom_manage/main/controller/admin_classMain.fxml"));
    	Parent root = loader.load();
    	
    	Admin_classMainController adminClass = loader.getController();
    	adminClass.setAvo(avo);
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    }
    
    private AdminVO avo;
    
    public void setAvo(AdminVO avo) {
    	this.avo = avo;
    }

    @FXML
    void commu(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/comm_manage/controller/homeCommunity.fxml"));
    	Parent root = loader.load();
    	
    	HomeCommunityController commController  = loader.getController();
    	commController.setAvo(avo);
    	pane.getChildren().setAll(root);
    }

    @FXML
    void lectInfo(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	Parent Lecinfo = FXMLLoader.load(getClass().getResource("../../../admin/lectinfo_manage/controller/HomeLecture_info.fxml"));
    	pane.getChildren().setAll(Lecinfo);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "로그아웃 하시겠습니까?");
		if (ans == ButtonType.OK) {
			stage = (Stage) btnLogout.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../login/main/controller/login.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("대덕 사이버 캠퍼스");
			stage.show();
		}
    }

    @FXML
    void ddit(MouseEvent event) {
    	try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI URL = new URI("http://www.ddit.or.kr/");
			desktop.browse(URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void view(MouseEvent event) throws IOException {
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminView.fxml"));
		Parent admView = loader.load();
		
		pane.getChildren().setAll(admView);	
    }
    
    @FXML
    void member(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/mem_manage/controller/memManage.fxml"));
		Parent memManage = loader.load();
		
		MemManageController memCon = loader.getController();
		memCon.viewMain();
		pane.getChildren().setAll(memManage);
    }

    @FXML
    void initialize() {
    	 assert btnLectInfo != null : "fx:id=\"btnLectInfo\" was not injected: check your FXML file 'adminMain.fxml'.";
         assert btnMem != null : "fx:id=\"btnMem\" was not injected: check your FXML file 'adminMain.fxml'.";
         assert btnClass != null : "fx:id=\"btnClass\" was not injected: check your FXML file 'adminMain.fxml'.";
         assert btnCommu != null : "fx:id=\"btnCommu\" was not injected: check your FXML file 'adminMain.fxml'.";
         assert btnLogout != null : "fx:id=\"btnlogout\" was not injected: check your FXML file 'adminMain.fxml'.";
         assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'adminMain.fxml'.";

         btnClass.setFocusTraversable(false);
         btnCommu.setFocusTraversable(false);
         btnLectInfo.setFocusTraversable(false);
         btnLogout.setFocusTraversable(false);
         btnMem.setFocusTraversable(false);
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
