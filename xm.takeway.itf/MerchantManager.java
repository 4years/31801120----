package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public interface MerchantManager {
	/**
	 * 商家注册
	 * 商家名不能为空且不能重复
	 * 两次密码输入不能不一致
	 * @param merchantName
	 * @param pwd
	 * @param pwd2
	 * @return
	 * @throws BaseException
	 */
	public BeanMerchant reg(String merchantName,String merchantRank,int avg_consume,int total_sales,String pwd,String pwd2) throws BaseException;
	/**
	 * 商家登陆
	 * @param merchantName
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanMerchant login(String merchantName,String pwd) throws BaseException;
	/**
	 * 修改密码
	 * 旧密码需一致
	 * 新密码两次输入需一致
	 * @param merchant
	 * @param oldpwd
	 * @param newpwd
	 * @param newpwd2
	 * @return
	 * @throws BaseException
	 */
	public void changpwd(BeanMerchant merchant,String oldpwd,String newpwd,String newpwd2) throws BaseException;
	
	public List<BeanMerchant> loadAll() throws BaseException;
	
}
