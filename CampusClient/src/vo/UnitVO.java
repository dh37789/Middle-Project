package vo;

import java.io.Serializable;

public class UnitVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Un_id; // 단원 아이디 PK
	private String Un_nm; // 단원 이름 
	
	public String getUn_id() {
		return Un_id;
	}
	public void setUn_id(String un_id) {
		Un_id = un_id;
	}
	public String getUn_nm() {
		return Un_nm;
	}
	public void setUn_nm(String un_nm) {
		Un_nm = un_nm;
	}
	
}
