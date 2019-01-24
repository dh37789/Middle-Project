package home.lecture_info.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import home.community.dao.FreeBoardDaoImpl;
import home.community.dao.IFreeBoardDao;
import home.lecture_info.dao.ILecture_infoDao;
import home.lecture_info.dao.Lecture_infoDaoimpl;
import vo.Lecture_infoVO;

/**
 * 강의 정보 서비스 Impl
 * @author PC02
 *
 */
public class Lecture_infoServiceImpl extends UnicastRemoteObject implements ILecture_infoService{
	
	private static ILecture_infoDao dao;
	
	public Lecture_infoServiceImpl() throws RemoteException {
		dao = Lecture_infoDaoimpl.getInstance();
	}

	@Override
	public Lecture_infoVO getLectureInfo(String linf_id) throws RemoteException {
		
		return dao.getLectureInfo(linf_id);
	}

}
