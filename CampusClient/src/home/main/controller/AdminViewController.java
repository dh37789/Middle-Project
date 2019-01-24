package home.main.controller;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class AdminViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    void ddit(MouseEvent event) {
    	try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI URL = new URI("http://www.ddit.or.kr/");
			desktop.browse(URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'adminView.fxml'.";

    }
}
