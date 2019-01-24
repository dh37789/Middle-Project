package admin.mem_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import admin.mem_manage.service.IMem_manageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import vo.MemberVO;

public class MemInfoListController {
	
	final Image IMG_MEM = new Image(getClass().getResourceAsStream("image/user.png"));
	final Image IMG_BLACK = new Image(getClass().getResourceAsStream("image/user2.png"));
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private ListView<MemberVO> memList;

    private List<MemberVO> list;
    
    private ObservableList<MemberVO> data;
    
    private IMem_manageService service;
    
    @FXML
    void listClick(MouseEvent event) throws IOException {
    	if (event.getClickCount()==2&&!memList.getSelectionModel().isEmpty()) {
    		pane.getChildren().clear();
    		
    		MemberVO mvo = memList.getSelectionModel().getSelectedItem();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfo.fxml"));
    		Parent memInfo = loader.load();
    		
    		MemInfoController infoCon = loader.getController();
    		infoCon.setMvo(mvo);
    		infoCon.viewMember();
    		
    		pane.getChildren().setAll(memInfo);
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memInfoList.fxml'.";
        assert memList != null : "fx:id=\"memList\" was not injected: check your FXML file 'memInfoList.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IMem_manageService) reg.lookup("memMana");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        getAllMember();
        memList.setCellFactory(new Callback<ListView<MemberVO>, ListCell<MemberVO>>() {
			@Override
			public ListCell<MemberVO> call(ListView<MemberVO> param) {
				ListCell<MemberVO> cell = new ListCell<MemberVO>() {
					ImageView userView = new ImageView();
					protected void updateItem(MemberVO item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
							setGraphic(null); 
						} else {
							if (item.getMem_access() == 2) {
								userView.setUserData(item);
								userView.setImage(IMG_BLACK);
							} else {
								userView.setUserData(item);
								userView.setImage(IMG_MEM);
							}
							setText(item.getMem_nm() + "(" + item.getMem_id() + ")");
							setGraphic(userView);
						}
					};
				};
				return cell;
			}
		});
        //memList.setMouseTransparent( false );
        //memList.setFocusTraversable( false );
        
    }
    public void getAllMember() {
    	try {
			list = service.getAllMember();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	data = FXCollections.observableArrayList(list);
    	memList.setItems(data);
    }
}
