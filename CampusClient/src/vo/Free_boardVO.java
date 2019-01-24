package vo;

import java.io.Serializable;

public class Free_boardVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String frbd_id; // 자유게시판 아이디 P.K
	private String frbd_ti; // 자유게시판 제목
	private String frbd_dt; // 자유게시판 작성날짜
	private String frbd_wrr; // 자유게시판 글쓴이
	private String frbd_con; // 자유게시판 내용
	private int frbd_iNum; // 자유게시판 조회수
	private String frbd_memId; // 자유게시판 회원 아이디 F.K
	
	public String getFrbd_id() {
		return frbd_id;
	}
	public void setFrbd_id(String frbd_id) {
		this.frbd_id = frbd_id;
	}
	public String getFrbd_ti() {
		return frbd_ti;
	}
	public void setFrbd_ti(String frbd_ti) {
		this.frbd_ti = frbd_ti;
	}
	public String getFrbd_dt() {
		return frbd_dt;
	}
	public void setFrbd_dt(String frbd_dt) {
		this.frbd_dt = frbd_dt;
	}
	public String getFrbd_wrr() {
		return frbd_wrr;
	}
	public void setFrbd_wrr(String frbd_wrr) {
		this.frbd_wrr = frbd_wrr;
	}
	public String getFrbd_con() {
		return frbd_con;
	}
	public void setFrbd_con(String frbd_con) {
		this.frbd_con = frbd_con;
	}
	public int getFrbd_iNum() {
		return frbd_iNum;
	}
	public void setFrbd_iNum(int frbd_iNum) {
		this.frbd_iNum = frbd_iNum;
	}
	public String getFrbd_memId() {
		return frbd_memId;
	}
	public void setFrbd_memId(String frbd_memId) {
		this.frbd_memId = frbd_memId;
	}
	
	
}
