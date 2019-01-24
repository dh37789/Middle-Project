package admin.lectinfo_manage.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import admin.lectBoard_manage.dao.ILectBoard_manageDao;
import admin.lectBoard_manage.dao.LectBoard_manageDaoImpl;
import admin.lectinfo_manage.dao.ILectinfo_manageDao;
import admin.lectinfo_manage.dao.Lectinfo_manageDaoImpl;
import home.lecture_info.dao.ILecture_infoDao;
import home.lecture_info.dao.Lecture_infoDaoimpl;
import vo.Lecture_infoVO;

/**
 * 강의 정보 관리 Service impl
 * @author PC02
 *
 */
public class Lectinfo_manageServiceImpl extends UnicastRemoteObject implements ILectinfo_manageService {

	private static ILectinfo_manageDao dao;
	
	public Lectinfo_manageServiceImpl() throws RemoteException {
		dao = Lectinfo_manageDaoImpl.getInstance();
	}

	@Override
	public Lecture_infoVO getLectureInfo(String linf_id) throws RemoteException {
		
		return dao.getLectureInfo(linf_id);
	}

	@Override
	public int updateLect(Lecture_infoVO vo) throws RemoteException{
		return dao.updateLect(vo);
	}

}







