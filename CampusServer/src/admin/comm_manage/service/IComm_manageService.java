package admin.comm_manage.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.LcsVO;

/**
 * 커뮤니티 게시판 관리를 위한 Service
 * @author PC02
 *
 */
public interface IComm_manageService extends Remote{
	
	// 자격증 정보 등록
	public int insertLicense(LcsVO lvo) throws RemoteException;
	
	// 자격증 정보 리스트 조회
	public List<LcsVO> getLicenseBoard() throws RemoteException;
	
	// 자격증 정보 삭제
	public int deleteLicense(String lcs_id) throws RemoteException;
	
	// 자격증 정보 수정
	public int updateLicense(LcsVO lvo) throws RemoteException;
}
