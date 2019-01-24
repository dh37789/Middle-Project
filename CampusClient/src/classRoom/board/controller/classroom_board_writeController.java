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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vo.MemberVO;
import vo.NbdVO;

public class classroom_board_writeController {

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
    private HBox writerbox;

    @FXML
    private HBox backbox;
    
    @FXML
    private TextField tffilepath;

    @FXML
    private Button searchfile;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
    

    void insert() throws IOException{
    	
    	
    	if(tftitle.getText().isEmpty() ) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText(null);
    		alert.setContentText("id값을 입력하세요");
    		alert.showAndWait();
    		tftitle.requestFocus();
    		return;
    	}
    	
    	
    	NbdVO vo = new NbdVO();
    	
    	vo.setNbd_id("2");
    	vo.setNbd_ti(tftitle.getText().trim());
    	vo.setNbd_admId("admin"); // 관리자 계정 값 가져와야함
    	vo.setNbd_con(tarea.getText().trim());
    	vo.setNbd_wrr("관리자");
    	vo.setNbd_file(tffilepath.getText());
    	try {
			int result = service.insertnbd(vo);
			if(result >0) {
				if(!tffilepath.getText().isEmpty()) {
		  			FileInputStream fin = null;
	    			FileOutputStream fout = null;
					File file = new File(filetemp);
					fin = new FileInputStream(file);
					String folder = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/admin";
					File newDir = new File(folder); 
					if (!newDir.exists()) {
						newDir.mkdir();
					}
					fout = new FileOutputStream(folder + "/" + tffilepath.getText());
					int c;
					
					while((c = fin.read()) != -1){
						fout.write(c);
					}
					System.out.println("작업 종료..");
					fin.close();
					fout.close();
				}
				
				
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setContentText("입력성공!");
	    		alert.showAndWait();
				back();
			}else {
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText(null);
	    		alert.setContentText("입력실패!");
	    		alert.showAndWait();
	    		tftitle.setText("");
	    		tfwriter.setText("");
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    }
    // 공지사항으로 돌아가기
    void back() {
    	root.getChildren().clear();
      	Parent login =null;
		try {
			login = FXMLLoader.load(getClass().getResource("classroom_board.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
      	root.getChildren().setAll(login);
    }
    private IBoardService service;
    private String filetemp;
    
    
    // 파일 경로설정
    private Stage stage;
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
    	   assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert tftitle != null : "fx:id=\"tftitle\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert tfwriter != null : "fx:id=\"tfwriter\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert tarea != null : "fx:id=\"tarea\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert tffilepath != null : "fx:id=\"tffilepath\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert searchfile != null : "fx:id=\"searchfile\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert writerbox != null : "fx:id=\"writerbox\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
           assert backbox != null : "fx:id=\"backbox\" was not injected: check your FXML file 'classroom_boardwrite.fxml'.";
        
        tfwriter.isDisable();
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

        searchfile.setFocusTraversable(false);
        
        tfwriter.setEditable(true);
        
        backbox.setOnMouseClicked(e->{
     	   back();
        });
        
        
        //글쓰기를 눌렀을떄 insert하기
        writerbox.setOnMouseClicked(e->{
        	try {
				insert();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        
        writerbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
        	
        	@Override
        	public void handle(Event event) {
        		writerbox.setCursor(Cursor.HAND);
        	}
        });
        backbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
        	
        	@Override
        	public void handle(Event event) {
        		backbox.setCursor(Cursor.HAND);
        	}
        });
        
        
    }
}
