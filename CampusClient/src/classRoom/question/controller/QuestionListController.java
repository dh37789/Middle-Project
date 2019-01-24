package classRoom.question.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import classRoom.question.service.IQuestionService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vo.MemberVO;
import vo.NbdVO;
import vo.Practice_questionVO;

public class QuestionListController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<?, ?> colTCount;

    @FXML
    private Pagination page;
    
    @FXML
    private TableColumn<ImageView, ImageView> colChk;

    private IQuestionService service;
    
    private Practice_questionVO questionVO;
    
    private ObservableList<Practice_questionVO> datalist;
    
    private List<Practice_questionVO> questList;
    
    private Stage stage;
    
    private int questSize;
    
    private int rowPerPage = 15;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
    void solve(MouseEvent event) throws IOException {
    	stage = (Stage) tableQuest.getScene().getWindow();
    	Practice_questionVO quest = tableQuest.getSelectionModel().getSelectedItem();
    	if (event.getClickCount()==2&&!tableQuest.getSelectionModel().isEmpty()) {
	    	Stage coding = new Stage(StageStyle.DECORATED);
    		int cnt = service.increase(quest.getPqt_id());
    		if (cnt == 1) {
    			getTableData();
			}
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
			Parent root = loader.load();
			
			QuestionController questController = loader.getController();
			questController.setQuestionVO(quest);
			questController.viewQuest();
			questController.setMvo(mvo);
			questController.setController(this);
			coding.initModality(Modality.WINDOW_MODAL);
			coding.initOwner(stage);
			
			Scene scene = new Scene(root);
			questController.setScene(scene);
			questController.cssScene();
			coding.setScene(scene);
			coding.setTitle("알고리즘 풀기");
			coding.show();
    	}
    }

    @FXML
    void initialize() {
    	assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'questionList.fxml'.";
    	assert tableQuest != null : "fx:id=\"tableQuest\" was not injected: check your FXML file 'questionList.fxml'.";
        assert colQuest != null : "fx:id=\"colQuest\" was not injected: check your FXML file 'questionList.fxml'.";
        assert colTitle != null : "fx:id=\"colTitle\" was not injected: check your FXML file 'questionList.fxml'.";
        assert colCount != null : "fx:id=\"colCount\" was not injected: check your FXML file 'questionList.fxml'.";
        assert col_diff != null : "fx:id=\"col_diff\" was not injected: check your FXML file 'questionList.fxml'.";
        assert colTCount != null : "fx:id=\"colTCount\" was not injected: check your FXML file 'questionList.fxml'.";
        assert colChk != null : "fx:id=\"colChk\" was not injected: check your FXML file 'questionList.fxml'.";
        
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IQuestionService) reg.lookup("question");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
        colQuest.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_id"));
        colTitle.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_ti"));
        colCount.setCellValueFactory(
        		new PropertyValueFactory<>("pqt_ct"));
		tableQuest.setFocusTraversable(false);
		col_diff.setCellFactory(new Callback<TableColumn<String,String>, TableCell<String,String>>() {
			@Override
			public TableCell<String, String> call(TableColumn<String, String> param) {
				TableCell<String, String> cell = new TableCell<String, String>(){
					protected void updateItem(String item, boolean empty) {
						int diff = 0;
						if(empty) {
		            		  setText(null);
		            		  setGraphic(null);
		            	  }else {
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
					};
				};
				//cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
		colChk.setCellFactory(new Callback<TableColumn<ImageView,ImageView>,TableCell<ImageView,ImageView>>(){   
            @Override 
            public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {     
             TableCell<ImageView,ImageView> cell = new TableCell<ImageView, ImageView>(){ 
              @Override 
              public void updateItem(ImageView imgview, boolean empty) { 
            	int fileName = 0;
            	  if(empty) {
            		  setText(null);
            		  setGraphic(null);
            	  }else {
            		  try {
            			  fileName = ((Practice_questionVO)getTableRow().getItem()).getMem_pqt_ean();
					} catch (Exception e) {
						e.printStackTrace();
					}
						if(fileName!=0) {
							Image saveImg = new Image(getClass().getResourceAsStream("checked.png"));
							ImageView imgview2 = new ImageView(saveImg);
							imgview2.setFitHeight(10);
							imgview2.setFitWidth(10);
							setGraphic(imgview2);
						}else {
							setText(null);
							setGraphic(null);
						}
						

            	  }
              } 
             }; 
             cell.setStyle("-fx-alignment: CENTER;");
             return cell; 
            } 

        }); 
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
			questList = service.getAllQ(mvo.getMem_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		datalist = FXCollections.observableArrayList(questList);
		tableQuest.setItems(datalist);
		questSize = questList.size();
	}
}
