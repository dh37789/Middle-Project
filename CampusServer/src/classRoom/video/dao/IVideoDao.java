package classRoom.video.dao;
/**
 * 동영상 강의 Dao 인터페이스
 * @author PC02
 *
 */

import java.sql.SQLException;
import java.util.Map;

import vo.Member_videoVO;
import vo.VideoVO;

public interface IVideoDao {

	/**
	 * 동영상 내용을 이름을 입력받아
	 * 동영상 시간을 출력하는 메서드
	 */
	
	public VideoVO selectVideo(String vd_name) throws SQLException;
	
	
	public Member_videoVO selectTime(Map<String, String> param) throws SQLException ;
	
	/**
	 *  비디오 시간이 저동 저장할수있게 해주는 인터페이스
	 */
	public void updateTime(Map<String, String> param) throws SQLException; 
	
	
	/**
	 *  비디오 가 저장되어있지 않다면 새로 하나 생성하는 인터페이스
	 */
	public void insert(Member_videoVO vo) throws SQLException;
	
	/**
	 * 비디오를 다 봤을때 변환
	 * @param vo
	 * @throws SQLException
	 */
	public void complete(Member_videoVO vo) throws SQLException;
	
	
	/**
	 * 비디오를 다봤을씨 포인트 5 증가
	 */
	public int updatept(String mem_id) throws SQLException;
	
	
}
