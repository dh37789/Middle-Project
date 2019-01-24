package login.register.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import vo.MemberVO;


/**
 * 회원가입 Service 인터페이스
 * @author PC02
 *
 */
public interface IRegisterService extends Remote{
	
	public int insertMember(MemberVO memvo) throws RemoteException;
	
	public int getMemberCount(String memid) throws RemoteException;
}
