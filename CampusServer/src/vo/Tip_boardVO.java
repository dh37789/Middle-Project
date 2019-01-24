package vo;

import java.io.Serializable;

public class Tip_boardVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tbd_id; // 팁 게시판 아이디 PK
	private String tbd_ti; // 팁 게시판 제목
	private String tbd_wrr; // 팁게시판 작성자
	private String tbd_dt; // 팁 게시판 작성 날짜
	private String tbd_con; // 팁 게시판 내용
	private int tbd_iNum; // 팁 게시판 조회수 
	private String tbd_memID; // 팁 게시판 회원 아이디 FK
	
	public String getTbd_id() {
		return tbd_id;
	}
	public void setTbd_id(String tbd_id) {
		this.tbd_id = tbd_id;
	}
	public String getTbd_ti() {
		return tbd_ti;
	}
	public void setTbd_ti(String tbd_ti) {
		this.tbd_ti = tbd_ti;
	}
	public String getTbd_wrr() {
		return tbd_wrr;
	}
	public void setTbd_wrr(String tbd_wrr) {
		this.tbd_wrr = tbd_wrr;
	}
	public String getTbd_dt() {
		return tbd_dt;
	}
	public void setTbd_dt(String tbd_dt) {
		this.tbd_dt = tbd_dt;
	}
	public String getTbd_con() {
		return tbd_con;
	}
	public void setTbd_con(String tbd_con) {
		this.tbd_con = tbd_con;
	}
	public int getTbd_iNum() {
		return tbd_iNum;
	}
	public void setTbd_iNum(int tbd_iNum) {
		this.tbd_iNum = tbd_iNum;
	}
	public String getTbd_memID() {
		return tbd_memID;
	}
	public void setTbd_memID(String tbd_memID) {
		this.tbd_memID = tbd_memID;
	}

	
}
