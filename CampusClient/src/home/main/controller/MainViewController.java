package home.main.controller;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import vo.MemberVO;

public class MainViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    private ImageView ddit;

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

	private MemberVO mvo;

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
    
    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'mainView.fxml'.";
        assert ddit != null : "fx:id=\"ddit\" was not injected: check your FXML file 'mainView.fxml'.";

    }
}
