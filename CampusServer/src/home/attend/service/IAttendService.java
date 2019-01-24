package home.attend.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import vo.MemberVO;

/**
 * 출석기능 서비스 인터페이스
 * @author PC02
 *
 */
public interface IAttendService extends Remote{
	public MemberVO selectPt(String id) throws RemoteException;
	
	public int updatePt(MemberVO memvo) throws RemoteException;
	
	public int getCouponChk(Map<String, String> map) throws RemoteException;
}
