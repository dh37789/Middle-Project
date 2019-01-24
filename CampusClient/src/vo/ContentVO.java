package vo;

import java.io.Serializable;

public class ContentVO   implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String con_id;
	private String con_name;
	private String con_con;
	private String con_unid;
	
	
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getCon_name() {
		return con_name;
	}
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}
	public String getCon_con() {
		return con_con;
	}
	public void setCon_con(String con_con) {
		this.con_con = con_con;
	}
	public String getCon_unid() {
		return con_unid;
	}
	public void setCon_unid(String con_unid) {
		this.con_unid = con_unid;
	}
	
}
