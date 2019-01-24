package home.lecture_info.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.Lecture_infoVO;

/**
 * 강의정보 서비스 인터페이스
 * @author PC02
 *
 */
public interface ILecture_infoService extends Remote{
	
	public Lecture_infoVO getLectureInfo(String linf_id) throws RemoteException;
}
