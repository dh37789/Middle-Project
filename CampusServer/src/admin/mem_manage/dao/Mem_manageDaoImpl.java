package admin.mem_manage.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.MemberVO;
import vo.QuizJoinVO;
import vo.VideoJoinVO;

/**
 * 회원 정보 관리 DaoImpl
 * @author PC02
 *
 */
public class Mem_manageDaoImpl implements IMem_manageDao {
	private SqlMapClient smc;
	private static Mem_manageDaoImpl dao;
	
	private Mem_manageDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static Mem_manageDaoImpl getInstance() {
		if (dao == null) dao = new Mem_manageDaoImpl();
		return dao;
	}

	@Override
	public List<MemberVO> applyList() {
		List<MemberVO> list = null;
		try {
			list = smc.queryForList("member.noAccessList");
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int accessOK(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.update("member.accessOK", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int accessNO(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("member.outMember",mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getBlackList() {
		List<MemberVO> list = null;
		try {
			list = smc.queryForList("member.getBlackList");
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int addBlack(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.update("member.addBlack", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int subBlack(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.update("member.subBlack", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		List<MemberVO> list = null;
		try {
			list = smc.queryForList("member.getAllMember");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<VideoJoinVO> getVD(String mem_id) {
		List<VideoJoinVO> list = null;
		try {
			System.out.println(mem_id);
			list = smc.queryForList("video.getJoinVideo", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<QuizJoinVO> getQuiz(String mem_id) {
		List<QuizJoinVO> list = null;
		try {
			list = smc.queryForList("question.getQuizJoin",mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int deleteMember(String mem_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("member.outMember", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int vdCount() {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("video.vdCount");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int vdEanCount(String mem_id) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("video.vdEanCount", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int quizSize() {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("question.quizSize");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int eanQuiz(String mem_id) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("question.eanQuiz", mem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
