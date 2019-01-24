package home.mem_info.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import home.attend.service.IAttendService;
import home.mem_info.service.IMem_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.register.service.IRegisterService;
import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;

public class MemCouponController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox pane;

	@FXML
	private TextField tfCoffee;

	@FXML
	private Button btnUseCof;

	@FXML
	private TextField tfDoughnut;

	@FXML
	private Button btnUseDon;

	@FXML
	private TextField tfCake;

	@FXML
	private Button btnUseCak;

	IAttendService service;
	IMem_infoService service1;

	MemberVO mvo;

	CouponVO cvo;

	Member_couponVO mcvo;

	Stage stage;

	public void setMcvo(Member_couponVO mcvo) {
		this.mcvo = mcvo;
	}

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}

	public void setCvo(CouponVO cvo) {
		this.cvo = cvo;
	}

	@FXML
	void chkUseCak(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "쿠폰을 발급받으시겠습니까? 보유 수량이 하나 감소합니다.");
		if (ans == ButtonType.OK) {
			Map<String, String> map = new HashMap<>();
			map.put("memid", mvo.getMem_id());
			map.put("coid", "cak");

			mcvo = service1.getCouponCount(map);
			if (mcvo != null) {
				int chk = mcvo.getMem_co_count();
				if (chk > 0) {
					int use = 0;
					try {
						use = service1.updateUseCoupon(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					if (use > 0) {
					} else {
						alert(null, "사용 실패");
					}
					Member_couponVO memcoupvo = null;
					try {
						memcoupvo = service1.getCouponCount(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					int amount = memcoupvo.getMem_co_count();
					if (memcoupvo != null) {
						tfCake.setText(amount + "");
						Stage giftIcon = new Stage(StageStyle.DECORATED);
						giftIcon.initModality(Modality.WINDOW_MODAL);
						giftIcon.initOwner(stage);
						giftIcon.setTitle("기프티콘");
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource("../../mem_info/controller/cake.fxml"));
						Parent root = loader.load();

						cakeController cake = loader.getController();
						cake.Coupon();
						cake.setStage(giftIcon);
						cake.stageResize();

						Scene scene = new Scene(root, 280, 510);
						giftIcon.setScene(scene);
						giftIcon.show();
					}
				} else {
					alert(null, "보유 쿠폰이 없습니다.");
					return;
				}
			} else {
				alert(null, "보유 쿠폰이 없습니다.");
				return;
			}
		}
	}

	@FXML
	void chkUseCof(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "쿠폰을 발급받으시겠습니까? 보유 수량이 하나 감소합니다.");
		if (ans == ButtonType.OK) {
			Map<String, String> map = new HashMap<>();
			map.put("memid", mvo.getMem_id());
			map.put("coid", "cof");

			mcvo = service1.getCouponCount(map);
			if (mcvo != null) {
				int chk = mcvo.getMem_co_count();
				if (chk > 0) {
					int use = 0;
					try {
						use = service1.updateUseCoupon(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					if (use > 0) {
					} else {
						alert(null, "사용 실패");
					}
					Member_couponVO memcoupvo = null;
					try {
						memcoupvo = service1.getCouponCount(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					int amount = memcoupvo.getMem_co_count();
					if (memcoupvo != null) {
						if (amount >= 0) {
							tfCoffee.setText(amount + "");

							Stage giftIcon = new Stage(StageStyle.DECORATED);
							giftIcon.initModality(Modality.WINDOW_MODAL);
							giftIcon.initOwner(stage);
							giftIcon.setTitle("기프티콘");
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("../../mem_info/controller/coffee.fxml"));
							Parent root = loader.load();

							coffeeController coffee = loader.getController();
							coffee.Coupon();
							coffee.setStage(giftIcon);
							coffee.stageResize();

							Scene scene = new Scene(root);
							giftIcon.setScene(scene);
							giftIcon.show();
						}
					}
				} else {
					alert(null, "보유 쿠폰이 없습니다.");
					return;
				}
			} else {
				alert(null, "보유 쿠폰이 없습니다.");
				return;
			}
		}
	}

	@FXML
	void chkUseDon(ActionEvent event) throws IOException {
		ButtonType ans = confirm(null, "쿠폰을 발급받으시겠습니까? 보유 수량이 하나 감소합니다.");
		if (ans == ButtonType.OK) {
			Map<String, String> map = new HashMap<>();
			map.put("memid", mvo.getMem_id());
			map.put("coid", "don");

			mcvo = service1.getCouponCount(map);
			if (mcvo != null) {
				int chk = mcvo.getMem_co_count();
				if (chk > 0) {
					int use = 0;
					try {
						use = service1.updateUseCoupon(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					if (use >= 1) {
					} else {
						alert(null, "사용 실패");
					}
					Member_couponVO memcoupvo = null;
					try {
						memcoupvo = service1.getCouponCount(map);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					int amount = memcoupvo.getMem_co_count();
					if (memcoupvo != null) {
						if (amount >= 0) {
							tfDoughnut.setText(amount + "");

							Stage giftIcon = new Stage(StageStyle.DECORATED);
							giftIcon.initModality(Modality.WINDOW_MODAL);
							giftIcon.initOwner(stage);
							giftIcon.setTitle("기프티콘");
							FXMLLoader loader = new FXMLLoader(
									getClass().getResource("../../mem_info/controller/doughnut.fxml"));
							Parent root = loader.load();

							doughnutController doughnut = loader.getController();
							doughnut.Coupon();
							doughnut.setStage(giftIcon);
							doughnut.stageResize();

							Scene scene = new Scene(root);
							giftIcon.setScene(scene);
							giftIcon.show();
						}
					}
				} else {
					alert(null, "보유 쿠폰이 없습니다.");
					return;
				}
			} else {
				alert(null, "보유 쿠폰이 없습니다.");
				return;
			}
		}
	}

	@FXML
	void initialize() {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert tfCoffee != null : "fx:id=\"tfCoffee\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert btnUseCof != null : "fx:id=\"btnUseCof\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert tfDoughnut != null : "fx:id=\"tfDoughnut\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert btnUseDon != null : "fx:id=\"btnUseDon\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert tfCake != null : "fx:id=\"tfCake\" was not injected: check your FXML file 'memCoupon.fxml'.";
		assert btnUseCak != null : "fx:id=\"btnUseCak\" was not injected: check your FXML file 'memCoupon.fxml'.";

		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 3333);
			service1 = (IMem_infoService) reg.lookup("memberInfo");
			service = (IAttendService) reg.lookup("attend");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		btnUseCak.setFocusTraversable(false);
		btnUseCof.setFocusTraversable(false);
		btnUseDon.setFocusTraversable(false);
		
		tfCake.setMouseTransparent(true);
		tfCoffee.setMouseTransparent(true);
		tfDoughnut.setMouseTransparent(true);
	}

	public void alert(String header, String msg) {
		Alert warning = new Alert(AlertType.WARNING);
		warning.setTitle("경고");
		warning.setHeaderText(header);
		warning.setContentText(msg);

		warning.showAndWait();
	}

	public ButtonType Inform(String msg) {
		Alert inform = new Alert(AlertType.INFORMATION);

		inform.setTitle("확인");
		inform.setHeaderText(null);
		inform.setContentText(msg);

		return inform.showAndWait().get();
	}

	public ButtonType confirm(String header, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("안내");
		alert.setHeaderText(header);
		alert.setContentText(msg);

		ButtonType comfirmResult = alert.showAndWait().get();
		return comfirmResult;
	}

	public void checkCake() {
		Map<String, String> map = new HashMap<>();
		map.put("memid", mvo.getMem_id());
		map.put("coid", "cak");

		Member_couponVO memcoupvo = null;
		try {
			memcoupvo = service1.getCouponCount(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (memcoupvo == null) {
			tfCake.setText("0");
		} else {
			int amount = memcoupvo.getMem_co_count();
			tfCake.setText(amount + "");
		}
	}

	public void checkCoffee() {
		Map<String, String> map = new HashMap<>();
		map.put("memid", mvo.getMem_id());
		map.put("coid", "cof");

		Member_couponVO memcoupvo = null;
		try {
			memcoupvo = service1.getCouponCount(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (memcoupvo == null) {
			tfCoffee.setText("0");
		} else {
			int amount = memcoupvo.getMem_co_count();
			tfCoffee.setText(amount + "");
		}
	}

	public void checkDoughnut() {
		Map<String, String> map = new HashMap<>();
		map.put("memid", mvo.getMem_id());
		map.put("coid", "don");

		Member_couponVO memcoupvo = null;
		try {
			memcoupvo = service1.getCouponCount(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (memcoupvo == null) {
			tfDoughnut.setText("0");
		} else {
			int amount = memcoupvo.getMem_co_count();
			tfDoughnut.setText(amount + "");
		}
	}
}
