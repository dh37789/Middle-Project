package vo;

import java.io.Serializable;

public class Meal_chartVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mlc_id; // 식단표 아이디 PK
	private String mlc_dt; // 식단표 날짜 
	private String mlc_ml; // 식단표 식단
	private String mlc_admid; // 식단표 관리자 아이디 FK
	
	public String getMlc_id() {
		return mlc_id;
	}
	public void setMlc_id(String mlc_id) {
		this.mlc_id = mlc_id;
	}
	public String getMlc_dt() {
		return mlc_dt;
	}
	public void setMlc_dt(String mlc_dt) {
		this.mlc_dt = mlc_dt;
	}
	public String getMlc_ml() {
		return mlc_ml;
	}
	public void setMlc_ml(String mlc_ml) {
		this.mlc_ml = mlc_ml;
	}
	public String getMlc_admid() {
		return mlc_admid;
	}
	public void setMlc_admid(String mlc_admid) {
		this.mlc_admid = mlc_admid;
	}
	
	
}
