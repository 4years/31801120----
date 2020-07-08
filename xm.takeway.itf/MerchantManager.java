package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public interface MerchantManager {
	/**
	 * �̼�ע��
	 * �̼�������Ϊ���Ҳ����ظ�
	 * �����������벻�ܲ�һ��
	 * @param merchantName
	 * @param pwd
	 * @param pwd2
	 * @return
	 * @throws BaseException
	 */
	public BeanMerchant reg(String merchantName,String merchantRank,int avg_consume,int total_sales,String pwd,String pwd2) throws BaseException;
	/**
	 * �̼ҵ�½
	 * @param merchantName
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanMerchant login(String merchantName,String pwd) throws BaseException;
	/**
	 * �޸�����
	 * ��������һ��
	 * ����������������һ��
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
