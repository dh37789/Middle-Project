package classRoom.question.dao;

import java.util.List;
import java.util.Map;

import vo.Practice_questionVO;

/**
 * 연습문제 Dao인터페이스
 * @author PC02
 *
 */
public interface IQuestionDao {
	/**
	 * PQT_ID값을 받고 문제하나를 받아온다.
	 * @param string 문제를 찾을 PQT_ID
	 * @return 받아온 문제
	 */
	Practice_questionVO getOneQ(String pqt_id);
	/**
	 * 모든문제를 List로 가져온다.
	 * @param mem_id 
	 * @return 문제의 List
	 */
	List<Practice_questionVO> getAllQ(String mem_id);
	/**
	 * 문제를 클릭할때마다. 문제의 시도횟수를 증가시킨다.
	 * @param pqt_id 시도한 문제의 아이디
	 * @return 성공시 1 실패시 0
	 */
	int increase(String pqt_id);
	/**
	 * 문제를 맞출때마다 정답횟수를 증가시킨다.
	 * @param pqt_id 맞춘 문제의 아이디
	 * @return 성공시 1 실패시 0
	 */
	int increaseHit(String pqt_id);
	/**
	 * 문제를 맞출경우 맞췄다고 체크
	 * @param chkMap 문제를푼 문제의ID와 회원의 ID
	 * @return 성공시 1 실패시 0 반환
	 */
	int memberQuizChk(Map<String, String> chkMap);

}
