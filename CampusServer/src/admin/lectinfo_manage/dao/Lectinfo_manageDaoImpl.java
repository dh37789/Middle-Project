package admin.lectinfo_manage.dao;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;

import home.lecture_info.dao.Lecture_infoDaoimpl;
import sqlMapConfig.SqlMapClientFactory;
import vo.Lecture_infoVO;

/**
 * 강의 정보 관리DaoImpl
 * @author PC02
 *
 */
public class Lectinfo_manageDaoImpl implements ILectinfo_manageDao{
	private  SqlMapClient smc;
	private static ILectinfo_manageDao service;
	
	private Lectinfo_manageDaoImpl() {
		try {
			smc = SqlMapClientFactory.getSqlMapClient();
		} catch (Exception e) {
			System.out.println("ibatis설정 오류");
			e.printStackTrace();
			}
	}
	
	public static ILectinfo_manageDao getInstance() {
		if (service == null) {
			service = new Lectinfo_manageDaoImpl();
		}
		return service;
	}
	
	@Override
	public Lecture_infoVO getLectureInfo(String linf_id) {
		Lecture_infoVO vo = null;
		try {
			vo = (Lecture_infoVO) smc.queryForObject("lecinfo.getLectureInfo",linf_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public int updateLect(Lecture_infoVO vo) {
		
		int cnt = 0;
		
		try {
			cnt = smc.update("lecinfo.updatelectInfo",vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
}
