package home.inquire.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 문의하기 서비스 Impl
 * @author PC02
 *
 */
public class InquireServiceImpl extends UnicastRemoteObject implements IInquireService{

	public InquireServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
