package admin.comm_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.time.YearMonth;
import java.util.ResourceBundle;

import classRoom.board.controller.SubBoardController;
import home.community.controller.sikdanController;
import home.community.controller.sikdan.FullCalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vo.AdminVO;
import vo.Free_boardVO;
import vo.MemberVO;
import javafx.stage.Stage;

public class HomeCommunityController {
	private Stage stage;
	
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
    
	private Stage cstage;
	
    @FXML
    private AnchorPane community_window;
    
    private AdminVO avo;
    public void setAvo(AdminVO avo) {
    	this.avo = avo;
    }

	//자유게시판 눌렀을때
    @FXML
    void freeBoard(ActionEvent event) throws IOException {
    	community_window.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
    	Parent fbboard = loader.load();
    	
    	FreeBoardController fbController = loader.getController();
    	fbController.setAvo(avo);
    	
    	community_window.getChildren().setAll(fbboard);
    }
    //자격증정보 눌렀을때
    @FXML
    void licenseInfo(ActionEvent event) throws IOException {
    	community_window.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
    	Parent info = loader.load();
    	
    	Comm_lcsController lcsController = loader.getController();
    	lcsController.setAvo(avo);
    	
    	community_window.getChildren().setAll(info);
    }
    
    //자격증tip 선택
    @FXML
    void licenseTip(ActionEvent event) throws IOException {
    	community_window.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_Tip.fxml"));
    	Parent root = loader.load();
    	
    	Comm_TipController tipController = loader.getController();
    	tipController.setAvo(avo);
    	
    	community_window.getChildren().setAll(root);
    }
    
    //식단표 버튼을 클릭했을 떄
    @FXML
    void meal(ActionEvent event) throws IOException, NotBoundException {
//    	Stage stage = new Stage();
//		cstage = (Stage) btnMeal.getScene().getWindow();
//		stage.setTitle("Full Calendar FXML Example");
//		
//		stage.initModality(Modality.WINDOW_MODAL);
//		stage.initOwner(cstage);
//		
//		stage.setTitle("대전도시락 식단표");
//		stage.setScene(new Scene(new FullCalendarView(YearMonth.now()).getView()));
//		stage.show();
    	community_window.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_sikdan.fxml"));
    	Parent root = loader.load();
    	
    	manager_sikdanController sController = loader.getController();
    	community_window.getChildren().setAll(root);
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
        
        btnFrBoard.setFocusTraversable(false);
        btnLicenseInfo.setFocusTraversable(false);
        btnLicenseTip.setFocusTraversable(false);
        btnMeal.setFocusTraversable(false);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("community_freeBoard.fxml"));
		Parent fbboard = loader.load();
		community_window.getChildren().setAll(fbboard);
    }
}
