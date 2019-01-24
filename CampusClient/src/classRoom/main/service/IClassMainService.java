package classRoom.main.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import vo.ContentVO;
import vo.UnitVO;
import vo.VideoVO;
import vo.joinVO;

public interface IClassMainService extends Remote{
	
	
	/**
	 * 단원 목록을 검색해 콤보박스에 출력하는 메서드
	 */
	
	public List<UnitVO> selectUnit() throws SQLException , RemoteException; 
	
	
	public List<VideoVO> selectCon() throws SQLException , RemoteException;
	
	
	/**
	 * 검색해서 con내용을 받아오는 메서드
	 * @return 
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public List<VideoVO> selConName(String str) throws SQLException , RemoteException;
	
	/**
	 * join을 이용해 회원 강의 수강내역을 확인한다.
	 * @param mem_id
	 * @return
	 */
	
	public List<joinVO> join(String mem_id) throws SQLException, RemoteException;
	

}
