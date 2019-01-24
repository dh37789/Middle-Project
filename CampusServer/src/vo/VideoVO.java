package vo;

import java.io.Serializable;

public class VideoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String vd_id; // 동영상 아이디 PK
	private String vd_nm; // 동영상 이름
	private int vd_pltm; // 동영상 재생시간 
	private String vd_flrt; // 동영상 파일 경로
	private String vd_unId; // 동영상 단원 아이디 FK
	private String vd_admId; // 동영상 관리자 아이디FK
	
	public String getVd_id() {
		return vd_id;
	}
	public void setVd_id(String vd_id) {
		this.vd_id = vd_id;
	}
	public String getVd_nm() {
		return vd_nm;
	}
	public void setVd_nm(String vd_nm) {
		this.vd_nm = vd_nm;
	}
	public int getVd_pltm() {
		return vd_pltm;
	}
	public void setVd_pltm(int vd_pltm) {
		this.vd_pltm = vd_pltm;
	}
	public String getVd_flrt() {
		return vd_flrt;
	}
	public void setVd_flrt(String vd_flrt) {
		this.vd_flrt = vd_flrt;
	}
	public String getVd_unId() {
		return vd_unId;
	}
	public void setVd_unId(String vd_unId) {
		this.vd_unId = vd_unId;
	}
	public String getVd_admId() {
		return vd_admId;
	}
	public void setVd_admId(String vd_admId) {
		this.vd_admId = vd_admId;
	}
	
	
}
