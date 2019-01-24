package login.main.controller;

import java.io.IOException;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import home.main.controller.AdminMainController;
import home.main.controller.HomeMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.main.service.ILoginService;
import login.register.controller.AES256Util;
import login.register.service.IRegisterService;
import vo.AdminVO;
import vo.MemberVO;

public class LoginController {
	
	@FXML
	private Label label;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;
    
    @FXML
    private TextField tfId;

    @FXML
    private PasswordField tfPass;
    
    @FXML
    private Button btnLogin;

    @FXML
    private RadioButton rdMem;

    @FXML
    private ToggleGroup mamber;

    @FXML
    private RadioButton rdAdmin;

    @FXML
    private Button btnRegi;

    @FXML
    private Button btnIdSearch;

    @FXML
    private Button brnPassSearch;

	private Stage stage;
    
    private ILoginService service;

    private MemberVO memvo;
    
    private AdminVO avo;
    
    public void setStage(Stage stage) {
		this.stage = stage;
	}
    
	@FXML
    void login(ActionEvent event) throws IOException {
		
		String pass =convert(tfPass.getText());
    	String key = "dditCampusProject";       // key는 16자 이상
        AES256Util aes256 = new AES256Util(key);
    	String chk = mamber.getSelectedToggle().getUserData().toString();

    	if (chk.equals("회원")) {
    		String strId = tfId.getText();
        	String strPass = pass;
        	
        	if(tfId.getText().isEmpty()) {
        		alert(null, "ID를 입력하세요.");
        		tfId.requestFocus();
        		return;
        	}
        	if(tfPass.getText().isEmpty()) {
        		alert(null, "비밀번호를 입력하세요.");
        		tfPass.requestFocus();
        		return;
        	}
        	String aes = "";
        	try {
				aes = aes256.aesEncode(strPass);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}

        	Map<String, String> log = new HashMap<>();
        	log.put("mem_id", strId);
        	log.put("mem_ps", aes);
        	
        	memvo = service.getLogin(log);
        	
        	if(memvo != null) {
        		if (memvo.getMem_access() == 0) {
        			alert(null, "회원 승인이 필요합니다.");
        			return;
				}else if (memvo.getMem_access() == 2){
					alert(null, "불량 회원으로 지정되어 접근이 불가능합니다.");
					return;
				}
        		stage = (Stage) btnLogin.getScene().getWindow();
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../home/main/controller/homeMain.fxml"));
        		Parent root = loader.load();
        		
        		HomeMainController homeController = loader.getController();
        		homeController.setMvo(memvo);
        		homeController.lookName();
        		Scene scene = new Scene(root);
        		stage.setScene(scene);
        		stage.setTitle("대덕 사이버 캠퍼스");
        		stage.show();
        	}else {
        		alert(null, "아이디와 비밀번호를 확인해주세요.");
        	}
		}else {

			String adm_id = tfId.getText();
        	String adm_ps = pass;
			if(tfId.getText().isEmpty()) {
        		alert(null, "ID를 입력하세요.");
        		tfId.requestFocus();
        		return;
        	}
        	if(tfPass.getText().isEmpty()) {
        		alert(null, "비밀번호를 입력하세요.");
        		tfPass.requestFocus();
        		return;
        	}
        	Map<String, String> log = new HashMap<>();
        	log.put("adm_id", adm_id);
        	log.put("adm_ps", adm_ps);
        	
        	avo = service.adminLogin(log);
        	if (avo != null) {
        		stage = (Stage) btnLogin.getScene().getWindow();
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../home/main/controller/adminMain.fxml"));
        		Parent root = loader.load();
        		
        		
        		avo.setAdm_id("admin");
        		avo.setAdm_ps("admin");
        		
        		AdminMainController admController = loader.getController();
        		admController.setAvo(avo);
        		Scene scene = new Scene(root);
        		stage.setScene(scene);
        		stage.setTitle("대덕 사이버 캠퍼스");
        		stage.show();
        	}else {
        		alert(null, "아이디와 비밀번호를 확인해주세요.");
        	}
		}
    }
    @FXML
    void Idsearch(ActionEvent event) throws IOException {
    	stage = (Stage) btnIdSearch.getScene().getWindow();

    	Stage regiStage = new Stage(StageStyle.DECORATED);
    	regiStage.initModality(Modality.WINDOW_MODAL);
    	regiStage.initOwner(stage);
    	regiStage.setTitle("아이디 찾기");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../login/search/controller/idSearch.fxml"));
    	Parent root = loader.load();

    	Scene scene = new Scene(root);
    	regiStage.initStyle(StageStyle.UTILITY);
    	regiStage.setScene(scene);
    	regiStage.show();
    	System.out.println(tfPass.getText());
    }
    
    @FXML
    void register(ActionEvent event) throws IOException {
    	stage = (Stage) btnLogin.getScene().getWindow();

    	Stage regiStage = new Stage(StageStyle.DECORATED);
    	regiStage.initModality(Modality.WINDOW_MODAL);
    	regiStage.initOwner(stage);
    	regiStage.setTitle("회원가입");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../login/register/controller/loginResgister.fxml"));
    	Parent root = loader.load();

    	Scene scene = new Scene(root);
    	regiStage.initStyle(StageStyle.UTILITY);
    	regiStage.setScene(scene);
    	regiStage.show();
    }

    @FXML
    void passSearch(ActionEvent event) throws IOException {
    	stage = (Stage) btnIdSearch.getScene().getWindow();

    	Stage regiStage = new Stage(StageStyle.DECORATED);
    	regiStage.initModality(Modality.WINDOW_MODAL);
    	regiStage.initOwner(stage);
    	regiStage.setTitle("비밀번호 찾기");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../login/search/controller/passSearch.fxml"));
    	Parent root = loader.load();

    	Scene scene = new Scene(root);
    	regiStage.initStyle(StageStyle.UTILITY  );
    	regiStage.setScene(scene);
    	regiStage.show();
    }
    
    @FXML
    void idReset(MouseEvent event) {
//    	tfId.clear();
    }

    @FXML
    void passReset(MouseEvent event) {
//    	tfPass.clear();
    }
    @FXML
    void initialize() {
    	assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'login.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'login.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'login.fxml'.";
        assert rdMem != null : "fx:id=\"rdMem\" was not injected: check your FXML file 'login.fxml'.";
        assert mamber != null : "fx:id=\"mamber\" was not injected: check your FXML file 'login.fxml'.";
        assert rdAdmin != null : "fx:id=\"rdAdmin\" was not injected: check your FXML file 'login.fxml'.";
        assert btnRegi != null : "fx:id=\"btnRegi\" was not injected: check your FXML file 'login.fxml'.";
        assert btnIdSearch != null : "fx:id=\"btnIdSearch\" was not injected: check your FXML file 'login.fxml'.";
        assert brnPassSearch != null : "fx:id=\"brnPassSearch\" was not injected: check your FXML file 'login.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'login.fxml'.";
        tfPass.setStyle("");
        
        
        Registry reg;
        try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (ILoginService) reg.lookup("login");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        rdAdmin.setUserData("관리자");
        rdMem.setUserData("회원");
        btnLogin.setFocusTraversable(false);
        rdMem.setFocusTraversable(false);
        rdAdmin.setFocusTraversable(false);
        btnRegi.setFocusTraversable(false);
        btnIdSearch.setFocusTraversable(false);
        brnPassSearch.setFocusTraversable(false);
        pane.setOnKeyPressed(e -> {
    		if (e.getCode() == KeyCode.ENTER) {
    			String key = "dditCampusProject";       // key는 16자 이상
    	        AES256Util aes256 = null;
				try {
					aes256 = new AES256Util(key);
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
    	    	String chk = mamber.getSelectedToggle().getUserData().toString();
    	    	if (chk.equals("회원")) {
    	    		String strId = tfId.getText();
    	        	String strPass = tfPass.getText();
    	        	
    	        	if(tfId.getText().isEmpty()) {
    	        		alert(null, "ID를 입력하세요.");
    	        		tfId.requestFocus();
    	        		return;
    	        	}
    	        	if(tfPass.getText().isEmpty()) {
    	        		alert(null, "비밀번호를 입력하세요.");
    	        		tfPass.requestFocus();
    	        		return;
    	        	}
    	        	String aes = "";
    	        	try {
    					aes = aes256.aesEncode(strPass);
    				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
    						| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e1) {
    					e1.printStackTrace();
    				}
    	        	Map<String, String> log = new HashMap<>();
    	        	log.put("mem_id", strId);
    	        	log.put("mem_ps", aes);
    	        	
    	        	try {
						memvo = service.getLogin(log);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
    	        	
    	        	if(memvo != null) {
    	        		if (memvo.getMem_access() == 0) {
    	        			alert(null, "회원 승인이 필요합니다.");
    	        			return;
    					}else if (memvo.getMem_access() == 2){
    						alert(null, "불량 회원으로 지정되어 접근이 불가능합니다.");
    						return;
    					}
    	        		stage = (Stage) btnLogin.getScene().getWindow();
    	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../home/main/controller/homeMain.fxml"));
    	        		Parent root = null;
						try {
							root = loader.load();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    	        		
    	        		HomeMainController homeController = loader.getController();
    	        		homeController.setMvo(memvo);
    	        		homeController.lookName();
    	        		Scene scene = new Scene(root);
    	        		stage.setScene(scene);
    	        		stage.setTitle("대덕 사이버 캠퍼스");
    	        		stage.show();
    	        	}else {
    	        		alert(null, "아이디와 비밀번호를 확인해주세요.");
    	        	}
    			}else {
    				String adm_id = tfId.getText();
    	        	String adm_ps = tfPass.getText();
    				if(tfId.getText().isEmpty()) {
    	        		alert(null, "ID를 입력하세요.");
    	        		tfId.requestFocus();
    	        		return;
    	        	}
    	        	if(tfPass.getText().isEmpty()) {
    	        		alert(null, "비밀번호를 입력하세요.");
    	        		tfPass.requestFocus();
    	        		return;
    	        	}
    	        	Map<String, String> log = new HashMap<>();
    	        	log.put("adm_id", adm_id);
    	        	log.put("adm_ps", adm_ps);
    	        	
    	        	try {
						avo = service.adminLogin(log);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
    	        	if (avo != null) {
    	        		stage = (Stage) btnLogin.getScene().getWindow();
    	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../home/main/controller/adminMain.fxml"));
    	        		Parent root = null;
						try {
							root = loader.load();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    	        		
    	        		
    	        		avo.setAdm_id("admin");
    	        		avo.setAdm_ps("admin");
    	        		
    	        		AdminMainController admController = loader.getController();
    	        		admController.setAvo(avo);
    	        		Scene scene = new Scene(root);
    	        		stage.setScene(scene);
    	        		stage.setTitle("대덕 사이버 캠퍼스");
    	        		stage.show();
    	        	}else {
    	        		alert(null, "아이디와 비밀번호를 확인해주세요.");
    	        	}
    			}
			}
    	});
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
    public void stageResize(){
    	stage.setResizable(false);
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
