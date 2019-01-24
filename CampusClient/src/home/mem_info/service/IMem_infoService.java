package home.mem_info.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;

/**
 * 회원정보 서비스 인터페이스
 * @author PC02
 *
 */
public interface IMem_infoService extends Remote{
	/**
	 * 회원 정보 수정 메서드
	 * @param mvo 수정할 회원 정보
	 * @return 성공시 1 실패시 0
	 */
	int infoUpdate(MemberVO mvo) throws RemoteException;

	int passUpdate(MemberVO mvo) throws RemoteException;

	int outMember(String mem_id) throws RemoteException;

	public int getCouponChk(Map<String, String> map) throws RemoteException;
	
	public int insertCouponChk(Member_couponVO mcvo) throws RemoteException;
	
	public int updateCouponChk(Map<String, String> map) throws RemoteException;

	public CouponVO getCouponId(String cid) throws RemoteException;
	
	public Member_couponVO getCouponCount(Map<String, String> map) throws RemoteException; 
	
	public int updateUseCoupon(Map<String, String> map) throws RemoteException;
}
