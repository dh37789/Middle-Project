package admin.classroom_manage.question.controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

public class ViewQuizController {

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
    private Button btnQDel;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnUp;

    private IAdminQuizService service;
    
    private QuizManListController controller;
    
    public void setController(QuizManListController controller) {
		this.controller = controller;
	}
    
    private Practice_questionVO qvo;
    
    private Stage stage;
    
    public void setQvo(Practice_questionVO qvo) {
		this.qvo = qvo;
	}
    
    @FXML
    void QuizDel(ActionEvent event) {
    	ButtonType ans = confirm(null, "삭제하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		int cnt = 0;
    		try {
    			cnt = service.deleteQuiz(qvo.getPqt_id());
    		} catch (RemoteException e) {
    			e.printStackTrace();
    		}
    		if (cnt > 0) {
    			infoAlert(null, "삭제를 성공하였습니다.");
    			controller.getTableData();
    			stage = (Stage) btnCancel.getScene().getWindow();
    			stage.close();
    		}else {
    			alert(null, "삭제에 실패하였습니다.");
    			return;
    		}
		}
    }

    @FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void quizUp(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "수정하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("upQuiz.fxml"));
    		Parent root = loader.load();
    		
    		UpQuizController upController = loader.getController();
    		upController.setQvo(qvo);
    		upController.viewQuiz();
    		upController.setController(controller);
    		pane.getChildren().setAll(root);
    	}
    }
    
    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert tfQuizId != null : "fx:id=\"tfQuizId\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert tfDiff != null : "fx:id=\"tfDiff\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert tfQuizName != null : "fx:id=\"tfQuizName\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert taQuizCon != null : "fx:id=\"taQuizCon\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert tfQuizAns != null : "fx:id=\"tfQuizAns\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert btnQDel != null : "fx:id=\"btnQDel\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'viewQuiz.fxml'.";
        assert btnUp != null : "fx:id=\"btnUp\" was not injected: check your FXML file 'viewQuiz.fxml'.";
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
        btnQDel.setFocusTraversable(false);
        btnUp.setFocusTraversable(false);
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
