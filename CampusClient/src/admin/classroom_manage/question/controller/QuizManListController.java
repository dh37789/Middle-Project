package admin.classroom_manage.question.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import admin.classroom_manage.question.service.IAdminQuizService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vo.Practice_questionVO;

public class QuizManListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox pane;
    
    @FXML
    private TableView<Practice_questionVO> tableQuest;

    @FXML
    private TableColumn<?, ?> colQuest;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<String,String> col_diff;

    @FXML
    private TableColumn<?, ?> colCount;

    @FXML
    private TableColumn<?, ?> colChk;

    @FXML
    private Pagination page;

    @FXML
    private Button btnAdd;

    private IAdminQuizService service;
    
    private Practice_questionVO questionVO;
    
    private ObservableList<Practice_questionVO> datalist;
    
    private List<Practice_questionVO> questList;
    
    private Stage stage;
    
    private int questSize;
    
    private int rowPerPage = 15;
    
    @FXML
    void addQuiz(ActionEvent event) throws IOException {
    	stage = (Stage) btnAdd.getScene().getWindow();

    	Stage addStage = new Stage(StageStyle.DECORATED);
    	addStage.initModality(Modality.WINDOW_MODAL);
    	addStage.initOwner(stage);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("addQuiz.fxml"));
    	Parent root = loader.load();
    	AddQuizController addCon = loader.getController();
    	
    	addCon.setController(this);
    	
    	Scene scene = new Scene(root);
    	addStage.setScene(scene);
    	addStage.show();
    }

    @FXML
    void solve(MouseEvent event) throws IOException {
    	if (event.getClickCount() == 2 && (! tableQuest.getSelectionModel().isEmpty()) ) {
    		questionVO = tableQuest.getSelectionModel().getSelectedItem();
    		stage = (Stage) btnAdd.getScene().getWindow();
    		
    		Stage upStage = new Stage(StageStyle.DECORATED);
    		upStage.initModality(Modality.WINDOW_MODAL);
    		upStage.initOwner(stage);
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("viewQuiz.fxml"));
    		Parent root = loader.load();
    		ViewQuizController upCon = loader.getController();
    		upCon.setQvo(questionVO);
    		upCon.setController(this);
    		upCon.viewQuiz();
    		
    		Scene scene = new Scene(root);
    		upStage.setScene(scene);
    		upStage.show();
    	}
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert tableQuest != null : "fx:id=\"tableQuest\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert colQuest != null : "fx:id=\"colQuest\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert colTitle != null : "fx:id=\"colTitle\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert col_diff != null : "fx:id=\"col_diff\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert colCount != null : "fx:id=\"colCount\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert colChk != null : "fx:id=\"colChk\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'quizManList.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'quizManList.fxml'.";
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IAdminQuizService) reg.lookup("adminQuiz");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        try {
			questList = service.getAllQ();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        getTableData();
		paging();
		
		btnAdd.setFocusTraversable(false);
    }
    public void paging(){
    	int pageCount = questSize / rowPerPage + 
				(questSize%rowPerPage==0 ? 0 : 1);
		page.setPageCount(pageCount);
		page.setCurrentPageIndex(0);
		
		changeTableView(0);
		
		page.currentPageIndexProperty().addListener(
				new ChangeListener<Number>() {
					
					@Override
					public void changed(ObservableValue<? extends Number> observable, 
							Number oldValue, Number newValue) {
						changeTableView(newValue.intValue());
					}
				}
			);
    }
    public void changeTableView(int index) {
		int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
		int toIndex = Math.min(fromIndex + rowPerPage, questSize);

		tableQuest.setItems(FXCollections.observableArrayList(questList.subList(fromIndex, toIndex)));
	}
	public void getTableData() {
		try {
			questList = service.getAllQ();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		datalist = FXCollections.observableArrayList(questList);
		tableQuest.setItems(datalist);
		

        questSize = questList.size();
        colQuest.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_id"));
        colTitle.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_ti"));
        colCount.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_ct"));
        colChk.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_tct"));
		tableQuest.setFocusTraversable(false);
		col_diff.setCellFactory(new Callback<TableColumn<String,String>, TableCell<String,String>>() {
			@Override
			public TableCell<String, String> call(TableColumn<String, String> param) {
				TableCell<String, String> cell = new TableCell<String, String>(){
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						int diff = 0;
						if(empty) {
		            		  setText(null);
		            		  setGraphic(null);
		            	  }else {
		            		  if (((Practice_questionVO)getTableRow().getItem()) != null) {
		            			  try {
		            				  diff = ((Practice_questionVO)getTableRow().getItem()).getPqt_diff();
		            			  } catch (Exception e) {
		            				  e.printStackTrace();
		            			  }
		            			  String star = "";
		            			  for (int i = 0; i < diff; i++) {
		            				  star += "★  ";
		            			  }
		            			  setText(star);
		            			  setFont(Font.font ("Verdana", 12));
		            			  setGraphic(null);
							}
		            	  }
					};
				};
				//cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
	}
}
