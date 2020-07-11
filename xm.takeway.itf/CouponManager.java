package xm.takeway.itf;

import java.sql.Date;
import java.util.List;

import xm.takeway.model.BeanCoupon;
import xm.takeway.util.BaseException;

public interface CouponManager {
	//����Ż�ȯ
	public BeanCoupon addCoupon(double moneyOff_much,int consume_count,Date beginDate,Date endDate,int couponNum) throws BaseException;
	//�̼ҽ����ü����Ż�ȯ
	public List<BeanCoupon> MerchantloadAll() throws BaseException;
	//ɾ���Ż�ȯ
	public void DelCoupon(BeanCoupon coupon) throws BaseException;
	//�û������ü����Ż�ȯ
	public List<BeanCoupon> UserloadAll() throws BaseException;
	//�û���ȡ�Ż�ȯ
	public void UserGetCoupon(BeanCoupon curCoupon) throws BaseException;
	public int loadGetCouponFlag();
}
