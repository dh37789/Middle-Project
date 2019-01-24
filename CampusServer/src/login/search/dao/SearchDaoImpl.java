package login.search.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.MemberVO;

/**
 * 아이디 비밀번호 찾기 Dao Impl
 * @author PC02
 *
 */
public class SearchDaoImpl implements ISearchDao{
	SqlMapClient smc;
	private static SearchDaoImpl dao;

	private SearchDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static SearchDaoImpl getInstance() {
		if(dao == null) dao = new SearchDaoImpl();
		return dao;
	}
	
	@Override
	public MemberVO getIdSearch(Map<String, String> map) {
		MemberVO memvo = null;
		
		try {
			memvo = (MemberVO) smc.queryForObject("member.getIdSearch", map);
		} catch (SQLException e) {
			memvo = null;
			e.printStackTrace();
		}
		return memvo;
	}

	@Override
	public int getPass(Map<String, String> map) {
		int cnt = 0;
		try {
			cnt = smc.update("member.getPass", map);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

}