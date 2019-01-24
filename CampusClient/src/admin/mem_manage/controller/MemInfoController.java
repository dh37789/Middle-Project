package admin.mem_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Provider.Service;
import java.util.ResourceBundle;

import admin.mem_manage.service.IMem_manageService;
import home.mem_info.controller.MemInfoUpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import vo.MemberVO;

public class MemInfoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfTel;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfPoint;

    @FXML
    private Button btnChange;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Button gtnDel;

    @FXML
    private Button btnLearn;

    private MemberVO mvo;
    
    private IMem_manageService service;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void cancel(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfoList.fxml"));
		Parent Infolist = loader.load();
		
		MemInfoListController listCon = loader.getController();
		listCon.getAllMember();
		pane.getChildren().setAll(Infolist);
    }

    @FXML
    void change(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, mvo.getMem_nm() + "님의 회원정보를 수정하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		pane.getChildren().clear();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoUp.fxml"));
    		Parent InfoUp = loader.load();
    		
    		InfoUpController infoUpCon = loader.getController();
    		infoUpCon.setMvo(mvo);
    		infoUpCon.viewMember();
    		pane.getChildren().setAll(InfoUp);
		}
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, mvo.getMem_nm() + "님의 회원정보를 삭제하시겠습니까?");
    	if (ans == ButtonType.OK) {
			int cnt = 0;
			try {
				cnt = service.deleteMember(mvo.getMem_id());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (cnt > 0) {
				infoAlert(null, "회원정보를 삭제하였습니다.");
			}else {
				alert(null, "회원정보 삭제에 실패하였습니다.");
				return;
			}
			pane.getChildren().clear();
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfoList.fxml"));
			Parent Infolist = loader.load();
			
			MemInfoListController listCon = loader.getController();
			listCon.getAllMember();
			pane.getChildren().setAll(Infolist);
		}
    }

    @FXML
    void goLearn(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memLearn.fxml"));
		Parent learnInfo = loader.load();
		
		MemLearnController learnCon = loader.getController();
		learnCon.setMvo(mvo);
		learnCon.getVideoTable();
		learnCon.getQuizTable();
		learnCon.vdPieShow();
		learnCon.qzPieShow();
		pane.getChildren().setAll(learnInfo);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert tfId != null : "fx:id=\"tfId\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert tfPoint != null : "fx:id=\"tfPoint\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert btnChange != null : "fx:id=\"btnChange\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert imgProfile != null : "fx:id=\"imgProfile\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert gtnDel != null : "fx:id=\"gtnDel\" was not injected: check your FXML file 'memInfo.fxml'.";
        assert btnLearn != null : "fx:id=\"btnLearn\" was not injected: check your FXML file 'memInfo.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IMem_manageService) reg.lookup("memMana");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        tfMail.setEditable(false);
        tfMail.setFocusTraversable(false);
        tfMail.setMouseTransparent(true);
        
        tfName.setEditable(false);
        tfName.setFocusTraversable(false);
        tfName.setMouseTransparent(true); 
        
        tfTel.setEditable(false);
        tfTel.setFocusTraversable(false);
        tfTel.setMouseTransparent(true);
        
        tfId.setEditable(false);
        tfId.setFocusTraversable(false);
        tfId.setMouseTransparent(true);
        
        tfPoint.setEditable(false);
        tfPoint.setFocusTraversable(false);
        tfPoint.setMouseTransparent(true);
        
        btnCancel.setFocusTraversable(false);
        btnChange.setFocusTraversable(false);
        btnLearn.setFocusTraversable(false);
        gtnDel.setFocusTraversable(false);
    }
    public void viewMember(){
    	tfId.setText(mvo.getMem_id());
    	tfMail.setText(mvo.getMem_em());
    	tfName.setText(mvo.getMem_nm());
    	tfPoint.setText(mvo.getMem_pt()+"");
    	tfTel.setText(mvo.getMem_ph());
    	if (mvo.getMem_irt()==null||mvo.getMem_irt()=="") {
    		Image img = new Image("file:///D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/res/people.png");
    		imgProfile.setImage(img);
		}else {
			Image img = new Image(mvo.getMem_irt());
			imgProfile.setImage(img);
		}
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
