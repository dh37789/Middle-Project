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
import vo.AdminVO;
import vo.LcsVO;

public class comm_lcs_upController {
	private IComm_manageService service;
	private LcsVO lvo;
	
	public void setLvo(LcsVO lvo) {
		this.lvo = lvo;
	}
	
	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfDate;

    @FXML
    private TextArea taContents;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    void exit(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
    	Parent root = loader.load();
    	
    	Comm_TipController tipController = loader.getController();
    	tipController.setAvo(avo);
    	pane.getChildren().setAll(root);
    }

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
    	
    	int cnt = ((IComm_manageService) service).updateLicense(lvo);
    	if(cnt>0) {
    		alert(null,"게시글이 수정되었습니다.");
    	}else {
    		alert(null,"게시글 수정을 실패하였습니다.");
    	}
    	viewChange();
    }

    @FXML
    void initialize() throws AccessException, RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'comm_lcs_up.fxml'.";
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IComm_manageService) reg.lookup("com_manage");
    }
    
    public void setTable(LcsVO lvo) throws RemoteException {
		tfTitle.setText(lvo.getLcs_nm());
		tfDate.setText(lvo.getLcs_tdt());
		taContents.setText(lvo.getLcs_con());

	}
    
    void viewChange() throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
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

}
