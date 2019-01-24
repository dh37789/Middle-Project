package admin.comm_manage.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import admin.comm_manage.dao.Comm_manageDaoImpl;
import admin.comm_manage.dao.IComm_manageDao;
import vo.LcsVO;

/**
 * 커뮤니티 게시판 관리를 위한 ServiceImpl
 * @author PC02
 *
 */
public class Comm_manageServiceImpl extends UnicastRemoteObject implements IComm_manageService {
	private static IComm_manageDao dao;

	public Comm_manageServiceImpl() throws RemoteException {
		dao = Comm_manageDaoImpl.getInstance();
	}

	// 자격증 정보 등록
	@Override
	public int insertLicense(LcsVO lvo) throws RemoteException{
		return dao.insertLicense(lvo);
	}

	// 자격증 정보 리스트 조회
	@Override
	public List<LcsVO> getLicenseBoard() throws RemoteException {
		return dao.getLicenseBoard();
	}

	// 자격증 정보 삭제
	@Override
	public int deleteLicense(String lcs_id) throws RemoteException {
		return dao.deleteLicense(lcs_id);
	}

	// 자격증 정보 수정
	@Override
	public int updateLicense(LcsVO lvo) throws RemoteException {
		return dao.updateLicense(lvo);
	}

}
