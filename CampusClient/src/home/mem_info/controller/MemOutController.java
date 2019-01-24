package home.mem_info.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import classRoom.main.controller.ClassroomController;
import home.mem_info.service.IMem_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vo.MemberVO;

public class MemOutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private CheckBox chkOut;

    @FXML
    private Button btnOut;
    
    private IMem_infoService service;
    
    private MemberVO mvo;
    
    private Stage stage;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

    @FXML
    void out(ActionEvent event) throws IOException {
    	stage = (Stage) btnOut.getScene().getWindow();
    	if (chkOut.isSelected()) {
    		ButtonType chk = confirm(null, "정말로 회원을 탈퇴하시겠습니까?");
    		if (chk == ButtonType.OK) {
    			String mem_id = mvo.getMem_id();
				int cnt = 0;
				try {
					cnt = service.outMember(mem_id);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (cnt > 0) {
					infoAlert(null, "탈퇴가 완료되었습니다.");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../login/main/controller/login.fxml"));
					Parent root = loader.load();
					
					Scene scene = new Scene(root);
					stage.setTitle("대덕 사이버 캠퍼스");
					stage.setScene(scene);
				}else {
					alert(null, "회원탈퇴 오류입니다.[관리자 문의]");
				}
			}
		}else {
			alert(null, "약관에 동의해 주세요.");
			return;
		}
    }
    
    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memOut.fxml'.";
        assert chkOut != null : "fx:id=\"chkOut\" was not injected: check your FXML file 'memOut.fxml'.";
        assert btnOut != null : "fx:id=\"btnOut\" was not injected: check your FXML file 'memOut.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IMem_infoService) reg.lookup("memberInfo");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        chkOut.setFocusTraversable(false);
        btnOut.setFocusTraversable(false);
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
