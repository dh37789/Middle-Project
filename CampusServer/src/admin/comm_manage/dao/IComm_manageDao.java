package admin.comm_manage.dao;

import java.rmi.RemoteException;
import java.util.List;

import vo.LcsVO;

/**
 * 커뮤니티 게시판 관리를 위한 Dao
 * @author PC02
 *
 */
public interface IComm_manageDao {
	
	
	// 자격증 정보 리스트 조회
	public List<LcsVO> getLicenseBoard();
	
	// 자격증 정보 등록
	public int insertLicense(LcsVO lvo);
	
	// 자격증 정보 삭제
	public int deleteLicense(String lcs_id);
	
	// 자격증 정보 수정
	public int updateLicense(LcsVO lvo);
	
}
