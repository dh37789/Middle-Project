package classRoom.board.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import vo.CommentVO;
import vo.Homework_boardVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;
import vo.joinVO;
import vo.NbdVO;

/**
 * 나의 강의실 게시판 service 인터페이스
 * @author PC02
 *
 */
public interface IBoardService extends Remote{

	List<Homework_boardVO> getHomeBoardList() throws RemoteException;

	List<Homework_boardVO> getSubList(String sub) throws RemoteException;

	List<Homework_boardVO> searchHomeList(Map<String, String> searchMap) throws RemoteException;
	/**
	 * 과제제출 게시글 추가 메서드
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	int inHomeBoard(Homework_boardVO vo) throws RemoteException;
	/**
	 * 게시글 삭제 메서드
	 * @param hwbd_id 삭제할 게시글 id
	 * @return 삭제 성공 1, 삭제 실패 0 반환
	 */
	int delHBoard(String hwbd_id) throws RemoteException;
	//--------------------------------------------------------------
	int insertnbd(NbdVO vo) throws RemoteException;
	
	public List<NbdVO> getAllnbd() throws RemoteException; 
	
	public List<NbdVO> selectTi(String title) throws RemoteException;
	
	int nbdDelete(String id) throws RemoteException;
//---------------------------------------------------------------

	/**
	 * 게시글 수정 메서드
	 * @param vo 수정할 게시글VO
	 * @return 수정 성공1, 수정실패 0반환
	 */
	int upHBoard(Homework_boardVO vo) throws RemoteException;
	
	//------------------------------------------------------------
	
	List<Post_boardVO> getBoardList() throws RemoteException;
	
	List<Post_boardVO> subBoard(String sub) throws RemoteException;
	
	List<Post_boardVO> searchBoard(Map<String, String> searchMap) throws RemoteException;
	
	int inPostBoard(Post_boardVO vo) throws RemoteException; 
	
	/**
	 * 게시글 삭제 메서드
	 * @param popbd_id 삭제할 게시글 id
	 * @return 삭제 성공 1, 삭제 실패 0 반환
	 */
	int deleteBoard(String popbd_id) throws RemoteException;
	
	/**
	 * 게시글 수정 메서드
	 * @param vo 수정할 게시글VO
	 * @return 수정 성공1, 수정실패 0반환
	 */
	int updateBoard(Post_boardVO vo) throws RemoteException;
	
	int insertPostCmt(CommentVO comvo) throws RemoteException;

	List<CommentVO> getPostComment(String popbd_id) throws RemoteException;

	int delPostComment(String cmt_id) throws RemoteException;
	
	//-------------------------------------------------------------------
	
	/**
	 * QnA게시판 DB가져오기
	 * @return QnA게시판 리스트 반환
	 */
	List<Qna_boardVO> getQnaList() throws RemoteException;
	/**
	 * QnA게시판 글 작성하기
	 * @param vo 작성한 글 데이터
	 * @return 성공시 1 실패시 0 반환
	 * @throws RemoteException
	 */
	int qnaInsert(Qna_boardVO vo) throws RemoteException;
	/**
	 * qna게시판 글 삭제하기
	 * @param qnaId 삭제할 게시판 ID
	 * @return 삭제성공 1 삭제실패 0
	 */
	int qnaDelete(String qnaId) throws RemoteException;
	
	void inum(String nbd_id) throws RemoteException;


	int qnaUpdate(Qna_boardVO vo) throws RemoteException;
	/**
	 * 댓글 등록 메서드
	 * @param comvo 등록할 댓글 vo
	 * @return 등록성공 1, 실패 0 반환
	 */
	int insertCmt(CommentVO comvo) throws RemoteException;
	/**
	 * 게시글의 댓글을 가져온다.
	 * @param qabd_id 가져올 게시글의 댓글
	 * @return 댓글의 List
	 */
	List<CommentVO> getComment(String qabd_id) throws RemoteException;
	/**
	 * 게시글의 해당 댓글을 삭제한다.
	 * @param cmt_id 삭제할 댓글의 아이디값
	 * @return 삭제성공 1, 삭제실패 0
	 */
	int delComment(String cmt_id) throws RemoteException;
	/**
	 * 해당 게시물을 찾는다.
	 * @param searchMap 찾을 게시물의 정보
	 * @return 찾을 경우 리스트로 반환
	 */
	List<Qna_boardVO> searchQna(Map<String, String> searchMap) throws RemoteException;

	
	
}
