package admin.lectinfo_manage.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import vo.Lecture_infoVO;

/**
 * 강의 정보 관리 Service 인터페이스
 * @author PC02
 *
 */
public interface ILectinfo_manageService extends Remote{

	public Lecture_infoVO getLectureInfo(String linf_id) throws RemoteException;
	
	public int updateLect(Lecture_infoVO vo) throws RemoteException;
	
	
}
