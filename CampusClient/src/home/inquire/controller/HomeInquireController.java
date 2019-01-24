package home.inquire.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class HomeInquireController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfTItle;

    @FXML
    private Label label;

    @FXML
    private TextArea taCon;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnCancel;

    @FXML
    void reset(ActionEvent event) {

    }

    @FXML
    void send(ActionEvent event) throws EmailException {
    	 // 간단한 이메일만 보내기
		SimpleEmail email = new SimpleEmail();
		// 네이버 로그인 - 메일 - 환경설정 - POP3/IMAP 설정에서 SMTP 확인가능 
		email.setHostName("smtp.naver.com"); // SMTP 서버명
		email.setSmtpPort(587); // SMTP 포트
		// email을 보내는 사람의 아이디와 비밀번호
		email.setAuthenticator(new DefaultAuthenticator("++"
				+ ""
				+ ""
				+ "", ""));
		//공개 이메일 setSSLOnConnect
		//연결시 SMTP 전송에 대해 SSL / TLS 암호화를 사용할지 여부를 설정합니다 
		//(SMTPS / POPS). setStartTLSRequired (boolean)보다 우선합니다
		email.setSSLOnConnect(true);
		email.setCharset("euc-kr"); // 한글로 인코딩
		email.setFrom("jns37789@naver.com"); // 보내는 사람의 메일
		String sub = "jns37789님의 문의 <" + tfTItle.getText() + ">";
		email.setSubject(sub); // 메일의 제목
		String content = "문의 내역입니다 : \n" + taCon.getText();
		email.setMsg(content); // 메일의 본문
		email.addTo("kangyuigu@naver.com"); // 받는 사람의 메일주소
		email.send(); // 메일전송 메서드
		alert(null, "전송이 완료되었습니다.");
		System.out.println("전송 완료");
    	
    }

    private void alert(String header, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("메일이 전송되었습니다.");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	@FXML
    void initialize() {
        assert tfTItle != null : "fx:id=\"tfTItle\" was not injected: check your FXML file 'homeInquire.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'homeInquire.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'homeInquire.fxml'.";
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'homeInquire.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'homeInquire.fxml'.";
        Font.loadFont(HomeInquireController.class.getResource("NanumGothicExtraBold.ttf").toExternalForm(),10);
        
        btnCancel.setFocusTraversable(false);
        btnSend.setFocusTraversable(false);
    }
}
