package home.community.dao;
/**
 * 커뮤니티 Dao인터페이스
 * @author PC02
 *
 */

import java.rmi.RemoteException;
import java.util.List;

import vo.CommentVO;
import vo.LcsVO;
import vo.Tip_boardVO;

public interface ICommunityDao {
	
		// 자격증정보 리스트 불러오기
		public List<LcsVO> getLicenseBoard();
		
		// 자격증정보 리스트 검색
		public List<LcsVO> lcsSearch(String lcs_nm);
		
		// 자격증 Tip 리스트 불러오기
		public List<Tip_boardVO> getTipBoard();
		
		// 자격증 Tip 등록
		public int insertTip(Tip_boardVO tvo);
		
		// 자격증 Tip 삭제
		public int deleteTip(String tbd_id);
		
		// 자격증 Tip 수정
		public int updateTip(Tip_boardVO tvo);
		
		// 자격증 Tip 검색
		public List<Tip_boardVO> tipSearch(String tbd_ti);
		
		//댓글 등록
		public int insertTbCmt(CommentVO comvo);
		
		//댓글 출력
		public List<CommentVO> getTbComment(String frbd_id);
		
		//댓글 삭제
		int delTbComment(String cmt_id);
	
}
