package classRoom.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.tool.xml.html.table.TableRow;

import admin.classroom_manage.comm.controller.admin_classCommController;
import classRoom.board.service.IBoardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import vo.AdminVO;
import vo.MemberVO;
import vo.NbdVO;

public class Classroom_boardController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane root;

	@FXML
	private Label subjcetName;

	@FXML
	private TableColumn<ImageView, ImageView> remove;
	@FXML
	private TableView<NbdVO> tableview;

	@FXML
	private TableColumn<?, ?> colno;

	@FXML
	private TableColumn<?, ?> col_writer;

	@FXML
	private TableColumn<?, ?> coltitle;

	@FXML
	private TableColumn<Button, Button> colfile;

	@FXML
	private TableColumn<?, ?> colcount;

	@FXML
	private TableColumn<?, ?> coldate;

	@FXML
	private Pagination page;

	@FXML
	private Button btn_cancel;

	@FXML
	private TextField tfsearch;

	@FXML
	private HBox writebox;

	private AdminVO admin;

	private List<NbdVO> listall;
	
	private IBoardService service;
	
	private ObservableList<NbdVO> ob;
	
	private MemberVO vo;
	
	public void setVo(MemberVO vo) {
		this.vo = vo;
	}

	public void setMvo(AdminVO admin) {
		this.admin = admin;

		/*if (admin == null) {
			writebox.setVisible(false);
		}*/

	}

	public void table() {
		ob = FXCollections.observableArrayList(listall);
		tableview.setItems(ob);
		colno.setCellValueFactory(new PropertyValueFactory<>("nbd_id"));
		colno.setStyle("-fx-alignment: CENTER;");
		col_writer.setCellValueFactory(new PropertyValueFactory<>("nbd_wrr"));
		col_writer.setStyle("-fx-alignment: CENTER;");
		colcount.setCellValueFactory(new PropertyValueFactory<>("nbd_iNum"));
		colcount.setStyle("-fx-alignment: CENTER;");
		coldate.setCellValueFactory(new PropertyValueFactory<>("nbd_dt"));
		coldate.setStyle("-fx-alignment: CENTER;");
		coltitle.setCellValueFactory(new PropertyValueFactory<>("nbd_ti"));
		coltitle.setStyle("-fx-alignment: CENTER;");
		colfile.setCellFactory(new Callback<TableColumn<Button, Button>, TableCell<Button, Button>>() {
			@Override
			public TableCell<Button, Button> call(TableColumn<Button, Button> param) {
				TableCell<Button, Button> cell = new TableCell<Button, Button>() {
					Stage stage;
					Image saveImg = new Image(getClass().getResourceAsStream("save.png"));
					ImageView imgv = new ImageView(saveImg);
					Button btn = new Button();
					{
						imgv.setFitHeight(10);
						imgv.setFitWidth(10);
						btn.setGraphic(imgv);
						btn.setOnAction(e -> {
							stage = (Stage) btn_cancel.getScene().getWindow();
							ButtonType ans= confirm(null, "다운 받으시겠습니까?");
							if (ans == ButtonType.OK) {
								FileInputStream fin = null;
								FileOutputStream fout = null;
								String dir = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/";
								File file = new File(dir + vo.getMem_id() + "/" + ((NbdVO)getTableRow().getItem()).getNbd_file());
								try {
									fin = new FileInputStream(file);
									FileChooser fileChooser = new FileChooser();
									fileChooser.getExtensionFilters().addAll(
											new ExtensionFilter("All File", "*.*"));
									File selectedFile = fileChooser.showSaveDialog(stage);
									// 가져온 File이 없을 경우에 null
									if (selectedFile==null) {
										return;
									}
									String temp = selectedFile.getPath();
									String ext = file.getPath().substring(
											file.getPath().lastIndexOf("."), file.getPath().length()
											);
									if (!temp.endsWith(ext)) {
										temp = temp + ext;
									}
									fout = new FileOutputStream(temp);
									int c;
									while((c = fin.read()) != -1){
										fout.write(c);
									}
									infoAlert(null, "다운로드가 완료되었습니다.");
									fin.close();
									fout.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						btn.setStyle("-fx-background-color: transparent;");
						btn.setMaxSize(Double.MIN_VALUE,Double.MIN_VALUE);
					}
					@Override
					public void updateItem(Button imgview, boolean empty) {
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
								imgview2.setFitHeight(10);
								imgview2.setFitWidth(10);
								setGraphic(imgview2);
								setGraphic(btn);
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

			FXMLLoader loader = new FXMLLoader(getClass().getResource("classroom_boardView.fxml"));

			Parent root = loader.load();

			classroom_boardViewController viewController = loader.getController();

			viewController.setNbdVo(vo);
			viewController.setAvo(admin);
			AnchorPane pane = (AnchorPane) this.root.getParent();

			pane.getChildren().setAll(root);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void btn_cancel_click(ActionEvent event) throws IOException {

		if (admin == null) {
			root.getChildren().clear();
			Parent login = FXMLLoader.load(getClass().getResource("../../main/controller/classroomhalfMain.fxml"));
			root.getChildren().setAll(login);
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/classroom_manage/comm/controller/admin_classComm.fxml"));

			Parent root = loader.load();

			admin_classCommController com = loader.getController();

			com.setAvo(admin);
			AnchorPane pane = (AnchorPane) this.root.getParent();

			pane.getChildren().setAll(root);
		}
	}

	

	@FXML
	void initialize() throws RemoteException {

		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert subjcetName != null : "fx:id=\"subjcetName\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert colno != null : "fx:id=\"colno\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert col_writer != null : "fx:id=\"col_writer\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert coltitle != null : "fx:id=\"coltitle\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert colfile != null : "fx:id=\"colfile\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert colcount != null : "fx:id=\"colcount\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert coldate != null : "fx:id=\"coldate\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert btn_cancel != null : "fx:id=\"btn_cancel\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		assert tfsearch != null : "fx:id=\"tfsearch\" was not injected: check your FXML file 'classroom_Board.fxml'.";
		tableview.setFocusTraversable(false);
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		listall = service.getAllnbd();

		table();

		
		// 글쓰기 버튼을 클릭했을때의 이벤트처리
	/*	writebox.setOnMouseClicked(e -> {

			if (true) { // 회원인지 관리자 인지 검증하고 회원이면 접근제한

			}

			root.getChildren().clear();
			Parent login = null;
			try {
				login = FXMLLoader.load(getClass().getResource("classroom_boardwrite.fxml"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			root.getChildren().setAll(login);

		});*/
		tfsearch.setOnKeyReleased(e -> {
			List<NbdVO> list = null;
			String str = "%" + tfsearch.getText().trim() + "%";
			try {
				list = service.selectTi(str);
				if ("".equals(str)) {
					table();
				} else {
					table();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		tableview.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				try {
					view(tableview.getSelectionModel().getSelectedItem());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		/*writebox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				writebox.setCursor(Cursor.HAND);
			}
		});*/
	}
	public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public void infoAlert(String header, String msg) {
    	Alert warning = new Alert(AlertType.INFORMATION);
    	warning.setTitle("알림");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public ButtonType confirm(String header, String msg){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("안내");
    	alert.setHeaderText(header);
    	alert.setContentText(msg);
    	
    	ButtonType comfirmResult = alert.showAndWait().get();
    	return comfirmResult;
    }
}
