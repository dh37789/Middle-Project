package admin.classroom_manage.comm.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import admin.classroom_manage.comm.dao.AdminClassCommDaoImpl;
import admin.classroom_manage.comm.dao.IAdminClassCommDao;
import vo.CommentVO;
import vo.Homework_boardVO;
import vo.NbdVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;

public class AdminClassCommServiceImpl extends UnicastRemoteObject implements IadminClassCommSerivce{
	IAdminClassCommDao dao;
	public AdminClassCommServiceImpl() throws RemoteException {
		super();
		dao = AdminClassCommDaoImpl.getInstance();
	}
	@Override
	public List<Qna_boardVO> getQnaList() throws RemoteException {
		return dao.getQnaList();
	}
	@Override
	public int qnaDelete(String qnaId) {
		return dao.qnaDelete(qnaId);
	}
	@Override
	public List<CommentVO> getComment(String qabd_id) throws RemoteException {
		return dao.getComment(qabd_id);
	}
	@Override
	public int insertCmt(CommentVO comvo) throws RemoteException {
		return dao.insertCmt(comvo);
	}
	@Override
	public int delComment(String cmt_id) throws RemoteException {
		return dao.delComment(cmt_id);
	}
	@Override
	public List<Qna_boardVO> searchQna(Map<String, String> searchMap) throws RemoteException {
		return dao.searchQna(searchMap);
	}
	@Override
	public List<Homework_boardVO> getHomeBoardList() throws RemoteException {
		return dao.getHomeBoardList();
	}
	@Override
	public List<Homework_boardVO> searchHomeList(Map<String, String> searchMap) throws RemoteException {
		return dao.searchHomeList(searchMap);
	}
	@Override
	public List<Homework_boardVO> getSubList(String sub) throws RemoteException {
		return dao.getSubList(sub);
	}
	@Override
	public int delHBoard(String hwbd_id) throws RemoteException {
		return dao.delHBoard(hwbd_id);
	}
	@Override
	public List<Post_boardVO> getBoardList() throws RemoteException {
		return dao.getBoardList();
	}
	@Override
	public List<Post_boardVO> searchBoard(Map<String, String> searchMap) throws RemoteException {
		return dao.searchBoard(searchMap);
	}
	@Override
	public List<Post_boardVO> subBoard(String sub) throws RemoteException {
		return dao.subBoard(sub);
	}
	@Override
	public int deleteBoard(String popbd_id) throws RemoteException {
		return dao.deleteBoard(popbd_id);
	}
	@Override
	public List<CommentVO> getPostComment(String popbd_id) throws RemoteException {
		return dao.getPostComment(popbd_id);
	}
	@Override
	public List<NbdVO> getAllnbd() throws RemoteException {
		return dao.getAllnbd();
	}
	@Override
	public void inum(String nbd_id) throws RemoteException {
		dao.inum(nbd_id);
	}
	@Override
	public List<NbdVO> selectTi(String str) throws RemoteException {
		return dao.selectTi(str);
	}
	@Override
	public int nbdDelete(String id) throws RemoteException {
		return dao.nbdDelete(id);
	}
	@Override
	public int insertnbd(NbdVO vo) throws RemoteException {
		return dao.insertnbd(vo);
	}
	@Override
	public int upNBoard(NbdVO nbdvo) throws RemoteException {
		return dao.upNBoard(nbdvo);
	}

}
