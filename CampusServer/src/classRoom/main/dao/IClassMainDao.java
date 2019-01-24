package classRoom.main.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import vo.ContentVO;
import vo.UnitVO;
import vo.VideoVO;
import vo.joinVO;

public interface IClassMainDao {

	
	
	
	
	/**
	 * 단원 목록을 검색해 콤보박스에 출력하는 메서드
	 */
	
	public List<UnitVO> selectUnit() throws SQLException; 
	
	
	public List<VideoVO> selectCon() throws SQLException;
	
	/**
	 * 검색해서 con내용을 받아오는 메서드
	 * @return 
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public List<VideoVO> selConName(String con_name) throws SQLException , RemoteException;
	
	
	
	//////////////////////// join VO
	public List<joinVO> join(String mem_id) throws SQLException; 
}
