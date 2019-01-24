package home.lecture_info.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import home.community.dao.FreeBoardDaoImpl;
import sqlMapConfig.SqlMapClientFactory;
import vo.Free_boardVO;
import vo.Lecture_infoVO;

/**
 * 강의 정보 DaoImpl
 * @author PC02
 *
 */
public class Lecture_infoDaoimpl implements ILecture_infoDao{
	private  SqlMapClient smc;
	private static Lecture_infoDaoimpl service;
	
	private Lecture_infoDaoimpl() {
		try {
			smc = SqlMapClientFactory.getSqlMapClient();
		} catch (Exception e) {
			System.out.println("ibatis설정 오류");
			e.printStackTrace();
			}
	}
	
	public static Lecture_infoDaoimpl getInstance() {
		if (service == null) {
			service = new Lecture_infoDaoimpl();
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

}
