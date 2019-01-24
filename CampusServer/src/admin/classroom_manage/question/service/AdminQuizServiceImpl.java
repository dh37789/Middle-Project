package admin.classroom_manage.question.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import admin.classroom_manage.question.dao.AdminQuizDaoImpl;
import admin.classroom_manage.question.dao.IAdminQuizDao;
import vo.Practice_questionVO;

public class AdminQuizServiceImpl extends UnicastRemoteObject implements IAdminQuizService{
	private IAdminQuizDao dao;
	public AdminQuizServiceImpl() throws RemoteException {
		super();
		dao = AdminQuizDaoImpl.getInstance();
	}
	@Override
	public List<Practice_questionVO> getAllQ() throws RemoteException {
		return dao.getAllQ();
	}
	@Override
	public int AddQuiz(Practice_questionVO vo) throws RemoteException {
		return dao.AddQuiz(vo);
	}
	@Override
	public int deleteQuiz(String pqt_id) throws RemoteException {
		// TODO Auto-generated method stub
		return dao.deleteQuiz(pqt_id);
	}
	@Override
	public int updateQuiz(Practice_questionVO qvo) {
		return dao.updateQuiz(qvo);
	}
	
}
