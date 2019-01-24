package admin.classroom_manage.video.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import admin.classroom_manage.video.dao.AdminVideoDaoImpl;
import admin.classroom_manage.video.dao.IadminVideoDao;
import classRoom.main.dao.ClassMainDaoImpl;
import classRoom.main.dao.IClassMainDao;
import vo.VideoVO;

public class AdminVideoServiceImpl extends UnicastRemoteObject implements IadminVideoService {

	
	
	private static final long serialVersionUID = 1L;
	private IadminVideoDao dao; 
	
	
	public AdminVideoServiceImpl() throws RemoteException {
		dao = AdminVideoDaoImpl.getInstance();
	}
	
	
	
	@Override
	public List<VideoVO> getAllVideo() throws SQLException, RemoteException {
		List<VideoVO> list = null;
		
		try {
			list = dao.getAllVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}

	@Override
	public VideoVO selectVideo(String vd_id) throws SQLException, RemoteException {
		VideoVO vo = null;
		
		try {
			vo = dao.selectVideo(vd_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vo;
	}



	@Override
	public int deleteVideo(String vd_id) throws SQLException, RemoteException {
		int cnt = 0;
		
		try {
			cnt = dao.deleteVideo(vd_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}



	@Override
	public int insertVideo(VideoVO vo) throws SQLException, RemoteException {
		return dao.insertVideo(vo);
	}

}
