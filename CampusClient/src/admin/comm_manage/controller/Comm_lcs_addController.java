package admin.comm_manage.controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import admin.comm_manage.service.IComm_manageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vo.LcsVO;

public class Comm_lcs_addController {

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea taContents;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;
    
    @FXML
    private AnchorPane pane;
    
    private IComm_manageService service;
    
    @FXML
    void showList(ActionEvent event) throws IOException {
    	viewChange();
    }
    
    // 자격증 정보 수정하는 메서드
    @FXML
    void update(ActionEvent event) throws IOException {
    	LcsVO lvo = new LcsVO();
    	String title = tfTitle.getText().trim();
    	String content = taContents.getText().trim();
    	
    	if(title.isEmpty()) {
    		alert(null, "제목을 입력해주세요!");
    		tfTitle.requestFocus();
    		return;
    	}
    	
    	if(content.isEmpty()){
    		alert(null, "내용을 입력해주세요!");
    		taContents.requestFocus();
    		return;
    	}
    	
    	lvo.setLcs_nm(title);
    	lvo.setLcs_con(content);
    	lvo.setLcs_admId("admin");
    	int cnt = service.insertLicense(lvo);
    	
    	if(cnt>0) {
    		alert(null, "게시글이 등록되었습니다.");
    	}else {
    		alert(null,"게시글 등록에 실패했습니다.");
    		return;
    	}
    	
    	viewChange();
    }
    
    // 목록으로 돌아가는 메서드
    void viewChange() throws IOException{
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
    }
    
    // 알림창 메서드
    private void alert(String header, String msg) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    
    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs_add.fxml'.";
        assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_lcs_add.fxml'.";
        assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_lcs_add.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_lcs_add.fxml'.";
        assert btnSave != null : "fx:id=\"btnSae\" was not injected: check your FXML file 'comm_lcs_add.fxml'.";
        
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
        service = (IComm_manageService)reg.lookup("com_manage");
        
        btnExit.setFocusTraversable(false);
        btnSave.setFocusTraversable(false);
    }

}
