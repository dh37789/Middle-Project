package admin.classroom_manage.question.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.Practice_questionVO;

public class AdminQuizDaoImpl implements IAdminQuizDao{
	private SqlMapClient smc;
	private static AdminQuizDaoImpl dao;
	
	private AdminQuizDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	public static AdminQuizDaoImpl getInstance() {
		if (dao == null) dao = new AdminQuizDaoImpl();
		return dao;
	}
	@Override
	public int AddQuiz(Practice_questionVO vo) {
		int cnt = 0;
		Object obj;
		try {
			obj = smc.insert("question.addQuiz", vo);
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
	public List<Practice_questionVO> getAllQ() {
		List<Practice_questionVO> list = null;
		try {
			System.out.println("a");
			list = smc.queryForList("question.getAll");
		} catch (SQLException e) {
			System.out.println("b");
			e.printStackTrace();
		}
		System.out.println("c");
		return list;
	}
	@Override
	public int deleteQuiz(String pqt_id) {
		int cnt = 0;
		try {
		cnt = smc.delete("question.deleteQuiz", pqt_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	@Override
	public int updateQuiz(Practice_questionVO qvo) {
		int cnt = 0;
		try {
			cnt = smc.update("question.updateQuiz", qvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
}
