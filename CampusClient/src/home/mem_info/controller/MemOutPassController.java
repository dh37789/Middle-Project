package home.mem_info.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import login.register.controller.AES256Util;
import vo.MemberVO;

public class MemOutPassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private PasswordField tfPass;

    @FXML
    private Button btnChk;

    @FXML
    private Label lbChk;

    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
    	this.mvo = mvo;
    }

    @FXML
    void passChk(ActionEvent event) throws IOException {
    	String key = "dditCampusProject";
        AES256Util aes = new AES256Util(key);
        String pass = tfPass.getText();
        String des = ""; 
        try {
			des = aes.aesDecode(mvo.getMem_ps());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
        if (pass.equals(des)) {
        	pane.getChildren().clear();
        	
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("memOut.fxml"));
        	Parent memInfo = loader.load();
        	
        	MemOutController OutCon = loader.getController();
        	
        	OutCon.setMvo(mvo);
        	
        	pane.getChildren().setAll(memInfo);
        }else {
        	lbChk.setText("비밀번호가 옳바르지 않습니다.");
        }
    }

    @FXML
    void resetPass(MouseEvent event) {
    	tfPass.clear();
    	lbChk.setText("");
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memOutPass.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'memOutPass.fxml'.";
        assert btnChk != null : "fx:id=\"btnChk\" was not injected: check your FXML file 'memOutPass.fxml'.";
        assert lbChk != null : "fx:id=\"lbChk\" was not injected: check your FXML file 'memOutPass.fxml'.";

        btnChk.setFocusTraversable(false);
    }
}
    
