package vo;

import java.io.Serializable;

public class Qna_boardVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String qabd_id; // 질의응답 게시판 아이디 PK
	private String qabd_ti; // 질의응답 게시판 제목
	private String qabd_wrr; // 질의응답 게시판 글쓴이
	private String qabd_dt; // 질의응답 게시판 작성 날짜
	private String qabd_con; // 질의응답 게시판 내용
	private String qabd_ps; // 질의응답 게시판 비밀번호
	private String qabd_memid; // 질의응답 게시판 회원 아이디 FK
	
	public String getQabd_id() {
		return qabd_id;
	}
	public void setQabd_id(String qabd_id) {
		this.qabd_id = qabd_id;
	}
	public String getQabd_ti() {
		return qabd_ti;
	}
	public void setQabd_ti(String qabd_ti) {
		this.qabd_ti = qabd_ti;
	}
	public String getQabd_wrr() {
		return qabd_wrr;
	}
	public void setQabd_wrr(String qabd_wrr) {
		this.qabd_wrr = qabd_wrr;
	}
	public String getQabd_dt() {
		return qabd_dt;
	}
	public void setQabd_dt(String qabd_dt) {
		this.qabd_dt = qabd_dt;
	}
	public String getQabd_con() {
		return qabd_con;
	}
	public void setQabd_con(String qabd_con) {
		this.qabd_con = qabd_con;
	}
	public String getQabd_ps() {
		return qabd_ps;
	}
	public void setQabd_ps(String qabd_ps) {
		this.qabd_ps = qabd_ps;
	}
	public String getQabd_memid() {
		return qabd_memid;
	}
	public void setQabd_memid(String qabd_memid) {
		this.qabd_memid = qabd_memid;
	}
	 
}
