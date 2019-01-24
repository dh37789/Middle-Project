package home.attend.controller;

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
import home.main.controller.HomeMainController;
import home.mem_info.service.IMem_infoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;

public class HomeAttendController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnbuy1;

    @FXML
    private Button btnbuy2;

    @FXML
    private Button btnbuy3;

    @FXML
    private TextField tfmoney;

    @FXML
    private Button btnchk;

    IAttendService service;
    IMem_infoService service1;
    private MemberVO memvo;
    
    private CouponVO couvo;
    
    private int buycof = 150;
    
    private int buydon = 100;
    
    private int buycak = 200;
    
    
    private Member_couponVO mcvo;
    
    
    public void setMcvo(Member_couponVO mcvo) {
		this.mcvo = mcvo;
	}

	public void setCouvo(CouponVO couvo) {
		this.couvo = couvo;
	}
    
    public void setMemvo(MemberVO memvo) {
		this.memvo = memvo;
	}
    
    @FXML
    void buycak(ActionEvent event){
    	if (tfmoney.getText().isEmpty()) {
			alert(null, "포인트를 조회해주세요.");
			return;
		}
		int cnt = 0;

		if (memvo.getMem_pt() >= buycak) {
			tfmoney.setText((memvo.getMem_pt() - buycak) + "");
			memvo.setMem_pt(memvo.getMem_pt() - buycak);
			infoAlert(null, "구입 성공");
		} else {
			alert(null, "포인트가 부족합니다.");
			return;
		}
		try {
			cnt = service.updatePt(memvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Member_couponVO mccvo = new Member_couponVO();
		mccvo.setMem_co_con("케이크");
    	mccvo.setMem_co_count(mccvo.getMem_co_count());
    	mccvo.setMem_co_id("cak");
    	mccvo.setMem_co_mcid("");
    	mccvo.setMem_co_memid(memvo.getMem_id());
    	
		Map<String, String> map = new HashMap<>();
		map.put("memid", mccvo.getMem_co_memid());
		map.put("coid", mccvo.getMem_co_id());
		
		try {
			cnt = service.getCouponChk(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(cnt);
		if(cnt > 0) {
			if(memvo.getMem_pt() >= buycak) {
				
				try {
					service1.updateCouponChk(map);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}else {
				
			}
			//infoAlert(null, "회원정보에서 보유쿠폰을 확인하세요.");
		}else {
			try {
			int chk = service1.insertCouponChk(mccvo);
			if(chk > 0) {
			}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
    }
    @FXML
    void buycof(ActionEvent event) {
    	if (tfmoney.getText().isEmpty()) {
    		alert(null, "포인트를 조회해주세요.");
    		return;
    	}
		int cnt = 0;
		
		if (memvo.getMem_pt() >= buycof) {
			tfmoney.setText((memvo.getMem_pt() - buycof) + "");
			memvo.setMem_pt(memvo.getMem_pt() - buycof);
			infoAlert(null, "구입 성공");
		} else {
			alert(null, "포인트가 부족합니다.");
			return;
		}
		try {
			cnt = service.updatePt(memvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Member_couponVO mccvo = new Member_couponVO();
		mccvo.setMem_co_con("커피");
    	mccvo.setMem_co_count(mccvo.getMem_co_count());
    	mccvo.setMem_co_id("cof");
    	mccvo.setMem_co_mcid("");
    	mccvo.setMem_co_memid(memvo.getMem_id());
		
		Map<String, String> map = new HashMap<>();
		map.put("memid", mccvo.getMem_co_memid());
		map.put("coid", mccvo.getMem_co_id());
		
		try {
			cnt = service.getCouponChk(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(cnt);
		if(cnt > 0) {
			if(memvo.getMem_pt() >= buycof) {
			try {
			service1.updateCouponChk(map);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			}else {
				
			}
		}else {
			try {
			int chk = service1.insertCouponChk(mccvo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}
    @FXML
    void buydon(ActionEvent event) {
    	if (tfmoney.getText().isEmpty()) {
    		alert(null, "포인트를 조회해주세요.");
    		return;
    	}
		int cnt = 0;

		if (memvo.getMem_pt() >= buydon) {
			tfmoney.setText((memvo.getMem_pt() - buydon) + "");
			memvo.setMem_pt(memvo.getMem_pt() - buydon);
			infoAlert(null, "구입 성공");
		} else {
			alert(null, "포인트가 부족합니다.");
			return;
		}
		try {
			cnt = service.updatePt(memvo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Member_couponVO mccvo = new Member_couponVO();
		mccvo.setMem_co_con("도넛");
    	mccvo.setMem_co_count(mccvo.getMem_co_count());
    	mccvo.setMem_co_id("don");
    	mccvo.setMem_co_mcid("");
    	mccvo.setMem_co_memid(memvo.getMem_id());
		
		Map<String, String> map = new HashMap<>();
		map.put("memid", mccvo.getMem_co_memid());
		map.put("coid", mccvo.getMem_co_id());
		
		try {
			cnt = service.getCouponChk(map);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(cnt > 0) {
			if(memvo.getMem_pt() >= buydon) {
			try {
			service1.updateCouponChk(map);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			}else {
				
			}
//			infoAlert(null, "회원정보에서 보유쿠폰을 확인하세요.");
		}else {
			try {
			int chk = service1.insertCouponChk(mccvo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
    }

    @FXML
    void chkmn(ActionEvent event) throws IOException {
    	MemberVO mvo = new MemberVO();
    	mvo.setMem_id(memvo.getMem_id());
    	mvo.setMem_ps(memvo.getMem_ps());
    	mvo.setMem_em(memvo.getMem_em());
    	mvo.setMem_aDay(memvo.getMem_aDay());
    	mvo.setMem_ph(memvo.getMem_ph());
    	mvo.setMem_pt(memvo.getMem_pt());
    	mvo = service.selectPt(memvo.getMem_id());
    	
    	tfmoney.setText(mvo.getMem_pt()+"");
    }

    @FXML
    void initialize() {
        assert btnbuy1 != null : "fx:id=\"btnbuy1\" was not injected: check your FXML file 'homeAttend.fxml'.";
        assert btnbuy2 != null : "fx:id=\"btnbuy2\" was not injected: check your FXML file 'homeAttend.fxml'.";
        assert btnbuy3 != null : "fx:id=\"btnbuy3\" was not injected: check your FXML file 'homeAttend.fxml'.";
        assert tfmoney != null : "fx:id=\"tfmoney\" was not injected: check your FXML file 'homeAttend.fxml'.";
        assert btnchk != null : "fx:id=\"btnchk\" was not injected: check your FXML file 'homeAttend.fxml'.";

        Registry reg;
        
        try {
    		reg = LocateRegistry.getRegistry("localhost", 3333);
    		service = (IAttendService) reg.lookup("attend");
    		service1 = (IMem_infoService)reg.lookup("memberInfo");
    	} catch (RemoteException e) {
    		e.printStackTrace();
    	} catch (NotBoundException e) {
    		e.printStackTrace();
    	}
        btnbuy1.setFocusTraversable(false);
        btnbuy2.setFocusTraversable(false);
        btnbuy3.setFocusTraversable(false);
        btnchk.setFocusTraversable(false);
        
        tfmoney.setMouseTransparent(true);
        tfmoney.setEditable(false);

    }
    public void alert(String header, String msg) {
    	Alert warning = new Alert(AlertType.WARNING);
    	warning.setTitle("경고");
    	warning.setHeaderText(header);
    	warning.setContentText(msg);
    	
    	warning.showAndWait();
    }
    public void infoAlert(String header, String msg) {
    	Alert info = new Alert(AlertType.INFORMATION);
    	info.setTitle("알림");
    	info.setHeaderText(header);
    	info.setContentText(msg);
    	
    	info.showAndWait();
    }
    
}
