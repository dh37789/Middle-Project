package vo;

import java.io.Serializable;

public class Member_videoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mem_vd_memId; // 학습한 동영상 회원 아이디 FK
	private String mem_vd_vdId; // 학습한 동영상 동영상 아이디 FK
	private int mem_vd_pltm; // 학습한 동영상의 재생시간
	private int mem_vd_ean; // 학습한 동영상의 완료여부
	
	public String getMem_vd_memId() {
		return mem_vd_memId;
	}
	public void setMem_vd_memId(String mem_vd_memId) {
		this.mem_vd_memId = mem_vd_memId;
	}
	public String getMem_vd_vdId() {
		return mem_vd_vdId;
	}
	public void setMem_vd_vdId(String mem_vd_vdId) {
		this.mem_vd_vdId = mem_vd_vdId;
	}
	public int getMem_vd_pltm() {
		return mem_vd_pltm;
	}
	public void setMem_vd_pltm(int mem_vd_pltm) {
		this.mem_vd_pltm = mem_vd_pltm;
	}
	public int getMem_vd_ean() {
		return mem_vd_ean;
	}
	public void setMem_vd_ean(int mem_vd_ean) {
		this.mem_vd_ean = mem_vd_ean;
	}
	
}
