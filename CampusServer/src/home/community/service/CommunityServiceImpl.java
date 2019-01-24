package home.community.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import home.community.dao.CommunityDaoImpl;
import home.community.dao.ICommunityDao;
import vo.CommentVO;
import vo.LcsVO;
import vo.Tip_boardVO;

/**
 * 커뮤니티 서비스 Impl
 * @author PC02
 *
 */
public class CommunityServiceImpl extends UnicastRemoteObject implements ICommunityService{
	private static ICommunityDao dao;

	public CommunityServiceImpl() throws RemoteException {
		dao = CommunityDaoImpl.getInstance();
	}

	// 자격증정보 리스트 불러오기
	@Override
	public List<LcsVO> getLicenseBoard() throws RemoteException {
		return dao.getLicenseBoard();
	}

	// 자격증 Tip 리스트 불러오기
	@Override
	public List<Tip_boardVO> getTipBoard() throws RemoteException {
		return dao.getTipBoard();
	}

	// 자격증 Tip 등록
	@Override
	public int insertTip(Tip_boardVO tvo) throws RemoteException {
		return dao.insertTip(tvo);
	}

	// 자격증 Tip 삭제
	@Override
	public int deleteTip(String tbd_id) throws RemoteException {
		return dao.deleteTip(tbd_id);
	}

	// 자격증 Tip 수정
	@Override
	public int updateTip(Tip_boardVO tvo) throws RemoteException {
		return dao.updateTip(tvo);
	}

	// 자격증 Tip 검색
	@Override
	public List<Tip_boardVO> tipSearch(String tbd_ti) throws RemoteException {
		return dao.tipSearch(tbd_ti);
	}

	@Override
	public List<LcsVO> lcsSearch(String lcs_nm) throws RemoteException {
		return dao.lcsSearch(lcs_nm);
	}

	@Override
	public int insertTbCmt(CommentVO comvo) throws RemoteException {
		return dao.insertTbCmt(comvo);
	}

	@Override
	public List<CommentVO> getTbComment(String frbd_id) throws RemoteException {
		return dao.getTbComment(frbd_id);
	}

	@Override
	public int delTbComment(String cmt_id) throws RemoteException {
		return dao.delTbComment(cmt_id);
	}

}
