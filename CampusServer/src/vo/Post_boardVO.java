package vo;

import java.io.Serializable;

public class Post_boardVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String popbd_id; // 강의 후기 게시판 아이디 PK
	private String popbd_ti; // 강의 후기 게시판 제목
	private String popbd_wrr; // 강의 후기 게시판 작성자
	private String popbd_dt; // 강의 후기 게시판 작성 날짜
	private String popbd_con; // 강의 후기 게시판 내용
	private int popbd_iNum; // 강의 후기 게시판 조회수
	private String popbd_memId; // 강의 후기 게시판 회원 아이디 FK
	private String popbd_sub;   // 강의 후기 게시판 과목
	
	public String getPopbd_sub() {
		return popbd_sub;
	}

	public void setPopbd_sub(String popbd_sub) {
		this.popbd_sub = popbd_sub;
	}

	public String getPopbd_id() {
		return popbd_id;
	}

	public void setPopbd_id(String popbd_id) {
		this.popbd_id = popbd_id;
	}

	public String getPopbd_ti() {
		return popbd_ti;
	}

	public void setPopbd_ti(String popbd_ti) {
		this.popbd_ti = popbd_ti;
	}

	public String getPopbd_wrr() {
		return popbd_wrr;
	}

	public void setPopbd_wrr(String popbd_wrr) {
		this.popbd_wrr = popbd_wrr;
	}

	public String getPopbd_dt() {
		return popbd_dt;
	}

	public void setPopbd_dt(String popbd_dt) {
		this.popbd_dt = popbd_dt;
	}

	public String getPopbd_con() {
		return popbd_con;
	}

	public void setPopbd_con(String popbd_con) {
		this.popbd_con = popbd_con;
	}

	public int getPopbd_iNum() {
		return popbd_iNum;
	}

	public void setPopbd_iNum(int popbd_iNum) {
		this.popbd_iNum = popbd_iNum;
	}

	public String getPopbd_memId() {
		return popbd_memId;
	}

	public void setPopbd_memId(String popbd_memId) {
		this.popbd_memId = popbd_memId;
	}

}
