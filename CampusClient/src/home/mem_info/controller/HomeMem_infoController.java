package home.mem_info.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import vo.MemberVO;

public class HomeMem_infoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnMemInfo;

    @FXML
    private Button btnPassChange;

    @FXML
    private Button btnCoupon;

    @FXML
    private Button btnMemOut;

    @FXML
    private Pane pane;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void coupon(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memCoupon.fxml"));
		Parent memInfo = loader.load();
		
		MemCouponController couponCon = loader.getController();
		couponCon.setMvo(mvo);
		couponCon.checkCake();
		couponCon.checkCoffee();
		couponCon.checkDoughnut();
		pane.getChildren().setAll(memInfo);
		
    }

    @FXML
    void memInfo(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("mem_info.fxml"));
    	Parent memInfo = loader.load();
    	
    	Mem_infoController infoCon = loader.getController();
    	
    	infoCon.setMvo(mvo);
    	infoCon.viewMember();
    	pane.getChildren().setAll(memInfo);
    }

    @FXML
    void memOut(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("memOutPass.fxml"));
    	Parent memInfo = loader.load();
    	
    	MemOutPassController OutPassCon = loader.getController();
    	
    	OutPassCon.setMvo(mvo);
    	
    	pane.getChildren().setAll(memInfo);
    }

    @FXML
    void passChange(ActionEvent event) throws IOException {
    	pane.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("memPass.fxml"));
    	Parent memInfo = loader.load();
    	
    	MemPassController passCon = loader.getController();
    	
    	passCon.setMvo(mvo);
    	
    	pane.getChildren().setAll(memInfo);
    }

    @FXML
    void initialize() throws IOException {
        assert btnMemInfo != null : "fx:id=\"btnMemInfo\" was not injected: check your FXML file 'homeMem_info.fxml'.";
        assert btnPassChange != null : "fx:id=\"btnPassChange\" was not injected: check your FXML file 'homeMem_info.fxml'.";
        assert btnCoupon != null : "fx:id=\"btnCoupon\" was not injected: check your FXML file 'homeMem_info.fxml'.";
        assert btnMemOut != null : "fx:id=\"btnMemOut\" was not injected: check your FXML file 'homeMem_info.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'homeMem_info.fxml'.";
        
    }
    public void viewPage() throws IOException{
    	pane.getChildren().clear();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("mem_info.fxml"));
    	Parent memInfo = loader.load();
    	
    	Mem_infoController infoCon = loader.getController();
    	infoCon.setMvo(mvo);
    	infoCon.viewMember();
    	pane.getChildren().setAll(memInfo);
    }
}
