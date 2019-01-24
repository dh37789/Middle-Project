package vo;

import java.io.Serializable;

public class Video_bookmarkVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String vd_bm_memId; // 동영상 즐겨찾기 회원 아이디 FK
	private String vd_bm_vdId; // 동영상 즐겨찾기 비디오 아이디 FK
	
	public String getVd_bm_memId() {
		return vd_bm_memId;
	}
	public void setVd_bm_memId(String vd_bm_memId) {
		this.vd_bm_memId = vd_bm_memId;
	}
	public String getVd_bm_vdId() {
		return vd_bm_vdId;
	}
	public void setVd_bm_vdId(String vd_bm_vdId) {
		this.vd_bm_vdId = vd_bm_vdId;
	}
	
}
