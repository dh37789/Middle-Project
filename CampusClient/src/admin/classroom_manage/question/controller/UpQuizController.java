package admin.classroom_manage.question.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.classroom_manage.question.service.IAdminQuizService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vo.Practice_questionVO;

public class UpQuizController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

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
    private Button btnQUp;

    @FXML
    private Button btnCancel;

    private Stage stage;
    
    private Practice_questionVO qvo;
    
    private IAdminQuizService service;
    
    private QuizManListController controller;
    
    public void setController(QuizManListController controller) {
		this.controller = controller;
	}
    
    public void setQvo(Practice_questionVO qvo) {
    	this.qvo = qvo;
    }
    
    @FXML
    void QuizUp(ActionEvent event) throws IOException {
    	qvo.setPqt_ti(tfQuizName.getText());
    	qvo.setPqt_aw(tfQuizAns.getText());
    	qvo.setPqt_diff(Integer.parseInt(tfDiff.getText()));
    	qvo.setPqt_con(taQuizCon.getText());
    	
    	int cnt = 0;
    	try {
			cnt = service.updateQuiz(qvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt > 0) {
			infoAlert(null, "문제 수정을 성공하였습니다.");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("viewQuiz.fxml"));
        	Parent root = loader.load();
        	
        	ViewQuizController viewController = loader.getController();
        	viewController.setQvo(qvo);
        	viewController.viewQuiz();
        	controller.getTableData();
        	pane.getChildren().setAll(root);
		}else {
			alert(null, "문제 수정을 실패하였습니다.");
		}
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "수정을 취소하고 돌아가시겠습니까?");
    	if (ans == ButtonType.OK) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("viewQuiz.fxml"));
        	Parent root = loader.load();
        	
        	ViewQuizController viewController = loader.getController();
        	viewController.setQvo(qvo);
        	viewController.viewQuiz();
        	
        	pane.getChildren().setAll(root);
		}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert tfQuizId != null : "fx:id=\"tfQuizId\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert tfDiff != null : "fx:id=\"tfDiff\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert tfQuizName != null : "fx:id=\"tfQuizName\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert taQuizCon != null : "fx:id=\"taQuizCon\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert tfQuizAns != null : "fx:id=\"tfQuizAns\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert btnQUp != null : "fx:id=\"btnQUp\" was not injected: check your FXML file 'upQuiz.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'upQuiz.fxml'.";
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
        btnQUp.setFocusTraversable(false);
    }
    public void viewQuiz() {
    	tfDiff.setText(qvo.getPqt_diff() + "");
    	tfQuizAns.setText(qvo.getPqt_aw());
    	tfQuizId.setText(qvo.getPqt_id());
    	tfQuizName.setText(qvo.getPqt_ti());
    	taQuizCon.setText(qvo.getPqt_con());
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
