package home.mem_info.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import home.mem_info.dao.IMem_infoDao;
import home.mem_info.dao.Mem_infoDaoimpl;
import vo.CouponVO;
import vo.MemberVO;
import vo.Member_couponVO;

/**
 * 회원정보 서비스 Impl
 * @author PC02
 *
 */
public class Mem_infoServiceImpl extends UnicastRemoteObject implements IMem_infoService{
	IMem_infoDao dao;
	
	public Mem_infoServiceImpl() throws RemoteException {
		super();
		dao = Mem_infoDaoimpl.getInstance();
	}

	@Override
	public int infoUpdate(MemberVO mvo) throws RemoteException {
		return dao.infoUpdate(mvo);
	}

	@Override
	public int passUpdate(MemberVO mvo) throws RemoteException {
		return dao.passUpdate(mvo);
	}

	@Override
	public int outMember(String mem_id) throws RemoteException {
		return dao.outMember(mem_id);
	}

	@Override
	public int getCouponChk(Map<String, String> map) throws RemoteException {
		return dao.getCouponChk(map);
	}

	@Override
	public int insertCouponChk(Member_couponVO mcvo) throws RemoteException {
		return dao.insertCouponChk(mcvo);
	}

	@Override
	public int updateCouponChk(Map<String, String> map) throws RemoteException {
		return dao.updateCouponChk(map);
	}

	@Override
	public CouponVO getCouponId(String cid) throws RemoteException {
		return dao.getCouponId(cid);
	}

	@Override
	public Member_couponVO getCouponCount(Map<String, String> map) throws RemoteException {
		return dao.getCouponCount(map);
	}

	@Override
	public int updateUseCoupon(Map<String, String> map) throws RemoteException {
		return dao.updateUseCoupon(map);
	}

}
