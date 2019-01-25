package home.mem_info.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;
/**
 * 회원정보 Dao Impl
 * @author PC02
 *
 */
public class Mem_infoDaoimpl implements IMem_infoDao{
	private static Mem_infoDaoimpl dao;
	private SqlMapClient smc;
	
	private Mem_infoDaoimpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	public static Mem_infoDaoimpl getInstance() {
		if (dao ==  null) dao = new Mem_infoDaoimpl(); 
		return dao;
	}
	@Override
	public int infoUpdate(MemberVO mvo) {
		int cnt = 0;
		try {
			cnt = smc.update("member.infoUpdate", mvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int passUpdate(MemberVO mvo) {
		int cnt = 0;
		try {
			cnt = smc.update("member.passUpdate", mvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int outMember(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("member.outMember", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int getCouponChk(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("member_coupon.getCouponChk", map);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int insertCouponChk(Member_couponVO mcvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("member_coupon.insertCouponChk", mcvo);
			if (obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int updateCouponChk(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = smc.update("member_coupon.updateCouponChk", map);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public CouponVO getCouponId(String cid) {
		CouponVO cvo = null;
		try {
			cvo = (CouponVO) smc.queryForObject("coupon.getCouponId", cid);
		} catch (SQLException e) {
			cvo = null;
			e.printStackTrace();
		}
		return cvo;
	}
	
	@Override
	public Member_couponVO getCouponCount(Map<String, String> map) {
		Member_couponVO mcvo = null;
		try {
			mcvo = (Member_couponVO) smc.queryForObject("member_coupon.getCouponCount", map);
		} catch (SQLException e) {
			mcvo = null;
			e.printStackTrace();
		}
		return mcvo;
	}
	@Override
	public int updateUseCoupon(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = smc.update("member_coupon.updateUseCoupon", map);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
}
