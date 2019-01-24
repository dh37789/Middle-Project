package home.community.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.javafx.collections.MappingChange.Map;

import sqlMapConfig.SqlMapClientFactory;
import vo.CommentVO;
import vo.Free_boardVO;
import vo.Meal_chartVO;

public class FreeBoardDaoImpl implements IFreeBoardDao {
	private  SqlMapClient smc;
	private static FreeBoardDaoImpl service;
	
	private FreeBoardDaoImpl() {
		try {
			smc = SqlMapClientFactory.getSqlMapClient();
		} catch (Exception e) {
			System.out.println("ibatis설정 오류");
			e.printStackTrace();
			}
	}
	
	public static FreeBoardDaoImpl getInstance() {
		if (service == null) {
			service = new FreeBoardDaoImpl();
		}
		return service;
	}
	
	//자유게시판 db를 가져오기
	@Override
	public List<Free_boardVO> getFreeBoard() {
		List<Free_boardVO> list = new ArrayList<Free_boardVO>();
		try {
			list = smc.queryForList("freeboard.getFreeBoard");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//자유게시판에 글 등록
	@Override
	public int FreeBoardWrite(Free_boardVO vo) {
		int cnt = 0;
		try {
			Object a = smc.insert("freeboard.FreeBoardWrite", vo);
			if (a == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	//자유게시판 글 수정후 저장
	@Override
	public int FreeBoardUpdate(Free_boardVO vo) {
		int a = 0;
		try {
			 a = smc.update("freeboard.FreeBoardUpdate", vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public int FreeBoardDelete(String id) {
		int a = 0;
		try {
			a = smc.delete("freeboard.FreeBoardDelete", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public int iNum(String id) {
		int a = 0;
		try {
			a = smc.update("freeboard.iNum", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	//자유게시판 글제목으로 검색
	@Override
	public List<Free_boardVO> freeBoardSearch(String ti) {
		List<Free_boardVO> list = null;
		try {
			list = smc.queryForList("freeboard.FreeBoardSearch",ti);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//자유게시판 댓글
	@Override
	public int insertFbCmt(CommentVO comvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("comment.insertFbCmt", comvo);
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
	public List<CommentVO> getFbComment(String frbd_id) {
		List<CommentVO> list = null;
		try {
			list = smc.queryForList("comment.getFbComment", frbd_id);
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
	public Meal_chartVO getSikdan(String id) {
		Meal_chartVO vo = null;
		try {
			vo = (Meal_chartVO) smc.queryForObject("freeboard.getSikdan", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public int addSikdan(Meal_chartVO mealvo) {
		int cnt = 0;
		try {
			Object a = smc.insert("freeboard.addSikdan", mealvo);
			if (a == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		System.out.println(cnt);
		return cnt;
	}

	@Override
	public int updateSikdan(Meal_chartVO mealvo) {
		int a = 0;
		try {
			 a = smc.update("freeboard.updateSikdan", mealvo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public int chkSikdan(String mlc_id) {
		int count =0;
		try {
			count= (int) smc.queryForObject("freeboard.chkSikdan",mlc_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
