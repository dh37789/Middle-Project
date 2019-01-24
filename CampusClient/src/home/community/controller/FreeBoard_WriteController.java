package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.plaf.synth.SynthSliderUI;

import home.community.service.IFreeBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vo.Free_boardVO;
import vo.MemberVO;

public class FreeBoard_WriteController {
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
	private TextArea frbd_con;

	@FXML
	private Button btn_save;

	@FXML
	private Button btn_cancel;

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
	void cancel(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent root = loader.load();
		
		FreeBoardController fbController = loader.getController();
    	fbController.setMvo(mvo);
		pane.getChildren().setAll(root);
	}

	// 저장버튼 눌렀을때
	@FXML
	void save(ActionEvent event) throws IOException {
		String ti = frbd_ti.getText();
		String con = frbd_con.getText();
		
		Free_boardVO vo = new Free_boardVO();
		vo.setFrbd_ti(ti);
		vo.setFrbd_wrr(mvo.getMem_nm());
		vo.setFrbd_con(con);
		vo.setFrbd_iNum(0);
		vo.setFrbd_memId(mvo.getMem_id());
		
		System.out.println(vo.getFrbd_memId());
		System.out.println(vo.getFrbd_wrr());

		int result = service.FreeBoardWrite(vo);

		if (result == 0) {
			System.out.println("실패@@");
		} else {
			System.out.println("성공@@");
		}

		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent root = loader.load();
		
		FreeBoardController fbController = loader.getController();
    	fbController.setMvo(mvo);
		pane.getChildren().setAll(root);
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert frbd_ti != null : "fx:id=\"frbd_ti\" was not injected: check your FXML file 'community_freeBoard_write.fxml'.";
		assert frbd_con != null : "fx:id=\"frbd_con\" was not injected: check your FXML file 'community_freeBoard_write.fxml'.";
		assert btn_save != null : "fx:id=\"btn_save\" was not injected: check your FXML file 'community_freeBoard_write.fxml'.";
		assert btn_cancel != null : "fx:id=\"btn_cancel\" was not injected: check your FXML file 'community_freeBoard_write.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_tip_write.fxml'.";
		
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (IFreeBoardService) reg.lookup("freeboard");
		
		btn_cancel.setFocusTraversable(false);
		btn_save.setFocusTraversable(false);
	}
}
