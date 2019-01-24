package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import classRoom.board.service.IBoardService;
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
import javafx.scene.layout.VBox;
import vo.MemberVO;
import vo.Qna_boardVO;

public class QnABoardAddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private TextField tfTi;

    @FXML
    private TextArea taCon;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfPass;
    
    private MemberVO mvo;
    
    private IBoardService service;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void close(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
    	Parent homework = loader.load();
    	
    	QnABoardController subCon = loader.getController();
    	subCon.setMvo(mvo);
    	
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(homework);
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
    	Qna_boardVO vo = new Qna_boardVO();
    	vo.setQabd_ti(tfTi.getText());
    	vo.setQabd_con(taCon.getText());
    	vo.setQabd_wrr(mvo.getMem_nm());
    	vo.setQabd_memid(mvo.getMem_id());
    	vo.setQabd_ps(tfPass.getText());
    	
    	int cnt = 0;
    	try {
			cnt = service.qnaInsert(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (cnt > 0) {
    		infoAlert(null, "게시글이 작성되었습니다.");
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
        	Parent homework = loader.load();
        	
        	QnABoardController subCon = loader.getController();
        	subCon.setMvo(mvo);
        	
        	AnchorPane boardPane = (AnchorPane)pane.getParent();
        	boardPane.getChildren().setAll(homework);
		}else {
			alert(null, "게시글 작성에 실패하였습니다.");
		}
    	
    	
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'qnaBoardAdd.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        btnAdd.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
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
