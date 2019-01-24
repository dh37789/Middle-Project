package classRoom.question.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import classRoom.question.service.IQuestionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import vo.MemberVO;
import vo.Practice_questionVO;

public class QuestionController {
	// 색이 변할 키워드들
	private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };
	// 특정 키워드 안에있을경우 색이 변한다.
	 private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	 private static final String PAREN_PATTERN = "\\(|\\)";
	 private static final String BRACE_PATTERN = "\\{|\\}";
	 private static final String BRACKET_PATTERN = "\\[|\\]";
	 private static final String SEMICOLON_PATTERN = "\\;";
	 private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	 private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
	    
	 private static final Pattern PATTERN = Pattern.compile(
	            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
	            + "|(?<PAREN>" + PAREN_PATTERN + ")"
	            + "|(?<BRACE>" + BRACE_PATTERN + ")"
	            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
	            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
	            + "|(?<STRING>" + STRING_PATTERN + ")"
	            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
	    );
	 
    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox vCode;
    
    @FXML
    private URL location;

    @FXML
    private AnchorPane vBox;
    		
    private CodeArea taSource;

    @FXML
    private Text lbQuest;
    
    @FXML
    private TextArea taResult;

    @FXML
    private Button btnCompile;

    @FXML
    private Button btnChk;

    @FXML
    private Button btnCancel;
    
    private Stage stage;

    private IQuestionService service;
    
    private Practice_questionVO questionVO;
    
    private MemberVO mvo;
    
    public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	private boolean ctrlPress = false; 
    
    private Scene scene;
    
    private QuestionListController controller;
    
    public void setController(QuestionListController controller) {
		this.controller = controller;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setQuestionVO(Practice_questionVO questionVO) {
		this.questionVO = questionVO;
	}

	@FXML
    void cancel(ActionEvent event) {
    	stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void compile(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
    	String command = "";
    	String result = "";
    	FileOutputStream fout = null;
		try{
			fout = new FileOutputStream("D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/Test.java");
			OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");
			osw.write(taSource.getText());
			osw.flush();
			osw.close();
			fout.close(); // 스트림 닫기
		} catch (IOException e){
			e.printStackTrace();
		}
		
		Cmd cmd = new Cmd();
		taResult.setText("");
		
		command = cmd.inputCommand("javac -encoding UTF-8 Test.java");
		result = cmd.execCommand(command);
		command = cmd.inputCommand("java -Dfile.encoding=UTF-8 Test");
		result = cmd.execCommand(command);

		if (result.length() == 0) {
			 JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		        DiagnosticCollector<JavaFileObject> diagnostics =
		                new DiagnosticCollector<>();
		        StandardJavaFileManager standardFileManager =
		                compiler.getStandardFileManager(diagnostics, null, null);

		        File file = new File("D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/Test.java");

		        Iterable<? extends JavaFileObject> javaFileObjects =
		                standardFileManager.getJavaFileObjects(file);

		        JavaCompiler.CompilationTask task = compiler.getTask(
		                null, standardFileManager, diagnostics, null,
		                null, javaFileObjects);

		        Future<Boolean> future = Executors.newFixedThreadPool(1).submit(task);
		        Boolean result2 = future.get();
		        if (result2 != null && result2) {
		            diagnostics.getDiagnostics().forEach(System.out::println);
		        } else {
		           result = diagnostics.getDiagnostics().get(0).toString();
		           taResult.setStyle("-fx-text-fill: red;"); 
		           taResult.setText(result);
		        }
		        standardFileManager.close();
		}else {
			btnChk.setDisable(false);
			taResult.setStyle("-fx-text-fill: black;"); 
			taResult.setText(result);
			File file = new File("D:/A_TeachingMaterial/4.MiddleProject/workspace/CampusClient/Test.class");
			file.delete();
		}
    }

    @FXML
    void resultChk(ActionEvent event) {
    	String answer = questionVO.getPqt_aw() + "\n";
    	String result = taResult.getText();
    	taResult.setText(result);
    	if (result.equals(answer)) {
			taResult.setText(result + "정답입니다!!!");
			btnChk.setDisable(true);
			btnCompile.setDisable(true);
			int up = 0;
			if (questionVO.getMem_pqt_ean() == 0) {
				int cnt = 0;
				Map<String, String> chkMap = new HashMap<>();
				chkMap.put("mem_id", mvo.getMem_id());
				chkMap.put("pqt_id", questionVO.getPqt_id());
				try {
					up = service.increaseHit(questionVO.getPqt_id());
					cnt = service.memberQuizChk(chkMap);
					if (cnt > 0&&up > 0) {
						controller.getTableData();
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}else {
			taResult.setText(result + "정답이 아닙니다.");
			btnChk.setDisable(true);
		}
    }
    @FXML
    void initialize() {
    	assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'question.fxml'.";
    	assert vCode != null : "fx:id=\"vCode\" was not injected: check your FXML file 'question.fxml'.";
    	assert lbQuest != null : "fx:id=\"lbQuest\" was not injected: check your FXML file 'question.fxml'.";
        assert taResult != null : "fx:id=\"taResult\" was not injected: check your FXML file 'question.fxml'.";
        assert btnCompile != null : "fx:id=\"btnCompile\" was not injected: check your FXML file 'question.fxml'.";
        assert btnChk != null : "fx:id=\"btnChk\" was not injected: check your FXML file 'question.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'question.fxml'.";
        taSource = new CodeArea();
        taSource.setPrefWidth(780);
        taSource.setPrefHeight(320);
        taSource.setStyle("-fx-font-size : 17");
        taSource.setParagraphGraphicFactory(LineNumberFactory.get(taSource));
        Subscription cleanupWhenNoLongerNeedIt = taSource
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> taSource.setStyleSpans(0, computeHighlighting(taSource.getText())));
        
        vCode.getChildren().add(taSource);
        taSource.insertText(0, "public class Test {\n\n}");
        taSource.moveTo(0);
        
        Registry reg;
        try {
        	reg = LocateRegistry.getRegistry("localhost", 3333);
        	service = (IQuestionService) reg.lookup("question");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e)
        
        {
			e.printStackTrace();
		}
        btnChk.setDisable(true);
        taSource.setOnKeyPressed(e -> {
        	if (e.getCode() == KeyCode.CONTROL) {
        		ctrlPress = true;
			}
        	if (e.getCode() == KeyCode.SPACE) {
				if (ctrlPress == true) {
					System.out.println("컨 + 스");
					int bob = taSource.getCaretPosition();
					String sou = taSource.getText();
					String change4 = sou.substring(bob-4, bob);
					String change3 = sou.substring(bob-3, bob);
					String change2 = sou.substring(bob-2, bob);
					if (change4.equals("main")) {
						change4 = replaceLast(taSource.getText(), "main", "public static void main(String[] args) {\r\n" + 
								"		\r\n" + 
								"	}");
						taSource.replaceText(change4);
						taSource.moveTo(bob);
					}else if(change3.equals("for")) {
						change3 = replaceLast(taSource.getText(), "for", "for (int i = 0; i < args.length; i++) {\r\n" + 
								"			\r\n" + 
								"		}");
						taSource.replaceText(change3);
						taSource.moveTo(bob);
					}else if(change4.equals("syso")) {
						change4 = replaceLast(taSource.getText(), "syso", "System.out.println();"
								+ "");
						taSource.replaceText(change4);
						taSource.moveTo(bob);
					}else if(change2.equals("if")) {
						change2 = replaceLast(taSource.getText(), "if", "if (condition) {\r\n" + 
								"				\r\n" + 
								"				}"
								+ "");
						taSource.replaceText(change2);
						taSource.moveTo(bob);
					}
				}
			}
        });
        taSource.setOnKeyReleased(e -> {
        	if (e.getCode() == KeyCode.CONTROL) {
        		ctrlPress = false;
			}
        });
        
        btnCancel.setFocusTraversable(false);
        btnChk.setFocusTraversable(false);
        btnCompile.setFocusTraversable(false);
    }
    private static String replaceLast(String string, String toReplace, String replacement) {    
    	   int pos = string.lastIndexOf(toReplace);     
    	   if (pos > -1) {        
    	   return string.substring(0, pos)+ replacement + string.substring(pos +   toReplace.length(), string.length());     
    	   } else { 
    		return string;     
    	   } 
    	} 

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
	void viewQuest() {
		 lbQuest.setText(questionVO.getPqt_con());
	}
	void cssScene() {
		scene.getStylesheets().add(getClass().getResource("java-keywords.css").toExternalForm());
	}
    
}
