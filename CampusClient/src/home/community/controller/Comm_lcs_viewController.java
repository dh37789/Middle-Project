package home.community.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import home.community.service.ICommunityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vo.LcsVO;
/**
 * 회원-자격증 정보-보기
 * @author PC03
 *
 */
public class Comm_lcs_viewController {
	
	private ICommunityService service;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea taContents;

    @FXML
    private Button btnExit;

    private LcsVO lvo;
    
    public void setLcvo(LcsVO lvo) {
    	this.lvo = lvo;
    }
    
    // 자격증 정보 목록으로 이동
    @FXML
    void showList(ActionEvent event) throws IOException{
    	pane.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("comm_lcs.fxml"));
    	Parent root = loader.load();
    	
    	pane.getChildren().setAll(root);
    }

    @FXML
    void initialize() throws RemoteException, NotBoundException {
    	  assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
          assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
          assert taContents != null : "fx:id=\"taContents\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
          assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'comm_lcs_view.fxml'.";
          
          Registry reg = LocateRegistry.getRegistry("localhost", 3333);
          service = (ICommunityService)reg.lookup("community");
          
          btnExit.setFocusTraversable(false);
          
          tfTitle.setMouseTransparent(true);
          taContents.setMouseTransparent(true);
    }
    
    public void viewBoard() {
    	tfTitle.setText(lvo.getLcs_nm());
    	taContents.setText(lvo.getLcs_con());
    	
    }
}