package admin.classroom_manage.video.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import vo.VideoVO;

public interface IadminVideoService extends Remote {
	
	
	/**
	 * 동영상 전체 목록을 가져오는 메서드
	 */
	
	public List<VideoVO> getAllVideo() throws SQLException , RemoteException;
	
	/**
	 * 동영상 한개 정보를 가져오는 메서드
	 */
	
	public VideoVO selectVideo(String vd_id) throws SQLException , RemoteException;
	
	
	/**
	 *  동영상 id를 가져와 삭제하는 메서드
	 */
	
	public int deleteVideo(String vd_id) throws SQLException, RemoteException;
	
	
	
	/**
	 * 동영상 추가 인터페이스
	 */
	
	public int insertVideo(VideoVO vo) throws SQLException, RemoteException;
	
	
	

}
