package admin.mem_manage.controller;

import javafx.scene.control.CheckBox;
import vo.MemberVO;

public class MemberApply {
	private MemberVO mvo;
	private CheckBox ch;
	
	public MemberVO getMvo() {
		return mvo;
	}
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	public CheckBox getCh() {
		return ch;
	}
	public void setCh(CheckBox ch) {
		this.ch = ch;
	}
} 
