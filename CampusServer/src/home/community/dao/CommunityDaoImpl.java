package home.community.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.CommentVO;
import vo.LcsVO;
import vo.Tip_boardVO;

/**
 * 커뮤니티 DaoImpl
 * @author PC02
 *
 */
public class CommunityDaoImpl implements ICommunityDao{

	private SqlMapClient smc;
	private static CommunityDaoImpl service;
	
	private CommunityDaoImpl() {
		try {
			smc = SqlMapClientFactory.getSqlMapClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static CommunityDaoImpl getInstance() {
		if(service==null) service = new CommunityDaoImpl();
		return service;
	}
	
	// 자격증정보 리스트 불러오기
	@Override
	public List<LcsVO> getLicenseBoard() {
		List<LcsVO> list = new ArrayList<LcsVO>();
		
		try {
			list = smc.queryForList("licenseBoard.getLicenseBoard");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	// 자격증 Tip 리스트 불러오기
	@Override
	public List<Tip_boardVO> getTipBoard() {
		List<Tip_boardVO> list = new ArrayList<Tip_boardVO>();
		try {
			list = smc.queryForList("licenseBoard.getTipBoard");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 자격증 Tip 등록
	@Override
	public int insertTip(Tip_boardVO tvo) {
		int chk = 0;
		try {
			Object obj = smc.insert("licenseBoard.insertTip",tvo);
			if(obj==null) {
				chk=1;
			}
		} catch (SQLException e) {
			chk = 0;
			e.printStackTrace();
		}
		return chk;
	}

	// 자격증 Tip 삭제
	@Override
	public int deleteTip(String tbd_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("licenseBoard.deleteTip", tbd_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	// 자격증 Tip 수정
	@Override
	public int updateTip(Tip_boardVO tvo) {
		int cnt=0;
		try {
			cnt = smc.update("licenseBoard.updateTip", tvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	
	// 자격증 Tip 제목으로 검색
	@Override
	public List<Tip_boardVO> tipSearch(String tbd_ti) {
		List<Tip_boardVO> list = null;
		try {
			list = smc.queryForList("licenseBoard.tipSearch", tbd_ti);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<LcsVO> lcsSearch(String lcs_nm) {
		List<LcsVO> list = null;
		try {
			list = smc.queryForList("licenseBoard.lcsSearch", lcs_nm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public int insertTbCmt(CommentVO comvo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("comment.insertTbCmt", comvo);
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
	public List<CommentVO> getTbComment(String frbd_id) {
		List<CommentVO> list = null;
		try {
			list = smc.queryForList("comment.getTbComment", frbd_id);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public int delTbComment(String cmt_id) {
		int a = 0;
		try {
			a = smc.delete("comment.delTbComment", cmt_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

}
