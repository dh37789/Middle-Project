package admin.comm_manage.controller;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import home.community.service.IFreeBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import vo.Meal_chartVO;

public class manager_sikdanController {
	private IFreeBoardService service;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField tf_date;

    @FXML
    private TextArea ta_sikdan;

    @FXML
    private Button btn_save;
    
    Meal_chartVO mealvo = new Meal_chartVO();

    @FXML
    void save(ActionEvent event){
    	String date = tf_date.getText();
    	String sikdan = ta_sikdan.getText();
    	String pattern = "(d{4}-d{2}-d{2})";
    	String pattern2 = "([0-9]{4})-([0-9]{2})-([0-9]{2})";

    	if(date.isEmpty()) {
    		alert(null, "날짜를 입력해주세요!");
    		return;
    	}
    	
    	if(!Pattern.matches(pattern2, date)) {
    		alert(null,"날짜를 형식에 맞게 입력하세요 ex)2018-01-01");
    		return;
    	}
    	
    	mealvo.setMlc_id(date);
    	mealvo.setMlc_ml(sikdan);
    	
    	int chk = 0;
		try {
			chk = service.chkSikdan(date);
			if(chk == 0) {
				chk = service.addSikdan(mealvo);
				if(chk == 1) {
					alert(null, "등록성공");
				}else alert(null, "등록실패");
			}else {
				chk = service.updateSikdan(mealvo);
				if(chk == 1) {
					alert(null, "수정성공");
				}else alert(null, "수정실패");
			}
			
		} catch (RemoteException e1) {
			
		}
    }
    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'manager_sikdan.fxml'.";
        assert tf_date != null : "fx:id=\"tf_date\" was not injected: check your FXML file 'manager_sikdan.fxml'.";
        assert ta_sikdan != null : "fx:id=\"ta_sikdan\" was not injected: check your FXML file 'manager_sikdan.fxml'.";
        assert btn_save != null : "fx:id=\"btn_save\" was not injected: check your FXML file 'manager_sikdan.fxml'.";
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");
    }
    private void alert(String header, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
