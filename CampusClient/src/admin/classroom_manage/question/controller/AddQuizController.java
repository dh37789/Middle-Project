package admin.classroom_manage.question.controller;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.classroom_manage.question.service.IAdminQuizService;
import classRoom.question.service.IQuestionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import vo.Practice_questionVO;

public class AddQuizController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfQuizId;

    @FXML
    private TextField tfDiff;

    @FXML
    private TextField tfQuizName;

    @FXML
    private TextArea taQuizCon;

    @FXML
    private TextField tfQuizAns;

    @FXML
    private Button btnQAdd;

    @FXML
    private Button btnCancel;

    private Practice_questionVO qvo;
    
    private Stage stage;
    
    private QuizManListController controller;
    
    public void setController(QuizManListController controller) {
		this.controller = controller;
	}
    
	private IAdminQuizService service;
    
    public void setQvo(Practice_questionVO qvo) {
		this.qvo = qvo;
	}

	@FXML
    void QuizAdd(ActionEvent event) {
		Practice_questionVO vo = new Practice_questionVO();
		vo.setPqt_admid("admin");
		vo.setPqt_ti(tfQuizName.getText());
		vo.setPqt_aw(tfQuizAns.getText());
		vo.setPqt_con(taQuizCon.getText());
		vo.setPqt_id(tfQuizId.getText());
		vo.setPqt_diff(Integer.parseInt(tfDiff.getText()));
		vo.setPqt_tct(0);
		vo.setPqt_ct(0);
		
		int cnt = 0;
		try {
			cnt = service.AddQuiz(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (cnt > 0) {
			infoAlert(null, "문제가 추가되었습니다.");
			controller.getTableData();
			
			stage = (Stage) btnCancel.getScene().getWindow();
	    	stage.close();
		}else {
			alert(null, "문제가 추가되지 않았습니다.");
			return;
		}
		
    }

    @FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    } 
    
    @FXML
    void initialize() {
        assert tfQuizId != null : "fx:id=\"tfQuizId\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert tfDiff != null : "fx:id=\"tfDiff\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert tfQuizName != null : "fx:id=\"tfQuizName\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert taQuizCon != null : "fx:id=\"taQuizCon\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert tfQuizAns != null : "fx:id=\"tfQuizAns\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert btnQAdd != null : "fx:id=\"btnQAdd\" was not injected: check your FXML file 'addQuiz.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'addQuiz.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IAdminQuizService) reg.lookup("adminQuiz");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        btnCancel.setFocusTraversable(false);
        btnQAdd.setFocusTraversable(false);
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
