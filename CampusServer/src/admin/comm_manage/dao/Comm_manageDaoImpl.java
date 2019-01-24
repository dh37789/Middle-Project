package admin.comm_manage.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.LcsVO;

/**
 * 커뮤니티 게시판 관리를 위한 DaoImpl
 * @author PC02
 *
 */
public class Comm_manageDaoImpl implements IComm_manageDao {

	private SqlMapClient smc;
	private static Comm_manageDaoImpl service;
	
	private Comm_manageDaoImpl() {
		smc = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static Comm_manageDaoImpl getInstance() {
		if(service==null) service = new Comm_manageDaoImpl();
		return service;
	}

	// 자격증 정보 등록
	@Override
	public int insertLicense(LcsVO lvo) {
		int chk = 0;
		try {
			Object obj = smc.insert("licenseBoard.insertLicense",lvo);
			if(obj==null) {
				chk=1;
			}
		} catch (SQLException e) {
			chk = 0;
			e.printStackTrace();
		}
		return chk;
	}
	
	// 자격증 정보 리스트 조회
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

	// 자격증 정보 삭제
	@Override
	public int deleteLicense(String lcs_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("licenseBoard.deleteLicense", lcs_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	// 자격증 정보 수정
	@Override
	public int updateLicense(LcsVO lvo) {
		int cnt=0;
		try {
			cnt = smc.update("licenseBoard.updateLicense", lvo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}
	

}
