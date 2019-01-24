package vo;

import java.io.Serializable;

public class Member_questionVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mem_pqt_memId; // 회원이 푼문제 회원 아이디 FK
	private String mem_pqt_pqtId; // 회원이 푼문제 회원 아이디 FK
	private int mem_pqt_ean; // 회원이 푼 문제 문제 풀이 유무
	
	public String getMem_pqt_memId() {
		return mem_pqt_memId;
	}
	public void setMem_pqt_memId(String mem_pqt_memId) {
		this.mem_pqt_memId = mem_pqt_memId;
	}
	public String getMem_pqt_pqtId() {
		return mem_pqt_pqtId;
	}
	public void setMem_pqt_pqtId(String mem_pqt_pqtId) {
		this.mem_pqt_pqtId = mem_pqt_pqtId;
	}
	public int getMem_pqt_ean() {
		return mem_pqt_ean;
	}
	public void setMem_pqt_ean(int mem_pqt_ean) {
		this.mem_pqt_ean = mem_pqt_ean;
	}
	
}
