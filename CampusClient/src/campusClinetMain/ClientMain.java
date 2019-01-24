package campusClinetMain;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.main.controller.LoginController;

public class ClientMain extends Application {
	@Override
	
	
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/main/controller/login.fxml"));
		Parent root = loader.load();
		LoginController login = new LoginController();
		login.setStage(primaryStage);
		login.stageResize();
		
		Scene scene = new Scene(root, 660, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("대덕 사이버 캠퍼스");
		primaryStage.sizeToScene();
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
