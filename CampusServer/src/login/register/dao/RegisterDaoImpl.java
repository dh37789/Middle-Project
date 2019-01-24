package login.register.dao;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import sqlMapConfig.SqlMapClientFactory;
import vo.MemberVO;

/**
 * 회원가입 Dao Impl
 * 
 * @author PC02
 *
 */
public class RegisterDaoImpl implements IRegisterDao {
	SqlMapClient smc;
	private static RegisterDaoImpl dao; 
	
	private RegisterDaoImpl(){
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static RegisterDaoImpl getInstance() {
		if(dao == null) dao = new RegisterDaoImpl();
		return dao; 
	}
	
	@Override
	public int insertMember(MemberVO memvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("member.insertMember", memvo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int getMemberCount(String memid) {
		int count = 0;
		
		try {
			count = (int) smc.queryForObject("member.getMemberCount", memid);
		} catch (SQLException e) {
			count = 0;
			e.printStackTrace();
		}
		return count;
	}
}










