package classRoom.video.controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import classRoom.main.controller.ClassroomController;
import classRoom.video.service.IVideoService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.stage.Stage;
import vo.MemberVO;
import vo.Member_videoVO;
import vo.VideoVO;

public class video_playController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane root;

	@FXML
	private ImageView imgrewind;

	@FXML
	private ImageView imgx2;

	@FXML
	private Label lblx;

	@FXML
	private ImageView imgPlay;

	@FXML
	private ImageView imgStop;

	@FXML
	private ImageView volume;

	@FXML
	private Slider volumebar;

	@FXML
	private Slider slider;

	@FXML
	private Label playTime;

	@FXML
	private Label totalTime;

	@FXML
	private Label lblResult;

	@FXML
	private TextField tfTitle;

	private Stage stage;
	
	@FXML
	private MediaView media;

	private MemberVO mvo;

	private Map<String, String> param = new HashMap<>();
	
	private ClassroomController mainCon;
	
	TimeSetting playSet = new TimeSetting();
	
    public void setMainCon(ClassroomController mainCon) {
		this.mainCon = mainCon;
	}

	@FXML
    private HBox savebox;

	public void setWorker(boolean worker) {
		this.worker = worker;
		
		//현재 쓰레드를 중지시키기위해 무한반복문을 걸어준다.
		while(!worker) {
			
			if(timeThread==null || timeThread.getState()==Thread.State.NEW || timeThread.getState()==Thread.State.TERMINATED) {
				break;
			}
		}
	}

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
		param.put("mem_id", mvo.getMem_id());
		param.put("vd_id", vo.getVd_id());

		try {
			thread();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 클릭했을때 동영상시작
	public void start() {
		try {
			mediaplayer = new MediaPlayer(new Media(this.getClass().getResource(temp.temp + ".mp4").toExternalForm()));
			if (mediaplayer == null) {
				return;
			}
			mediaplayer.setAutoPlay(true);
			media.setFitWidth(750);
			media.setFitHeight(1200);
			media.setMediaPlayer(mediaplayer);
		} catch (Exception e) {
			
		}

	}
	  public void stopVd() {
	    	if (mediaplayer != null) {
				mediaplayer.stop();
			}
	    }
	void volume(boolean bol) {
		if (bol == true) {
			volume.setImage(mute);
			mediaplayer.setVolume(0);
		} else {
			volume.setImage(imgVol);
			mediaplayer.setVolume(volumebar.getValue() / 100);
		}
	}

	void play(boolean result) {

		if (result == true) {
			mediaplayer.pause();
			imgPlay.setImage(pause);
			timework = false;
		} else {
			timework = true;
			mediaplayer.play();
			imgPlay.setImage(play);
		}
	}

	public void stop() {
//    	mediaplayer.stop();
//    	mediaplayer.setRate(1.2);
//    	timework = false;
//    	a = !a;
		play(a);
		a = !a;
	}

	/**
	 * x2 버튼을 눌렀을때 0.2씩 더해서 빠르게한다.
	 * 
	 */
	void x2(String value) {
		String temp = value.substring(1, 4);
		if (Double.parseDouble(temp) >= 2.0) {
			lblx.setText("x1.0");
			mediaplayer.setRate(1.0);
			return;
		}

		double a = Double.parseDouble(temp) + 0.2F;
		mediaplayer.setRate(a);
		String str = "x" + a;
		lblx.setText(str.substring(0, 4));
	}

	void rewind(String value) {
		String temp = value.substring(1, 4);
		double a = Double.parseDouble(temp) - 0.19F;
		if (Double.parseDouble(temp) == 0.0) {
			lblx.setText("x1.0");
			mediaplayer.setRate(1.0);
			return;
		}
		mediaplayer.setRate(a);
		String str = "x" + a;
		lblx.setText(str.substring(0, 4));

	}

	boolean a = true;
	boolean vol = true;
	Image play = new Image(getClass().getResourceAsStream("pause.png"));
	Image pause = new Image(getClass().getResourceAsStream("play.png"));
	Image stop = new Image(getClass().getResourceAsStream("stop.png"));
	Image mute = new Image(getClass().getResourceAsStream("mute.png"));
	Image imgVol = new Image(getClass().getResourceAsStream("volume.png"));
	private MediaPlayer mediaplayer;
	
	public MediaPlayer getMediaPlayer() {
		return mediaplayer;
	}

	public IVideoService service;
	public VideoVO vo;
	private volatile boolean timework = true;
	private volatile static boolean worker = true;
	private int times;
	private Double tts;
	static Thread timeThread = null;
	Member_videoVO tt = new Member_videoVO();
	

	/// --------------------------쓰레드 메서드
	public void thread() throws RemoteException, SQLException{

		Member_videoVO vvo = service.selectTime(param);
		if (vvo == null) {
			Member_videoVO tt = new Member_videoVO();
			tt.setMem_vd_memId(mvo.getMem_id());
			tt.setMem_vd_vdId(vo.getVd_id());
			service.insert(tt);
			times = 0;
		} else {
			times = 0;
			times = vvo.getMem_vd_pltm();// 이곳에 db값 불러오기
		}
//		if(timeThread==null || timeThread.getState()==Thread.State.NEW || timeThread.getState()==Thread.State.TERMINATED) {
			//System.out.println("1. timeThread=" + timeThread);
			worker = true;
			timeThread = new Thread() {
				@Override
				public void run() {
					while (worker) {
						if (timework) {
							Platform.runLater(() -> {
								times++;
								tts = mediaplayer.getTotalDuration().toSeconds();
								if (!(Double.isNaN(tts))) {
									mediaplayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
										slider.setValue(newValue.toSeconds());
									});
									slider.valueProperty().addListener(new ChangeListener<Number>() {
										@Override
										public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
											if (Math.abs(oldValue.doubleValue() - newValue.doubleValue()) > 0.5) {
												mediaplayer.seek(Duration.seconds(slider.getValue()));
											}
										}
									});
									slider.maxProperty().bind(Bindings.createDoubleBinding(
											() -> mediaplayer.getTotalDuration().toSeconds(),
											mediaplayer.totalDurationProperty()));
								}
		        				   Map<String, String> param2 = new HashMap<>();
		        				   param2.put("vd_pltm", times+"");
		        				   param2.put("mem_id",mvo.getMem_id());
		        				   param2.put("vd_id",vo.getVd_id());
		        				   try {
									service.updateTime(param2);
								} catch (Exception e) {
									e.printStackTrace();
								}
//								int a = 0;
//								int b = 0; // 뒷자리
//	
//								a = times / 60;
//								b = times % 60;
//	
//								String tmp = "";// 숫자String
//								if (a < 10) {
//									tmp = "0" + a;
//								} else {
//									tmp += a;
//								}
//								if (b < 10) {
//									tmp += ":" + "0" + b;
//								} else {
//									tmp += ":" + b;
//								}
		        				playSet.setSecond(times);
		        				String sc = "";
		        				if (playSet.getSecond() == 0) {
									sc = playSet.getSecond() + "0";
								}else if (playSet.getSecond() < 10) {
									sc = "0"+playSet.getSecond();
								}else {
									sc = playSet.getSecond() + "";
								}
		        				String mn = "";
		        				if (playSet.getMinute() == 0) {
									mn = playSet.getMinute() + "0";
								}else if (playSet.getMinute() < 10){
									mn = "0" + playSet.getMinute();
								}else {
									mn = playSet.getMinute() + "";
								}
		        				playTime.setText(mn + ":" + sc);
								// 시간이 일치할경우 학습상태로 변경.
//								System.out.println("비디오 시간 : " + vo.getVd_pltm());
//								System.out.println("진행시간 : " + times);
								if (vo.getVd_pltm() == times) {
									lblResult.setText("학습을 완료하였습니다!");
									tt.setMem_vd_memId(mvo.getMem_id());
									tt.setMem_vd_vdId(vo.getVd_id());
									tt.setMem_vd_ean(1);
									try {
										int f= service.updatept(mvo.getMem_id());
										service.complete(tt);
										if(f >0) {
								    		Alert alert = new Alert(AlertType.INFORMATION);
								    		alert.setHeaderText(null);
								    		alert.setContentText("20포인트 적립완료!");
								    		alert.showAndWait();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
	
								}
								if(vvo ==null) {
									return;
								}
								if ( vvo.getMem_vd_ean() == 1) {
									lblResult.setText("학습을 완료하였습니다!");
								}
	
							});
	
							try {
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			};
			timeThread.setDaemon(true);
			timeThread.start();
//		}
	}

	@FXML
	void initialize() throws RemoteException, SQLException {
		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'video_play.fxml'.";
		assert slider != null : "fx:id=\"slider\" was not injected: check your FXML file 'video_play.fxml'.";
		assert imgrewind != null : "fx:id=\"imgrewind\" was not injected: check your FXML file 'video_play.fxml'.";
		assert imgx2 != null : "fx:id=\"imgx2\" was not injected: check your FXML file 'video_play.fxml'.";
		assert lblx != null : "fx:id=\"lblx\" was not injected: check your FXML file 'video_play.fxml'.";
		assert imgPlay != null : "fx:id=\"imgPlay\" was not injected: check your FXML file 'video_play.fxml'.";
		assert imgStop != null : "fx:id=\"imgStop\" was not injected: check your FXML file 'video_play.fxml'.";
		assert volume != null : "fx:id=\"volume\" was not injected: check your FXML file 'video_play.fxml'.";
		assert volumebar != null : "fx:id=\"volumebar\" was not injected: check your FXML file 'video_play.fxml'.";
		assert playTime != null : "fx:id=\"playTime\" was not injected: check your FXML file 'video_play.fxml'.";
		assert totalTime != null : "fx:id=\"totalTime\" was not injected: check your FXML file 'video_play.fxml'.";
		assert lblResult != null : "fx:id=\"lblResult\" was not injected: check your FXML file 'video_play.fxml'.";
		assert tfTitle != null : "fx:id=\"tfTitle\" was not injected: check your FXML file 'video_play.fxml'.";
		assert media != null : "fx:id=\"media\" was not injected: check your FXML file 'video_play.fxml'.";
	    assert savebox != null : "fx:id=\"savebox\" was not injected: check your FXML file 'video_play.fxml'.";
	    assert slider != null : "fx:id=\"slider\" was not injected: check your FXML file 'video_play.fxml'.";
		

		tfTitle.setText(temp.temp);
		tfTitle.setAlignment(Pos.CENTER);
		imgPlay.setImage(play);
		imgStop.setImage(stop);
		volume.setImage(imgVol);
		volumebar.setValue(50);
		Registry reg;
		//start();
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service = (IVideoService) reg.lookup("vdService");
			// 해당 video 전체 시간구하기
			vo = service.selectVideo(temp.temp);
			

			
			if (vo == null) {
				return;
			}
//			String time = "";
//			if (vo.getVd_pltm() / 1000 >= 1) {
//				int a = vo.getVd_pltm() / 100;
//				int b = vo.getVd_pltm() % 100;
//				if (b < 10) {
//					time = a + ":0" + b;
//				} else {
//					time = a + ":" + b;
//				}
//			} else {
//				int a = vo.getVd_pltm() / 100;
//				int b = vo.getVd_pltm() % 100;
//				if (b < 10) {
//					time = "0" + a + ":0" + b;
//				} else {
//					time = "0" + a + ":" + b;
//				}
//			}
			TimeSetting videoTime = new TimeSetting();
			videoTime.setSecond(vo.getVd_pltm());
			String sc = "";
			if (videoTime.getSecond() == 0) {
				sc = videoTime.getSecond() + "0";
			}else if (videoTime.getSecond() < 10) {
				sc = "0"+videoTime.getSecond();
			}else {
				sc = videoTime.getSecond() + "";
			}
			String mn = "";
			if (videoTime.getMinute() == 0) {
				mn = videoTime.getMinute() + "0";
			}else if (videoTime.getMinute() < 10){
				mn = "0" + videoTime.getMinute();
			}else {
				mn = videoTime.getMinute() + "";
			}
			totalTime.setText(mn + ":" + sc);
//   			totalTime.setText(value);
		} catch (Exception e) {
			e.printStackTrace();

		}

		imgPlay.setOnMouseClicked(e -> {
			play(a);
			a = !a;
		});
		imgStop.setOnMouseClicked(e -> {
			stop();
		});
		imgx2.setOnMouseClicked(e -> {
			x2(lblx.getText().trim());
		});
		imgrewind.setOnMouseClicked(e -> {
			rewind(lblx.getText().trim());
		});

		volume.setOnMouseClicked(e -> {
			volume(vol);
			vol = !vol;
		});

		volumebar.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mediaplayer.setVolume(volumebar.getValue() / 100);
			}

		});
		
		savebox.setOnMouseClicked(e->{
			timework =false;
			mediaplayer.pause();
			imgPlay.setImage(pause);
			mainCon.setFalse();
		});
		
	}
	
}





