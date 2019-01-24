package admin.classroom_manage.question.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuestQain extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("quizManList.fxml"));
		
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("알고리즘 풀기");
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
