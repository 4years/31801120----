package xm.takeway.itf;

import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;

public interface UserManager {
	/**
	 * 用户注册：
	 * 要求用户名不能为空，不能重复
	 * 两次输入密码需一致，密码不能为空
	 * @param pwd  密码
	 * @param pwd2 重复输入密码
	 * @return
	 * @throws BaseException
	 */
	public BeanUser reg(String username,String usersex,String usertel,String city,String email,String pwd,String pwd2) throws BaseException;
	/**
	 * 登陆
	 *  1、如果用户不存在或者密码错误，抛出一个异常
	 *  2、如果认证成功，则返回当前用户信息
	 * @param userid
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanUser login(String username,String pwd)throws BaseException;
	/**
	 * 修改密码
	 * 如果没有成功修改，则抛出异常
	 * @param user    当前用户
	 * @param oldPwd  原密码
	 * @param newPwd  新密码
	 * @param newPwd2 重复输入的新密码
	 */
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException;
	
}
