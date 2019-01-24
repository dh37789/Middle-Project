package admin.mem_manage.controller;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import admin.mem_manage.service.IMem_manageService;
import classRoom.question.controller.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vo.MemberVO;
import vo.Practice_questionVO;

public class MemBlackController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane; 

    @FXML
    private TableView<MemberVO> table;

    @FXML
    private TableColumn<?, ?> colNM;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<String,String> colStatus;
    
    private IMem_manageService service;
    
    private List<MemberVO> blackList;
    
    private ObservableList<MemberVO> blackData;
    
    @FXML
    void blackAdd(MouseEvent event) {
    	if (event.getClickCount()==2&&!table.getSelectionModel().isEmpty()) {
    		int access = table.getSelectionModel().getSelectedItem().getMem_access();
    		String name = table.getSelectionModel().getSelectedItem().getMem_nm();
    		String mem_id = table.getSelectionModel().getSelectedItem().getMem_id();
    		if (access == 1) {
    			ButtonType ans = confirm(null, name + "님을 불량 회원으로 지정하시겠습니까?");
    			if (ans == ButtonType.OK) {
					int cnt = 0;
					try {
						cnt = service.addBlack(mem_id);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					getBlackList();
				}else {
					return;
				}
			}else {
				ButtonType ans = confirm(null, name + "님을 일반 회원으로 지정하시겠습니까?");
				if (ans == ButtonType.OK) {
					int cnt = 0;
					try {
						cnt = service.subBlack(mem_id);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					getBlackList();
				}else {
					return;
				}
			}
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert colNM != null : "fx:id=\"comNM\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert colMail != null : "fx:id=\"colAddr\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert colTel != null : "fx:id=\"comTel\" was not injected: check your FXML file 'memBlack.fxml'.";
        assert colStatus != null : "fx:id=\"colStatus\" was not injected: check your FXML file 'memBlack.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IMem_manageService) reg.lookup("memMana");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        colNM.setCellValueFactory(new PropertyValueFactory<>("mem_nm"));
        colId.setCellValueFactory(new PropertyValueFactory<>("mem_id"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mem_em"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("mem_ph"));
        colStatus.setCellFactory(new Callback<TableColumn<String,String>, TableCell<String,String>>() {
        	@Override
        	public TableCell<String, String> call(TableColumn<String, String> param) {
        		TableCell<String, String> cell = new TableCell<String,String>(){
        			protected void updateItem(String item, boolean empty) {
        				int access = 0;
                  	  if(empty) {
                  		  setText(null);
                  		  setGraphic(null);
                  	  }else {
                  		  try {
                  			access = ((MemberVO)getTableRow().getItem()).getMem_access();
      					} catch (Exception e) {
      						e.printStackTrace();
      					}
      						if(access == 1) {
      							setText("일반 회원");
      						}else {
      							setText("불량 회원");
      						}
      						

                  	  }
        			};
        		};
        		 cell.setStyle("-fx-alignment: CENTER;");
                 return cell; 
        	}
		});
    }
    public void getBlackList() {
    	try {
			blackList = service.getBlackList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	blackData = FXCollections.observableArrayList(blackList);
    	table.setItems(blackData);
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
