package admin.lectinfo_manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.html.table.Table;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import admin.lectinfo_manage.service.ILectinfo_manageService;
import home.lecture_info.service.ILecture_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vo.Lecture_infoVO;

public class lecInfoView_java1_Controller {
	private ILectinfo_manageService service;

	@FXML
	private ImageView lec_image;

	@FXML
	private VBox pane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField tf_id;

	@FXML
	private TextArea ta_pupcon;

	@FXML
	private TextArea ta_lncon;

	@FXML
	private TextArea ta_ol;

	@FXML
	private TextField tf_em;

	@FXML
	private TextField tf_ph;

	@FXML
	private TextField tf_tc;

	@FXML
	private TextField tf_nm;

	@FXML
	private TextField tf_unid;

	@FXML
	private Button btn_print;

	@FXML
	private Button btn_pdf;

	@FXML
	private Button btnedit;

	@FXML
	private Button btnconfirm;

	/**
	 * pdf출력, 생성 메서드
	 */
	@FXML
	void pdf(ActionEvent event) throws IOException, DocumentException {

		// Document 생성
		Document document = new Document(PageSize.A4, 50, 50, 50, 50); // 용지 및 여백 설정

		// PdfWriter 생성
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/test.pdf")); // 바로 다운로드.
		writer.setInitialLeading(12.5f);

		// Document 오픈
		document.open();
		XMLWorkerHelper helper = XMLWorkerHelper.getInstance();

		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(
				"D:\\A_TeachingMaterial\\4.MiddleProject\\workspace\\CampusClient\\src\\home\\lecture_info\\controller\\pdf.css"));
		cssResolver.addCss(cssFile);

		// HTML, 폰트 설정
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		fontProvider.register(
				"D:\\A_TeachingMaterial\\4.MiddleProject\\workspace\\CampusClient\\src\\home\\community\\controller\\MALGUN.TTF",
				"MalgunGothic"); // MalgunGothic은 alias,
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);

		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		XMLWorker worker = new XMLWorker(css, true);
		XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));

		// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.

		String htmlStr = "<html><head><body style='font-family: MalgunGothic;'>" + "<p>강의정보</p>" + "<hr/>"
				+ "<h3> 강의ID : " + tf_id.getText() + "</h3>" + "<hr/>" + "<h3> 강의명 : " + tf_nm.getText() + "</h3>"
				+ "<hr/>" + "<h3> 담당교수 : " + tf_tc.getText() + "</h3>" + "<hr/>" + "<h3> 연락처 : " + tf_ph.getText()
				+ "</h3>" + "<hr/>" + "<h3> e-mail : " + tf_em.getText() + "</h3>" + "<hr/>" + "<h3> 과목개요 : "
				+ ta_ol.getText() + "</h3>" + "<hr/>" + "<h3> 학습내용 : " + ta_lncon.getText() + "</h3>" + "<hr/>"
				+ "<h3> 학습목표 : " + ta_pupcon.getText() + "</h3>" + "<hr/>" + "</body></head></html>";
		StringReader strReader = new StringReader(htmlStr);
		xmlParser.parse(strReader);

		document.close();
		writer.close();
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "d:\\test.pdf");
	}

	// 강의정보를 tf,ta에 설정
	public void setInfvo(Lecture_infoVO vo) {
		tf_id.setText(vo.getLinf_id());
		ta_pupcon.setText(vo.getLinf_pupCon());
		ta_lncon.setText(vo.getLinf_lnCon());
		ta_ol.setText(vo.getLinf_ol());
		tf_em.setText(vo.getLinf_em());
		tf_ph.setText(vo.getLinf_ph());
		tf_tc.setText(vo.getLinf_tc());
		tf_nm.setText(vo.getLinf_nm());
	}

	@FXML
	void initialize() throws RemoteException, NotBoundException {
		assert btn_pdf != null : "fx:id=\"btn_pdf\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert ta_pupcon != null : "fx:id=\"tf_pupcon\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert ta_lncon != null : "fx:id=\"tf_con\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert ta_ol != null : "fx:id=\"tf_ol\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_em != null : "fx:id=\"tf_em\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_ph != null : "fx:id=\"tc_ph\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_tc != null : "fx:id=\"tf_tc\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_nm != null : "fx:id=\"tf_nm\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert tf_unid != null : "fx:id=\"tf_unid\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert btn_print != null : "fx:id=\"btn_print\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert lec_image != null : "fx:id=\"lec_image\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert btnedit != null : "fx:id=\"btnedit\" was not injected: check your FXML file 'lecInfoView_java1.fxml'.";
		assert btnconfirm != null : "fx:id=\"btnconfirm\" was not injected: check your FXML file 'lecInfoView_java5.fxml'.";

		ta_pupcon.setWrapText(true);

		ta_lncon.setWrapText(true);
		ta_ol.setWrapText(true);
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ILectinfo_manageService) reg.lookup("lectInfo");

		btnedit.setOnMouseClicked(e -> {
			ButtonType ans = confirm(null, "정보를 수정하시겠습니까?");
			if (ans == ButtonType.OK) {
				tf_em.setEditable(true);
				tf_ph.setEditable(true);
				tf_tc.setEditable(true);
				ta_lncon.setEditable(true);
				ta_ol.setEditable(true);
				ta_pupcon.setEditable(true);
			}
		});

		btnconfirm.setOnMouseClicked(e -> {
			Lecture_infoVO vo = new Lecture_infoVO();

			vo.setLinf_tc(tf_tc.getText());
			vo.setLinf_ph(tf_ph.getText());
			vo.setLinf_em(tf_em.getText());
			vo.setLinf_ol(ta_ol.getText());
			vo.setLinf_lnCon(ta_lncon.getText());
			vo.setLinf_pupCon(ta_pupcon.getText());
			vo.setLinf_id(tf_id.getText());

			int a = 0;
			try {
				a = service.updateLect(vo);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if (a > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("수정 성공!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("수정 실패!");
				alert.showAndWait();
			}
			tf_em.setEditable(false);
			tf_ph.setEditable(false);
			tf_tc.setEditable(false);
			ta_lncon.setEditable(false);
			ta_ol.setEditable(false);
			ta_pupcon.setEditable(false);
		});

		btn_pdf.setFocusTraversable(false);
//		btn_print.setFocusTraversable(false);
		btnconfirm.setFocusTraversable(false);
		btnedit.setFocusTraversable(false);
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
