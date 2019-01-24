package home.attend.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import home.attend.dao.AttendDaoImpl;
import home.attend.dao.IAttendDao;
import vo.MemberVO;

/**
 * 출석기능 서비스 Impl
 * @author PC02
 *
 */
public class AttendServiceImpl extends UnicastRemoteObject implements IAttendService{
	
	private static IAttendDao dao;

	public AttendServiceImpl() throws RemoteException {
		dao = AttendDaoImpl.getInstance();
	}
	
	@Override
	public MemberVO selectPt(String id) throws RemoteException {
		return dao.selectPt(id);
	}
	
	@Override
	public int updatePt(MemberVO memvo) throws RemoteException {
		return dao.updatePt(memvo);
	}

	@Override
	public int getCouponChk(Map<String, String> map) throws RemoteException {
		return dao.getCouponChk(map);
	}

}
