package login.register.controller;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import login.register.service.IRegisterService;
import vo.MemberVO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.*;
import java.util.Date;


public class LoginResgisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField tfPass;

    @FXML
    private PasswordField tfPassChk;

    @FXML
    private TextField tfTel;

    @FXML
    private TextField tfMail;

    @FXML
    private Button btnIdChk;

    @FXML
    private Button join;

    @FXML
    private Button btnCancel;

    @FXML
    private ImageView captcha;
    
    @FXML
    private TextField tfCaptcha;
    
    @FXML
    private Button btnRe;
    
    @FXML
    private WebView webCap;
    
    @FXML
    private Label lbPsChk;
    
    @FXML
    private Label lbIdChk;
    
    private Stage stage;
    
    private IRegisterService service;
    
    private String str;
    
    private String capchaKey;
    
    private WebEngine capEngine;
    
    private String capResult;
    
    private String idChkStr;
    
    @FXML
    void Join(ActionEvent event) throws RemoteException, UnsupportedEncodingException {
    	stage = (Stage) join.getScene().getWindow();
    	String regex1 = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
    	String regex2 = "^\\d{3}-\\d{3,4}-\\d{4}$";
    	String key = "dditCampusProject";       // key는 16자 이상
    	AES256Util aes256 = new AES256Util(key);
    	if(tfId.getText().isEmpty()) {
    		alert(null, "ID를 입력하세요.");
    		tfId.requestFocus();
    		return;
    	}
    	if(tfName.getText().isEmpty()) {
    		alert(null, "이름을 입력하세요.");
    		tfName.requestFocus();
    		return;
    	}
    	if(tfPass.getText().isEmpty()) {
    		alert(null, "비밀번호를 입력하세요.");
    		tfPass.requestFocus();
    		return;
    	}
    	if(!tfPass.getText().equals(tfPassChk.getText())) {
    		tfPassChk.requestFocus();
    		lbPsChk.setText("같지 않습니다.");
    		return;
    	}
    	String pass = convert(tfPass.getText());
    	if(tfTel.getText().isEmpty()||
    			!Pattern.matches(regex2, tfTel.getText())) {
    		alert(null, "전화번호가 비어있거나 양식에 맞지 않습니다.");
    		tfTel.requestFocus();
    		return;
    	}
    	if(tfMail.getText().isEmpty()||
    			!Pattern.matches(regex1, tfMail.getText())) {
    		alert(null, "이메일이 비어있거나 양식에 맞지 않습니다.");
    		return;
    	}
    	if (idChkStr.equals("F")) {
    		alert(null, "아이디 중복확인을 해주세요.");
    		return;
		}
    	capResult = captchaResult(capchaKey, tfCaptcha.getText());
    	capResult = capResult.substring(10, 14);
    	System.out.println(capResult);
    	if (!capResult.equals("true")) {
    		alert(null, "보안문자를 다시 입력해 주세요.");
    		capchaKey = captchaKey().substring(8, 24);
    		capEngine.load("https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + capchaKey);
    		tfCaptcha.clear();
    		return;
		}
    	MemberVO memvo = new MemberVO();
    	
         
    	memvo.setMem_id(tfId.getText());
    	memvo.setMem_nm(tfName.getText());
    	String aes= "";
		try {
			aes = aes256.aesEncode(pass);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
    	memvo.setMem_ps(aes);
    	memvo.setMem_em(tfMail.getText());
    	memvo.setMem_ph(tfTel.getText());
    	memvo.setMem_aDay(0);
    	memvo.setMem_irt("");
    	memvo.setMem_pt(500);
    	memvo.setMem_access(0);
    	
    	int cnt = service.insertMember(memvo);
    	if(cnt>0) {
    		alert(null, "회원가입 성공");
    		stage.close();
    	}else {
    		alert(null, "회원가입 실패");
    	}
    }
    @FXML
    void resetId(MouseEvent event) {
    	tfId.clear();
    	lbIdChk.setText("");
    	idChkStr = "F";
    }
    @FXML
    void PsChkRe(MouseEvent event) {
    	tfPassChk.clear();
    	lbPsChk.setText("");
    }
    @FXML
    void mailRe(MouseEvent event) {
    	tfMail.clear();
    }

    @FXML
    void nameReset(MouseEvent event) {
    	tfName.clear();
    }

    @FXML
    void psReset(MouseEvent event) {
    	tfPass.clear();
    }

    @FXML
    void telRe(MouseEvent event) {
    	tfTel.clear();
    }

	@FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void idChk(ActionEvent event) throws RemoteException {
    		str = tfId.getText();
    		if(tfId.getText().isEmpty()) {
        		alert(null, "ID를 입력하세요.");
        		tfId.requestFocus();
        		return;
        	}
       		int cnt = service.getMemberCount(str);
       		if(cnt > 0) {
       			lbIdChk.setText(tfId.getText() + "는 이미 있는 ID입니다.");
       			lbIdChk.setStyle("-fx-text-fill : red");
       		}else {
       			lbIdChk.setText(tfId.getText() + "는 사용 가능한 ID입니다.");
       			lbIdChk.setStyle("-fx-text-fill : green");
       			idChkStr = "T";
       		}
       	}

    @FXML
    void captchaRe(ActionEvent event) {
    	capEngine.load("https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + capchaKey);
    }
    
    @FXML
    void initialize() throws MalformedURLException {
        assert tfId != null : "fx:id=\"tfId\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfPass != null : "fx:id=\"tfpass\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfPassChk != null : "fx:id=\"tfPassChk\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert btnIdChk != null : "fx:id=\"btnIdChk\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert join != null : "fx:id=\"join\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert tfCaptcha != null : "fx:id=\"tfCaptcha\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert btnRe != null : "fx:id=\"btnRe\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert captcha != null : "fx:id=\"captcha\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert webCap != null : "fx:id=\"webCap\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert lbPsChk != null : "fx:id=\"lbPsChk\" was not injected: check your FXML file 'loginResgister.fxml'.";
        assert lbIdChk != null : "fx:id=\"lbIdChk\" was not injected: check your FXML file 'loginResgister.fxml'.";
        Registry reg;
        try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IRegisterService) reg.lookup("register");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        capchaKey = captchaKey().substring(8, 24);
        WebEngine capEngine = webCap.getEngine();
        this.capEngine = capEngine;
        capEngine.load("https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + capchaKey);
        idChkStr = "F";
        
        btnIdChk.setFocusTraversable(false);
        btnRe.setFocusTraversable(false);
    }
    
    public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    
    public ButtonType confirm(String msg) {
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	
    	confirm.setTitle("선택");
    	confirm.setHeaderText(null);
    	confirm.setContentText(msg);
    	
    	return confirm.showAndWait().get();
    }
    
    public String captchaKey() {
    	String clientId = "ZEJ7rVwBb4ZAF70iGaEm";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "G6KDRwthdx";//애플리케이션 클라이언트 시크릿값";
        String key = "";
        try {
            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
                key = inputLine;
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return key;
	}
    
    public void captchaImg(String ckey) {
    	String clientId = "ZEJ7rVwBb4ZAF70iGaEm";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "G6KDRwthdx";//애플리케이션 클라이언트 시크릿값";
        try {
            String key = ckey; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
            String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File("tempname" + ".jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
    public String captchaResult(String cKey, String inValue) {
    	String clientId = "ZEJ7rVwBb4ZAF70iGaEm";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "G6KDRwthdx";//애플리케이션 클라이언트 시크릿값";
        String result = "";
        try {
            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String key = cKey; // 캡차 키 발급시 받은 키값
            String value = inValue; // 사용자가 입력한 캡차 이미지 글자값
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ key + "&value="+ value;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            result = response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
	}
    
    
  public String convert(String ps) {
    	
    	final char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
				0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
				0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
		final char[] arrJungSung = { 0x314f, 0x3150, 0x3151, 0x3152,
				0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
				0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
				0x3163 };
		final char[] arrJongSung = { 0x0000, 0x3131, 0x3132, 0x3133,
				0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
				0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
				0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
		
		
		final String[] arrChoSungEng = { "r", "R", "s", "e", "E",
			"f", "a", "q", "Q", "t", "T", "d", "w",
			"W", "c", "z", "x", "v", "g" };
		
		/** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
		final String[] arrJungSungEng = { "k", "o", "i", "O",
			"j", "p", "u", "P", "h", "hk", "ho", "hl",
			"y", "n", "nj", "np", "nl", "b", "m", "ml",
			"l" };
		
		/** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
		final String[] arrJongSungEng = { "", "r", "R", "rt",
			"s", "sw", "sg", "e", "f", "fr", "fa", "fq",
			"ft", "fx", "fv", "fg", "a", "q", "qt", "t",
			"T", "d", "w", "c", "z", "x", "v", "g" };
		
		/** 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임) */
		final String[] arrSingleJaumEng = { "r", "R", "rt",
			"s", "sw", "sg", "e","E" ,"f", "fr", "fa", "fq",
			"ft", "fx", "fv", "fg", "a", "q","Q", "qt", "t",
			"T", "d", "w", "W", "c", "z", "x", "v", "g" };

		String word 		= ps;		// 분리할 단어
		String result		= "";									// 결과 저장할 변수
		String resultEng	= "";									// 알파벳으로
		
		for (int i = 0; i < word.length(); i++) {
			
			/*  한글자씩 읽어들인다. */
			char chars = (char) (word.charAt(i) - 0xAC00);

			if (chars >= 0 && chars <= 11172) {
				/* A. 자음과 모음이 합쳐진 글자인경우 */

				/* A-1. 초/중/종성 분리 */
				int chosung 	= chars / (21 * 28);
				int jungsung 	= chars % (21 * 28) / 28;
				int jongsung 	= chars % (21 * 28) % 28;

				
				/* A-2. result에 담기 */
				result = result + arrChoSung[chosung] + arrJungSung[jungsung];

				
				/* 자음분리 */
				if (jongsung != 0x0000) {
					/* A-3. 종성이 존재할경우 result에 담는다 */
					result =  result + arrJongSung[jongsung];
				}

				/* 알파벳으로 */
				resultEng = resultEng + arrChoSungEng[chosung] + arrJungSungEng[jungsung];
				if (jongsung != 0x0000) {
					/* A-3. 종성이 존재할경우 result에 담는다 */
					resultEng =  resultEng + arrJongSungEng[jongsung];
				}

			} else {
				/* B. 한글이 아니거나 자음만 있을경우 */
				
				/* 자음분리 */
				result = result + ((char)(chars + 0xAC00));
				
				/* 알파벳으로 */
				if( chars>=34097 && chars<=34126){
					/* 단일자음인 경우 */
					int jaum 	= (chars-34097);
					resultEng = resultEng + arrSingleJaumEng[jaum];
				} else if( chars>=34127 && chars<=34147) {
					/* 단일모음인 경우 */
					int moum 	= (chars-34127);
					resultEng = resultEng + arrJungSungEng[moum];
				} else {
					/* 알파벳인 경우 */
					resultEng = resultEng + ((char)(chars + 0xAC00));
				}
			}//if
			
		}//for

    	
    	return resultEng;
    }
    
}
