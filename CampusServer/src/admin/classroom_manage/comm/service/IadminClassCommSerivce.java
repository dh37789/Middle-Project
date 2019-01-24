package admin.classroom_manage.comm.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import vo.CommentVO;
import vo.Homework_boardVO;
import vo.NbdVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;

public interface IadminClassCommSerivce extends Remote{
	/**
	 * 관리자의 Qna리스트를 불러오는 메서드
	 * @return Qna게시판 리스트
	 * @throws RemoteException
	 */
	List<Qna_boardVO> getQnaList() throws RemoteException;
	/**
	 * qna 게시글 삭제
	 * @param qnaId qnaId를 받아 삭제한다.
	 * @return 삭제성공 1 삭제실패 0
	 */
	int qnaDelete(String qnaId) throws RemoteException;
	/**
	 * 댓글 리스트를 가져온다.
	 * @param qabd_id 댓글 리스트를 가져올 게시판 종류
	 * @return 댓글 List배열
	 */
	List<CommentVO> getComment(String qabd_id) throws RemoteException;
	/**
	 * 댓글추가 메서드(관리자용)
	 * @param comvo 추가할 댓글
	 * @return 성공시 1 실패시 0
	 */
	int insertCmt(CommentVO comvo) throws RemoteException;
	/**
	 * 댓글 삭제 메서드(관리자용)
	 * @param cmt_id 삭제할 댓글 아이디
	 * @return
	 * @throws RemoteException
	 */
	int delComment(String cmt_id) throws RemoteException;
	/**
	 * qna검색(관리자용)
	 * @param searchMap (검색할 키워드)
	 * @return 검색한 리스트들
	 */
	List<Qna_boardVO> searchQna(Map<String, String> searchMap) throws RemoteException;
	/**
	 * 게시판 리스트를 가져온다.
	 * @return
	 */
	List<Homework_boardVO> getHomeBoardList() throws RemoteException;
	/**
	 * 과제제출 게시판 검색(관리자용)
	 * @param searchMap 검색할 데이터
	 * @return 검색 성공시 검색한 리스트를 가져온다.
	 * @throws RemoteException
	 */
	List<Homework_boardVO> searchHomeList(Map<String, String> searchMap) throws RemoteException;
	/**
	 * 과제제출 게시판 과목별 리스트를 가져온다(관리자)
	 * @param sub 가져올 리스트의 과목
	 * @return 과목별 게시글 리스트
	 * @throws RemoteException
	 */
	List<Homework_boardVO> getSubList(String sub) throws RemoteException;
	/**
	 * 과제제출 게시판의 게시글을 삭제한다(관리자용)
	 * @param hwbd_id 삭제할 게시글의 아이디
	 * @return 삭제성공 1 삭제실패 0
	 * @throws RemoteException
	 */
	int delHBoard(String hwbd_id) throws RemoteException;
	/**
	 * 강의 후기 게시판 리스트를 가져온다.(관리자용)
	 * @return 강의후기 게시글 리스트
	 */
	List<Post_boardVO> getBoardList() throws RemoteException;
	/**
	 * 강의 후기 게시판 검색(관리자용)
	 * @param searchMap 검색할 데이터
	 * @return 검색한 리스트
	 */
	List<Post_boardVO> searchBoard(Map<String, String> searchMap) throws RemoteException;
	/**
	 * 과목별 리스트(관리자용)
	 * @param sub 리스트를 가져올 과목
	 * @return 과목별 리스트를 가져온다.
	 * @throws RemoteException
	 */
	List<Post_boardVO> subBoard(String sub) throws RemoteException;
	/**
	 * 강의 후기 게시글 삭제(관리자용)
	 * @param popbd_id 삭제할 후기 아이디
	 * @return
	 */
	int deleteBoard(String popbd_id) throws RemoteException;
	/**
	 * 게시글의 댓글 리스트를 가져온다(관리자용)
	 * @param popbd_id 댓글리스트를 가져올 댓글id
	 * @return 일치하는 게시글의 댓글리스트
	 * @throws RemoteException
	 */
	List<CommentVO> getPostComment(String popbd_id) throws RemoteException;
	/**
	 * 공지사항 리스트를 가져온다(관리자용)
	 * @return 공지사항 리스트
	 */
	List<NbdVO> getAllnbd() throws RemoteException;
	/**
	 * 공지사항 게시글의 조회수를 늘려준다.
	 * @param nbd_id 조회수를 추가할 게시글의 아이디
	 */
	void inum(String nbd_id) throws RemoteException;
	/**
	 * 공지사항의 게시글을 검색한다.
	 * @param str 검색할 키워드
	 * @return 검색 목록
	 * @throws RemoteException
	 */
	List<NbdVO> selectTi(String str) throws RemoteException;
	/**
	 * 공지사항을 삭제한다.
	 * @param id 삭제할 아이디
	 * @return 삭제시
	 */
	int nbdDelete(String id) throws RemoteException;
	/**
	 * 공지사항 글쓰기 버튼
	 * @param vo 추가할 게시글객체
	 * @return 성공 1 실패 0
	 * @throws RemoteException
	 */
	int insertnbd(NbdVO vo) throws RemoteException;
	/**
	 * 공지사항 수정(관리자)
	 * @param nbdvo 수정할 게시글 객체
	 * @return 수정 성공 1 수정 실패 0
	 * @throws RemoteException
	 */
	int upNBoard(NbdVO nbdvo) throws RemoteException;

}
