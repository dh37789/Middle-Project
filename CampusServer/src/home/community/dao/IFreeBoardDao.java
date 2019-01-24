package home.community.dao;

import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

import vo.CommentVO;
import vo.Free_boardVO;
import vo.Meal_chartVO;

public interface IFreeBoardDao {
	
	public List<Free_boardVO> getFreeBoard();
	
	public int FreeBoardWrite(Free_boardVO vo);
	
	public int FreeBoardUpdate(Free_boardVO vo);
	
	public int FreeBoardDelete(String id);
	
	public int iNum(String id);
	
	public List<Free_boardVO> freeBoardSearch(String ti);
	
	public int insertFbCmt(CommentVO comvo);
	
	public List<CommentVO> getFbComment(String frbd_id);
	
	int delComment(String cmt_id);
	
	public Meal_chartVO getSikdan(String id);
	
	public int addSikdan(Meal_chartVO mealvo);
	
	public int updateSikdan(Meal_chartVO mealvo);
	
	public int chkSikdan(String mlc_id);
}
