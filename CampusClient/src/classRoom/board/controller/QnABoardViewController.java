package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import classRoom.board.service.IBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import vo.CommentVO;
import vo.MemberVO;
import vo.Qna_boardVO;

public class QnABoardViewController {

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
    private ScrollPane paneReply;

    @FXML
    private TextField tfReply;

    @FXML
    private Button btnReply;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnList;

    @FXML
    private VBox paneV;

    private MemberVO mvo;
    
    private Qna_boardVO qvo;
    
    private IBoardService service;

    private CommentVO comvo;
    
    private List<CommentVO> comList;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setQvo(Qna_boardVO qvo) {
		this.qvo = qvo;
	}

	@FXML
    void delete(ActionEvent event) throws IOException {
		ButtonType ans= confirm(null, "게시글을 삭제 하시겠습니까?");
		if (ans == ButtonType.OK) {
			String qnaId = qvo.getQabd_id();
			int cnt = 0;
			cnt = service.qnaDelete(qnaId);
			if (cnt > 0) {
				infoAlert(null,"게시글이 삭제되었습니다.");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
		    	Parent homework = loader.load();
		    	
		    	QnABoardController subCon = loader.getController();
		    	subCon.setMvo(mvo);
		    	
		    	AnchorPane boardPane = (AnchorPane)pane.getParent();
		    	boardPane.getChildren().setAll(homework);
			}else {
				alert(null, "게시글 삭제에 실패하였습니다.");
			}
		}else {
			return;
		}
    }

    @FXML
    void goList(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
    	Parent homework = loader.load();
    	
    	QnABoardController subCon = loader.getController();
    	subCon.setMvo(mvo);
    	
    	AnchorPane boardPane = (AnchorPane)pane.getParent();
    	boardPane.getChildren().setAll(homework);
    }

    @FXML
    void inReply(ActionEvent event) {
    	comvo = new CommentVO();
    	comvo.setCmt_con(tfReply.getText());
    	comvo.setCmt_qabd_id(qvo.getQabd_id());
    	comvo.setCmt_memid(mvo.getMem_id());
    	int cnt = 0;
    	try {
			cnt = service.insertCmt(comvo);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	if (cnt > 0) {
			infoAlert(null, "댓글이 등록되었습니다.");
		}else {
			alert(null, "댓글 등록에 실패하였습니다.");
			return;
		}
    	paneV.getChildren().clear();
    	viewReply();
    	tfReply.clear();
    }
    public void viewReply(){
    	try {
			comList = service.getComment(qvo.getQabd_id());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	for (int i = 0; i < comList.size(); i++) {
    		HBox reBox = new HBox(10);
    		VBox total = new VBox();
        	HBox idBox = new HBox();
        	Label lbId = new Label();
        	if (comList.get(i).getCmt_memid() != null) {
        		lbId.setText(comList.get(i).getCmt_memid() + " : ");
			}else {
				lbId.setText(comList.get(i).getCmt_admid() + " : ");
				lbId.setStyle("-fx-text-fill : RED");
			}
        	HBox conBox = new HBox();
        	conBox.setPrefWidth(620);
        	TextFlow lbCon = new TextFlow(new Text(comList.get(i).getCmt_con()));
        	HBox btnBox = new HBox();
        	if (comList.get(i).getCmt_memid()!=null) {
        		if (comList.get(i).getCmt_memid().equals(mvo.getMem_id())) {
        			String cmt_id = comList.get(i).getCmt_id();
        			Button btnDel = new Button("삭제");
        			btnDel.setStyle("-fx-background-color: transparent; -fx-underline: true; -fx-text-fill: blue;");
        			btnDel.setOnAction(e -> {
        				ButtonType confirm = confirm(null, "댓글을 삭제 하시겠습니까?");
        				if (confirm == ButtonType.OK) {
        					paneV.getChildren().remove(total);
        					int cnt = 0;
        					try {
        						cnt = service.delComment(cmt_id);
        					} catch (RemoteException e1) {
        						e1.printStackTrace();
        					}
        					if (cnt>0) {
        						infoAlert(null, "댓글이 삭제되었습니다.");
        					}else {
        						alert(null, "댓글 삭제에 실패했습니다.");
        					}
        				}
        			});
        			btnBox.getChildren().add(btnDel);
        		}
			}
        	idBox.getChildren().add(lbId);
        	conBox.getChildren().add(lbCon);
        	reBox.getChildren().addAll(idBox, conBox, btnBox);
        	Separator line = new Separator();
        	total.getChildren().addAll(reBox, line);
        	paneV.getChildren().addAll(total);
        	paneV.setPrefHeight(50);
        	paneV.setAlignment(Pos.CENTER_LEFT);
		}
    }

    @FXML
    void update(ActionEvent event) throws IOException {
    	ButtonType ans= confirm(null, "게시글을 수정 하시겠습니까?");
		if (ans == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoardUp.fxml"));
			Parent homework = loader.load();
			
			QnaBoardUpController upCon = loader.getController();
			upCon.setMvo(mvo);
			upCon.setQvo(qvo);
			upCon.viewBoard();
			AnchorPane boardPane = (AnchorPane)pane.getParent();
			boardPane.getChildren().setAll(homework);
		}else {
			return;
		}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert tfSub != null : "fx:id=\"tfSub\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert lbMem != null : "fx:id=\"lbMem\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert paneV != null : "fx:id=\"paneV\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert tfReply != null : "fx:id=\"tfReply\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert paneReply != null : "fx:id=\"paneReply\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert btnReply != null : "fx:id=\"btnReply\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        assert btnList != null : "fx:id=\"btnList\" was not injected: check your FXML file 'qnaBoardView.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        paneReply.setStyle("-fx-background-color:transparent;");
        tfTi.setEditable(false);
        tfSub.setEditable(false);
        tfDate.setEditable(false);
        taCon.setEditable(false);
        tfTi.setFocusTraversable(false);
        tfSub.setFocusTraversable(false);
        tfDate.setFocusTraversable(false);
        taCon.setFocusTraversable(false);
        tfTi.setMouseTransparent(true);
        tfSub.setMouseTransparent(true);
        taCon.setMouseTransparent(true);
        tfDate.setMouseTransparent(true);
        
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        
        btnDelete.setFocusTraversable(false);
        btnList.setFocusTraversable(false);
        btnReply.setFocusTraversable(false);
        btnUpdate.setFocusTraversable(false);
    }
    public void viewBoard() {
		lbMem.setText(qvo.getQabd_wrr());
		tfTi.setText(qvo.getQabd_ti());
		taCon.setText(qvo.getQabd_con());
		tfDate.setText(qvo.getQabd_dt());
	}
    public void memBoardChk() {
    	if(mvo==null) {
    		return;
    	}
    	String memId = mvo.getMem_id();
    	String boardId = qvo.getQabd_memid();
    	if (memId.equals(boardId)) {
    		btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
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
    public ButtonType confirm(String header, String msg){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	
    	ButtonType comfirmResult = alert.showAndWait().get();
    	return comfirmResult;
    }
}
