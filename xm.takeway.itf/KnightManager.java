package xm.takeway.itf;

import xm.takeway.model.BeanKnight;
import xm.takeway.util.BaseException;

public interface KnightManager {
	/**
	 * ����ע��
	 * Ҫ�����ֲ���Ϊ�գ������ظ�
	 * ��������������һ�£����벻��Ϊ��
	 * @param pwd  ����
	 * @param pwd2 �ظ���������
	 * @return
	 * @throws BaseException
	 */
	public BeanKnight reg(String knight_name,String pwd,String pwd2) throws BaseException;
	//���ֵ�½
	public BeanKnight login(String knight_name,String pwd) throws BaseException; 
	
}
