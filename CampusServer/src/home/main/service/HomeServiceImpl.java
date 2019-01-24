package home.main.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 홈화면 ServiceImpl
 * @author PC02
 *
 */
public class HomeServiceImpl extends UnicastRemoteObject implements IHomeService{

	public HomeServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
