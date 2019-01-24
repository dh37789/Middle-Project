package classRoom.video.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import classRoom.main.dao.ClassMainDaoImpl;
import classRoom.main.dao.IClassMainDao;
import sqlMapConfig.SqlMapClientFactory;
import vo.Member_videoVO;
import vo.VideoVO;

/**
 * 동영상 강의 DaoImpl
 * @author PC02
 *
 */
public class VideoDaoImpl implements IVideoDao {

	private  static IVideoDao dao;
	private  SqlMapClient client;
	
	private  VideoDaoImpl() {
		client = SqlMapClientFactory.getSqlMapClient();
	}
	
	public static IVideoDao getInstance() {
		if(dao==null) {
			dao = new VideoDaoImpl();
		}
		return dao;
	}
	
	@Override
	public VideoVO selectVideo(String vd_name) throws SQLException {
		return (VideoVO) client.queryForObject("video.selectVideo",vd_name);
	}

	@Override
	public Member_videoVO selectTime(Map<String, String> param) throws SQLException {
		return  (Member_videoVO) client.queryForObject("video.selectTime",param);
	}


	@Override
	public void insert(Member_videoVO vo) throws SQLException {
		client.insert("video.insertVideo",vo);
	}

	@Override
	public void updateTime(Map<String, String> param) throws SQLException {
		client.update("video.updateTime",param);
	}

	@Override
	public void complete(Member_videoVO vo) throws SQLException {
		client.update("video.complete",vo);
	}

	@Override
	public int updatept(String mem_id) throws SQLException {
		return client.update("video.updatept", mem_id);
	}

}
