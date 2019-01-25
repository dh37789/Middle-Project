package classRoom.board.controller;

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

import classRoom.board.service.IBoardService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vo.AdminVO;
import vo.NbdVO;

public class classroom_boardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tftitle;

    @FXML
    private TextField tfwriter;

    @FXML
    private TextArea tarea;

    @FXML
    private Button backbutton;

    @FXML
    private TextField tfdate;

    @FXML
    private HBox editbox;

    @FXML
    private HBox deletebox;

    @FXML
    private HBox filebox;

    @FXML
    private Label tffile;

    
    private NbdVO Nbdvo;
    
    @FXML
    private AnchorPane root;

    private Stage stage;
    
    private IBoardService service;
    
    private AdminVO avo;
    
    
    
    public void setAvo(AdminVO avo) {
    	
		this.avo = avo;
		
		if(avo==null) {
			editbox.setVisible(false);
			deletebox.setVisible(false);
		}
		
	}
	public void setNbdVo(NbdVO vo) {
		this.Nbdvo = vo;
		
		
		tftitle.setText(vo.getNbd_ti());
		tfwriter.setText(vo.getNbd_wrr());
		tarea.setText(vo.getNbd_con());
		tfdate.setText(vo.getNbd_dt());
		if(vo.getNbd_file()==null) {
			tffile.setText("파일없음");
		}
		else {
			tffile.setText(vo.getNbd_file());
			ImageView imgview = new ImageView(new Image(getClass().getResourceAsStream("save.png")));
			imgview.setFitHeight(15);
			imgview.setFitWidth(15);
			filebox.getChildren().add(imgview);
			
			hover();
		}
		
		deletebox.setOnMouseClicked(e->{
			String id = vo.getNbd_id();
			
			
			try {
				int cnt =  service.nbdDelete(id);
				if(cnt>0) {
					infoAlert(null, "삭제가 완료되었습니다.");
				}
				else {
			    	Alert warning = new Alert(AlertType.WARNING);
			    	warning.setContentText("삭제실패!");
			    	warning.showAndWait();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
		});
		
	}
    //마우스 올려놓기 이벤트
    void hover() {
        filebox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				filebox.setCursor(Cursor.HAND);
			}
		});
        //파일이 있고 클릭했을때의 이벤트
        
        filebox.setOnMouseClicked(e->{
        	
        	stage = (Stage) tfdate.getScene().getWindow();
    		ButtonType ans= confirm(null, "다운 받으시겠습니까?");
    		if (ans == ButtonType.OK) {
    			FileInputStream fin = null;
    			FileOutputStream fout = null;
    			String dir = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/admin";
    			File file = new File(dir +  "/" + tffile.getText());
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
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		}
        });
    }

	@FXML
    void backbuttonClick(ActionEvent event) throws IOException {

		root.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("classroom_Board.fxml"));
		
		Parent root = loader.load();
		
		Classroom_boardController Controller = loader.getController();
		
		
		Controller.setMvo(avo);
		AnchorPane pane = (AnchorPane)this.root.getParent();
		
		pane.getChildren().setAll(root);
		

    }

    @FXML
    void initialize() {
        assert tftitle != null : "fx:id=\"tftitle\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert tfwriter != null : "fx:id=\"tfwriter\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert tarea != null : "fx:id=\"tarea\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert backbutton != null : "fx:id=\"backbutton\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert tfdate != null : "fx:id=\"tfdate\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert editbox != null : "fx:id=\"editbox\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert deletebox != null : "fx:id=\"deletebox\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert filebox != null : "fx:id=\"filebox\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert tffile != null : "fx:id=\"tffile\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'classroom_boardView.fxml'.";
        
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
//        tfdate.setMouseTransparent(true);
//        tffile.setMouseTransparent(true);
//        tftitle.setMouseTransparent(true);
//        tfwriter.setMouseTransparent(true);
//        tarea.setMouseTransparent(true);
        
        
        backbutton.setFocusTraversable(false);

        editbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				editbox.setCursor(Cursor.HAND);
			}
		});
        deletebox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
        	
        	@Override
        	public void handle(Event event) {
        		deletebox.setCursor(Cursor.HAND);
        	}
        });
        backbutton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
        	
        	@Override
        	public void handle(Event event) {
        		backbutton.setCursor(Cursor.HAND);
        	}
        });
        
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
