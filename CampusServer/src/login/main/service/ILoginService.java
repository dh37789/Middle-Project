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
	/**
	 * 관리자 로그인 메서드
	 * @param log 관리자 로그인 정보
	 * @return 로그인성공시 관리자 객체를 반환한다.
	 * @throws RemoteException
	 */
	public AdminVO adminLogin(Map<String, String> log) throws RemoteException;
}
