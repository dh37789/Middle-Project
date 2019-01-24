package login.register.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import login.register.dao.IRegisterDao;
import login.register.dao.RegisterDaoImpl;
import vo.MemberVO;

/**
 * 회원가입 서비스 Impl
 * 
 * @author PC02
 *
 */
public class RegisterServiceImpl extends UnicastRemoteObject implements IRegisterService {

	private IRegisterDao regDao;	
	
	public RegisterServiceImpl() throws RemoteException {
		regDao = RegisterDaoImpl.getInstance();
	}

	@Override
	public int insertMember(MemberVO memvo) throws RemoteException {
		return regDao.insertMember(memvo);
	}

	@Override
	public int getMemberCount(String memid) throws RemoteException {
		return regDao.getMemberCount(memid);
	}
}
