package home.community.controller.sikdan;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import home.community.service.IFreeBoardService;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Controller {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    // Get the pane to put the calendar on
    @FXML
	public Pane calendarPane;
    
    
}
