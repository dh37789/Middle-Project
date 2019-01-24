package admin.classroom_manage.question.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.Practice_questionVO;

public interface IAdminQuizService extends Remote{
	/**
	 * 연습문제를 추가해준다.
	 * @param vo 추가할 연습문제VO
	 * @return 성공시 1 실패시 0
	 */
	int AddQuiz(Practice_questionVO vo) throws RemoteException;
	/**
	 * 모든문제를 List로 가져온다.
	 * @return 문제의 List
	 */
	List<Practice_questionVO> getAllQ() throws RemoteException;
	/**
	 * 연습문제를 삭제한다.
	 * @param pqt_id 삭제할 문제 id
	 * @return 삭제 성공 1 삭제 실패 0
	 * @throws RemoteException
	 */
	int deleteQuiz(String pqt_id) throws RemoteException;
	/**
	 * 연습문제를 수정한다.
	 * @param qvo 수정할 연습문제VO
	 * @return 수정 성공 1 수정 실패 0
	 */
	int updateQuiz(Practice_questionVO qvo) throws RemoteException;

}
