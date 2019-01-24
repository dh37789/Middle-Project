package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import home.community.service.ICommunityService;
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
import vo.Tip_boardVO;

public class Comm_tip_viewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private AnchorPane pane;

	@FXML
	private URL location;

	@FXML
	private TextField tfTitle;

	@FXML
	private TextField tfDate;

	@FXML
	private TextArea taContents;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnDel;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnReply;

	@FXML
	private VBox paneV;

	@FXML
	private TextField tfReply;

	private CommentVO comvo;

	private List<CommentVO> comList;

	@FXML
	void inReply(ActionEvent event) {
		comvo = new CommentVO();
		comvo.setCmt_con(tfReply.getText());
		comvo.setCmt_popbd_id("");
		comvo.setCmt_qabd_id("");
		comvo.setCmt_frbd_id("");
		comvo.setCmt_tbd_id(tvo.getTbd_id());
		comvo.setCmt_memid(mvo.getMem_id());
		int cnt = 0;
		try {
			cnt = service.insertTbCmt(comvo);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		if (cnt > 0) {
			infoAlert(null, "댓글이 등록되었습니다.");
		} else {
			alert(null, "댓글 등록에 실패하였습니다.");
		}
		paneV.getChildren().clear();
		viewReply();
		tfReply.clear();
	}

	public void viewReply() {
		try {
			comList = service.getTbComment(tvo.getTbd_id());
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
							cnt = service.delTbComment(cmt_id);
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
			}

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

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	private ICommunityService service;
	private Tip_boardVO tvo;

	public void setTvo(Tip_boardVO tvo) {
		this.tvo = tvo;
	}

	@FXML
	void exit(ActionEvent event) throws IOException {
		viewChange();
	}

	// 회원 게시글 삭제
	@FXML
	void delete(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "삭제하시겠습니까?");
		if (ans == ButtonType.OK) {
			String tbd_id = tvo.getTbd_id();
			try {
				int a = service.deleteTip(tbd_id);
				if (a > 0) {
					alert(null, "게시글 삭제 성공");
				} else {
					alert(null, "게시글 삭제 실패");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			viewChange();
		}
	}

	// 회원 게시글 수정 가능
	@FXML
	void update(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "수정하시겠습니까?");
		if (ans == ButtonType.OK) {
			pane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip_up.fxml"));
			Parent root = loader.load();

			Comm_tip_upController upController = loader.getController();
			upController.setMvo(mvo);
			upController.setTvo(tvo);
			upController.setTable(tvo);

			pane.getChildren().setAll(root);
		}
	}

	// 회원 게시글 수정 저장
	@FXML
	void save(ActionEvent event) throws IOException {
		if (tfTitle.getText().isEmpty()) {
			alert(null, "제목이 비어있습니다.");
			tfTitle.requestFocus();
			return;
		}
		if (taContents.getText().isEmpty()) {
			alert(null, "본문을 입력하세요.");
			taContents.requestFocus();
			return;
		}
		tvo.setTbd_ti(tfTitle.getText());
		tvo.setTbd_con(taContents.getText());

		int cnt = service.updateTip(tvo);

		if (cnt > 0) {
			alert(null, "게시글이 수정되었습니다.");
		} else {
			alert(null, "게시글 수정을 실패했습니다.");
		}
		viewChange();
	}

	void viewChange() throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip.fxml"));
		Parent root = loader.load();
		Comm_tipController tipController = loader.getController();
		tipController.setMvo(mvo);
		pane.getChildren().setAll(root);
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert tfReply != null : "fx:id=\"tfReply\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert btnDel != null : "fx:id=\"btnDel\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
	    assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_tip_view.fxml'.";
		assert paneV != null : "fx:id=\"paneV\" was not injected: check your FXML file 'community_freeBoard_view.fxml'.";
		assert btnReply != null : "fx:id=\"btnReply\" was not injected: check your FXML file 'comm_tip_view.fxml'.";

		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ICommunityService) reg.lookup("community");
		
		tfDate.setEditable(false);
		tfTitle.setEditable(false);
		taContents.setEditable(false);
		
		tfDate.setMouseTransparent(true);
		tfTitle.setMouseTransparent(true);
		taContents.setMouseTransparent(true);
		
		btnDel.setFocusTraversable(false);
		btnExit.setFocusTraversable(false);
		btnUpdate.setFocusTraversable(false);
		btnReply.setFocusTraversable(false);
	}

	// 알림창 뜨게
	private void alert(String header, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public void viewBoard() {
		tfTitle.setText(tvo.getTbd_ti());
		tfDate.setText(tvo.getTbd_dt());
		taContents.setText(tvo.getTbd_con());

	}

	public void memChk() {
		String memId = mvo.getMem_id();
		String tbId = tvo.getTbd_memID();
		if (memId.equals(tbId)) {
			btnDel.setDisable(false);
			btnUpdate.setDisable(false);
		}
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
