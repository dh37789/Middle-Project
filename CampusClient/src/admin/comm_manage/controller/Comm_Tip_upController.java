package admin.comm_manage.controller;

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
import vo.AdminVO;
import vo.Free_boardVO;
import vo.Tip_boardVO;

public class Comm_Tip_upController {
	
	private ICommunityService service;
	private Tip_boardVO tvo;

	public void setTvo(Tip_boardVO tvo) {
		this.tvo = tvo;
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip.fxml"));
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
    	tvo.setTbd_ti(tfTitle.getText());
    	tvo.setTbd_con(taContents.getText());
    	
    	int cnt = service.updateTip(tvo);
    	
    	if(cnt>0) {
    		alert(null, "게시글이 수정되었습니다.");
    	}else {
    		alert(null, "게시글 수정을 실패했습니다.");
    	}
    	viewChange();
    }

    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'comm_tip_up.fxml'.";
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");
    }
    void viewChange() throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip.fxml"));
		Parent root = loader.load();

		pane.getChildren().setAll(root);
	}
    
	private void alert(String header, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public void setTable(Tip_boardVO fvo) throws RemoteException {
		tfTitle.setText(fvo.getTbd_ti());
		tfDate.setText(fvo.getTbd_dt());
		taContents.setText(fvo.getTbd_con());

	}
}
