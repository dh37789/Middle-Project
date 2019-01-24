package vo;

import java.io.Serializable;

public class QuizJoinVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pqt_id;
	private String pqt_ti;
	private int ean;
	
	public String getPqt_id() {
		return pqt_id;
	}
	public void setPqt_id(String pqt_id) {
		this.pqt_id = pqt_id;
	}
	public String getPqt_ti() {
		return pqt_ti;
	}
	public void setPqt_ti(String pqt_ti) {
		this.pqt_ti = pqt_ti;
	}
	public int getEan() {
		return ean;
	}
	public void setEan(int ean) {
		this.ean = ean;
	}
}
