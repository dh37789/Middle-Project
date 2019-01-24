package admin.mem_manage.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import admin.mem_manage.service.IMem_manageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import vo.MemberVO;
import vo.QuizJoinVO;
import vo.VideoJoinVO;

public class MemLearnController {

	private final Image IMG = new Image(getClass().getResourceAsStream("image/check.png"));
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<VideoJoinVO> tableVD;

    @FXML
    private TableColumn<?, ?> vColId;

    @FXML
    private TableColumn<?, ?> vColNM;

    @FXML
    private TableColumn<?, ?> vColCon;

    @FXML
    private TableColumn<ImageView, ImageView> vColStatus;

    @FXML
    private TableView<QuizJoinVO> tableQuiz;

    @FXML
    private TableColumn<?, ?> colQId;

    @FXML
    private TableColumn<?, ?> colQTi;

    @FXML
    private PieChart vdPie;

    @FXML
    private Label lbvdName;
    
    @FXML
    private Label lbQuiz;
    
    @FXML
    private PieChart quizPie;
    
    @FXML
    private TableColumn<ImageView, ImageView> colQStatus;

    private List<VideoJoinVO> vdList;
    
    private List<QuizJoinVO> qzList;
    
    private ObservableList<VideoJoinVO> vdData;
    
    private ObservableList<QuizJoinVO> qzData;
    
    private IMem_manageService service;
    
    private MemberVO mvo;

    private int vdSize;
    
    private int vdEan;
    
    private int quizSize;
    
    private int quizEan;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void cancel(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("memInfo.fxml"));
		Parent Info = loader.load();
		
		MemInfoController infoCon = loader.getController();
		infoCon.setMvo(mvo);
		infoCon.viewMember();
		pane.getChildren().setAll(Info);
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert tableVD != null : "fx:id=\"tableVD\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert vColId != null : "fx:id=\"vColId\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert vColNM != null : "fx:id=\"vColNM\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert vColCon != null : "fx:id=\"vColCon\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert vColStatus != null : "fx:id=\"vColStatus\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert tableQuiz != null : "fx:id=\"tableQuiz\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert colQId != null : "fx:id=\"colQId\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert colQTi != null : "fx:id=\"colQTi\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert colQStatus != null : "fx:id=\"colQStatus\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert vdPie != null : "fx:id=\"vdPie\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert quizPie != null : "fx:id=\"quizPie\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert lbvdName != null : "fx:id=\"lbvdName\" was not injected: check your FXML file 'memLearn.fxml'.";
        assert lbQuiz != null : "fx:id=\"lbQuiz\" was not injected: check your FXML file 'memLearn.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IMem_manageService) reg.lookup("memMana");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        
        btnCancel.setFocusTraversable(false);
        
        vColId.setCellValueFactory(new PropertyValueFactory<>("un_id"));
        vColNM.setCellValueFactory(new PropertyValueFactory<>("un_nm"));
        vColCon.setCellValueFactory(new PropertyValueFactory<>("con_con"));
        vColStatus.setCellFactory(new Callback<TableColumn<ImageView,ImageView>, TableCell<ImageView,ImageView>>() {
        	@Override
        	public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {
        		ImageView imgView = new ImageView();
        		TableCell<ImageView, ImageView> cell = new TableCell<ImageView, ImageView>(){
        			protected void updateItem(ImageView item, boolean empty) {
        				int ean = 0;
        				if (empty) {
        					setText(null);
        					setGraphic(null);
						}else {
							if (getTableRow().getItem()!=null) {
								ean = ((VideoJoinVO)getTableRow().getItem()).getMem_vd_ean();
								if (ean == 1) {
									imgView.setImage(IMG);
									imgView.setFitWidth(10);
									imgView.setFitHeight(10);
									setGraphic(imgView);
									vColStatus.setStyle("");
								}else {
									setText(null);
									setGraphic(null);
								}
							}
						}
        				
        			};
        		};
        		cell.setStyle("-fx-alignment: CENTER;");
        		return cell;
        	}
        });
        colQId.setCellValueFactory(new PropertyValueFactory<>("pqt_id"));
        colQTi.setCellValueFactory(new PropertyValueFactory<>("pqt_ti"));
        colQStatus.setCellFactory(new Callback<TableColumn<ImageView,ImageView>, TableCell<ImageView,ImageView>>() {
			@Override
			public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {
				ImageView imgView = new ImageView();
				TableCell<ImageView, ImageView> cell = new TableCell<ImageView, ImageView>(){
					protected void updateItem(ImageView item, boolean empty) {
						super.updateItem(item, empty);
						int ean = 0;
        				if (empty) {
        					setText(null);
        					setGraphic(null);
						}else {
							if (getTableRow().getItem()!=null) {
								ean = ((QuizJoinVO)getTableRow().getItem()).getEan();
								if (ean == 1) {
									imgView.setImage(IMG);
									imgView.setFitWidth(10);
									imgView.setFitHeight(10);
									setGraphic(imgView);
									//vColStatus.setStyle("");
								}else {
									setText(null);
									setGraphic(null);
								}
							}
						}
        				
					};
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
//        tableVD.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        tableQuiz.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public void getVideoTable(){
    	try {
			vdList = service.getVD(mvo.getMem_id());
			vdSize = service.vdCount();
			vdEan = service.vdEanCount(mvo.getMem_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    	vdData = FXCollections.observableArrayList(vdList);
    	tableVD.setItems(vdData);
    }
    public void vdPieShow() {
    	ObservableList<Data> VPData = 
    			FXCollections.observableArrayList(
    					new PieChart.Data("시청", vdEan),
    					new PieChart.Data("미시청", vdSize - vdEan)
    					);
    	vdPie.setData(VPData);
    	//vdPie.setTitle(mvo.getMem_nm() + "님의 강의 시청 수");
    	vdPie.setLabelLineLength(5);
    	vdPie.setLegendSide(Side.BOTTOM);
    	lbvdName.setText(mvo.getMem_nm() + "님의 강의 시청 비율");
    }
    public void getQuizTable() {
    	try {
			qzList = service.getQuiz(mvo.getMem_id());
			quizSize = service.quizSize();
			quizEan = service.eanQuiz(mvo.getMem_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	qzData = FXCollections.observableArrayList(qzList);
    	tableQuiz.setItems(qzData);
	} 
    public void qzPieShow() {
    	ObservableList<Data> qzData = 
    			FXCollections.observableArrayList(
    					new PieChart.Data("해결", quizEan),
    					new PieChart.Data("미해결", quizSize - quizEan)
    					);
    	quizPie.setData(qzData);
    	//vdPie.setTitle(mvo.getMem_nm() + "님의 강의 시청 수");
    	quizPie.setLabelLineLength(5);
    	quizPie.setLegendSide(Side.BOTTOM);
    	lbQuiz.setText(mvo.getMem_nm() + "님의 문제 해결 비율");
    }
}
