package admin.classroom_manage.video.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ResourceBundle;

import admin.classroom_manage.video.service.IadminVideoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import vo.VideoVO;

public class admin_classVideoViewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane root;

	@FXML
	private TextField vd_id;

	@FXML
	private TextField vd_nm;

	@FXML
	private TextField vd_pltm;

	@FXML
	private TextField vd_flrt;

	@FXML
	private TextField vd_unid;

	@FXML
	private TextField vd_unnm;

	@FXML
	private HBox deletebox;

	@FXML
	private HBox exitbox;

	private VideoVO vvo;

	public void setVvo(VideoVO vvo) {
		this.vvo = vvo;

		if ("un001".equals(vvo.getVd_unId())) {
			vd_unnm.setText("초급자바");
		} else if ("un002".equals(vvo.getVd_unId())) {
			vd_unnm.setText("고급자바");

		} else if ("un003".equals(vvo.getVd_unId())) {
			vd_unnm.setText("jquery");

		} else if ("un004".equals(vvo.getVd_unId())) {
			vd_unnm.setText("jsp");

		} else if ("un005".equals(vvo.getVd_unId())) {
			vd_unnm.setText("데이터베이스");
		}

		vd_id.setText(vvo.getVd_id());
		vd_nm.setText(vvo.getVd_nm());
		vd_pltm.setText(vvo.getVd_pltm() + "");
		vd_unid.setText(vvo.getVd_unId());
		vd_flrt.setText(vvo.getVd_flrt());

	}
	
	void edit(){
		
	}

	void exit() {
		
	}
	
	
	void back() {
		root.getChildren().clear();
      	Parent login =null;
		try {
			login = FXMLLoader.load(getClass().getResource("admin_classVideo.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
      	root.getChildren().setAll(login);
	}
	
	void delete()  {
		ButtonType ans = confirm(null, "삭제하시겠습니까?");
		if (ans == ButtonType.OK) {
			int cnt = 0;
			try {
				cnt = service.deleteVideo(vd_id.getText().trim());
			} catch (RemoteException | SQLException e) {
				e.printStackTrace();
			}
			if(cnt >0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("삭제성공!");
				alert.showAndWait();
				back();
				
			}
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("삭제실패!");
				alert.showAndWait();
			}
		}
	}
	
	private IadminVideoService service;
	@FXML
	void initialize() {
		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_id != null : "fx:id=\"vd_id\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_nm != null : "fx:id=\"vd_nm\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_pltm != null : "fx:id=\"vd_pltm\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_flrt != null : "fx:id=\"vd_flrt\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_unid != null : "fx:id=\"vd_unid\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert vd_unnm != null : "fx:id=\"vd_unnm\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert deletebox != null : "fx:id=\"deletebox\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		assert exitbox != null : "fx:id=\"exitbox\" was not injected: check your FXML file 'admin_VideoView.fxml'.";
		 Registry reg;
	        try {
				reg = LocateRegistry.getRegistry("localhost",3333);
				service = (IadminVideoService) reg.lookup("admin_video");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		exitbox.setOnMouseClicked(e->{
			back();
		});
		
		deletebox.setOnMouseClicked(e->{
			delete();
		});
		
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
