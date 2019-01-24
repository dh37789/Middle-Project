package login.search.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import login.search.dao.ISearchDao;
import login.search.dao.SearchDaoImpl;
import vo.MemberVO;

/**
 * 아이디 비밀번호 찾기 ServiceImpl
 * @author PC02
 *
 */
public class SearchServiceImpl extends UnicastRemoteObject implements ISearchService{
	
	private ISearchDao searchDao;
	
	public SearchServiceImpl() throws RemoteException {
		searchDao = SearchDaoImpl.getInstance();
	}

	@Override
	public MemberVO getIdSearch(Map<String, String> map) throws RemoteException {
		return (MemberVO) searchDao.getIdSearch(map);
	}

	@Override
	public int getPass(Map<String, String> map) throws RemoteException {
		return (int) searchDao.getPass(map);
	}

	

}
