package home.community.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.CommentVO;
import vo.LcsVO;
import vo.Tip_boardVO;

/**
 * 커뮤니티 서비스 인터페이스
 * @author PC02
 *
 */
public interface ICommunityService extends Remote{
	
	// 자격증정보 리스트 불러오기
	public List<LcsVO> getLicenseBoard() throws RemoteException;
	
	// 자격증정보 리스트 검색
	public List<LcsVO> lcsSearch(String lcs_nm) throws RemoteException;
	
	// 자격증 Tip 리스트 불러오기
	public List<Tip_boardVO> getTipBoard() throws RemoteException;
	
	// 자격증 Tip 등록
	public int insertTip(Tip_boardVO tvo) throws RemoteException;
	
	// 자격증 Tip 삭제
	public int deleteTip(String tbd_id) throws RemoteException;
	
	// 자격증 Tip 수정
	public int updateTip(Tip_boardVO tvo) throws RemoteException;
	
	// 자격증 Tip 검색
	public List<Tip_boardVO> tipSearch(String tbd_ti) throws RemoteException;
	
	public int insertTbCmt(CommentVO comvo) throws RemoteException;
	
	public List<CommentVO> getTbComment(String frbd_id) throws RemoteException;
	
	int delTbComment(String cmt_id) throws RemoteException;
	
}




