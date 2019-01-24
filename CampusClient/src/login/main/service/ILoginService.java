package login.main.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import vo.AdminVO;
import vo.MemberVO;

/**
 * 로그인 서비스 인터페이스
 * @author PC02
 *
 */
public interface ILoginService extends Remote{
	public MemberVO getLogin(Map<String, String> map) throws RemoteException;

	public AdminVO adminLogin(Map<String, String> log) throws RemoteException;
}
