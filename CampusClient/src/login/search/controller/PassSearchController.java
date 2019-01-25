package login.search.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import login.register.controller.AES256Util;
import login.search.service.ISearchService;
import vo.MemberVO;

public class PassSearchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfTel;

    @FXML
    private TextField tfMail;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnCancel;

    private Stage stage;
    
    private ISearchService service;
    
    @FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void search(ActionEvent event) throws RemoteException, EmailException, UnsupportedEncodingException {
    	String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다. 
		uuid = uuid.substring(0, 6); //uuid를 앞에서부터 6자리 잘라줌.
		
		String key = "dditCampusProject";       // key는 16자 이상
    	AES256Util aes256 = new AES256Util(key);
    	
    	String strId = tfId.getText();
    	String strTel = tfTel.getText();
    	String strEm = tfMail.getText();
    	String aes= "";
		try {
			aes = aes256.aesEncode(uuid);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
    	Map<String, String> map = new HashMap<>();
    	map.put("mem_id", strId);
    	map.put("mem_ph", strTel);
    	map.put("mem_em", strEm);
    	map.put("mem_ps", aes);
    	
    	
    	if(tfId.getText().isEmpty()) {
    		alert(null, "이름을 입력해주세요.");
    		tfId.requestFocus();
    		return;
    	}
    	if(tfTel.getText().isEmpty()) {
    		alert(null, "전화번호를 입력해주세요.");
    		tfTel.requestFocus();
    		return;
    	}
    	if(tfMail.getText().isEmpty()) {
    		alert(null, "메일을 입력해주세요.");
    		tfMail.requestFocus();
    		return;
    	}
    	int cnt = service.getPass(map);
    	if(cnt>0) {
    		
    		// 간단한 이메일만 보내기
    		SimpleEmail email = new SimpleEmail();
    		// 네이버 로그인 - 메일 - 환경설정 - POP3/IMAP 설정에서 SMTP 확인가능 
    		email.setHostName("smtp.naver.com"); // SMTP 서버명
    		email.setSmtpPort(587); // SMTP 포트
    		// email을 보내는 사람의 아이디와 비밀번호
    		email.setAuthenticator(new DefaultAuthenticator("jns37789", "dnjcl@9310")); // 아이디와 비밀번호 입력.
    		//공개 이메일 setSSLOnConnect
    		//연결시 SMTP 전송에 대해 SSL / TLS 암호화를 사용할지 여부를 설정합니다 
    		//(SMTPS / POPS). setStartTLSRequired (boolean)보다 우선합니다
    		email.setSSLOnConnect(true);
    		email.setCharset("euc-kr"); // 한글로 인코딩
    		email.setFrom("jns37789@naver.com"); // 보내는 사람의 메일
    		String sub = strId + "님의 임시 비밀번호 발급안내";
    		email.setSubject(sub); // 메일의 제목
    		String content = strId + "님의 임시 비밀번호입니다. : " + uuid;
    		email.setMsg(content); // 메일의 본문
    		email.addTo("jns37789@naver.com"); // 받는 사람의 메일주소
    		email.send(); // 메일전송 메서드
    		Inform("임시 비밀번호가 전송되었습니다.");
    		System.out.println("전송 완료");
    	}else {
    		alert(null, "입력한 정보를 확인해주세요.");
    	}
    }

    @FXML
    void initialize() {
        assert tfId != null : "fx:id=\"tfName\" was not injected: check your FXML file 'passSearch.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'passSearch.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'passSearch.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'passSearch.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'passSearch.fxml'.";
        
        Registry reg;
        try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
				service = (ISearchService) reg.lookup("search");
        } catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        btnSearch.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
    }
    public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    
    public ButtonType Inform(String msg) {
    	Alert inform = new Alert(AlertType.INFORMATION);
    	
    	inform.setTitle("확인");
    	inform.setHeaderText(null);
    	inform.setContentText(msg);
    	
    	return inform.showAndWait().get();
    }
}
