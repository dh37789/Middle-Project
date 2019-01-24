package vo;

import java.io.Serializable;

public class Lecture_infoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String linf_id; // 강의정보 아이디 P.K
	private String linf_nm; // 강의정보 강의명
	private String linf_tc; // 강의정보 선생님
	private String linf_ph; // 강의정보 연락처
	private String linf_em; // 강의정보 이메일
	private String linf_ol; // 강의정보 개요
	private String linf_lnCon; // 강의정보 학습내용
	private String linf_pupCon; // 강의정보 목표 내용
	private String linf_unId; // 강의정도 단원 아이디 F.K
	
	public String getLinf_id() {
		return linf_id;
	}
	public void setLinf_id(String linf_id) {
		this.linf_id = linf_id;
	}
	public String getLinf_nm() {
		return linf_nm;
	}
	public void setLinf_nm(String linf_nm) {
		this.linf_nm = linf_nm;
	}
	public String getLinf_tc() {
		return linf_tc;
	}
	public void setLinf_tc(String linf_tc) {
		this.linf_tc = linf_tc;
	}
	public String getLinf_ph() {
		return linf_ph;
	}
	public void setLinf_ph(String linf_ph) {
		this.linf_ph = linf_ph;
	}
	public String getLinf_em() {
		return linf_em;
	}
	public void setLinf_em(String linf_em) {
		this.linf_em = linf_em;
	}
	public String getLinf_ol() {
		return linf_ol;
	}
	public void setLinf_ol(String linf_ol) {
		this.linf_ol = linf_ol;
	}
	public String getLinf_lnCon() {
		return linf_lnCon;
	}
	public void setLinf_lnCon(String linf_lnCon) {
		this.linf_lnCon = linf_lnCon;
	}
	public String getLinf_pupCon() {
		return linf_pupCon;
	}
	public void setLinf_pupCon(String linf_pupCon) {
		this.linf_pupCon = linf_pupCon;
	}
	public String getLinf_unId() {
		return linf_unId;
	}
	public void setLinf_unId(String linf_unId) {
		this.linf_unId = linf_unId;
	}
	
}
