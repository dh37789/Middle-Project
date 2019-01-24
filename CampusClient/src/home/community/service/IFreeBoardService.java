package home.community.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.CommentVO;
import vo.Free_boardVO;
import vo.Meal_chartVO;

public interface IFreeBoardService extends Remote {

	public List<Free_boardVO> getFreeBoard() throws RemoteException;

	public int FreeBoardWrite(Free_boardVO vo) throws RemoteException;

	public int FreeBoardUpdate(Free_boardVO vo) throws RemoteException;

	public int FreeBoardDelete(String id) throws RemoteException;

	public int iNum(String id) throws RemoteException;

	public List<Free_boardVO> freeBoardSearch(String ti) throws RemoteException;

	public int insertFbCmt(CommentVO comvo) throws RemoteException;

	public List<CommentVO> getFbComment(String qabd_id) throws RemoteException;

	public int delComment(String cmt_id) throws RemoteException;

	public Meal_chartVO getSikdan(String id) throws RemoteException;

	public int addSikdan(Meal_chartVO mealvo) throws RemoteException;

	public int updateSikdan(Meal_chartVO mealvo) throws RemoteException;
	
	public int chkSikdan(String mlc_id) throws RemoteException;

}
