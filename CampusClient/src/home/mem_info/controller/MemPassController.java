package home.mem_info.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import home.mem_info.service.IMem_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import login.register.controller.AES256Util;
import vo.MemberVO;

public class MemPassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private PasswordField tfPass;

    @FXML
    private Button btnChange;

    @FXML
    private PasswordField tfPassNew;

    @FXML
    private PasswordField tfPassNewChk;

    @FXML
    private Label lbPassChk;

    @FXML
    private Label lbPassNew;

    @FXML
    private Label lbPass;
    
    private IMem_infoService service;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void passChange(ActionEvent event) throws IOException {
		String key = "dditCampusProject";       // key는 16자 이상
    	AES256Util aes256 = new AES256Util(key);
    	String oldPass = "";
    	try {
			oldPass = aes256.aesDecode(mvo.getMem_ps());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
    	if (!tfPass.getText().equals(oldPass)) {
    		lbPass.setText("현재 비밀번호와 일치하지 않습니다.");
    		return;
    	}
		if (tfPassNew.getText().equals(oldPass)) {
			lbPassNew.setText("현재 비밀번호와 동일합니다.");
			return;
		}
		if (tfPass.getText().isEmpty()) {
			lbPass.setText("비밀번호를 입력해 주세요.");
			return;
		}
		if (!tfPassNew.getText().equals(tfPassNewChk.getText())) {
			lbPassChk.setText("비밀번호가 일치하지 않습니다.");
			return;
		}
		if (tfPassNew.getText().isEmpty()) {
			lbPassNew.setText("비밀번호를 입력해 주세요.");
			return;
		}
		if (tfPassNewChk.getText().isEmpty()) {
			lbPassChk.setText("비밀번호를 입력해 주세요.");
			return;
		}
		String pass = "";
		try {
			pass = aes256.aesEncode(tfPassNew.getText());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mvo.setMem_ps(pass);
		int cnt = 0;
		cnt = service.passUpdate(mvo);
		if (cnt > 0) {
			infoAlert(null, "비밀번호가 변경되었습니다.");
			tfPass.clear();
			lbPass.setText("");
			tfPassNew.clear();
			lbPassNew.setText("");
			tfPassNewChk.clear();
			lbPassChk.setText("");
		}else {
			alert(null, "비밀번호 변경에 실패하였습니다.");
		}
    }
	@FXML
	void resetPass(MouseEvent event) {
		tfPass.clear();
		lbPass.setText("");
	}

	@FXML
	void resetPass2(MouseEvent event) {
		tfPassNew.clear();
		lbPassNew.setText("");
	}

	@FXML
	void resetPass3(MouseEvent event) {
		tfPassNewChk.clear();
		lbPassChk.setText("");
	}

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memPass.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'memPass.fxml'.";
        assert btnChange != null : "fx:id=\"btnChange\" was not injected: check your FXML file 'memPass.fxml'.";
        assert tfPassNew != null : "fx:id=\"tfPassNew\" was not injected: check your FXML file 'memPass.fxml'.";
        assert tfPassNewChk != null : "fx:id=\"tfPassNewChk\" was not injected: check your FXML file 'memPass.fxml'.";
        assert lbPassChk != null : "fx:id=\"lbPassChk\" was not injected: check your FXML file 'memPass.fxml'.";
        assert lbPassNew != null : "fx:id=\"lbPassNew\" was not injected: check your FXML file 'memPass.fxml'.";
        assert lbPass != null : "fx:id=\"lbPass\" was not injected: check your FXML file 'memPass.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IMem_infoService) reg.lookup("memberInfo");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
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
}
