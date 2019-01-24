package admin.comm_manage.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FreeBoardMain extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("D:\\A_TeachingMaterial\\4.MiddleProject\\workspace\\CampusClient\\src\\home\\main\\controller\\homeCommunity.fxml"));
		Parent root = loader.load();
		
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("커뮤니티메인화면");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
