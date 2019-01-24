package classRoom.board.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import classRoom.board.service.IBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import javafx.stage.Stage;
import vo.CommentVO;
import vo.MemberVO;
import vo.Post_boardVO;

public class LecPostBoardViewController {

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
	private VBox paneV;

	@FXML
	private TextField tfComm;

	@FXML
	private Button btnComm;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnList;
    
    private IBoardService service;
    
    private Node node;
    
    private Stage stage;
    
    private MemberVO memvo;
    
    private CommentVO comvo;
    
    private List<CommentVO> comList;
    
    public void setNode(Node node) {
		this.node = node;
	}
    
    public void setMemvo(MemberVO memvo) {
    	this.memvo = memvo;
    	if(memvo==null) {
    		btnUpdate.setDisable(false);
    	}
    	else {
            tfComm.setVisible(true);
            btnComm.setVisible(true);
    	}
    }
    
    private Post_boardVO vo;
    
    public void setVo(Post_boardVO vo) {
		this.vo = vo;
	}

    @FXML
    void delete(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "삭제 하시겠습니까?");
    	if(ans == ButtonType.OK) {
    		String popbd_id = vo.getPopbd_id();
    		int cnt = 0;
    		try {
				cnt = service.deleteBoard(popbd_id);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		if(cnt>0) {
    			info(null, "게시글이 삭제되었습니다.");
    			pane.getChildren().clear();
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoard.fxml"));
    			Parent root = loader.load();
    			
    			LecPostBoardController postController = loader.getController();
    			postController.setMemvo(memvo);
    			
    			AnchorPane boardPane = (AnchorPane) pane.getParent();
    	    	boardPane.getChildren().setAll(root);
    		}else {
    			alert(null, "게시글 삭제에 실패했습니다.");
    			return;
    		}
    	}else {
    		return;
    	}
    }

    @FXML
    void goList(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoard.fxml"));
    	Parent root = loader.load();
    	
    	LecPostBoardController postController = loader.getController();
    	postController.setMemvo(memvo);
    	
    	AnchorPane boardPane = (AnchorPane) pane.getParent();
    	boardPane.getChildren().setAll(root);
    }
    
    @FXML
    void inReply(ActionEvent event) {
   	 	comvo = new CommentVO();
   	 	
   	 	comvo.setCmt_con(tfComm.getText());
   	 	comvo.setCmt_popbd_id(vo.getPopbd_id());
   	 	if(memvo==null) {
   	 		comvo.setCmt_memid("admin");
   	 	}
   	 	else {
   	 		comvo.setCmt_memid(memvo.getMem_id());
   	 	}
   	 	int cnt = 0;
   	 	try {
			cnt = service.insertPostCmt(comvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
   	 	if(cnt > 0) {
   	 		info(null, "댓글이 작성되었습니다.");
   	 	}else {
   	 		alert(null, "댓글 작성에 실패했습니다.");
   	 	}
   	 	paneV.getChildren().clear();
   	 	viewReply();
   	 	tfComm.clear();		
    }
    public void viewReply() {
		try {
			comList = service.getPostComment(vo.getPopbd_id());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < comList.size(); i++) {
			HBox reBox = new HBox(10);
			VBox total = new VBox();
			HBox idBox = new HBox();
			Label lbId = new Label(comList.get(i).getCmt_memid() + " : ");
			HBox conBox = new HBox();
			conBox.setPrefWidth(620);
			TextFlow lbCon = new TextFlow(new Text(comList.get(i).getCmt_con()));
			HBox btnBox = new HBox();
			if(comList==null) {
				return;
			}
			if ( memvo ==null||comList.get(i).getCmt_memid().equals(memvo.getMem_id()) ){
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
						if (cnt > 0) {
							info(null, "댓글이 삭제되었습니다.");
						} else {
							alert(null, "댓글 삭제에 실패했습니다.");
						}
					}
				});
				btnBox.getChildren().add(btnDel);
			}
			idBox.getChildren().add(lbId);
			conBox.getChildren().add(lbCon);
			reBox.getChildren().addAll(idBox, conBox, btnBox);
			Separator line = new Separator();
			paneV.setPrefHeight(50);
			total.getChildren().addAll(reBox, line);
	        paneV.getChildren().addAll(total);
			paneV.setAlignment(Pos.CENTER_LEFT);
		}
	}
    
    @FXML
    void update(ActionEvent event) throws IOException {
    	ButtonType ans = confirm(null, "수정하시겠습니까?");
    	if(ans == ButtonType.OK) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecPostBoardUpdate.fxml"));
    		Parent root = loader.load();
    		
    		LecPostBoardUpdateController upPostController = loader.getController();
    		
    		upPostController.setMemVo(memvo);
    		upPostController.setVo(vo);
    		upPostController.viewBoard();
    		
    		AnchorPane boardPane = (AnchorPane) pane.getParent();
    		boardPane.getChildren().setAll(root);
    	}
    	
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert tfTi != null : "fx:id=\"tfTi\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert tfSub != null : "fx:id=\"tfSub\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert lbMem != null : "fx:id=\"lbMem\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert taCon != null : "fx:id=\"taCon\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        assert btnList != null : "fx:id=\"btnList\" was not injected: check your FXML file 'lecPostBoardView.fxml'.";
        tfComm.setVisible(false);
        btnComm.setVisible(false);
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
        
        btnComm.setFocusTraversable(false);
        btnDelete.setFocusTraversable(false);
        btnList.setFocusTraversable(false);
        btnUpdate.setFocusTraversable(false);
    }
    /**
     * 회원 아이디와 게시글의 아이디를 비교해서 본인 글인지 확인 
     */
    public void memBoardChk() {
    	if(memvo !=null) {
    		String memId = memvo.getMem_id();
    		String boadrId = vo.getPopbd_memId();
    		if(memId.equals(boadrId)) {
    			btnDelete.setDisable(false);
    			btnUpdate.setDisable(false);
    		}
    		
    	}
    	else {
    		btnUpdate.setVisible(false);
    		btnDelete.setDisable(false);
    	}
    }
    
	public void viewBoard() {
		lbMem.setText(vo.getPopbd_wrr());
		tfTi.setText(vo.getPopbd_ti());
		tfSub.setText(vo.getPopbd_sub());
		taCon.setText(vo.getPopbd_con());
		tfDate.setText(vo.getPopbd_dt());
	}
	
	public void alert(String header, String msg) {
		Alert warning = new Alert(AlertType.WARNING);
		warning.setTitle("경고");
		warning.setHeaderText(header);
		warning.setContentText(msg);
		
		warning.showAndWait();
	}
	
	public void info(String header, String msg) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("알림");
		info.setHeaderText(header);
		info.setContentText(msg);
		
		info.showAndWait();
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