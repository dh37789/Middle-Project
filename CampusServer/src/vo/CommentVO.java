package vo;

import java.io.Serializable;

public class CommentVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String cmt_id; // 댓글 아이디 P.K
	private String cmt_memid; // 댓글 회원 아이디 F.K
	private String cmt_tbd_id; // 댓글 팁게시판 아이디 F.K
	private String cmt_popbd_id; // 댓글 강의후기 게시판 아이디 F.K
	private String cmt_qabd_id; // 댓글 질의응답 게시판 아이디 F.K
	private String cmt_frbd_id; // 댓글 자유게시판 아이디 F.K
	private String cmt_con; // 댓글 내용
	private String cmt_dt; // 댓글 작성 날짜
	private String cmt_admid;
	
	
	public String getCmt_admid() {
		return cmt_admid;
	}
	public void setCmt_admid(String cmt_admid) {
		this.cmt_admid = cmt_admid;
	}
	public String getCmt_id() {
		return cmt_id;
	}
	public void setCmt_id(String cmt_id) {
		this.cmt_id = cmt_id;
	}
	public String getCmt_memid() {
		return cmt_memid;
	}
	public void setCmt_memid(String cmt_memid) {
		this.cmt_memid = cmt_memid;
	}
	public String getCmt_tbd_id() {
		return cmt_tbd_id;
	}
	public void setCmt_tbd_id(String cmt_tbd_id) {
		this.cmt_tbd_id = cmt_tbd_id;
	}
	public String getCmt_popbd_id() {
		return cmt_popbd_id;
	}
	public void setCmt_popbd_id(String cmt_popbd_id) {
		this.cmt_popbd_id = cmt_popbd_id;
	}
	public String getCmt_qabd_id() {
		return cmt_qabd_id;
	}
	public void setCmt_qabd_id(String cmt_qabd_id) {
		this.cmt_qabd_id = cmt_qabd_id;
	}
	public String getCmt_frbd_id() {
		return cmt_frbd_id;
	}
	public void setCmt_frbd_id(String cmt_frbd_id) {
		this.cmt_frbd_id = cmt_frbd_id;
	}
	public String getCmt_con() {
		return cmt_con;
	}
	public void setCmt_con(String cmt_con) {
		this.cmt_con = cmt_con;
	}
	public String getCmt_dt() {
		return cmt_dt;
	}
	public void setCmt_dt(String cmt_dt) {
		this.cmt_dt = cmt_dt;
	}
	
}
