package classRoom.main.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import classRoom.main.dao.ClassMainDaoImpl;
import classRoom.main.dao.IClassMainDao;
import vo.ContentVO;
import vo.UnitVO;
import vo.VideoVO;
import vo.joinVO;

public class ClassMainServiceImpl extends UnicastRemoteObject implements IClassMainService{

	private static final long serialVersionUID = 1L;
	private IClassMainDao dao; 
	
	
	public ClassMainServiceImpl() throws RemoteException {
		dao = ClassMainDaoImpl.getInstance();
	}

	@Override
	public List<UnitVO> selectUnit() throws SQLException, RemoteException {
		List<UnitVO> list = null; 
		try {
			list =dao.selectUnit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<VideoVO> selectCon() throws SQLException, RemoteException {
		List<VideoVO> list = null; 
		try {
			list =dao.selectCon();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<VideoVO> selConName(String str) throws SQLException, RemoteException {
		List<VideoVO> vo = null;
		
		try {
			vo = dao.selConName(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return vo;
	}

	@Override
	public List<joinVO> join(String mem_id) throws SQLException, RemoteException {
		List<joinVO> list = null;
		
		try {
			list = dao.join(mem_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
