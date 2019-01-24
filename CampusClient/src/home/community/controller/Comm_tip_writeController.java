package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import home.community.service.ICommunityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import vo.MemberVO;
import vo.Tip_boardVO;

public class Comm_tip_writeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea taContents;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    @FXML
    private AnchorPane pane;
    
    
    private ICommunityService service;

    @FXML
    void cancel(ActionEvent event) throws IOException {
    	viewChange();
    }
    
    private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
    
    // 게시글 저장 (작성자명, 아이디 따로 설정)
    @FXML
    void save(ActionEvent event) throws IOException {
    	Tip_boardVO tvo = new Tip_boardVO();
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
    	
    	tvo.setTbd_ti(title);
    	tvo.setTbd_con(content);
    	tvo.setTbd_wrr(mvo.getMem_nm());
    	tvo.setTbd_memID(mvo.getMem_id());
    	
    	int cnt = service.insertTip(tvo);
    	
    	if(cnt>0) {
    		alert(null, "게시글이 등록되었습니다.");
    	}else {
    		alert(null, "게시글 등록에 실패했습니다.");
    		return;
    	}
    	
    	viewChange();
    	
    }
    
    // 알림창 메서드
    private void alert(String header, String msg) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    
    // 목록으로 이동
    void viewChange() throws IOException{
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
    }

    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
        assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
        
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
        service = (ICommunityService)reg.lookup("community");
        
        btnCancel.setFocusTraversable(false);
        btnSave.setFocusTraversable(false);
    }
}
