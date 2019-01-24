package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.comm_manage.service.IComm_manageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import vo.AdminVO;
import vo.LcsVO;
/**
 * 관리자-자격증 정보관리-보기
 * @author PC03
 *
 */
public class Comm_lcs_viewController {
	
	private AdminVO avo;
    public void setAvo(AdminVO avo) {
    	this.avo = avo;
    }

	private IComm_manageService service;
	
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
    private Button btnUpdate;

    @FXML
    private Button btnDel;
    
    private LcsVO lvo;
    
    public void setLcvo(LcsVO lvo) {
    	this.lvo = lvo;
    }
    
    // 자격증 정보 삭제
    @FXML
    void delete(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "삭제 하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		String lcs_id = lvo.getLcs_id();
    		try {
    			int a = service.deleteLicense(lcs_id);
    			alert(null, "게시글이 삭제되었습니다.");
    		} catch (RemoteException e) {
    			alert(null, "삭제 실패하였습니다");
    			e.printStackTrace();
    		}
    		
    		viewChange();
		}
    }
    
    @FXML
    void exit(ActionEvent event) throws IOException {
    	viewChange();
    }
    
    // 수정 가능하게 하는 메서드
    @FXML
    void update(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "수정 하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		pane.getChildren().clear();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs_up.fxml"));
    		Parent root = loader.load();
    		comm_lcs_upController upController = loader.getController();
    		upController.setAvo(avo);
    		upController.setLvo(lvo);
    		upController.setTable(lvo);
    		pane.getChildren().setAll(root);
    	}
    }
    
    // 수정후 저장하는 메서드
    @FXML
    void save(ActionEvent event) throws IOException {
    	if(tfTitle.getText().isEmpty()) {
    		alert(null,"제목이 비어있습니다.");
    		tfTitle.requestFocus();
    		return;
    	}
    	if(taContents.getText().isEmpty()) {
    		alert(null,"본문을 입력하세요.");
    		taContents.requestFocus();
    		return;
    	}
    	lvo.setLcs_nm(tfTitle.getText());
    	lvo.setLcs_con(taContents.getText());
    	
    	int cnt = service.updateLicense(lvo);
    	if(cnt>0) {
    		alert(null,"게시글이 수정되었습니다.");
    	}else {
    		alert(null,"게시글 수정을 실패하였습니다.");
    	}
    	viewChange();
    }

    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert btnDel != null : "fx:id=\"btnDel\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs_view2.fxml'.";
        
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
        service = (IComm_manageService)reg.lookup("com_manage");
        
        btnDel.setFocusTraversable(false);
        btnExit.setFocusTraversable(false);
//        btnSave.setFocusTraversable(false);
        btnUpdate.setFocusTraversable(false);
        
        tfDate.setMouseTransparent(true);
        tfTitle.setMouseTransparent(true);
        taContents.setMouseTransparent(true);
    }
    
    // 목록으로 돌아가는 메서드
    void viewChange() throws IOException{
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
    }
    
    // 목록 클릭하면 내용 보이는 메서드
    public void viewBoard() {
    	tfTitle.setText(lvo.getLcs_nm());
    	tfDate.setText(lvo.getLcs_tdt());
    	taContents.setText(lvo.getLcs_con());
    	
    }
    
    // 알림창 
    private void alert(String header, String msg) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	alert.showAndWait();
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
