package vo;

import java.io.Serializable;

public class CouponVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String co_id; // 쿠폰 아이디 P.K
	private String co_nm; // 쿠폰 이름
	private int co_pe; // 쿠폰 가격
	private String co_con; // 쿠폰 내용
	
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getCo_nm() {
		return co_nm;
	}
	public void setCo_nm(String co_nm) {
		this.co_nm = co_nm;
	}
	public int getCo_pe() {
		return co_pe;
	}
	public void setCo_pe(int co_pe) {
		this.co_pe = co_pe;
	}
	public String getCo_con() {
		return co_con;
	}
	public void setCo_con(String co_con) {
		this.co_con = co_con;
	}
	
}
