package home.mem_info.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javax.crypto.Cipher;

import classRoom.question.service.IQuestionService;
import home.mem_info.service.IMem_infoService;
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

public class Mem_infoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private Button btnChange;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfTel;

    @FXML
    private ImageView imgProfile;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfPoint;

    private IMem_infoService service;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void change(ActionEvent event) throws IOException {
		ButtonType chk = confirm(null, "정보를 수정하시겠습니까?");
		if (chk == ButtonType.OK) {
			pane.getChildren().clear();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfoPass.fxml"));
			Parent memInfo = loader.load();
			
			MemInfoPassController infoPass = loader.getController();
			
			infoPass.setMvo(mvo);
			
			pane.getChildren().setAll(memInfo);
		}
		
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert btnChange != null : "fx:id=\"btnChange\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert tfId != null : "fx:id=\"tfId\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert imgProfile != null : "fx:id=\"imgProfile\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'mem_info.fxml'.";
        assert tfPoint != null : "fx:id=\"tfPoint\" was not injected: check your FXML file 'mem_info.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IMem_infoService) reg.lookup("memberInfo");
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
        
        btnChange.setFocusTraversable(false);
        

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
    public ButtonType confirm(String header, String msg){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	
    	ButtonType comfirmResult = alert.showAndWait().get();
    	return comfirmResult;
    }
}
