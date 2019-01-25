package home.lecture_info.controller;

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

import home.lecture_info.service.ILecture_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vo.Lecture_infoVO;

public class lecInfoView_java1_Controller {
	private ILecture_infoService service;

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
		com.itextpdf.text.Image img2 = null;	 
		if(tf_nm.getText().equals("초급자바")) {
			img2 = com.itextpdf.text.Image.getInstance("you.jpg");
		}
		else if(tf_nm.getText().equals("고급자바")) {
			img2 = com.itextpdf.text.Image.getInstance("people.png");
		}
		else if(tf_nm.getText().equals("jQuery")) {
			img2 = com.itextpdf.text.Image.getInstance("people.png");
			
		}
		else if(tf_nm.getText().equals("JSP")) {
			img2 = com.itextpdf.text.Image.getInstance("people.png");
			
		}
		else if(tf_nm.getText().equals("데이터베이스")) {
			img2 = com.itextpdf.text.Image.getInstance("people.png");
			
		}
		
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

		String htmlStr = "<html><head></head><body style='font-family: MalgunGothic;'>";

		htmlStr+="<h1 align=\"center\" style='font-size:40px;font-weight:bold;'> 강의계획서</h1>" + 
				"<table  border='1' id=\"tb1\" style='margin-bottom:15px;margin-top:50px'>\r\n" + 
				"	<tr>\r\n" + 
				"		<td class=\"cen\">강의ID</td>\r\n" + 
				"		<td>"+"&nbsp;&nbsp;&nbsp;"+tf_id.getText()+"</td>\r\n" + 
				"		<td class=\"cen\">과목 명</td>\r\n" + 
				"		<td>"+"&nbsp;&nbsp;&nbsp;"+tf_nm.getText() +"</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td class=\"cen\">과목명</td>\r\n" + 
				"		<td>초급 java</td>\r\n" + 
				"		<td class=\"cen\">강의실</td>\r\n" + 
				"		<td>202호임다</td>\r\n" + 
				"	</tr>\r\n" + 
				"</table>\r\n" ;

				img2.scaleAbsolute(80f,90f);
				img2.setAbsolutePosition(59, 556);
				document.add(img2);
			htmlStr +="<table border='1' id=\"tb2\" style='margin-bottom:30px; '>" + 
				"	<tr>\r\n" + 
				"		<td rowspan=\"3\" id=\"timg\"><img id=\"img1\" src='/people.png'/></td>\r\n" + 
				"		<td class='cen'>성명</td>\r\n" + 
				"		<td colspan=\"2\">"+"&nbsp;&nbsp;&nbsp;"+tf_tc.getText()+"</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td class='cen'>이메일</td>\r\n" + 
				"		<td colspan=\"2\">"+"&nbsp;&nbsp;&nbsp;"+tf_em.getText()+"</td>" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td class='cen'>연락처</td>\r\n" + 
				"		<td colspan=\"2\">"+"&nbsp;&nbsp;&nbsp;"+tf_ph.getText()+"</td>" + 
				"	</tr>\r\n" + 
				"</table>\r\n" + 
				"	<h2 style='font-size:20px;font-weight:bold'>교과목 개요</h2>\r\n" + 
				"	\r\n" + 
				"	<table border='1' id='tb3' style='margin-top:40px;margin-bottom:200px'>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class=\"td3\">&nbsp;&nbsp;&nbsp;1.과목개요</td>\r\n" + 
				"		</tr>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class='ta3'>"+ta_ol.getText()+"</td>\r\n" + 
				"		</tr>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class=\"td3\">&nbsp;&nbsp;&nbsp;2.학습내용</td>\r\n" + 
				"		</tr>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class='ta3'>"+ta_lncon.getText()+"</td>\r\n" + 
				"		</tr>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class=\"td3\">&nbsp;&nbsp;&nbsp;3.학습목표</td>\r\n" + 
				"		</tr>\r\n" + 
				"		<tr>\r\n" + 
				"		<td class='ta3'>"+ta_pupcon.getText()+"</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n" + 
				"	<br></br>\r\n" + 
				"	<h2 style='font-size:20px;font-weight:bold'>교육과정</h2>\r\n" + 
				"	<img src=\"../images/list.PNG\"></img>";
		htmlStr += "</body>";
		htmlStr += "</html>";
		
		StringReader strReader = new StringReader(htmlStr);
		com.itextpdf.text.Image img =  com.itextpdf.text.Image.getInstance("list.PNG");
		
		img.scaleAbsolute(450f,350f);
		xmlParser.parse(strReader);
		document.add(img);
		
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
//		tf_unid.setText(vo.getLinf_unId());
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
		ta_pupcon.setWrapText(true);
		ta_lncon.setWrapText(true);
		ta_ol.setWrapText(true);
		Registry reg = LocateRegistry.getRegistry("localhost", 3333);
		service = (ILecture_infoService) reg.lookup("lecture_info");

		btn_pdf.setFocusTraversable(false);
	}

}
