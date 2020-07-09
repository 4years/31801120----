package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanUser;
import xm.takeway.model.BeanUserAddress;
import xm.takeway.util.BaseException;

public interface UserManager {
	/**
	 * �û�ע�᣺
	 * Ҫ���û�������Ϊ�գ������ظ�
	 * ��������������һ�£����벻��Ϊ��
	 * @param pwd  ����
	 * @param pwd2 �ظ���������
	 * @return
	 * @throws BaseException
	 */
	public BeanUser reg(String username,String usersex,String usertel,String city,String email,String pwd,String pwd2) throws BaseException;
	/**
	 * ��½
	 *  1������û������ڻ�����������׳�һ���쳣
	 *  2�������֤�ɹ����򷵻ص�ǰ�û���Ϣ
	 * @param userid
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanUser login(String username,String pwd)throws BaseException;
	/**
	 * �޸�����
	 * ���û�гɹ��޸ģ����׳��쳣
	 * @param user    ��ǰ�û�
	 * @param oldPwd  ԭ����
	 * @param newPwd  ������
	 * @param newPwd2 �ظ������������
	 */
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException;
	//��ΪVip
	public void BeVip() throws BaseException;
	//�ж��Ƿ�ΪVip
	public Boolean isVip() throws BaseException;
	//�ж�Vip�Ƿ���
	public Boolean isVipDead() throws BaseException;
	//����ջ���ַ
	public void userAddAddress(String Province,String City,String Block,String Address,String Tel) throws BaseException;
	//�����ջ���ַ
	public List<BeanUserAddress> loadUserAddress() throws BaseException;
}
