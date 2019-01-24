package home.community.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import home.community.dao.FreeBoardDaoImpl;
import home.community.dao.IFreeBoardDao;
import vo.CommentVO;
import vo.Free_boardVO;
import vo.Meal_chartVO;

public class FreeBoardServiceImpl extends UnicastRemoteObject implements IFreeBoardService{
	private static IFreeBoardDao dao;
	
	public FreeBoardServiceImpl() throws RemoteException{
		dao = FreeBoardDaoImpl.getInstance();
	}
	
	@Override
	public List<Free_boardVO> getFreeBoard() throws RemoteException{
		return dao.getFreeBoard();
	}

	@Override
	public int FreeBoardWrite(Free_boardVO vo) throws RemoteException {
		
		return dao.FreeBoardWrite(vo);
	}

	@Override
	public int FreeBoardUpdate(Free_boardVO vo) throws RemoteException {
		return dao.FreeBoardUpdate(vo);
	}

	@Override
	public int FreeBoardDelete(String id) throws RemoteException {
		return dao.FreeBoardDelete(id);
	}

	@Override
	public int iNum(String id) throws RemoteException {
		return dao.iNum(id);
	}

	@Override
	public List<Free_boardVO> freeBoardSearch(String ti) throws RemoteException {
		return dao.freeBoardSearch(ti);
	}

	@Override
	public int insertFbCmt(CommentVO comvo) throws RemoteException {
		return dao.insertFbCmt(comvo);
	}

	@Override
	public List<CommentVO> getFbComment(String frbd_id) throws RemoteException {
		return dao.getFbComment(frbd_id);
	}

	@Override
	public int delComment(String cmt_id) throws RemoteException {
		return dao.delComment(cmt_id);
	}

	@Override
	public Meal_chartVO getSikdan(String id) throws RemoteException {
		return dao.getSikdan(id);
	}

	@Override
	public int addSikdan(Meal_chartVO mealvo) throws RemoteException {
		return dao.addSikdan(mealvo);
	}

	@Override
	public int updateSikdan(Meal_chartVO mealvo) throws RemoteException {
		return dao.updateSikdan(mealvo);
	}

	@Override
	public int chkSikdan(String mlc_id) throws RemoteException {
		return dao.chkSikdan(mlc_id);
	}

}
