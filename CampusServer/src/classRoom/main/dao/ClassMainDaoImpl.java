package classRoom.main.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import sqlMapConfig.SqlMapClientFactory;
import vo.ContentVO;
import vo.UnitVO;
import vo.VideoVO;
import vo.joinVO;

public class ClassMainDaoImpl implements IClassMainDao{

	private  static IClassMainDao dao;
	private  SqlMapClient client;
	
	private  ClassMainDaoImpl() {
		client = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static IClassMainDao getInstance() {
		if(dao==null) {
			dao = new ClassMainDaoImpl();
		}
		return dao;
	}
	
	// 과목들을 가져오는 메서드
	@Override
	public List<UnitVO> selectUnit() throws SQLException {
		return client.queryForList("unit.selectUnit");
	}
	// 과목에있는 내용을 가져오는 메서드
	@Override
	public List<VideoVO> selectCon() throws SQLException {
		return client.queryForList("content.selectContent");
	}

	@Override
	public List<VideoVO> selConName(String con_name) throws SQLException, RemoteException {
		return client.queryForList("content.selectConName",con_name);
	}
	
	@Override
	public List<joinVO> join(String mem_id) throws SQLException {
		return client.queryForList("unit.join",mem_id);
	}

}
