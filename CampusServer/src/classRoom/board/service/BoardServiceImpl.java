package classRoom.board.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import classRoom.board.dao.BoardDaoImpl;
import classRoom.board.dao.IBoardDao;
import vo.CommentVO;
import vo.Homework_boardVO;
import vo.Post_boardVO;
import vo.Qna_boardVO;
import vo.NbdVO;

/**
 * 나의 강의실 게시판
 * @author PC02
 *
 */
public class BoardServiceImpl extends UnicastRemoteObject implements IBoardService {
	
	private IBoardDao dao;
	
	public BoardServiceImpl() throws RemoteException {
		super();
		dao = BoardDaoImpl.getInstance();
	}

	@Override
	public List<Homework_boardVO> getHomeBoardList() throws RemoteException {
		return dao.getHomeBoardList();
	}

	@Override
	public List<Homework_boardVO> getSubList(String sub) throws RemoteException {
		return dao.getSubList(sub);
	}

	@Override
	public List<Homework_boardVO> searchHomeList(Map<String, String> searchMap) throws RemoteException {
		return dao.searchHomeList(searchMap);
	}

	@Override
	public int inHomeBoard(Homework_boardVO vo) throws RemoteException {
		return dao.inHomeBoard(vo);
	}

	@Override
	public int delHBoard(String hwbd_id) throws RemoteException {
		return dao.delHBoard(hwbd_id);
	}

	@Override
	public int insertnbd(NbdVO vo) throws RemoteException {
		return dao.insertnbd(vo);
	}

	@Override
	public List<NbdVO> getAllnbd() throws RemoteException {
		return dao.getAllnbd();
	}

	@Override
	public List<NbdVO> selectTi(String title) throws RemoteException {
		return dao.selectTi(title);
	}

	@Override
	public int upHBoard(Homework_boardVO vo) throws RemoteException {
		return dao.upHBoard(vo);
	}

	// ----------------------------------------------------------------

	@Override
	public List<Qna_boardVO> getQnaList() throws RemoteException {
		return dao.getQnaList();
	}

	@Override
	public List<Post_boardVO> getBoardList() throws RemoteException {
		return dao.getBoardList();
	}

	@Override
	public List<Post_boardVO> subBoard(String sub) throws RemoteException {
		return dao.subBoard(sub);
	}

	@Override
	public List<Post_boardVO> searchBoard(Map<String, String> searchMap) throws RemoteException {
		return dao.searchBoard(searchMap);
	}
	
	@Override
	public int inPostBoard(Post_boardVO vo) throws RemoteException {
		return dao.inPostBoard(vo);
	}

	@Override
	public int deleteBoard(String popbd_id) throws RemoteException {
		return dao.deleteBoard(popbd_id);
	}

	@Override
	public int updateBoard(Post_boardVO vo) throws RemoteException {
		return dao.updateBoard(vo);
	}
	
	@Override
	public int insertPostCmt(CommentVO comvo) throws RemoteException {
		return dao.insertPostCmt(comvo);
	}

	@Override
	public List<CommentVO> getPostComment(String popbd_id) throws RemoteException {
		return dao.getPostComment(popbd_id);
	}

	@Override
	public int delPostComment(String cmt_id) throws RemoteException {
		return dao.delPostComment(cmt_id);
	}
	
	// ------------------------------------------------------------------------
	
	@Override
	public int qnaInsert(Qna_boardVO vo) throws RemoteException {
		return dao.qnaInsert(vo);
	}

	@Override
	public int qnaDelete(String qnaId) throws RemoteException {
		return dao.qnaDelete(qnaId);
	}

	@Override
	public int qnaUpdate(Qna_boardVO vo) throws RemoteException {
		return dao.qnaUpdate(vo);
	}

	@Override
	public int insertCmt(CommentVO comvo) throws RemoteException {
		return dao.insertCmt(comvo);
	}

	@Override
	public List<CommentVO> getComment(String qabd_id) throws RemoteException {
		return dao.getComment(qabd_id);
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
	public void inum(String nbd_id) throws RemoteException {
		 dao.inum(nbd_id);
	}


	@Override
	public int nbdDelete(String id) throws RemoteException{
		return dao.nbdDelete(id);
	}
}
