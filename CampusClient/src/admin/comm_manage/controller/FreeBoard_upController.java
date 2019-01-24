package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import home.community.service.IFreeBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import vo.AdminVO;
import vo.Free_boardVO;

public class FreeBoard_upController {
	private IFreeBoardService service;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField frbd_ti;

    @FXML
    private TextField frbd_dt;

    @FXML
    private TextArea frbd_con;

    @FXML
    private Button btn_exit;

    @FXML
    private TextField frbd_wrr;

    @FXML
    private Button btn_save;
    
    private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	private Free_boardVO fvo;

	public void setFvo(Free_boardVO fvo) {
		this.fvo = fvo;
	}

    @FXML
    void exit(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent root = loader.load();

		FreeBoardController fbController = loader.getController();
		fbController.setAvo(avo);

		pane.getChildren().setAll(root);
    }

    @FXML
    void save(ActionEvent event) throws IOException {
    	String ti = frbd_ti.getText();
		String con = frbd_con.getText();

		fvo.setFrbd_ti(ti);
		fvo.setFrbd_con(con);
		fvo.setFrbd_id(fvo.getFrbd_id());

		try {
			int a = service.FreeBoardUpdate(fvo);
			if(a==1) {
				infoAlert(null, "저장성공");
			}else {
				infoAlert(null, "저장실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent root = loader.load();

		FreeBoardController fbController = loader.getController();
		fbController.setAvo(avo);
		pane.getChildren().setAll(root);
    }

    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert frbd_ti != null : "fx:id=\"frbd_ti\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert frbd_dt != null : "fx:id=\"frbd_dt\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert frbd_con != null : "fx:id=\"frbd_con\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert btn_exit != null : "fx:id=\"btn_exit\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert frbd_wrr != null : "fx:id=\"frbd_wrr\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        assert btn_save != null : "fx:id=\"btn_save\" was not injected: check your FXML file 'community_freeBoard_up.fxml'.";
        Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");
    }
    
    public void setTable(Free_boardVO fvo) throws RemoteException {
		frbd_ti.setText(fvo.getFrbd_ti());
		frbd_dt.setText(fvo.getFrbd_dt());
		frbd_con.setText(fvo.getFrbd_con());
		frbd_wrr.setText(fvo.getFrbd_wrr());
	}
	
	public void infoAlert(String header, String msg) {
		Alert warning = new Alert(AlertType.INFORMATION);
		warning.setTitle("알림");
		warning.setHeaderText(header);
		warning.setContentText(msg);

		warning.showAndWait();
	}
}
