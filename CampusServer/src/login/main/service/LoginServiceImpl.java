package login.main.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import login.main.dao.ILoginDao;
import login.main.dao.LoginDaoimpl;
import vo.AdminVO;
import vo.MemberVO;

/**
 * 로그인 서비스 임플
 * @author PC02
 *
 */
public class LoginServiceImpl extends UnicastRemoteObject implements ILoginService{
	
	private ILoginDao logdao;
	
	public LoginServiceImpl() throws RemoteException {
		logdao = LoginDaoimpl.getInstance();
	}

	@Override
	public MemberVO getLogin(Map<String, String> map) throws RemoteException {
		return (MemberVO) logdao.getLogin(map);
	}

	@Override
	public AdminVO adminLogin(Map<String, String> log) throws RemoteException {
		return logdao.adminLogin(log);
	}

}
