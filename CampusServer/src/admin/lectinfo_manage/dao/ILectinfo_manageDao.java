package admin.lectinfo_manage.dao;

import vo.Lecture_infoVO;

/**
 * 강의 정보 관리Dao
 * @author PC02
 *
 */
public interface ILectinfo_manageDao {

	
	public Lecture_infoVO getLectureInfo(String linf_id);
	
	public int updateLect(Lecture_infoVO vo);
}
