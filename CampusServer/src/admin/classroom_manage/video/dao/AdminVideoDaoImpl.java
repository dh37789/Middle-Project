package admin.classroom_manage.video.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import classRoom.main.dao.ClassMainDaoImpl;
import classRoom.main.dao.IClassMainDao;
import sqlMapConfig.SqlMapClientFactory;
import vo.VideoVO;

public class AdminVideoDaoImpl implements IadminVideoDao {

	private  static IadminVideoDao dao;
	private  SqlMapClient client;
	
	private  AdminVideoDaoImpl() {
		client = SqlMapClientFactory.getSqlMapClient();
	}
	
	
	public static IadminVideoDao getInstance() {
		if(dao==null) {
			dao = new AdminVideoDaoImpl();
		}
		return dao;
	}
	
	
	
	
	
	@Override
	public List<VideoVO> getAllVideo() throws SQLException {
		return client.queryForList("video.getAllVideo");
	}

	@Override
	public VideoVO selectVideo(String vd_id) throws SQLException {
		return (VideoVO) client.queryForObject("video.selectVideoId",vd_id);
	}


	@Override
	public int deleteVideo(String vd_id) throws SQLException {
		return client.delete("video.deleteVideo",vd_id);
	}


	@Override
	public int insertVideo(VideoVO vo) {
		int cnt = 0;
		try {
			
			Object ob = client.insert("video.insertVideo2", vo);
			if(ob ==null) {
				cnt = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			cnt =0;
		}
		return cnt;
	}
	
	
	
	
	
	
	
	

}
