package classRoom.question.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import classRoom.question.dao.IQuestionDao;
import classRoom.question.dao.QuestionDaoImpl;
import vo.Practice_questionVO;

/**
 * 연습문제 ServiceImpl
 * @author PC02
 *
 */
public class QuestionServiceImpl extends UnicastRemoteObject implements IQuestionService {
	private IQuestionDao dao;
	
	public QuestionServiceImpl() throws RemoteException {
		super();
		dao = QuestionDaoImpl.getInstance();
	}

	@Override
	public Practice_questionVO getOneQ(String pqt_id) throws RemoteException{
		return dao.getOneQ(pqt_id);
	}

	@Override
	public List<Practice_questionVO> getAllQ(String mem_id) throws RemoteException {
		return dao.getAllQ(mem_id);
	}

	@Override
	public int increase(String pqt_id) throws RemoteException {
		return dao.increase(pqt_id);
	}

	@Override
	public int increaseHit(String pqt_id) throws RemoteException{
		return dao.increaseHit(pqt_id);
	}

	@Override
	public int memberQuizChk(Map<String, String> chkMap) throws RemoteException{
		return dao.memberQuizChk(chkMap);
	}

}
