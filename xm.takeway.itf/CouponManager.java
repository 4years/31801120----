package xm.takeway.itf;

import java.sql.Date;
import java.util.List;

import xm.takeway.model.BeanCoupon;
import xm.takeway.util.BaseException;

public interface CouponManager {
	//ÃÌº””≈ª›»Ø
	public BeanCoupon addCoupon(double moneyOff_much,int consume_count,Date beginDate,Date endDate,int couponNum) throws BaseException;
	//º”‘ÿ”≈ª›»Ø
	public List<BeanCoupon> MerchantloadAll() throws BaseException;
	//…æ≥˝”≈ª›»Ø
	public void DelCoupon(BeanCoupon coupon) throws BaseException;
}
