package home.attend.dao;

import java.sql.SQLException;
import java.util.Map;

import vo.MemberVO;

/**
 * 출석 기능 Dao 인터페이스
 * @author PC02
 *
 */
public interface IAttendDao {
	public MemberVO selectPt(String id);
	
	public int updatePt(MemberVO memvo);
	
	public int getCouponChk(Map<String, String> map);
}
