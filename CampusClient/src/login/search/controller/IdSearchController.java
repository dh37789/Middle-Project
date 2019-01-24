package login.search.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.search.service.ISearchService;
import vo.MemberVO;
import javafx.scene.control.Alert.AlertType;

public class IdSearchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfName;

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
    
    private MemberVO memvo;
    
    @FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void search(ActionEvent event) throws IOException {
    	String strNm = tfName.getText();
    	String strPh = tfTel.getText();
    	String strEm = tfMail.getText();
    	
    	Map<String, String> map = new HashMap<>();
    	map.put("mem_nm", strNm);
    	map.put("mem_ph", strPh);
    	map.put("mem_em", strEm);
    	
    	if(tfName.getText().isEmpty()) {
    		alert(null, "이름을 입력해주세요.");
    		tfName.requestFocus();
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
    	memvo = service.getIdSearch(map);
    	if(memvo != null) {
    		Inform("찾으실 ID는 : " + memvo.getMem_id());
    	}else {
    		alert(null, "입력한 정보를 확인해주세요.");
    	}
    	
    	
    }

    @FXML
    void initialize() {
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'idSearch.fxml'.";
        assert tfTel != null : "fx:id=\"tfTel\" was not injected: check your FXML file 'idSearch.fxml'.";
        assert tfMail != null : "fx:id=\"tfMail\" was not injected: check your FXML file 'idSearch.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'idSearch.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'idSearch.fxml'.";
        
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
