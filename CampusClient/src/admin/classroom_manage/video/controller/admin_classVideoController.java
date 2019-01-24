package admin.classroom_manage.video.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import admin.classroom_manage.video.service.IadminVideoService;
import admin.mem_manage.controller.MemInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import vo.AdminVO;
import vo.MemberVO;
import vo.VideoVO;

public class admin_classVideoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<VideoVO> listview;

    
    private IadminVideoService service;
    
    private Image videoImg= new Image(getClass().getResourceAsStream("video-file2.png"));
    
    ObservableList<VideoVO> ob;
    List<VideoVO> list ;
    
    @FXML
    private Button btnadd;

    private AdminVO adminVo;
    
    
    
    
    public void setAdminVo(AdminVO adminVo) {
		this.adminVo = adminVo;
	}

	@FXML
    void btnaddClick(ActionEvent event) {
    	
    	
    		root.getChildren().clear();
    		
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_VideoAdd.fxml"));
    		Parent videoview=null;
			try {
				videoview = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    		
    		admin_videoAddController infoCon = loader.getController();
//    		infoCon.setAdminVO(adminVo);
    		root.getChildren().setAll(videoview);
    	

    }
    
    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'admin_classVideo.fxml'.";
        assert listview != null : "fx:id=\"listview\" was not injected: check your FXML file 'admin_classVideo.fxml'.";
        assert btnadd != null : "fx:id=\"btnadd\" was not injected: check your FXML file 'admin_classVideo.fxml'.";
        
        Registry reg;
        
        try {
			reg = LocateRegistry.getRegistry("localhost",3333);
			service = (IadminVideoService) reg.lookup("admin_video");
			 list =service.getAllVideo();
			
			ob = FXCollections.observableArrayList(list);
			
			listview.setItems(ob);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        btnadd.setFocusTraversable(false);
        
        
        listview.setOnMouseClicked(e->{
        	if (e.getClickCount()==2&&!listview.getSelectionModel().isEmpty()) {
        		root.getChildren().clear();
        		
        		VideoVO vo = listview.getSelectionModel().getSelectedItem();
        		
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_VideoView.fxml"));
        		Parent videoview=null;
				try {
					videoview = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        		
        		admin_classVideoViewController infoCon = loader.getController();
        		
        		infoCon.setVvo(vo);
        		
        		root.getChildren().setAll(videoview);
        	}
        	
        	
        	
        });
        
        
        listview.setCellFactory(new Callback<ListView<VideoVO>, ListCell<VideoVO>>() {
			@Override
			public ListCell<VideoVO> call(ListView<VideoVO> param) {
				ListCell<VideoVO> cell = new ListCell<VideoVO>() {
					ImageView userView = new ImageView();
					protected void updateItem(VideoVO item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
							setGraphic(null); 
						} else {
								userView.setUserData(item);
								userView.setImage(videoImg);
								
								setText("   강의 명 :" + item.getVd_nm() );
							}
						
//							setText(item.getMem_nm() + "(" + item.getMem_id() + ")");
//						setText("강의 명 :"+item.getVd_nm());
							setGraphic(userView);
							
					};
				};
				return cell;
			}
		});
        
        
        

    }
}
