package home.main.controller;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import classRoom.main.controller.ClassroomController;
import home.attend.controller.HomeAttendController;
import home.community.controller.FreeBoardController;
import home.community.controller.HomeCommunityController;
import home.mem_info.controller.HomeMem_infoController;
import home.mem_info.controller.Mem_infoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import vo.MemberVO;

public class HomeMainController {
	@FXML
	private ImageView ddit;

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
	private String str;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane border;

	@FXML
	private Button btnMemInfo;

	@FXML
	private Button btnLectInfo;

	@FXML
	private Button btnClass;

	@FXML
	private Button btnInquire;
    
    @FXML
    private Button btnLogout;

    @FXML
    private Label lbName;
    
	@FXML
	private Button btnCoupon;

	@FXML
	private Button btnCommu;

	@FXML
	private Pane pane;

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	private Stage stage;

	@FXML
	void commu(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../community/controller/homeCommunity.fxml"));
		Parent root = loader.load();

		HomeCommunityController commController = loader.getController();
		commController.setMvo(mvo);
		commController.love(mvo);
		pane.getChildren().setAll(root);
		
		
	}

	@FXML
    void goMain(MouseEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
		Parent root = loader.load();

		MainViewController viewController = loader.getController();
		viewController.setMvo(mvo);
		pane.getChildren().setAll(root);
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
	void coupon(ActionEvent event) throws IOException {
		pane.getChildren().clear();
//    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../attend/controller/homeAttend.fxml"));
//    	HomeMainController mainController = loader.getController();
//    	mainController.setMvo(mvo);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../attend/controller/homeAttend.fxml"));
		Parent Lecinfo = loader.load();
		HomeAttendController attendController = loader.getController();
		attendController.setMemvo(mvo);
		pane.getChildren().setAll(Lecinfo);
	}
	

	@FXML
	void inquire(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		Parent inquire = FXMLLoader.load(getClass().getResource("../../inquire/controller/homeInquire.fxml"));
		pane.getChildren().setAll(inquire);
	}

	@FXML
	void lectInfo(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		Parent Lecinfo = FXMLLoader.load(getClass().getResource("../../lecture_info/controller/HomeLecture_info.fxml"));
		pane.getChildren().setAll(Lecinfo);
	}

	@FXML
	void memInfo(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../mem_info/controller/homeMem_info.fxml"));
		Parent info = loader.load();

		HomeMem_infoController infoHomeCon = loader.getController();
		infoHomeCon.setMvo(mvo);
		infoHomeCon.viewPage();
		pane.getChildren().setAll(info);
	}

	@FXML
	void myClass(ActionEvent event) throws IOException {
		stage = (Stage) pane.getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../../../classRoom/main/controller/classroomMain.fxml"));
		Parent classRoot = loader.load();

		ClassroomController classCon = loader.getController();
		classCon.setMvo(mvo);

		Scene scene = new Scene(classRoot);
		stage.setTitle("나의 강의실");
		stage.setScene(scene);
	}

	@FXML
	void initialize() {
		assert border != null : "fx:id=\"border\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert lbName != null : "fx:id=\"lbName\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnMemInfo != null : "fx:id=\"btnMemInfo\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnLectInfo != null : "fx:id=\"btnLectInfo\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnClass != null : "fx:id=\"btnClass\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnInquire != null : "fx:id=\"btnInquire\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnCoupon != null : "fx:id=\"btnCoupon\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnCommu != null : "fx:id=\"btnCommu\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert pane != null : "fx:id=\"btnpane\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert ddit != null : "fx:id=\"ddit\" was not injected: check your FXML file 'homeMain.fxml'.";
		assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'homeMain.fxml'.";
		
		final Font font = Font.loadFont(getClass().getResourceAsStream("THEjunggt140.otf"), 16);
		btnMemInfo.setFont(font);
		
		btnClass.setFocusTraversable(false);
		btnCommu.setFocusTraversable(false);
		btnCoupon.setFocusTraversable(false);
		btnInquire.setFocusTraversable(false);
		btnLectInfo.setFocusTraversable(false);
		btnLogout.setFocusTraversable(false);
		btnMemInfo.setFocusTraversable(false);
    }
	
	public void lookName() {
		lbName.setText(mvo.getMem_nm() + "님 환영합니다!!");
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
