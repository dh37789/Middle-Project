package admin.mem_manage.controller;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.mail.imap.protocol.Item;

import admin.mem_manage.service.IMem_manageService;
import classRoom.question.controller.QuestionController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vo.MemberVO;

public class MemApplyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<MemberApply> table;

    @FXML
    private TableColumn<MemberApply,String> colNM;

    @FXML
    private TableColumn<MemberApply,String> colId;

    @FXML
    private TableColumn<MemberApply,String> colMail;

    @FXML
    private TableColumn<MemberApply,String> cokTel;

    @FXML
    private TableColumn<MemberApply,CheckBox> colCheck;
    
    @FXML
    private Button btnAll;

    @FXML
    private Button btnNo;
    
    private IMem_manageService service;
    
    private List<MemberVO> accessList;
    
    private ObservableList<MemberApply> dataList;
    
    @FXML
    void allAcess(ActionEvent event) throws InterruptedException {
    	ButtonType ans = confirm(null, "회원을 승인하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		for (int i = 0; i < table.getItems().size(); i++) {
    			Boolean chk = table.getItems().get(i).getCh().isSelected();
    			if (chk == true) {
    				String mem_id = table.getItems().get(i).getMvo().getMem_id();
    				// 가입 승인을 위한 쓰레드
    				Platform.runLater(() -> {
    					try {
    						int cnt = service.accessOK(mem_id);
    					} catch (RemoteException e) {
    						e.printStackTrace();
    					}
    					getList();
    				});
    			}
    		}
		}
    }

    
    @FXML
    void allNo(ActionEvent event) {
    	ButtonType ans = confirm(null, "회원을 거부하시겠습니까?");
    	if (ans == ButtonType.OK) {
    		for (int i = 0; i < table.getItems().size(); i++) {
    			Boolean chk = table.getItems().get(i).getCh().isSelected();
    			if (chk == true) {
    				String mem_id = table.getItems().get(i).getMvo().getMem_id();
    				// 삭제를 위한 쓰레드
    				Platform.runLater(() -> {
    					try {
    						int cnt = service.accessNO(mem_id);
    					} catch (RemoteException e) {
    						e.printStackTrace();
    					}
    					getList();
    				});
    			}
    		}
    	}
    }
    
    @FXML
    void apply(MouseEvent event) {
    	if (!table.getSelectionModel().isEmpty()) {
    		int cnt = table.getSelectionModel().getSelectedIndex();
    		Boolean chk = table.getItems().get(cnt).getCh().isSelected();
    		if (chk == true) {
    			table.getItems().get(cnt).getCh().setSelected(false);
    		}else {
    			table.getItems().get(cnt).getCh().setSelected(true);
    		}
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memApply.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'memApply.fxml'.";
        assert colNM != null : "fx:id=\"colNM\" was not injected: check your FXML file 'memApply.fxml'.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file 'memApply.fxml'.";
        assert colMail != null : "fx:id=\"colAddr\" was not injected: check your FXML file 'memApply.fxml'.";
        assert cokTel != null : "fx:id=\"cokTel\" was not injected: check your FXML file 'memApply.fxml'.";
        assert colCheck != null : "fx:id=\"colCheck\" was not injected: check your FXML file 'memApply.fxml'.";
        assert btnAll != null : "fx:id=\"btnAll\" was not injected: check your FXML file 'memApply.fxml'.";
        assert btnNo != null : "fx:id=\"btnNo\" was not injected: check your FXML file 'memApply.fxml'.";
        
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IMem_manageService) reg.lookup("memMana");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        btnAll.setFocusTraversable(false);
        btnNo.setFocusTraversable(false);
        
        getList();
        colNM.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMvo().getMem_nm()));
        colNM.setStyle("-fx-alignment: CENTER;");
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMvo().getMem_id()));
        colId.setStyle("-fx-alignment: CENTER;");
        colMail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMvo().getMem_em()));
        colMail.setStyle("-fx-alignment: CENTER;");
        cokTel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMvo().getMem_ph()));
        cokTel.setStyle("-fx-alignment: CENTER;");
        colCheck.setCellValueFactory(new PropertyValueFactory<>("ch"));
        colCheck.setStyle("-fx-alignment: CENTER;");
    }
    public void getList(){
    	try {
			accessList = service.applyList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	List<MemberApply> list = new ArrayList<MemberApply>();
    	for (MemberVO vo : accessList){
    		CheckBox ch = new CheckBox();
			MemberApply ap = new MemberApply();
			ap.setMvo(vo);
			ap.setCh(ch);
			list.add(ap);
		}
    	dataList = FXCollections.observableArrayList(list);
    	table.setItems(dataList);
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
