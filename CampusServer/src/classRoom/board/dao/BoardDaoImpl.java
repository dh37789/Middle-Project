package classRoom.board.dao;
/**
 * 나의 강의실관련 게시판 관리를 위한 DaoImpl
 * @author PC02
 *
 */

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import sqlMapConfig.SqlMapClientFactory;
import vo.CommentVO;
import vo.Homework_boardVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;
import vo.joinVO;
import vo.NbdVO;

public class BoardDaoImpl implements IBoardDao {
	
	private SqlMapClient smc;
	private static BoardDaoImpl dao;
	
	private BoardDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static BoardDaoImpl getInstance() {
		if (dao == null) dao = new BoardDaoImpl();
		return dao;
	}

	@Override
	public List<Homework_boardVO> getHomeBoardList() {
		List<Homework_boardVO> list = null;
		try {
			list = smc.queryForList("homework.getAllBoard");
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Homework_boardVO> getSubList(String sub) {
		List<Homework_boardVO> list = null;
		try {
			list = smc.queryForList("homework.subBoard", sub);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Homework_boardVO> searchHomeList(Map<String, String> searchMap) {
		List<Homework_boardVO> list = null;
		try {
			list = smc.queryForList("homework.searchBoard", searchMap);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int inHomeBoard(Homework_boardVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("homework.insertHBoard",vo);
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
	public int delHBoard(String hwbd_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("homework.deleteBoard", hwbd_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}


	@Override
	public int insertnbd(NbdVO vo) {
		int cnt = 0;
		try {
		Object	ob = smc.insert("nbd.insertNBD",vo);
		if(ob == null) {
			cnt = 1;
		}
		} catch (Exception e) {
			e.printStackTrace();
			cnt = 0;
		}
		
		return cnt;
	}

	@Override
	public List<NbdVO> getAllnbd() {
		List<NbdVO> list = null;
		try {
			list = smc.queryForList("nbd.getAllNBD");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<NbdVO> selectTi(String title) {
		List<NbdVO> list = null;
		try {
			list = smc.queryForList("nbd.selectTi",title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	@Override
	public int upHBoard(Homework_boardVO vo) {
		int cnt = 0;
		try {
			cnt = smc.update("homework.updateBoard", vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	// -------------------------------------------------------

	@Override
	public List<Post_boardVO> getBoardList() {
		List<Post_boardVO> list = null;
		try {
			list = smc.queryForList("postscript.getBoardList");
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Post_boardVO> subBoard(String sub) {
		List<Post_boardVO> list = null;
		try {
			list = smc.queryForList("postscript.subBoard", sub);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Post_boardVO> searchBoard(Map<String, String> searchMap) {
		List<Post_boardVO> list = null;
		try {
			list = smc.queryForList("postscript.searchBoard", searchMap);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int inPostBoard(Post_boardVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("postscript.insertPopBoard", vo);
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
	public int deleteBoard(String popbd_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("postscript.deleteBoard", popbd_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	

	@Override
	public int updateBoard(Post_boardVO vo) {
		int cnt = 0;
		try {
			cnt = smc.update("postscript.updateBoard", vo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	
	@Override
	public int insertPostCmt(CommentVO comvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("comment.insertPostCmt", comvo);
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
	public List<CommentVO> getPostComment(String popbd_id) {
		List<CommentVO> list = null;
		try {
			list = smc.queryForList("comment.getPostComment", popbd_id);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int delPostComment(String cmt_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("comment.delPostComment", cmt_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	// ---------------------------------------------------------------------------------
	
	@Override
	public List<Qna_boardVO> getQnaList() {
		List<Qna_boardVO> list = null;
		try {
			list = smc.queryForList("qnaBoard.getQnaList");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int qnaInsert(Qna_boardVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("qnaBoard.insertBoard", vo);
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
	public int qnaDelete(String qnaId) {
		int cnt = 0;
		try {
			cnt = smc.delete("qnaBoard.deleteBoard", qnaId);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int qnaUpdate(Qna_boardVO vo) {
		int cnt = 0;
		try {
			cnt = smc.delete("qnaBoard.updateBoard", vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int insertCmt(CommentVO comvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("comment.insertCmt", comvo);
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
	public List<CommentVO> getComment(String qabd_id) {
		List<CommentVO> list = null;
		try {
			list = smc.queryForList("comment.getComment", qabd_id);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int delComment(String cmt_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("comment.delComment", cmt_id);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<Qna_boardVO> searchQna(Map<String, String> searchMap) {
		List<Qna_boardVO> list = null;
		try {
			list = smc.queryForList("qnaBoard.searchQna", searchMap);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void inum(String nbd_id) {
		try {
			smc.update("nbd.iNum",nbd_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int nbdDelete(String id) {
		int cnt=0;
		try {
			cnt = smc.delete("nbd.delete",id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}


}