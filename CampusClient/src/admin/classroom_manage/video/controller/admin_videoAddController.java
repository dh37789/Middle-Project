package admin.classroom_manage.video.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ResourceBundle;

import admin.classroom_manage.video.service.IadminVideoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vo.AdminVO;
import vo.VideoVO;
import javafx.stage.FileChooser.ExtensionFilter;

public class admin_videoAddController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane root;

	@FXML
	private TextField tftitle;

	@FXML
	private TextField tfwriter;

	@FXML
	private TextField tfwriter2;

	@FXML
	private TextField tffilepath;

	@FXML
	private Button searchfile;

	@FXML
	private HBox writerbox;

	@FXML
	private HBox backbox;

	private Stage stage;
	private String filetemp;

	private AdminVO adminVO;

	public void setAdminVO(AdminVO adminVO) {
		this.adminVO = adminVO;
	}

	@FXML
	void searchfile(ActionEvent event) {

		stage = (Stage) searchfile.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("video File", "*.mp4"));
		File selectFile = fileChooser.showOpenDialog(stage);
		if (selectFile == null) {
			return;
		}
		filetemp = selectFile.getPath();
		tffilepath.setText(selectFile.getPath().substring(selectFile.getPath().lastIndexOf("\\") + 1,
				selectFile.getPath().length()));

		String str = tffilepath.getText();
//		System.out.println(str);

		int a = str.indexOf(".");
		tftitle.setText(str.substring(0, a));
//		String b =str.split(".")[0];
//		String C =str.split(".")[0];
//		System.out.println(a);
//		System.out.println(b);
	}

	void insert() {

		VideoVO vo = new VideoVO();
		Alert alert = new Alert(AlertType.WARNING);

		if (tftitle.getText().isEmpty()) {
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("id값을 입력하세요!");
			alert.showAndWait();
			return;
		}
		if (tfwriter.getText().isEmpty()) {
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("단원id를 입력하세요!");
			alert.showAndWait();
			return;
		}
		if (tfwriter2.getText().isEmpty()) {

			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("강의시간을 입력하세요!");
			alert.showAndWait();
			return;
		} else {
			try {
				Integer.parseInt(tfwriter2.getText().trim());
			} catch (Exception e) {
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("숫자형태로 입력하세요!");
				alert.showAndWait();
				return;
			}
		}

		vo.setVd_nm(tftitle.getText().trim());
		vo.setVd_pltm(Integer.parseInt(tfwriter2.getText().trim()));
		vo.setVd_unId(tfwriter.getText().trim());
		vo.setVd_flrt(tffilepath.getText());
		vo.setVd_admId("admin"); // 관리자값 읽어와야함

		try {
			int result = service.insertVideo(vo);

			if (result > 0) {
				if (!tffilepath.getText().isEmpty()) {
					FileInputStream fin = null;
					FileOutputStream fout = null;
					File file = new File(filetemp);
					fin = new FileInputStream(file);
					String folder = "D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/src/classRoom/video/controller";
					File newDir = new File(folder);
					if (!newDir.exists()) {
						newDir.mkdir();
					}
					fout = new FileOutputStream(folder + "/" + tffilepath.getText());
					int c;

					while ((c = fin.read()) != -1) {
						fout.write(c);
					}
					System.out.println("작업 종료..");
					fin.close();
					fout.close();
				}

				alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("입력성공!");
				alert.showAndWait();
				root.getChildren().clear();
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../video/controller/admin_classVideo.fxml"));
			    Parent main = loader.load();
			    root.getChildren().setAll(main);
			} else {
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("입력실패!");
				alert.showAndWait();
				tftitle.setText("");
				tfwriter.setText("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private IadminVideoService service;

	@FXML
	void initialize() {
		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert tftitle != null : "fx:id=\"tftitle\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert tfwriter != null : "fx:id=\"tfwriter\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert tfwriter2 != null : "fx:id=\"tfwriter2\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert tffilepath != null : "fx:id=\"tffilepath\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert searchfile != null : "fx:id=\"searchfile\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert writerbox != null : "fx:id=\"writerbox\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";
		assert backbox != null : "fx:id=\"backbox\" was not injected: check your FXML file 'admin_videoAdd.fxml'.";

		searchfile.setFocusTraversable(false);

		tftitle.setEditable(false);
		Registry reg;

		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IadminVideoService) reg.lookup("admin_video");

		} catch (Exception e) {
			e.printStackTrace();
		}

		writerbox.setOnMouseClicked(e -> {
			insert();
		});

		backbox.setOnMouseClicked(e -> {
			root.getChildren().clear();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_classVideo.fxml"));
			Parent videoview = null;
			try {
				videoview = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			admin_classVideoController infoCon = loader.getController();
//    		infoCon.setAdminVO(adminVo);
			root.getChildren().setAll(videoview);

		});

	}
}
