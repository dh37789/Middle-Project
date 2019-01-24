package vo;

import java.io.Serializable;

public class LcsVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String lcs_id; // 자격증 정보 아이디 P.K
	private String lcs_nm; // 자격증 정보 강의명
	private String lcs_tdt; // 자격증 정보 시험날짜 
	private String lcs_con; // 자격증 정보 자격증 내용
	private String lcs_admId; // 자격증 정보 관리자 ID F.K
	
	public String getLcs_id() {
		return lcs_id;
	}
	public void setLcs_id(String lcs_id) {
		this.lcs_id = lcs_id;
	}
	public String getLcs_nm() {
		return lcs_nm;
	}
	public void setLcs_nm(String lcs_nm) {
		this.lcs_nm = lcs_nm;
	}
	public String getLcs_tdt() {
		return lcs_tdt;
	}
	public void setLcs_tdt(String lcs_tdt) {
		this.lcs_tdt = lcs_tdt;
	}
	public String getLcs_con() {
		return lcs_con;
	}
	public void setLcs_con(String lcs_con) {
		this.lcs_con = lcs_con;
	}
	public String getLcs_admId() {
		return lcs_admId;
	}
	public void setLcs_admId(String lcs_admId) {
		this.lcs_admId = lcs_admId;
	}
	
	
}
