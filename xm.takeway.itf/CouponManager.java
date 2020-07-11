package xm.takeway.itf;

import java.sql.Date;
import java.util.List;

import xm.takeway.model.BeanCoupon;
import xm.takeway.util.BaseException;

public interface CouponManager {
	//添加优惠券
	public BeanCoupon addCoupon(double moneyOff_much,int consume_count,Date beginDate,Date endDate,int couponNum) throws BaseException;
	//商家界面用加载优惠券
	public List<BeanCoupon> MerchantloadAll() throws BaseException;
	//删除优惠券
	public void DelCoupon(BeanCoupon coupon) throws BaseException;
	//用户界面用加载优惠券
	public List<BeanCoupon> UserloadAll() throws BaseException;
	//用户领取优惠券
	public void UserGetCoupon(BeanCoupon curCoupon) throws BaseException;
	public int loadGetCouponFlag();
}
