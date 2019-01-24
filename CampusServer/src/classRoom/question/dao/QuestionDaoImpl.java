package classRoom.question.dao;
/**
 * 연습문제 DaoImpl
 * @author PC02
 *
 */

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.Practice_questionVO;

public class QuestionDaoImpl implements IQuestionDao {
	private SqlMapClient smc;
	private static QuestionDaoImpl dao;
	
	private QuestionDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static QuestionDaoImpl getInstance() {
		if (dao == null) dao = new QuestionDaoImpl();
		
		return dao;
	}

	@Override
	public Practice_questionVO getOneQ(String pqt_id) {
		Practice_questionVO vo = null;
		try {
			vo = (Practice_questionVO) smc.queryForObject("question.getOneQ", pqt_id);
		} catch (SQLException e) {
			vo = null;
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<Practice_questionVO> getAllQ(String mem_id) {
		List<Practice_questionVO> list = null;
		try {
			list = smc.queryForList("question.getAllQ", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int increase(String pqt_id) {
		int cnt = 0;
		try {
			cnt = smc.update("question.increaseCT", pqt_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int increaseHit(String pqt_id) {
		int cnt = 0;
		try {
			cnt = smc.update("question.increaseHitCT", pqt_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int memberQuizChk(Map<String, String> chkMap) {
		int cnt = 0;
		try {
			Object obj = smc.insert("member_question.insertMemQuiz", chkMap);
			if (obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
}
