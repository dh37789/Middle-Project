package vo;

import java.io.Serializable;

public class NbdVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nbd_id; // 공지사항 아이디 PK
	private String nbd_ti; // 공지사항 제목
	private String nbd_wrr; // 공지사항 글쓴이
	private String nbd_dt; // 공지사항 작성날짜
	private String nbd_con; // 공지사항 내용
	private int nbd_iNum; // 공지사항 조회수 
	private String nbd_admId; // 공지사항 관리자 ID FK
	private String nbd_file; // 파일 경로 
	
	public String getNbd_file() {
		return nbd_file;
	}
	public void setNbd_file(String nbd_file) {
		this.nbd_file = nbd_file;
	}
	public String getNbd_id() {
		return nbd_id;
	}
	public void setNbd_id(String nbd_id) {
		this.nbd_id = nbd_id;
	}
	public String getNbd_ti() {
		return nbd_ti;
	}
	public void setNbd_ti(String nbd_ti) {
		this.nbd_ti = nbd_ti;
	}
	public String getNbd_wrr() {
		return nbd_wrr;
	}
	public void setNbd_wrr(String nbd_wrr) {
		this.nbd_wrr = nbd_wrr;
	}
	public String getNbd_dt() {
		return nbd_dt;
	}
	public void setNbd_dt(String nbd_dt) {
		this.nbd_dt = nbd_dt;
	}
	public String getNbd_con() {
		return nbd_con;
	}
	public void setNbd_con(String nbd_con) {
		this.nbd_con = nbd_con;
	}
	public int getNbd_iNum() {
		return nbd_iNum;
	}
	public void setNbd_iNum(int nbd_iNum) {
		this.nbd_iNum = nbd_iNum;
	}
	public String getNbd_admId() {
		return nbd_admId;
	}
	public void setNbd_admId(String nbd_admId) {
		this.nbd_admId = nbd_admId;
	}
	
}
