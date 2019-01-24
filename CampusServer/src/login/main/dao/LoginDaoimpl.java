package login.main.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.AdminVO;
import vo.MemberVO;

/**
 * 로그인 Dao Impl
 * @author PC02
 *
 */
public class LoginDaoimpl implements ILoginDao{
	SqlMapClient smc;
	private static LoginDaoimpl dao;
	
	private LoginDaoimpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static LoginDaoimpl getInstance() {
		if(dao == null) dao = new LoginDaoimpl();
		return dao;
	}

	@Override
	public MemberVO getLogin(Map<String, String> map) {
		MemberVO log = null;
		try {
			log = (MemberVO) smc.queryForObject("member.getLogin", map);
		} catch (SQLException e) {
			log = null;
			e.printStackTrace();
		}
		return log;
	}

	@Override
	public AdminVO adminLogin(Map<String, String> log) {
		AdminVO vo = null;
		try {
			vo = (AdminVO) smc.queryForObject("admin.getAdmin", log);
		} catch (SQLException e) {
			vo = null;
			e.printStackTrace();
		}
		return vo;
	}
	
	
}