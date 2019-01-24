package vo;

import java.io.Serializable;

public class AdminVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String adm_id; // 관리자ID P.K
	private String adm_ps; // 관리자 비밀번호
	
	public String getAdm_id() {
		return adm_id;
	}
	public void setAdm_id(String adm_id) {
		this.adm_id = adm_id;
	}
	public String getAdm_ps() {
		return adm_ps;
	}
	public void setAdm_ps(String adm_ps) {
		this.adm_ps = adm_ps;
	}
	
	
}
