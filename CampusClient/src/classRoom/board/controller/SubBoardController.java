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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import admin.classroom_manage.comm.controller.admin_classCommController;
import classRoom.board.service.IBoardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import vo.AdminVO;
import vo.Homework_boardVO;
import vo.MemberVO;
import vo.NbdVO;

public class SubBoardController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox pane;

	@FXML
	private ComboBox<String> comSub;

	@FXML
	private ComboBox<String> comSearch;

	@FXML
	private TextField tfSearch;

	@FXML
	private Button btnSearch;

	@FXML
	private TableView<Homework_boardVO> tableSub;

	@FXML
	private TableColumn<?, ?> colSubNM;

	@FXML
	private TableColumn<?, ?> colNo;

	@FXML
	private TableColumn<?, ?> colTi;

	@FXML
	private TableColumn<?, ?> colWrr;

	@FXML
	private TableColumn<?, ?> colDate;

	@FXML
	private TableColumn<Button, Button> colFile;

	@FXML
	private Button btnWrite;

	@FXML
	private Pagination page;

	private String[] subList = { "전체", "초급자바", "Oracle", "고급자바", "HTML", "JSP" };

	private String[] searchCol = { "제목", "내용", "작성자" };

	private ObservableList<String> subData;

	private ObservableList<String> searchData;

	private IBoardService service;

	private List<Homework_boardVO> homeList;

	private ObservableList<Homework_boardVO> homeData;

	private Stage stage;

	private MemberVO mvo;

	private int homeSize;

	private int rowPerPage = 15;

	private AdminVO avo;

	public void setAvo(AdminVO avo) {
		this.avo = avo;

		if (avo != null) {
			btnWrite.setVisible(false);
		}
	}

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	@FXML
	void search(ActionEvent event) {
		Map<String, String> searchMap = new HashMap<>();
		String sub = comSub.getSelectionModel().getSelectedItem();
		if (sub.equals("전체")) {
			sub = "%%";
			searchMap.put("sub", sub);
		} else {
			sub = "%" + sub + "%";
			searchMap.put("sub", sub);
		}
		String col = comSearch.getSelectionModel().getSelectedItem();
		String colStr = "";
		String SearchStr = "%" + tfSearch.getText() + "%";
		if (col.equals("제목")) {
			colStr = "hwbd_ti";
		} else if (col.equals("내용")) {
			colStr = "hwbd_con";
		} else if (col.equals("작성자")) {
			colStr = "hwbd_wrr";
		}
		System.out.println("3");
		searchMap.put("colStr", colStr);
		searchMap.put("SearchStr", SearchStr);
		System.out.println("4");
		try {
			homeList = service.searchHomeList(searchMap);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		homeSize = homeList.size();
		System.out.println("5");
		indexNo();
		getTableList();
		paging();
	}

	@FXML
	void subView(ActionEvent event) throws RemoteException {
		String sub = comSub.getSelectionModel().getSelectedItem();
		if (sub.equals("전체")) {
			homeList = service.getHomeBoardList();
		} else {
			homeList = service.getSubList(sub);
		}
		indexNo();
		homeSize = homeList.size();
		getTableList();
		paging();
	}

	@FXML
	void viewBoard(MouseEvent event) throws IOException {
		if (event.getClickCount() == 2 && (!tableSub.getSelectionModel().isEmpty())) {
			Homework_boardVO vo = tableSub.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("subBoardView.fxml"));
			Parent root = loader.load();

			SubBoardViewController viewController = loader.getController();
			viewController.setNode(root);
			viewController.setMvo(mvo);
			viewController.setVo(vo);
			viewController.viewBoard();
			viewController.memBoardChk();
			// pane.getChildren().clear();
			// boardPane.getChildren().clear();
			// root.setLayoutX(0);
			// root.setLayoutY(0);
			AnchorPane boardPane = (AnchorPane) pane.getParent();
			boardPane.getChildren().setAll(root);
		}
	}

	@FXML
	void write(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("subBoardAdd.fxml"));
		Parent root = loader.load();

		SubBoardAddController addController = loader.getController();
		addController.setNode(root);
		addController.setMvo(mvo);
		AnchorPane boardPane = (AnchorPane) pane.getParent();
		boardPane.getChildren().setAll(root);
	}

	@FXML
	void initialize() {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert comSub != null : "fx:id=\"comSub\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert comSearch != null : "fx:id=\"comSearch\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert tfSearch != null : "fx:id=\"tfSearch\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert tableSub != null : "fx:id=\"tableSub\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colSubNM != null : "fx:id=\"colSubNM\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colNo != null : "fx:id=\"colNo\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colTi != null : "fx:id=\"colTi\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colWrr != null : "fx:id=\"colWrr\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert btnWrite != null : "fx:id=\"btnWrite\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert page != null : "fx:id=\"page\" was not injected: check your FXML file 'subBoard.fxml'.";
		assert colFile != null : "fx:id=\"colFile\" was not injected: check your FXML file 'subBoard.fxml'.";

		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IBoardService) reg.lookup("board");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		try {
			homeList = service.getHomeBoardList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		indexNo();
		getTableList();

		homeSize = homeList.size();

		subData = FXCollections.observableArrayList(subList);
		searchData = FXCollections.observableArrayList(searchCol);

		comSearch.setItems(searchData);
		comSearch.setValue(searchData.get(0));

		comSub.setItems(subData);
		comSub.setValue(subData.get(0));

		tableSub.setFocusTraversable(false);

//        btncancel.setFocusTraversable(false);
//        btnSearch.setFocusTraversable(false);
//        btnWrite.setFocusTraversable(false);
//        
//        btncancel.setOnMouseClicked(e->{
//        	if (avo == null) {
//    			pane.getChildren().clear();
//    			Parent login=null;
//				try {
//					login = FXMLLoader.load(getClass().getResource("../../main/controller/classroomhalfMain.fxml"));
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//    			pane.getChildren().setAll(login);
//    		} else {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/classroom_manage/comm/controller/admin_classComm.fxml"));
//
//    			Parent root=null;
//				try {
//					root = loader.load();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//    			admin_classCommController com = loader.getController();
//
//    			com.setAvo(avo);
//    			AnchorPane pane = (AnchorPane) this.pane.getParent();
//
//    			pane.getChildren().setAll(root);
//    		}
//        });
//        btncancel.setOnMouseClicked(e->{
//        	if (avo == null) {
//    			pane.getChildren().clear();
//    			Parent login=null;
//				try {
//					login = FXMLLoader.load(getClass().getResource("../../main/controller/classroomhalfMain.fxml"));
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//    			pane.getChildren().setAll(login);
//    		} else {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/classroom_manage/comm/controller/admin_classComm.fxml"));
//
//    			Parent root=null;
//				try {
//					root = loader.load();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//    			admin_classCommController com = loader.getController();
//
//    			com.setAvo(avo);
//    			AnchorPane pane = (AnchorPane) this.pane.getParent();
//
//    			pane.getChildren().setAll(root);
//    		}
//        });

//        btncancel.setOnMouseClicked(e->{
//        	if (avo == null) {
//    			pane.getChildren().clear();
//    			Parent login=null;
//				try {
//					login = FXMLLoader.load(getClass().getResource("../../main/controller/classroomhalfMain.fxml"));
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//    			pane.getChildren().setAll(login);
//    		} else {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../admin/classroom_manage/comm/controller/admin_classComm.fxml"));
//
//    			Parent root=null;
//				try {
//					root = loader.load();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//    			admin_classCommController com = loader.getController();
//
//    			com.setAvo(avo);
//    			AnchorPane pane = (AnchorPane) this.pane.getParent();
//
//    			pane.getChildren().setAll(root);
//    		}
//        });
//        
	}

	public void indexNo() {
		for (int i = 0; i < homeList.size(); i++) {
			Homework_boardVO vo = new Homework_boardVO();
			vo.setHwbd_id(homeList.get(i).getHwbd_id().substring(2));
			vo.setHwbd_con(homeList.get(i).getHwbd_con());
			vo.setHwbd_dt(homeList.get(i).getHwbd_dt());
			vo.setHwbd_frt(homeList.get(i).getHwbd_frt());
			vo.setHwbd_memId(homeList.get(i).getHwbd_memId());
			vo.setHwbd_sub(homeList.get(i).getHwbd_sub());
			vo.setHwbd_wrr(homeList.get(i).getHwbd_wrr());
			vo.setHwbd_ti(homeList.get(i).getHwbd_ti());
			homeList.set(i, vo);
		}
	}

	public void paging() {
		int pageCount = homeSize / rowPerPage + (homeSize % rowPerPage == 0 ? 0 : 1);
		page.setPageCount(pageCount);
		page.setCurrentPageIndex(0);

		changeTableView(0);

		page.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				changeTableView(newValue.intValue());
			}
		});
	}

	public void changeTableView(int index) {
		int fromIndex = index * rowPerPage; // 가져올 데이터의 시작번호
		int toIndex = Math.min(fromIndex + rowPerPage, homeSize);

		tableSub.setItems(FXCollections.observableArrayList(homeList.subList(fromIndex, toIndex)));
	}

	public void getTableList() {
		homeData = FXCollections.observableArrayList(homeList);
		tableSub.setItems(homeData);
		colSubNM.setCellValueFactory(new PropertyValueFactory<>("hwbd_sub"));
		colNo.setCellValueFactory(new PropertyValueFactory<>("hwbd_id"));
		colTi.setCellValueFactory(new PropertyValueFactory<>("hwbd_ti"));
		colWrr.setCellValueFactory(new PropertyValueFactory<>("hwbd_wrr"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("hwbd_dt"));
		colFile.setCellFactory(new Callback<TableColumn<Button, Button>, TableCell<Button, Button>>() {
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
							stage = (Stage) btnSearch.getScene().getWindow();
							ButtonType ans = confirm(null, "다운 받으시겠습니까?");
							if (ans == ButtonType.OK) {
								FileInputStream fin = null;
								FileOutputStream fout = null;
								String dir = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/sendFile/";
								File file = new File(dir + mvo.getMem_id() + "/"
										+ ((Homework_boardVO) getTableRow().getItem()).getHwbd_frt());
								try {
									fin = new FileInputStream(file);
									FileChooser fileChooser = new FileChooser();
									fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All File", "*.*"));
									File selectedFile = fileChooser.showSaveDialog(stage);
									// 가져온 File이 없을 경우에 null
									if (selectedFile == null) {
										return;
									}
									String temp = selectedFile.getPath();
									String ext = file.getPath().substring(file.getPath().lastIndexOf("."),
											file.getPath().length());
									if (!temp.endsWith(ext)) {
										temp = temp + ext;
									}
									fout = new FileOutputStream(temp);
									int c;
									while ((c = fin.read()) != -1) {
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
						btn.setMaxSize(Double.MIN_VALUE, Double.MIN_VALUE);
					}

					@Override
					public void updateItem(Button item, boolean empty) {
						super.updateItem(item, empty);
						String fileName = "";
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							if (item != null) {
								try {
									System.out.println(((Homework_boardVO) getTableRow().getItem()).getHwbd_frt());
									fileName = ((Homework_boardVO) getTableRow().getItem()).getHwbd_frt();
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (fileName != null) {
									setGraphic(btn);
								} else {
									setText(null);
									setGraphic(null);
								}

							}
						}
					}
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}

		});
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

	public ButtonType confirm(String header, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);

		ButtonType comfirmResult = alert.showAndWait().get();
		return comfirmResult;
	}
}
