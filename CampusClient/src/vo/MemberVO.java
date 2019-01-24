package vo;

import java.io.Serializable;

public class MemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mem_id; // 회원 아이디 PK
	private String mem_ps; // 회원 비밀번호
	private String mem_em; // 회원 이메일
	private String mem_ph; // 회원 전화번호
	private int mem_aDay; // 회원 출석 일수 
	private String mem_irt; // 회원 이미지 경로
	private int mem_pt; // 회원 보유 포인트 
	private int mem_access; // 회원 접근제어
	private String mem_nm; // 회원 이름
	
	
	
	public String getMem_nm() {
		return mem_nm;
	}
	public void setMem_nm(String mem_nm) {
		this.mem_nm = mem_nm;
	}
	public int getMem_access() {
		return mem_access;
	}
	public void setMem_access(int mem_access) {
		this.mem_access = mem_access;
	}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_ps() {
		return mem_ps;
	}
	public void setMem_ps(String mem_ps) {
		this.mem_ps = mem_ps;
	}
	public String getMem_em() {
		return mem_em;
	}
	public void setMem_em(String mem_em) {
		this.mem_em = mem_em;
	}
	public String getMem_ph() {
		return mem_ph;
	}
	public void setMem_ph(String mem_ph) {
		this.mem_ph = mem_ph;
	}
	public int getMem_aDay() {
		return mem_aDay;
	}
	public void setMem_aDay(int mem_aDay) {
		this.mem_aDay = mem_aDay;
	}
	public String getMem_irt() {
		return mem_irt;
	}
	public void setMem_irt(String mem_irt) {
		this.mem_irt = mem_irt;
	}
	public int getMem_pt() {
		return mem_pt;
	}
	public void setMem_pt(int mem_pt) {
		this.mem_pt = mem_pt;
	}

	
}
