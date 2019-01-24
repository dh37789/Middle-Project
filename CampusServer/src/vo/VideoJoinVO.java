package vo;

import java.io.Serializable;

public class VideoJoinVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String un_id;
	private String un_nm;
	private String con_con;
	private int mem_vd_ean;
	
	public String getUn_id() {
		return un_id;
	}
	public void setUn_id(String un_id) {
		this.un_id = un_id;
	}
	public String getUn_nm() {
		return un_nm;
	}
	public void setUn_nm(String un_nm) {
		this.un_nm = un_nm;
	}
	public String getCon_con() {
		return con_con;
	}
	public void setCon_con(String con_con) {
		this.con_con = con_con;
	}
	public int getMem_vd_ean() {
		return mem_vd_ean;
	}
	public void setMem_vd_ean(int mem_vd_ean) {
		this.mem_vd_ean = mem_vd_ean;
	}
	
}
