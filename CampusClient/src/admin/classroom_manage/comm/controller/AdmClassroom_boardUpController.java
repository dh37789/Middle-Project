package admin.classroom_manage.comm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.service.IadminClassCommSerivce;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vo.NbdVO;

public class AdmClassroom_boardUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tftitle;

    @FXML
    private TextField tfwriter;

    @FXML
    private TextArea tarea;

    @FXML
    private TextField tffilepath;

    @FXML
    private Button searchfile;

    @FXML
    private HBox writerbox;

    private String filetemp;
    
    // 파일 경로설정
    private Stage stage;
    
    @FXML
    private HBox backbox;
    
	private IadminClassCommSerivce service;
    
    private NbdVO Nbdvo;
    
    public void setNbdVo(NbdVO vo) {
		this.Nbdvo = vo;
		
		
		tftitle.setText(vo.getNbd_ti());
		tfwriter.setText(vo.getNbd_wrr());
		tarea.setText(vo.getNbd_con());
		if(vo.getNbd_file()==null) {
			tffilepath.setText("파일없음");
		}
		else {
			tffilepath.setText(vo.getNbd_file());
			ImageView imgview = new ImageView(new Image(getClass().getResourceAsStream("save.png")));
			imgview.setFitHeight(15);
			imgview.setFitWidth(15);
		}
    }
    @FXML
    void back(MouseEvent event) throws IOException {
    	ButtonType ans = confirm(null, "저장이 완료되지 않았습니다. 돌아가시겠습니까?");
		if (ans == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_boardView.fxml"));
			Parent root = loader.load();
			AdmClassroom_boardViewController viewController = loader.getController();
			viewController.setNbdVo(Nbdvo);
			Pane pane = (Pane) this.root.getParent();
			pane.getChildren().setAll(root);
		}
    }

    @FXML
    void edit(MouseEvent event) throws IOException {
    	if (tftitle.getText().isEmpty()) {
			alert(null, "제목을 입력해 주세요.");
			tftitle.requestFocus();
			return;
    	}
    	if (tarea.getText().isEmpty()) {
    		alert(null, "본문을 입력해 주세요.");
    		tarea.requestFocus();
    		return;
    	}
    	Nbdvo.setNbd_id(this.Nbdvo.getNbd_id());
    	if (tffilepath.getText()==null || tffilepath.getText().isEmpty()) {
    		Nbdvo.setNbd_file("");
    		tffilepath.setText("");
		}else {
			Nbdvo.setNbd_file(tffilepath.getText());
		}
    	String oldPath = Nbdvo.getNbd_file();
    	Nbdvo.setNbd_ti(tftitle.getText());
    	Nbdvo.setNbd_file(tffilepath.getText());
    	Nbdvo.setNbd_con(tarea.getText());
    	int cnt = 0;
    	try {
			cnt = service.upNBoard(Nbdvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt > 0) {
			infoAlert(null, "수정 성공");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_boardView.fxml"));
			Parent root = loader.load();
			AdmClassroom_boardViewController viewController = loader.getController();
			viewController.setNbdVo(Nbdvo);
			Pane pane = (Pane) this.root.getParent();
			pane.getChildren().setAll(root);
		}else {
			alert(null, "수정 실패");
		}
    }

    @FXML
    void searchfile(ActionEvent event) {
    	stage = (Stage)searchfile.getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("All File", "*.*")
		);
    	File selectFile = fileChooser.showOpenDialog(stage);
		if (selectFile==null) {
			return;
		}
		filetemp = selectFile.getPath();
		tffilepath.setText(selectFile.getPath().substring(selectFile.getPath().lastIndexOf("\\")+1, selectFile.getPath().length()));
    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert tftitle != null : "fx:id=\"tftitle\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert tfwriter != null : "fx:id=\"tfwriter\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert tarea != null : "fx:id=\"tarea\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert tffilepath != null : "fx:id=\"tffilepath\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert searchfile != null : "fx:id=\"searchfile\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert writerbox != null : "fx:id=\"writerbox\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        assert backbox != null : "fx:id=\"backbox\" was not injected: check your FXML file 'admClassroom_boardUp.fxml'.";
        Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IadminClassCommSerivce) reg.lookup("adminClass");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
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

	public ButtonType confirm(String header, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);

		ButtonType comfirmResult = alert.showAndWait().get();
		return comfirmResult;
	}
}
