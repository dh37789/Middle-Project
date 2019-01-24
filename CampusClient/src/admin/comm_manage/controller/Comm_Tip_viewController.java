package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.comm_manage.service.IComm_manageService;
import home.community.controller.FreeBoardController;
import home.community.service.ICommunityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import vo.AdminVO;
import vo.Tip_boardVO;

public class Comm_Tip_viewController {
	private ICommunityService service;
	private Tip_boardVO tvo;

	public void setTvo(Tip_boardVO tvo) {
		this.tvo = tvo;
	}

	@FXML
	private AnchorPane pane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField tfTitle;

	@FXML
	private TextField tfDate;

	@FXML
	private TextArea taContents;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnDel;

	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	// 자격증 Tip 삭제
	@FXML
	void delete(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "삭제 하시겠습니까?");
		if (ans == ButtonType.OK) {
			String tbd_id = tvo.getTbd_id();
			try {
				int a = service.deleteTip(tbd_id);
				if (a > 0) {
					alert(null, "게시글 삭제 성공");
				} else {
					alert(null, "게시글 삭제 실패");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			viewChange();
		}
	}

	// 나가기
	@FXML
	void exit(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
	}


	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";
		assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";
		assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";
		assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";
		assert btnDel != null : "fx:id=\"btnDel\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_Tip_view.fxml'.";

		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");
		
		btnDel.setFocusTraversable(false);
		btnExit.setFocusTraversable(false);
		
		tfDate.setMouseTransparent(true);
		tfTitle.setMouseTransparent(true);
		taContents.setMouseTransparent(true);
	}

	// 목록으로 돌아가는 메서드
	void viewChange() throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip.fxml"));
		Parent root = loader.load();

		pane.getChildren().setAll(root);
	}

	// 알림창
	private void alert(String header, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// 목록 클릭하면 내용 보이는 메서드
	public void viewBoard() {
		tfTitle.setText(tvo.getTbd_ti());
		tfDate.setText(tvo.getTbd_dt());
		taContents.setText(tvo.getTbd_con());

	}

	public void infoAlert(String header, String msg) {
		Alert warning = new Alert(AlertType.INFORMATION);
		warning.setTitle("알림");
		warning.setHeaderText(header);
		warning.setContentText(msg);

		warning.showAndWait();
	}

	public ButtonType confirm(String header, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);

		ButtonType comfirmResult = alert.showAndWait().get();
		return comfirmResult;
	}
}
