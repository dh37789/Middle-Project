package admin.mem_manage.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import admin.mem_manage.dao.IMem_manageDao;
import admin.mem_manage.dao.Mem_manageDaoImpl;
import vo.MemberVO;
import vo.QuizJoinVO;
import vo.VideoJoinVO;

public class Mem_manageServiceImpl extends UnicastRemoteObject implements IMem_manageService{
	private IMem_manageDao dao;
	public Mem_manageServiceImpl() throws RemoteException {
		super();
		dao = Mem_manageDaoImpl.getInstance();
	}
	@Override
	public List<MemberVO> applyList() throws RemoteException {
		return dao.applyList();
	}
	@Override
	public int accessOK(String mem_id) throws RemoteException {
		return dao.accessOK(mem_id);
	}
	@Override
	public int accessNO(String mem_id) throws RemoteException {
		return dao.accessNO(mem_id);
	}
	@Override
	public List<MemberVO> getBlackList() throws RemoteException {
		return dao.getBlackList();
	}
	@Override
	public int addBlack(String mem_id) throws RemoteException {
		return dao.addBlack(mem_id);
	}
	@Override
	public int subBlack(String mem_id) throws RemoteException {
		return dao.subBlack(mem_id);
	}
	@Override
	public List<MemberVO> getAllMember() throws RemoteException {
		return dao.getAllMember();
	}
	@Override
	public List<VideoJoinVO> getVD(String mem_id) throws RemoteException {
		return dao.getVD(mem_id);
	}
	@Override
	public List<QuizJoinVO> getQuiz(String mem_id) throws RemoteException {
		return dao.getQuiz(mem_id);
	}
	@Override
	public int deleteMember(String mem_id) {
		return dao.deleteMember(mem_id);
	}
	@Override
	public int vdCount() throws RemoteException {
		return dao.vdCount();
	}
	@Override
	public int vdEanCount(String mem_id) throws RemoteException {
		return dao.vdEanCount(mem_id);
	}
	@Override
	public int quizSize() throws RemoteException {
		return dao.quizSize();
	}
	@Override
	public int eanQuiz(String mem_id) throws RemoteException {
		return dao.eanQuiz(mem_id);
	}

}
