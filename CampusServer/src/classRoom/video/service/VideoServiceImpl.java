package classRoom.video.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;

import classRoom.video.dao.IVideoDao;
import classRoom.video.dao.VideoDaoImpl;
import vo.Member_videoVO;
import vo.VideoVO;

/**
 * 동영상 강의 Service Impl
 * @author PC02
 *
 */
public class VideoServiceImpl extends UnicastRemoteObject implements IVideoService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IVideoDao dao;
	
	
	
	public VideoServiceImpl() throws RemoteException {
		dao = VideoDaoImpl.getInstance();
	}

	@Override
	public VideoVO selectVideo(String vd_name) throws SQLException, RemoteException {
		VideoVO vo = null;
		try {
			vo = dao.selectVideo(vd_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	
	}

	@Override
	public Member_videoVO selectTime(Map<String, String> param) throws SQLException, RemoteException {
		Member_videoVO vo =null;
		
		try {
			vo = dao.selectTime(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return vo;
	}
	@Override
	public void insert(Member_videoVO vo) throws SQLException, RemoteException {
		dao.insert(vo);
	}

	@Override
	public void updateTime(Map<String, String> param) throws SQLException, RemoteException {
		dao.updateTime(param);
	}

	@Override
	public void complete(Member_videoVO vo) throws SQLException , RemoteException {
		dao.complete(vo);
	}

	@Override
	public int updatept(String mem_id) throws SQLException, RemoteException {
		return dao.updatept(mem_id);
	}


}
