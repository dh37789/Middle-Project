package classRoom.video.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

import vo.Member_videoVO;
import vo.VideoVO;

/**
 * 동영상 강의 Service 인터페이스
 * @author PC02
 *
 */
public interface IVideoService extends Remote{

	
	public VideoVO selectVideo(String vd_name) throws SQLException , RemoteException;
	
	
	/**
	 *  member와 vd_id를 이용해 시간을 가져오는 인터페이스
	 */
	
	public Member_videoVO selectTime(Map<String, String> param) throws SQLException , RemoteException;
	
	/**
	 *  비디오 시간이 저동 저장할수있게 해주는 인터페이스
	 */
	public void updateTime(Map<String, String> param) throws SQLException , RemoteException; 
	
	
	/**
	 *  비디오 가 저장되어있지 않다면 새로 하나 생성하는 인터페이스
	 */
	public void insert(Member_videoVO vo) throws SQLException, RemoteException;
	
	
	/**
	 * 비디오를 다 봤을때 변환
	 * @param vo
	 * @throws SQLException
	 */
	public void complete(Member_videoVO vo) throws SQLException,RemoteException;
	
	
	
	public int updatept(String mem_id) throws SQLException, RemoteException;


}
