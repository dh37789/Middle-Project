package home.mem_info.dao;

import java.util.List;
import java.util.Map;

import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;

/**
 * 회원정보 Dao 인터페이스
 * @author PC02
 *
 */
public interface IMem_infoDao {

	int infoUpdate(MemberVO mvo);

	int passUpdate(MemberVO mvo);

	int outMember(String mem_id);
	
	public int getCouponChk(Map<String, String> map);
	
	public int insertCouponChk(Member_couponVO mcvo);
	
	public int updateCouponChk(Map<String, String> map);
	
	public CouponVO getCouponId(String cid);

	public Member_couponVO getCouponCount(Map<String, String> map);
	
	public int updateUseCoupon(Map<String, String> map);
}
