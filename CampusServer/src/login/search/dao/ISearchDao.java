package login.search.dao;
/**
 * 아이디 비밀번호 찾기 Dao 인터페이스
 * @author PC02
 *
 */

import java.util.Map;

import vo.MemberVO;

public interface ISearchDao {
	public MemberVO getIdSearch(Map<String, String> map);
	
	public int getPass(Map<String, String> map);
}
