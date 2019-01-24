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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vo.Homework_boardVO;
import vo.MemberVO;

public class SubBoardAddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;
    
    @FXML
    private TextField tfTi;

    @FXML
    private ComboBox<String> comSub;

    @FXML
    private TextArea taCon;

    @FXML
    private TextField tfFrt;

    @FXML
    private Button btnFileUp;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    private String[] subList = {"초급자바","Oracle","고급자바","HTML","JSP"};
    
    private ObservableList<String> subData;
    
    private Node node;
    
    private MemberVO mvo;
    
    private IBoardService service;
    
    private Stage stage;
    // 파일 경로
    private String fileTemp;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@FXML
    void FileUp(ActionEvent event) {
		stage = (Stage) btnFileUp.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		// 검색할 파일 종류 설정(주로 확장자 위주로 설정한다.)
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("All File", "*.*")
		);
		File selectFile = fileChooser.showOpenDialog(stage);
		if (selectFile==null) {
			return;
		}
		fileTemp = selectFile.getPath();
		tfFrt.setText(selectFile.getPath().substring(selectFile.getPath().lastIndexOf("\\")+1, selectFile.getPath().length()));
    }

    @FXML
    void close(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("subBoard.fxml"));
    	Parent root = loader.load();
    	
    	SubBoardController subController = loader.getController();
    	subController.setMvo(mvo);
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(root);
    }

    @FXML
    void write(ActionEvent event) throws IOException {
    	if (tfTi.getText().isEmpty()) {
			alert(null, "제목을 입력해 주세요.");
			tfTi.requestFocus();
			return;
    	}
    	if (taCon.getText().isEmpty()) {
    		alert(null, "본문을 입력해 주세요.");
    		tfTi.requestFocus();
    		return;
    	}
    	Homework_boardVO vo = new Homework_boardVO();
    	vo.setHwbd_ti(tfTi.getText());
    	vo.setHwbd_con(taCon.getText());
    	vo.setHwbd_sub(comSub.getSelectionModel().getSelectedItem());
    	vo.setHwbd_wrr(mvo.getMem_nm());
    	vo.setHwbd_memId(mvo.getMem_id());
    	vo.setHwbd_frt(tfFrt.getText());
    	int cnt = 0;
    	try {
			cnt = service.inHomeBoard(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt == 1) {
    		if (!tfFrt.getText().isEmpty()) {
    			FileInputStream fin = null;
    			FileOutputStream fout = null;
				File file = new File(fileTemp);
				fin = new FileInputStream(file);
				String folder = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/" + mvo.getMem_id();
				File newDir = new File(folder); 
				if (!newDir.exists()) {
					newDir.mkdirs();
				}
				fout = new FileOutputStream(folder + "/" + tfFrt.getText());
				int c;
				
				while((c = fin.read()) != -1){
					fout.write(c);
				}
				System.out.println("작업 종료..");
				fin.close();
				fout.close();
			}
			infoAlert(null, "게시글이 등록되었습니다.");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("subBoard.fxml"));
	    	Parent root = loader.load();
	    	
	    	SubBoardController subController = loader.getController();
	    	subController.setMvo(mvo);
	    	AnchorPane boardPane = (AnchorPane)pane.getParent();
	    	boardPane.getChildren().setAll(root);
		}else {
			alert(null, "게시글 등록에 실패하였습니다.");
			return;
		}
    }

    @FXML
    void initialize() {
    	assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert tfFrt != null : "fx:id=\"tfFrt\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert btnFileUp != null : "fx:id=\"btnFileUp\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'subBoardAdd.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        subData = FXCollections.observableArrayList(subList);
        comSub.setItems(subData);
        comSub.setValue(subData.get(0));
        
        btnAdd.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
        btnFileUp.setFocusTraversable(false);
    }
    
    public void WrrWrite(){
    	tfTi.setText(mvo.getMem_nm());
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
}
