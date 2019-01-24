package admin.classroom_manage.comm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.service.IadminClassCommSerivce;
import classRoom.board.controller.SubBoardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vo.Homework_boardVO;

public class AdmSubBoardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private TextField tfTi;

    @FXML
    private TextField tfSub;

    @FXML
    private TextField tfDate;

    @FXML
    private Label lbMem;

    @FXML
    private TextArea taCon;

    @FXML
    private TextField tfFrt;

    @FXML
    private Button btnDown;

    @FXML
    private Button btnDelete;

    private Stage stage;
    
    @FXML
    private Button btnList;

	private IadminClassCommSerivce service;
    
    private Homework_boardVO vo;
    
   	public void setVo(Homework_boardVO vo) {
   		this.vo = vo;
   	}
    
    @FXML
    void delete(ActionEvent event) throws IOException {
    	ButtonType ans= confirm(null, "삭제 하시겠습니까?");
		if (ans == ButtonType.OK) {
			String Hwbd_id = vo.getHwbd_id();
	    	int cnt = 0;
	    	try {
				cnt = service.delHBoard(Hwbd_id);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	    	if (cnt > 0) {
	    		infoAlert(null, "게시글이 삭제되었습니다.");
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
	            Parent root = loader.load();
	            Pane aPane = (AnchorPane)pane.getParent();
	            aPane.getChildren().setAll(root);
			}else {
				alert(null, "게시글 삭제에 실패하였습니다.");
				return;
			}
		}else {
			return;
		}
    }

    @FXML
    void download(ActionEvent event) {
    	stage = (Stage) btnDown.getScene().getWindow();
		ButtonType ans= confirm(null, "다운 받으시겠습니까?");
		if (ans == ButtonType.OK) {
			FileInputStream fin = null;
			FileOutputStream fout = null;
			String dir = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/";
			File file = new File(dir + vo.getHwbd_memId() + "/" + tfFrt.getText());
			try {
				fin = new FileInputStream(file);
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("All File", "*.*"));
				File selectedFile = fileChooser.showSaveDialog(stage);
				// 가져온 File이 없을 경우에 null
				if (selectedFile==null) {
					return;
				}
				String temp = selectedFile.getPath();
				String ext = file.getPath().substring(
						file.getPath().lastIndexOf("."), file.getPath().length()
						);
				if (!temp.endsWith(ext)) {
					temp = temp + ext;
				}
				fout = new FileOutputStream(temp);
				int c;
				while((c = fin.read()) != -1){
					fout.write(c);
				}
				infoAlert(null, "다운로드가 완료되었습니다.");
				fin.close();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

    @FXML
    void goList(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
         Parent root = loader.load();
         Pane aPane = (AnchorPane)pane.getParent();
         aPane.getChildren().setAll(root);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert tfSub != null : "fx:id=\"tfSub\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert lbMem != null : "fx:id=\"lbMem\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert tfFrt != null : "fx:id=\"tfFrt\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert btnDown != null : "fx:id=\"btnDown\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        assert btnList != null : "fx:id=\"btnList\" was not injected: check your FXML file 'admSubBoardView.fxml'.";
        Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IadminClassCommSerivce) reg.lookup("adminClass");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        tfTi.setEditable(false);
        tfSub.setEditable(false);
        tfDate.setEditable(false);
        taCon.setEditable(false);
        tfFrt.setEditable(false);
        tfTi.setFocusTraversable(false);
        tfSub.setFocusTraversable(false);
        tfDate.setFocusTraversable(false);
        taCon.setFocusTraversable(false);
        tfFrt.setFocusTraversable(false);
        tfTi.setMouseTransparent(true);
        tfSub.setMouseTransparent(true);
        taCon.setMouseTransparent(true);
        tfFrt.setMouseTransparent(true);
        tfDate.setMouseTransparent(true);
        
        btnDelete.setFocusTraversable(false);
        btnDown.setFocusTraversable(false);
        btnList.setFocusTraversable(false);
    }
    public void viewBoard() {
		lbMem.setText(vo.getHwbd_wrr());
		tfTi.setText(vo.getHwbd_ti());
		tfSub.setText(vo.getHwbd_sub());
		tfFrt.setText(vo.getHwbd_frt());
		taCon.setText(vo.getHwbd_con());
		tfDate.setText(vo.getHwbd_dt());
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
