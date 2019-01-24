package home.attend.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.MemberVO;

/**
 * 출석기능 DaoImpl
 * @author PC02
 *
 */
public class AttendDaoImpl implements IAttendDao{
	
	private SqlMapClient smc;
	private static AttendDaoImpl dao;
	
	private AttendDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static AttendDaoImpl getInstance() {
		if(dao == null) dao = new AttendDaoImpl();
		return dao;
	}
	
	@Override
	public MemberVO selectPt(String id) {
		MemberVO vo = null;
		try {
			vo = (MemberVO) smc.queryForObject("member.selectPt", id);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public int updatePt(MemberVO memvo) {
		int cnt = 0;
		try {
			cnt = smc.update("member.updatePt", memvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int getCouponChk(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = (int)smc.queryForObject("member_coupon.getCouponChk", map);
		} catch (SQLException e) {
			System.out.println("오류");
			cnt = 0;
			e.printStackTrace();
		}
		System.out.println(cnt);
		return cnt;
	}

}
