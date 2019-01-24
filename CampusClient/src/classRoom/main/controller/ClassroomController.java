package classRoom.main.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import classRoom.board.controller.Classroom_boardController;
import classRoom.board.controller.LecPostBoardController;
import classRoom.board.controller.QnABoardController;
import classRoom.board.controller.SubBoardController;
import classRoom.main.service.IClassMainService;
import classRoom.question.controller.QuestionListController;
import classRoom.question.service.IQuestionService;
import classRoom.video.controller.TimeSetting;
import classRoom.video.controller.temp;
import classRoom.video.controller.video_playController;
import home.main.controller.HomeMainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;
import vo.AdminVO;
import vo.ContentVO;
import vo.MemberVO;
import vo.NbdVO;
import vo.UnitVO;
import vo.VideoVO;
import vo.joinVO;

/**
 * @author PC02
 *
 */
public class ClassroomController extends TreeView<String> {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
    private String str;

	@FXML
	private Button btn_notice;

	@FXML
	private Button btn_homework;

	@FXML
	private Button btn_qa;

	@FXML
	private Button btn_pratice;

	@FXML
	private Button btn_pop;

	@FXML
	private Button btn_home;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<joinVO> tableview;

	@FXML
	private TableColumn<?, ?> col1;

	@FXML
	private TableColumn<?, ?> col2;

	@FXML
	private TableColumn<Integer, Integer> col3;

	@FXML
	private TableColumn<Integer, Integer> col4;

	@FXML
	private TableColumn<ImageView, ImageView> col5;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField tfSearch;

	@FXML
	private AnchorPane root;

	private video_playController controller;

	@FXML
	private Label mem_id;

	@FXML
	private Label mem_em;

	@FXML
	private Label mem_ph;

	@FXML
	private Label mem_pt;

	@FXML
	private Label mem_nm;

	@FXML
	private HBox exitbox;

	private MemberVO mvo;

	private Stage stage;
	private TreeItem<String> java;
	private AdminVO admin;

	public void setAdmin(AdminVO admin) {
		this.admin = admin;
	}

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;

		List<joinVO> list = null;
		try {
			list = service.join(mvo.getMem_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table(list);
		mem_id.setText(mvo.getMem_id());
		mem_nm.setText(mvo.getMem_nm());
		mem_ph.setText(mvo.getMem_ph());
		mem_em.setText(mvo.getMem_em());
		mem_pt.setText(mvo.getMem_pt() + "");
		

	}

	void table(List<joinVO> list) {
		ObservableList<joinVO> ob = null;

		ob = FXCollections.observableArrayList(list);
		tableview.setItems(ob);

//    	PropertyValueFactory<String, String> a = new PropertyValueFactory<>("ada");

		col1.setCellValueFactory(new PropertyValueFactory<>("un_nm"));
		col2.setCellValueFactory(new PropertyValueFactory<>("vd_nm"));

		col3.setCellFactory(new Callback<TableColumn<Integer, Integer>, TableCell<Integer, Integer>>() {
			@Override
			public TableCell<Integer, Integer> call(TableColumn<Integer, Integer> param) {
				TableCell<Integer, Integer> cell = new TableCell<Integer, Integer>() {
					@Override
					public void updateItem(Integer imgview, boolean empty) {
						int bol = 0;
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							try {
								TimeSetting time = new TimeSetting();
								bol = ((joinVO) getTableRow().getItem()).getMem_vd_pltm();
								
								time.setSecond(bol);
								String sc ="";
								
								if (time.getSecond() == 0) {
									sc = time.getSecond() + "0";
								}else if (time.getSecond() < 10) {
									sc = "0"+time.getSecond();
								}else {
									sc = time.getSecond() + "";
								}
								String mn = "";
								if (time.getMinute() == 0) {
									mn = time.getMinute() + "0";
								}else if (time.getMinute() < 10){
									mn = "0" + time.getMinute();
								}else {
									mn = time.getMinute() + "";
								}
								
								
								setText(mn + ":" + sc);
							} catch (Exception e) {

							}

						}
					}
				};
				return cell;
			}

		});

		col4.setCellFactory(new Callback<TableColumn<Integer, Integer>, TableCell<Integer, Integer>>() {
			@Override
			public TableCell<Integer, Integer> call(TableColumn<Integer, Integer> param) {
				TableCell<Integer, Integer> cell = new TableCell<Integer, Integer>() {
					@Override
					public void updateItem(Integer imgview, boolean empty) {
						int bol = 0;
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							try {
								TimeSetting time = new TimeSetting();
								bol = ((joinVO) getTableRow().getItem()).getVd_pltm();
								
								time.setSecond(bol);
								String sc ="";
								
								if (time.getSecond() == 0) {
									sc = time.getSecond() + "0";
								}else if (time.getSecond() < 10) {
									sc = "0"+time.getSecond();
								}else {
									sc = time.getSecond() + "";
								}
								String mn = "";
								if (time.getMinute() == 0) {
									mn = time.getMinute() + "0";
								}else if (time.getMinute() < 10){
									mn = "0" + time.getMinute();
								}else {
									mn = time.getMinute() + "";
								}
								
								
								setText(mn + ":" + sc);
							} catch (Exception e) {

							}

						}
					}
				};
				return cell;
			}

		});

		col5.setCellFactory(new Callback<TableColumn<ImageView, ImageView>, TableCell<ImageView, ImageView>>() {
			@Override
			public TableCell<ImageView, ImageView> call(TableColumn<ImageView, ImageView> param) {
				TableCell<ImageView, ImageView> cell = new TableCell<ImageView, ImageView>() {
					@Override
					public void updateItem(ImageView imgview, boolean empty) {
						int bol = 0;
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							try {
								bol = ((joinVO) getTableRow().getItem()).getMem_vd_ean();

							} catch (Exception e) {

							}
							if (bol == 1) {
								Image saveImg = new Image(getClass().getResourceAsStream("check2.png"));
								ImageView imgview2 = new ImageView(saveImg);
								imgview2.setFitHeight(10);
								imgview2.setFitWidth(10);
								setGraphic(imgview2);
							} else {
								Image saveImg = new Image(getClass().getResourceAsStream("x.png"));
								ImageView imgview2 = new ImageView(saveImg);
								imgview2.setFitHeight(10);
								imgview2.setFitWidth(10);
								setGraphic(imgview2);
							}

						}
					}
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}

		});

	}

	/**
	 * 맨 오른쪽 버튼 클릭했을때의 이벤트
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void btn_home_click(ActionEvent event) throws IOException {

		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("classroomhalfMain.fxml"));

		Parent half = loader.load();

		classroomhalfController ch = loader.getController();
		ch.setMem(mvo);
		ch.setMvo(mvo);
		
		pane.getChildren().setAll(half);

	}

	@FXML
	void btn_homework_click(ActionEvent event) throws IOException {
		pane.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/subBoard.fxml"));
		Parent homework = loader.load();

		SubBoardController subCon = loader.getController();
		subCon.setAvo(admin);
		subCon.setMvo(mvo);
		subCon.paging();
		subCon.getTableList();
		// subCon.indexNo();

		pane.getChildren().setAll(homework);
	}

	@FXML
	void btn_notice_click(ActionEvent event) throws IOException {

		pane.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/classroom_Board.fxml"));
		Parent notice = loader.load();
		Classroom_boardController board = loader.getController();
		board.setVo(mvo);
		board.setMvo(admin);
		board.table();

		pane.getChildren().setAll(notice);

	}

	@FXML
	void btn_pop_click(ActionEvent event) throws IOException {
		pane.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/lecPostBoard.fxml"));
		Parent notice = loader.load();
		LecPostBoardController postboard = loader.getController();

		postboard.setMemvo(mvo);

		pane.getChildren().setAll(notice);
	}

	@FXML
	void btn_pratice_click(ActionEvent event) throws IOException {
		pane.getChildren().clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../question/controller/questionList.fxml"));
		Parent pratice = loader.load();

		QuestionListController quizCon = loader.getController();
		quizCon.setMvo(mvo);
		quizCon.getTableData();
		quizCon.paging();

		pane.getChildren().setAll(pratice);
	}

	@FXML
	void btn_qa_click(ActionEvent event) throws IOException {
		pane.getChildren().clear();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../board/controller/qnaBoard.fxml"));
		Parent homework = loader.load();

		QnABoardController subCon = loader.getController();

		subCon.setMvo(mvo);

		pane.getChildren().setAll(homework);
	}

	public IClassMainService service;
	private ObservableList<String> list;
	private List<UnitVO> vo;
	private List<VideoVO> vo2;
	private Parent login;
	@FXML
	private TreeView<String> tree;

	/**
	 * treeview셋팅
	 * 
	 */
	public void tree() throws RemoteException, SQLException {

		TreeItem<String> rootItem = new TreeItem<String>("강의 목록", new ImageView(normalChildIcon));
		TreeItem<String> java = new TreeItem<String>(vo.get(0).getUn_nm(), new ImageView(normalChildIcon));
		TreeItem<String> highJava = new TreeItem<>(vo.get(1).getUn_nm(), new ImageView(normalChildIcon));
		TreeItem<String> jquery = new TreeItem<>(vo.get(2).getUn_nm(), new ImageView(normalChildIcon));
		TreeItem<String> jsp = new TreeItem<>(vo.get(3).getUn_nm(), new ImageView(normalChildIcon));
		TreeItem<String> database = new TreeItem<>(vo.get(4).getUn_nm(), new ImageView(normalChildIcon));
		
	//	System.out.println(rootItem.expandedProperty().getName());
		
		
		for (int i = 0; i < vo2.size(); i++) {

			if ("un001".equals(vo2.get(i).getVd_unId())) {
				java.getChildren().add(new TreeItem<String>(vo2.get(i).getVd_nm(), new ImageView(VideoIcon)));
			} else if ("un002".equals(vo2.get(i).getVd_unId())) {
				highJava.getChildren().add(new TreeItem<String>(vo2.get(i).getVd_nm(), new ImageView(VideoIcon)));
			} else if ("un003".equals(vo2.get(i).getVd_unId())) {
				jquery.getChildren().add(new TreeItem<String>(vo2.get(i).getVd_nm(), new ImageView(VideoIcon)));
			} else if ("un004".equals(vo2.get(i).getVd_unId())) {
				jsp.getChildren().add(new TreeItem<String>(vo2.get(i).getVd_nm(), new ImageView(VideoIcon)));
			} else if ("un005".equals(vo2.get(i).getVd_unId())) {
				database.getChildren().add(new TreeItem<String>(vo2.get(i).getVd_nm(), new ImageView(VideoIcon)));
			}

		}
		rootItem.getChildren().addAll(java, highJava, jquery, jsp, database);
		tree.setRoot(rootItem);
		
	}

	void tree3(String str) throws RemoteException, SQLException {

		if ("".equals(str)) {
			try {
				tree();
			} catch (Exception e) {
			}
			return;
		}

		List<VideoVO> list = null;
		try {
			str = "%" + str + "%";
			list = service.selConName(str);
			if (list == null) {
				return;
			}
			TreeItem<String> item[] = new TreeItem[list.size()];
			TreeItem<String> rootItem = new TreeItem<String>("검색 목록", new ImageView(searchIcon));
			for (int i = 0; i < list.size(); i++) {
				item[i] = new TreeItem<String>(list.get(i).getVd_nm(), new ImageView(VideoIcon));

			}

			rootItem.getChildren().addAll(item);
			tree.setRoot(rootItem);

		} catch (Exception e) {
		}

	}

	private String DEFAULT_STYLE_CLASS = "gk2-tree-view";
	private String CSS_PATH = "/commons/ui/control/treeview/treeview.css";
	private String[] rootItem = { "1", "2", "3" };
	Image normalChildIcon = new Image(getClass().getResourceAsStream("folder3.png"));
	Image searchIcon = new Image(getClass().getResourceAsStream("search.png"));
	Image VideoIcon = new Image(getClass().getResourceAsStream("video-file.png"));

	// treeview 선택했을때 해당 비디오 나오기
	public void video(String value) {
		try {
			pane.getChildren().clear();
			temp.temp = value;

			setTrue();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../video/controller/video_play.fxml"));

			Parent root = loader.load();

			video_playController vp = loader.getController();
			vp.setMainCon(this);
			vp.setWorker(false);
			vp.setMvo(mvo);
			controller = loader.getController();
			controller.setMainCon(this);
			controller.setWorker(false);
			controller.setMvo(mvo);
			controller.start();
			pane.getChildren().setAll(root);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void initialize() throws RemoteException, SQLException {
		assert btn_notice != null : "fx:id=\"btn_notice\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btn_homework != null : "fx:id=\"btn_homework\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btn_qa != null : "fx:id=\"btn_qa\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btn_pratice != null : "fx:id=\"btn_pratice\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btn_pop != null : "fx:id=\"btn_pop\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btn_home != null : "fx:id=\"btn_home\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col1 != null : "fx:id=\"col1\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert tree != null : "fx:id=\"tree\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert exitbox != null : "fx:id=\"exitbox\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col1 != null : "fx:id=\"col1\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col2 != null : "fx:id=\"col2\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col3 != null : "fx:id=\"col3\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col4 != null : "fx:id=\"col4\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert col5 != null : "fx:id=\"col5\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert mem_id != null : "fx:id=\"mem_id\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert mem_em != null : "fx:id=\"mem_em\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert mem_ph != null : "fx:id=\"mem_ph\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert mem_pt != null : "fx:id=\"mem_pt\" was not injected: check your FXML file 'classroomMain.fxml'.";
		assert mem_nm != null : "fx:id=\"mem_nm\" was not injected: check your FXML file 'classroomMain.fxml'.";
		Registry reg;

		tableview.setFocusTraversable(false);
		
		tree.setFocusTraversable(false);
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IClassMainService) reg.lookup("classMain");
			vo = service.selectUnit();
			vo2 = service.selectCon();
//			System.out.println(vo2.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		tree();

		// 키를 눌렀을떄 검색하기
		tfSearch.setOnKeyReleased(e -> {
			
			try {
				tree3(tfSearch.getText().trim());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});
		
		
		
		// 파일을 클릭했을때 동영상 나오기
		tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<String> selectedItem = (TreeItem<String>) newValue;
			/*	if(tree.getSelectionModel().getSelectedItem().getChildren() == null) {
					return;
				}
				if (tree.getSelectionModel().getSelectedItem().getChildren().isEmpty()) {
					if (controller != null) {
						controller.stopVd();
					}
				}*/
				try {
					if ("강의 목록".equals(selectedItem.getValue()) || "초급자바".equals(selectedItem.getValue())
							|| "고급자바".equals(selectedItem.getValue()) || "JQUERY".equals(selectedItem.getValue())
							|| "JSP".equals(selectedItem.getValue()) || "데이터베이스".equals(selectedItem.getValue())) {

						return;
					}
					video(selectedItem.getValue());

				} catch (Exception e) {
				}

			}

		});

		// 강의실 나가기 버튼을 클릭했을떄 이벤트
		exitbox.setOnMouseClicked(e -> {

			stage = (Stage) pane.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../home/main/controller/homeMain.fxml"));
			Parent classRoot = null;
			try {
				classRoot = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			HomeMainController classCon = loader.getController();
			classCon.setMvo(mvo);
			classCon.lookName();
			Scene scene = new Scene(classRoot);
			stage.setScene(scene);

		});

	}

	public void setTrue() {
		btn_home.setDisable(true);
		btn_homework.setDisable(true);
		btn_notice.setDisable(true);
		btn_pop.setDisable(true);
		btn_pratice.setDisable(true);
		btn_qa.setDisable(true);
		exitbox.setDisable(true);

	}

	public void setFalse() {
		btn_home.setDisable(false);
		btn_homework.setDisable(false);
		btn_notice.setDisable(false);
		btn_pop.setDisable(false);
		btn_pratice.setDisable(false);
		btn_qa.setDisable(false);
		exitbox.setDisable(false);

	}
}
