package vo;

import java.io.Serializable;

public class Member_couponVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mem_co_mcid; // 보유쿠폰 아이디 PK
	private String mem_co_id; // 쿠폰 아이디 FK
	private String mem_co_memid; // 회원 아이디 FK
	private String mem_co_con; // 보유쿠폰 쿠폰 내용
	private int mem_co_count; // 보유쿠폰 수량
	
	public String getMem_co_mcid() {
		return mem_co_mcid;
	}
	public void setMem_co_mcid(String mem_co_mcid) {
		this.mem_co_mcid = mem_co_mcid;
	}
	public String getMem_co_id() {
		return mem_co_id;
	}
	public void setMem_co_id(String mem_co_id) {
		this.mem_co_id = mem_co_id;
	}
	public String getMem_co_memid() {
		return mem_co_memid;
	}
	public void setMem_co_memid(String mem_co_memid) {
		this.mem_co_memid = mem_co_memid;
	}
	public String getMem_co_con() {
		return mem_co_con;
	}
	public void setMem_co_con(String mem_co_con) {
		this.mem_co_con = mem_co_con;
	}
	public int getMem_co_count() {
		return mem_co_count;
	}
	public void setMem_co_count(int mem_co_count) {
		this.mem_co_count = mem_co_count;
	}

	
}