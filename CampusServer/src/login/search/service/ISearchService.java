package login.search.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import vo.MemberVO;

/**
 * 아이디 비밀번호 찾기 Service 인터페이스
 * @author PC02
 *
 */
public interface ISearchService extends Remote{
	public MemberVO getIdSearch(Map<String, String> map) throws RemoteException;
	
	public int getPass(Map<String, String> map) throws RemoteException;
}
