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
import javafx.stage.Stage;
import javafx.util.Callback;
import vo.ContentVO;
import vo.MemberVO;
import vo.NbdVO;
import vo.UnitVO;
import vo.joinVO;

/**
 * @author PC02
 *
 */
public class classroomhalfController extends TreeView<String> {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

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
	private AnchorPane root;

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

	private MemberVO mvo;

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

	}

	void table(List<joinVO> list) {
		ObservableList<joinVO> ob = null;

		ob = FXCollections.observableArrayList(list);
		tableview.setItems(ob);

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

	public IClassMainService service;

	@FXML

	void initialize() throws RemoteException, SQLException {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert col1 != null : "fx:id=\"col1\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert col2 != null : "fx:id=\"col2\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert col3 != null : "fx:id=\"col3\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert col4 != null : "fx:id=\"col4\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert col5 != null : "fx:id=\"col5\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert mem_id != null : "fx:id=\"mem_id\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert mem_em != null : "fx:id=\"mem_em\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert mem_ph != null : "fx:id=\"mem_ph\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert mem_pt != null : "fx:id=\"mem_pt\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		assert mem_nm != null : "fx:id=\"mem_nm\" was not injected: check your FXML file 'classroomhalfMain.fxml'.";
		Registry reg;
		tableview.setFocusTraversable(false);

		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IClassMainService) reg.lookup("classMain");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setMem(MemberVO mvo) {
		mem_id.setText(mvo.getMem_id());
		mem_nm.setText(mvo.getMem_nm());
		mem_ph.setText(mvo.getMem_ph());
		mem_em.setText(mvo.getMem_em());
		mem_pt.setText(mvo.getMem_pt() + "");
	}

}
