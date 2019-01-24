package admin.classroom_manage.comm.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.service.IadminClassCommSerivce;
import classRoom.board.controller.classroom_boardViewController;
import classRoom.board.service.IBoardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import vo.NbdVO;

public class AdmClassroom_boardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private VBox pain;
    
    @FXML
    private TableView<NbdVO> tableview;

    @FXML
    private TableColumn<?, ?> colno;

    @FXML
    private TableColumn<?, ?> col_writer;

    @FXML
    private TableColumn<?, ?> coltitle;

    @FXML
    private TableColumn<ImageView, ImageView> colfile;

    @FXML
    private TableColumn<?, ?> colcount;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<ImageView, ImageView> remove;

	private IadminClassCommSerivce service;
    
    @FXML
    private TextField tfsearch;

    @FXML
    private Pagination page;

    @FXML
    private Button btnNotice;

    @FXML
    private Button btnSubject;

    @FXML
    private HBox writebox;
    
    @FXML
    private Button btnQna;

    @FXML
    private Button brnPost;

    private List<NbdVO> listall;
	
    private ObservableList<NbdVO> ob;
	
    private List<ImageView> imglist;
	
    @FXML
    void notice(ActionEvent event) throws IOException {
    	pain.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_Board.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pain.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void post(ActionEvent event) throws IOException {
    	pain.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admLecPostBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pain.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void qna(ActionEvent event) throws IOException {
    	pain.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admQnaBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pain.getParent();
        aPane.getChildren().setAll(root);
    }

    @FXML
    void subject(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("admSubBoard.fxml"));
        Parent root = loader.load();
        Pane aPane = (Pane) pain.getParent();
        aPane.getChildren().setAll(root);
    }

	void table(List<NbdVO> vo) {

		ob = FXCollections.observableArrayList(vo);
		tableview.setItems(ob);

		colno.setCellValueFactory(new PropertyValueFactory<>("nbd_id"));
		col_writer.setCellValueFactory(new PropertyValueFactory<>("nbd_wrr"));
		colcount.setCellValueFactory(new PropertyValueFactory<>("nbd_iNum"));
		coldate.setCellValueFactory(new PropertyValueFactory<>("nbd_dt"));
		coltitle.setCellValueFactory(new PropertyValueFactory<>("nbd_ti"));

		colfile.setCellFactory(new Callback<TableColumn<ImageView, ImageView>, TableCell<ImageView, ImageView>>() {
			@Override
			public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {
				TableCell<ImageView, ImageView> cell = new TableCell<ImageView, ImageView>() {
					@Override
					public void updateItem(ImageView imgview, boolean empty) {
						String fileName = null;
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							try {
								fileName = ((NbdVO) getTableRow().getItem()).getNbd_file();

							} catch (Exception e) {

							}
							if (fileName != null) {
								Image saveImg = new Image(getClass().getResourceAsStream("save.png"));
								ImageView imgview2 = new ImageView(saveImg);
								imgview2.setFitHeight(10);
								imgview2.setFitWidth(10);
								setGraphic(imgview2);
							} else {
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

		remove.setCellFactory(new Callback<TableColumn<ImageView, ImageView>, TableCell<ImageView, ImageView>>() {
			@Override
			public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {
				TableCell<ImageView, ImageView> cell = new TableCell<ImageView, ImageView>() {
					@Override
					public void updateItem(ImageView imgview, boolean empty) {
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							Image saveImg = new Image(getClass().getResourceAsStream("search.png"));
							ImageView imgview2 = new ImageView(saveImg);
//							imglist.add(imgview2);
							imgview2.setFitHeight(15);
							imgview2.setFitWidth(15);
							setGraphic(imgview2);

						}

					}
				};
				return cell;
			}

		});

	}
	void view(NbdVO vo) throws IOException {

		try {
			service.inum(vo.getNbd_id());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_boardView.fxml"));
			Parent root = loader.load();
			AdmClassroom_boardViewController viewController = loader.getController();
			viewController.setNbdVo(vo);
			Pane pane = (Pane) this.pain.getParent();
			pane.getChildren().setAll(root);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
    @FXML
    void initialize() {
        assert pain != null : "fx:id=\"pain\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert colno != null : "fx:id=\"colno\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert col_writer != null : "fx:id=\"col_writer\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert coltitle != null : "fx:id=\"coltitle\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert colfile != null : "fx:id=\"colfile\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert colcount != null : "fx:id=\"colcount\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert coldate != null : "fx:id=\"coldate\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert remove != null : "fx:id=\"remove\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert tfsearch != null : "fx:id=\"tfsearch\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert btnNotice != null : "fx:id=\"btnNotice\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert btnSubject != null : "fx:id=\"btnSubject\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert btnQna != null : "fx:id=\"btnQna\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert brnPost != null : "fx:id=\"brnPost\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        assert writebox != null : "fx:id=\"writebox\" was not injected: check your FXML file 'admClassroom_Board.fxml'.";
        Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IadminClassCommSerivce) reg.lookup("adminClass");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		try {
			listall = service.getAllnbd();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		table(listall);
		tableview.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				try {
					view(tableview.getSelectionModel().getSelectedItem());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		tfsearch.setOnKeyReleased(e -> {
			List<NbdVO> list = null;
			String str = "%" + tfsearch.getText().trim() + "%";
			try {
				list = service.selectTi(str);
				if ("".equals(str)) {
					table(listall);
				} else {
					table(list);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
		writebox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				writebox.setCursor(Cursor.HAND);
			}
		});
		writebox.setOnMouseClicked(e -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("admClassroom_boardWrite.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AdmClassroom_boardWriteController viewController = loader.getController();
			Pane pane = (Pane) this.pain.getParent();
			pane.getChildren().setAll(root);
		});
    }
}
