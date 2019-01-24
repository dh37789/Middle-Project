package vo;

import java.io.Serializable;

public class Homework_boardVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String hwbd_id; // 과제제출 게시판 아이디 P.K
	private String hwbd_ti; // 과제제출 게시판 제목
	private String hwbd_wrr; // 과제제출 게시판 작성자
	private String hwbd_dt; // 과제제출 게시판 작성 날짜
	private String hwbd_con; // 과제제출 게시판 내용
	private String hwbd_frt; // 과제제출 게시판 파일경로
	private String hwbd_memId; // 과제제출 게시판 회원 아이디 F.K
	private String hwbd_sub; // 과제를 제출할 과목
	
	public String getHwbd_sub() {
		return hwbd_sub;
	}
	public void setHwbd_sub(String hwbd_sub) {
		this.hwbd_sub = hwbd_sub;
	}
	public String getHwbd_id() {
		return hwbd_id;
	}
	public void setHwbd_id(String hwbd_id) {
		this.hwbd_id = hwbd_id;
	}
	public String getHwbd_ti() {
		return hwbd_ti;
	}
	public void setHwbd_ti(String hwbd_ti) {
		this.hwbd_ti = hwbd_ti;
	}
	public String getHwbd_wrr() {
		return hwbd_wrr;
	}
	public void setHwbd_wrr(String hwbd_wrr) {
		this.hwbd_wrr = hwbd_wrr;
	}
	public String getHwbd_dt() {
		return hwbd_dt;
	}
	public void setHwbd_dt(String hwbd_dt) {
		this.hwbd_dt = hwbd_dt;
	}
	public String getHwbd_con() {
		return hwbd_con;
	}
	public void setHwbd_con(String hwbd_con) {
		this.hwbd_con = hwbd_con;
	}
	public String getHwbd_frt() {
		return hwbd_frt;
	}
	public void setHwbd_frt(String hwbd_frt) {
		this.hwbd_frt = hwbd_frt;
	}
	public String getHwbd_memId() {
		return hwbd_memId;
	}
	public void setHwbd_memId(String hwbd_memId) {
		this.hwbd_memId = hwbd_memId;
	}
	
	
}
