package login.register.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.MemberVO;

/**
 * 회원가입 Dao 인터페이스
 * @author PC02
 *
 */
public interface IRegisterDao{

	public int insertMember(MemberVO memvo);
	
	public int getMemberCount(String memid);
}
