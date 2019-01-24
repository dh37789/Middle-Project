package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.time.YearMonth;
import java.util.ResourceBundle;

import classRoom.board.controller.SubBoardController;
import home.community.controller.sikdan.Controller;
import home.community.controller.sikdan.FullCalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vo.AdminVO;
import vo.Free_boardVO;
import vo.MemberVO;
import javafx.stage.Stage;

public class HomeCommunityController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private ImageView back;

	@FXML
	private URL location;

	@FXML
	private AnchorPane pane;

	@FXML
	private Button btnFrBoard;

	@FXML
	private Button btnLicenseInfo;

	@FXML
	private Button btnLicenseTip;

	@FXML
	private Button btnMeal;

	@FXML
	public AnchorPane community_window;

	private Stage cstage;
	private MemberVO mvo;

	private AdminVO avo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setAvo(AdminVO avo) {
		this.avo = avo;
	}

	// 자유게시판 눌렀을때
	@FXML
	void freeBoard(ActionEvent event) throws IOException {
		community_window.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent fbboard = loader.load();

		FreeBoardController fbController = loader.getController();
		fbController.setMvo(mvo);
		fbController.setAvo(avo);

		community_window.getChildren().setAll(fbboard);
	}

	@FXML
	void licenseInfo(ActionEvent event) throws IOException {
		community_window.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
		Parent info = loader.load();

		community_window.getChildren().setAll(info);
	}

	@FXML
	void licenseTip(ActionEvent event) throws IOException {
		community_window.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_tip.fxml"));
		Parent root = loader.load();

		Comm_tipController tipController = loader.getController();
		tipController.setMvo(mvo);

		community_window.getChildren().setAll(root);
	}

	// 식단표 버튼을 클릭했을 떄
	@FXML
	void meal(ActionEvent event) throws IOException, NotBoundException {
		Stage stage = new Stage();
		cstage = (Stage) btnMeal.getScene().getWindow();
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_sikdan.fxml"));


//		FXMLLoader loader = new FXMLLoader(getClass().getResource("sikdan/fullCalendar.fxml"));
		stage.setTitle("Full Calendar FXML Example");
		// Get the controller and add the calendar view to it
//		Parent root = loader.load();
		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(cstage);
		
		stage.setTitle("대전도시락 식단표");
		stage.setScene(new Scene(new FullCalendarView(YearMonth.now()).getView()));
		stage.show();
		
//		Controller controller = loader.getController();
//		controller.calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
//		Scene scene = new Scene(root);
//		stage.setScene(scene);
//		stage.setTitle("대전도시락 식단");
//		stage.show();
	}

	@FXML
	void initialize() throws IOException {
		assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert btnFrBoard != null : "fx:id=\"btnFrBoard\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert btnLicenseInfo != null : "fx:id=\"btnLicenseInfo\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert btnLicenseTip != null : "fx:id=\"btnLicenseTip\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert btnMeal != null : "fx:id=\"btnMeal\" was not injected: check your FXML file 'homeCommunity.fxml'.";
		assert community_window != null : "fx:id=\"community_window\" was not injected: check your FXML file 'homeCommunity.fxml'.";

		final Font font = Font.loadFont(getClass().getResourceAsStream("NanumSquareR.otf"), 16);
		btnLicenseTip.setFont(font);
	}
	public void love(MemberVO mvo) throws IOException {
		
		btnFrBoard.setFocusTraversable(false);
		btnLicenseInfo.setFocusTraversable(false);
		btnLicenseTip.setFocusTraversable(false);
		btnMeal.setFocusTraversable(false);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent fbboard = loader.load();
		community_window.getChildren().setAll(fbboard);
		FreeBoardController fbController = loader.getController();
		fbController.setMvo(mvo);
		community_window.getChildren().setAll(fbboard);
	}
}
