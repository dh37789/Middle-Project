package classRoom.board.dao;

import java.util.List;
import java.util.Map;

import vo.CommentVO;
import vo.Homework_boardVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;
import vo.joinVO;
import vo.NbdVO;

/**
 * 나의 강의실관련 게시판 관리 Dao 인터페이스
 * @author PC02
 *
 */
public interface IBoardDao {

	List<Homework_boardVO> getHomeBoardList();

	List<Homework_boardVO> getSubList(String sub);

	List<Homework_boardVO> searchHomeList(Map<String, String> searchMap);

	int inHomeBoard(Homework_boardVO vo);

	int delHBoard(String hwbd_id);
	
	
	/**
	 * 공지사항 관련 게시판
	 * insert
	 */
	int insertnbd(NbdVO vo);
	
	// select
	public List<NbdVO> getAllnbd(); 
	
	public List<NbdVO> selectTi(String title);
	

	int upHBoard(Homework_boardVO vo);
	
	// ----------------------------------------------------------------------
	
	List<Post_boardVO> getBoardList();
	
	List<Post_boardVO> subBoard(String sub);
	
	List<Post_boardVO> searchBoard(Map<String, String> searchMap);
	
	int inPostBoard(Post_boardVO vo);
	
	int deleteBoard(String popbd_id);
	
	int updateBoard(Post_boardVO vo);
	
	int insertPostCmt(CommentVO comvo);

	List<CommentVO> getPostComment(String popbd_id);

	int delPostComment(String cmt_id);

	// ------------------------------------------------------------------
	
	List<Qna_boardVO> getQnaList();
	
	void inum(String nbd_id);

	int qnaInsert(Qna_boardVO vo);

	int qnaDelete(String qnaId);

	int qnaUpdate(Qna_boardVO vo);

	int insertCmt(CommentVO comvo);

	List<CommentVO> getComment(String qabd_id);

	int delComment(String cmt_id);

	List<Qna_boardVO> searchQna(Map<String, String> searchMap);
	
	int nbdDelete(String id);
	
	

	
	

}
