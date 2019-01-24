package admin.lectBoard_manage.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 나의 강의실 게시판 관리 serviceImpl
 * @author PC02
 *
 */
public class LectBoard_manageServiceImpl extends UnicastRemoteObject implements ILectBoard_manageService{
	public LectBoard_manageServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
