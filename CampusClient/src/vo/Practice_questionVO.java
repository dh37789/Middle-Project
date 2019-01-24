package vo;

import java.io.Serializable;

public class Practice_questionVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pqt_id; // 연습문제 아이디 PK
	private String pqt_ti; // 연습문제 제목
	private String pqt_aw; // 연습문제 정답
	private int pqt_tct; // 연습문제 정답횟수
	private String pqt_admid; // 연습문제 관리자 아이디 FK
	private String pqt_con; // 연습문제 내용
	private int pqt_ct; // 연습문제 시도횟수
	private int mem_pqt_ean;
	private int pqt_diff;
	
	public int getPqt_diff() {
		return pqt_diff;
	}
	public void setPqt_diff(int pqt_diff) {
		this.pqt_diff = pqt_diff;
	}
	public int getMem_pqt_ean() {
		return mem_pqt_ean;
	}
	public void setMem_pqt_ean(int mem_pqt_ean) {
		this.mem_pqt_ean = mem_pqt_ean;
	}
	public String getPqt_id() {
		return pqt_id;
	}
	public void setPqt_id(String pqt_id) {
		this.pqt_id = pqt_id;
	}
	public String getPqt_ti() {
		return pqt_ti;
	}
	public void setPqt_ti(String pqt_ti) {
		this.pqt_ti = pqt_ti;
	}
	public String getPqt_con() {
		return pqt_con;
	}
	public void setPqt_con(String pqt_con) {
		this.pqt_con = pqt_con;
	}
	public String getPqt_aw() {
		return pqt_aw;
	}
	public void setPqt_aw(String pqt_aw) {
		this.pqt_aw = pqt_aw;
	}
	public int getPqt_tct() {
		return pqt_tct;
	}
	public void setPqt_tct(int pqt_tct) {
		this.pqt_tct = pqt_tct;
	}
	public String getPqt_admid() {
		return pqt_admid;
	}
	public void setPqt_admid(String pqt_admid) {
		this.pqt_admid = pqt_admid;
	}
	public int getPqt_ct() {
		return pqt_ct;
	}
	public void setPqt_ct(int pqt_ct) {
		this.pqt_ct = pqt_ct;
	}
	
}
