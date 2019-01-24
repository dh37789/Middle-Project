package home.lecture_info.dao;
/**
 * 강의정보 Dao
 * @author PC02
 *
 */

import java.util.List;

import vo.Lecture_infoVO;

public interface ILecture_infoDao {
	
	public Lecture_infoVO getLectureInfo(String linf_id);
}
