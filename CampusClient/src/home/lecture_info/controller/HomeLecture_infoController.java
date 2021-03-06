package home.lecture_info.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import home.community.controller.FreeBoard_ViewController;
import home.lecture_info.service.ILecture_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeLecture_infoController {
	ILecture_infoService service;
	private Stage stage;


	@FXML
	private AnchorPane pane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView java1;

	@FXML
	private ImageView java2;

	@FXML
	private ImageView jquery;

	@FXML
	private ImageView jsp;

	@FXML
	private ImageView database;

	@FXML
	void database(MouseEvent event) throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecInfoView_java5.fxml"));
		Parent root = loader.load();

		lecInfoView_java1_Controller viewController = loader.getController();
		viewController.setInfvo(service.getLectureInfo("5"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("데이터베이스 강의정보");
		stage.show();
	}

	@FXML
	void java1(MouseEvent event) throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecInfoView_java1.fxml"));
		Parent root = loader.load();

		lecInfoView_java1_Controller viewController = loader.getController();
		viewController.setInfvo(service.getLectureInfo("1"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("초급Java 강의정보");
		stage.show();
	}

	@FXML
	void java2(MouseEvent event) throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecInfoView_java2.fxml"));
		Parent root = loader.load();

		lecInfoView_java1_Controller viewController = loader.getController();
		viewController.setInfvo(service.getLectureInfo("2"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("고급Java 강의정보");
		stage.show();
	}

	@FXML
	void jquery(MouseEvent event) throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecInfoView_java3.fxml"));
		Parent root = loader.load();

		lecInfoView_java1_Controller viewController = loader.getController();
		viewController.setInfvo(service.getLectureInfo("3"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("jquery 강의정보");
		stage.show();
	}

	@FXML
	void jsp(MouseEvent event) throws IOException {
		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("lecInfoView_java4.fxml"));
		Parent root = loader.load();

		lecInfoView_java1_Controller viewController = loader.getController();
		viewController.setInfvo(service.getLectureInfo("4"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("jsp 강의정보");
		stage.show();
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		assert java1 != null : "fx:id=\"java1\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		assert java2 != null : "fx:id=\"java2\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		assert jquery != null : "fx:id=\"jquery\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		assert jsp != null : "fx:id=\"jsp\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		assert database != null : "fx:id=\"database\" was not injected: check your FXML file 'HomeLecture_info.fxml'.";
		

		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ILecture_infoService) reg.lookup("lecture_info");
	}
}
