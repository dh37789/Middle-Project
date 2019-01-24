package admin.mem_manage.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.MemberVO;
import vo.QuizJoinVO;
import vo.VideoJoinVO;

public interface IMem_manageService extends Remote{
	/**
	 * 회원 승인이 안된 회원들을 데려온다.
	 * @return 회원정보를 List해서 가져온다.
	 * @throws RemoteException
	 */
	List<MemberVO> applyList() throws RemoteException;
	/**
	 * 체크된 사람들의 가입을 승인한다.
	 * @param mem_id 승인할 회원의 아이디
	 * @return 성공시 1반환
	 * @throws RemoteException
	 */
	int accessOK(String mem_id) throws RemoteException;
	/**
	 * 체크된 사람의 가입을 거절한다.
	 * @param mem_id 거절할 회원의 아이디
	 * @return 성공시 1반환
	 */
	int accessNO(String mem_id) throws RemoteException;
	/**
	 * 블랙리스트를 지정하기위해 블랙리스트나 일반회원을 데려온다.
	 * @return 승인완료된 회원이나 블랙리스트가된 회원 리스트
	 */
	
	List<MemberVO> getBlackList() throws RemoteException;
	/**
	 * 회원 아이디를 받아서 블랙리스트로 지정한다.
	 * @param mem_id 블랙리스트를 지정할 아이디
	 * @return
	 */
	int addBlack(String mem_id) throws RemoteException;
	/**
	 * 회원 아이디를 받아서 일반회원으로 지정한다.
	 * @param mem_id 일반회원으로 지정할 아이디
	 * @return
	 */
	int subBlack(String mem_id) throws RemoteException;
	/**
	 * 모든 회원 리스트를 가져온다.
	 * @return 회원가입한 회원들의 리스트
	 * @throws RemoteException
	 */
	List<MemberVO> getAllMember() throws RemoteException;
	/**
	 * 회원 아이디를 받아서 해당 회원의 강의 시청 목록을 가져온다.
	 * @param mem_id 목록을 확인할 회원아이디
	 * @return 시청 목록
	 */
	List<VideoJoinVO> getVD(String mem_id) throws RemoteException;
	/**
	 * 회원의 아이디를 받아서 해당 회원의 문제풀이 현황을 가져온다.
	 * @param mem_id 목록을 확인할 회원의 아이디
	 * @return 풀이 현황
	 */
	List<QuizJoinVO> getQuiz(String mem_id) throws RemoteException;
	/**
	 * 회원의 아이디를 가져와서 멤버를 삭제한다.
	 * @param mem_id 삭제할 회원의 아이디
	 * @return 삭제성공 1 삭제실패 0
	 */
	int deleteMember(String mem_id) throws RemoteException;
	/**
	 * 총 비디오 갯수를 불러온다.
	 * @return 비디오 갯수의 양
	 */
	int vdCount() throws RemoteException;
	/**
	 * 본 비디오의 갯수를 불러온다.
	 * @param mem_id 회원의 아이디
	 * @return 본 비디오 갯수의 수
	 */
	int vdEanCount(String mem_id) throws RemoteException;
	/**
	 * 총 문제의 갯수를 불러온다.
	 * @return 불린 문제의 갯수
	 * @throws RemoteException
	 */
	int quizSize() throws RemoteException;
	/**
	 * 푼 문제의 갯수를 불러온다.
	 * @param mem_id 문제를 푼 사람의 아이디
	 * @return 푼 문제의 수
	 */
	int eanQuiz(String mem_id) throws RemoteException;

}
