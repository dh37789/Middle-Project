package admin.mem_manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import home.mem_info.service.IMem_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import vo.MemberVO;

public class InfoUpController {

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
    private Button btnImgUP;

    @FXML
    private Button btnImgDel;

    @FXML
    private ImageView imgProfile;

    private Stage stage;
    
    private IMem_infoService service;
    
    private MemberVO mvo;
    
    private String fileTemp;
    
    private String imgTemp;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void cancel(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "회원 조회 페이지로 돌아가시겠습니까?");
		if (ans == ButtonType.OK) {
			pane.getChildren().clear();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfo.fxml"));
			Parent memInfo = loader.load();
			
			MemInfoController infoCon = loader.getController();
			infoCon.setMvo(mvo);
			infoCon.viewMember();
			
			pane.getChildren().setAll(memInfo);
		}
    }

    @FXML
    void change(ActionEvent event) throws IOException {
    	String regex1 = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
    	String regex2 = "^\\d{3}-\\d{3,4}-\\d{4}$";
    	if(tfName.getText().isEmpty()) {
    		alert(null, "이름을 입력하세요.");
    		tfName.requestFocus();
    		return;
    	}
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
    	if(tfPoint.getText().isEmpty()) {
    		alert(null, "포인트입력란이 비어있습니다.");
    		return;
    	}
    	mvo.setMem_nm(tfName.getText());
    	mvo.setMem_ph(tfTel.getText());
    	mvo.setMem_em(tfMail.getText());
    	mvo.setMem_pt(Integer.parseInt(tfPoint.getText()));
    	mvo.setMem_irt(imgTemp);
    	System.out.println(mvo.getMem_irt());
    	
    	int cnt = 0;
    	try {
			cnt = service.infoUpdate(mvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt > 0) {
    		infoAlert(null, "회원정보가 수정되었습니다.");
    		pane.getChildren().clear();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfo.fxml"));
    		Parent memInfo = loader.load();
    		
    		MemInfoController infoCon = loader.getController();
    		infoCon.setMvo(mvo);
    		infoCon.viewMember();
    		
    		pane.getChildren().setAll(memInfo);
		}else {
			alert(null, "회원정보 수정에 실패하였습니다.");
		}
    }

    @FXML
    void imgDel(ActionEvent event) {
    	File oldFile = new File(mvo.getMem_irt());
		oldFile.delete();
    	imgTemp="";
    	mvo.setMem_irt("");
    	Image img = new Image("file:///D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/res/people.png");
		imgProfile.setImage(img);
    }

    @FXML
    void imgUp(ActionEvent event) throws IOException {
    	stage = (Stage) btnImgUP.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		// 검색할 파일 종류 설정(주로 확장자 위주로 설정한다.)
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image File", "*.jpg", "*.png")
		);
		File selectFile = fileChooser.showOpenDialog(stage);
		if (selectFile==null) {
			return;
		}
		fileTemp = selectFile.getPath();
		String fileNM = selectFile.getPath().substring(selectFile.getPath().lastIndexOf("\\")+1, selectFile.getPath().length());
		FileInputStream fin = null;
		FileOutputStream fout = null;
		File file = new File(fileTemp);
		fin = new FileInputStream(file);
		String folder = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/profile/" + mvo.getMem_id();
		File newDir = new File(folder); 
		if (!newDir.exists()) {
			newDir.mkdirs();
		}
		fout = new FileOutputStream(folder + "/" + fileNM);
		int c;
		
		while((c = fin.read()) != -1){
			fout.write(c);
		}
		fin.close();
		fout.close();
		if (mvo.getMem_irt()!=null) {
			File oldFile = new File(mvo.getMem_irt());
			oldFile.delete();
		}
		System.out.println(folder + "/" + fileNM);
		mvo.setMem_irt(folder + "/" + fileNM);
		imgTemp = "file:///" + folder + "/" + fileNM;
		Image img = new Image("file:///" + folder + "/" + fileNM);
		imgProfile.setImage(img);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert tfId != null : "fx:id=\"tfId\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert tfPoint != null : "fx:id=\"tfPoint\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert btnChange != null : "fx:id=\"btnChange\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert btnImgUP != null : "fx:id=\"btnImgUP\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert btnImgDel != null : "fx:id=\"btnImgDel\" was not injected: check your FXML file 'InfoUp.fxml'.";
        assert imgProfile != null : "fx:id=\"imgProfile\" was not injected: check your FXML file 'InfoUp.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IMem_infoService) reg.lookup("memberInfo");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        tfId.setEditable(false);
        tfId.setFocusTraversable(false);
        tfId.setMouseTransparent(true);
        
        btnCancel.setFocusTraversable(false);
        btnChange.setFocusTraversable(false);
        btnImgDel.setFocusTraversable(false);
        btnImgUP.setFocusTraversable(false);
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
