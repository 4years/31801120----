package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanUser;
import xm.takeway.model.BeanUserAddress;
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
	//成为Vip
	public void BeVip() throws BaseException;
	//判断是否为Vip
	public Boolean isVip() throws BaseException;
	//判断Vip是否到期
	public Boolean isVipDead() throws BaseException;
	//添加收货地址
	public void userAddAddress(String Province,String City,String Block,String Address,String Tel) throws BaseException;
	//加载收货地址
	public List<BeanUserAddress> loadUserAddress() throws BaseException;
}
