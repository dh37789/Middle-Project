package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.CommunicationException;

import home.community.service.IFreeBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import vo.AdminVO;
import vo.CommentVO;
import vo.Free_boardVO;
import vo.MemberVO;

public class FreeBoard_ViewController {
	private IFreeBoardService service;

	@FXML
	private AnchorPane pane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField frbd_ti;

	@FXML
	private TextField frbd_dt;

	@FXML
	private TextArea frbd_con;

	@FXML
	private Button btn_exit;

	@FXML
	private Button btn_remove;

	@FXML
	private TextField frbd_wrr;

	private CommentVO comvo;

	private List<CommentVO> comList;

	@FXML
	private ScrollPane paneReply;

	@FXML
	private VBox paneV;

	@FXML
	private TextField tfReply;

	// 댓글 보여주기
	public void viewReply() {
		try {
			comList = service.getFbComment(fvo.getFrbd_id());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < comList.size(); i++) {
			HBox reBox = new HBox(10);
			VBox total = new VBox();
			HBox idBox = new HBox();
			Label lbId = new Label(comList.get(i).getCmt_memid() + " : ");
			HBox conBox = new HBox();
			conBox.setPrefWidth(400);
			TextFlow lbCon = new TextFlow(new Text(comList.get(i).getCmt_con()));
			HBox btnBox = new HBox();
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
							infoAlert(null, "댓글이 삭제되었습니다.");
						} else {
							alert(null, "댓글 삭제에 실패했습니다.");
						}
					}
				});
				btnBox.getChildren().add(btnDel);

			idBox.getChildren().add(lbId);
			conBox.getChildren().add(lbCon);
			reBox.getChildren().addAll(idBox, conBox, btnBox);
			Separator line = new Separator();

			total.getChildren().addAll(reBox, line);
			paneV.getChildren().addAll(total);
			paneV.setPrefHeight(20);
			paneV.setAlignment(Pos.CENTER_LEFT);
		}
	}

	// 종료
	@FXML
	void exit(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent root = loader.load();

		FreeBoardController fbController = loader.getController();
		fbController.setAvo(avo);

		pane.getChildren().setAll(root);
	}


	// 글삭제
	@FXML
	void remove(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "게시글을 삭제하시겠습니까?");
		if (ans == ButtonType.OK) {
			String id = fvo.getFrbd_id();
			try {
				int a = service.FreeBoardDelete(id);
				if(a==1) {
					infoAlert(null, "삭제성공");
				}else {
					infoAlert(null,"삭제실패");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			pane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
			Parent root = loader.load();
			
			FreeBoardController fbController = loader.getController();
			fbController.setAvo(avo);
			
			pane.getChildren().setAll(root);
		}
	}


	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	private Free_boardVO fvo;

	public void setFvo(Free_boardVO fvo) {
		this.fvo = fvo;
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert frbd_ti != null : "fx:id=\"frbd_ti\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert frbd_dt != null : "fx:id=\"frbd_dt\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert frbd_con != null : "fx:id=\"frbd_con\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert btn_exit != null : "fx:id=\"btn_exit\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert btn_remove != null : "fx:id=\"btn_remove\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert frbd_wrr != null : "fx:id=\"frbd_wrr\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");

		btn_exit.setFocusTraversable(false);
		btn_remove.setFocusTraversable(false);
		
		paneV.setFocusTraversable(false);
		
		frbd_con.setMouseTransparent(true);
		frbd_dt.setMouseTransparent(true);
		frbd_ti.setMouseTransparent(true);
		frbd_wrr.setMouseTransparent(true);
		paneV.setMouseTransparent(true);
	}

	// 리스트에서 클릭한항목의 객체를 설정
	public void setTable(Free_boardVO fvo) throws RemoteException {
		frbd_ti.setText(fvo.getFrbd_ti());
		frbd_dt.setText(fvo.getFrbd_dt());
		frbd_con.setText(fvo.getFrbd_con());
		frbd_wrr.setText(fvo.getFrbd_wrr());

	}

	// 로그인한 회원과 선택한 게시글의 작성자가 같은지 확인.

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
