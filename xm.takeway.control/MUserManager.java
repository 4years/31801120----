package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import xm.takeway.itf.UserManager;
import xm.takeway.model.BeanUser;
import xm.takeway.model.BeanUserAddress;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MUserManager implements UserManager {
	public BeanUser reg(String username,String usersex,String usertel,String email,String city,String pwd,String pwd2) throws BaseException {
		Connection conn = null;
		if(username.equals("root"))
			throw new BusinessException("用户名不能为root");
		if(username == null || "".equals(username))
			throw new BusinessException("用户名不能为空");
		if(pwd == null || "".equals(pwd) || pwd2 == null || "".equals(pwd2))
			throw new BusinessException("密码不能为空");
		if(!(pwd.equals(pwd2)))
			throw new BusinessException("两次密码输入不一致");
		if(usertel == null || "".equals(usertel))
			throw new BusinessException("联系方式不能为空");
		if(city == null || "".equals(city))
			throw new BusinessException("城市不能为空");
		BeanUser BU = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_id from user_message where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该用户名已存在");
			rs.close();
			pst.close();
			sql = "insert into user_message(user_name,user_sex,user_pwd,user_tel,user_email,user_city,user_regdate,user_vip) values(?,?,?,?,?,?,?,'否')";
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, usersex);
			pst.setString(3, pwd);
			pst.setString(4, usertel);
			pst.setString(5, email);
			pst.setString(6, city);
			pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();
			conn.commit();
			BU = new BeanUser();
			BU.setUser_name(username);
			BU.setUser_sex(usersex);
			BU.setUser_pwd(pwd);
			BU.setUser_city(city);
			BU.setUser_email(email);
			BU.setUser_regdate(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return BU;
	}
	
	public BeanUser login(String username,String pwd) throws BaseException {
		if(username == null || "".equals(username))
			throw new BusinessException("用户名不能为空");
		if(pwd == null || "".equals(pwd))
			throw new BusinessException("密码不能为空");
		Connection conn = null;
		BeanUser BU = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_pwd,user_tel,user_email,user_city,user_id,user_vip from user_message where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(pwd.equals(rs.getString(1))) {
					BU = new BeanUser();
					BU.setUser_name(username);
					BU.setUser_pwd(pwd);
					BU.setUser_tel(rs.getString(2));
					BU.setUser_email(rs.getString(3));
					BU.setUser_city(rs.getString(4));
					BU.setUser_id(rs.getInt(5));
					BU.setUser_vip(rs.getString(6));
				}
				else
					throw new BusinessException("密码错误");
			}
			else
				throw new BusinessException("该用户不存在");
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return BU;
	}
	
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException {
		Connection conn = null;
		if("".equals(oldPwd) || oldPwd == null)
			throw new BusinessException("原密码不能为空");
		if("".equals(newPwd) || newPwd == null)
			throw new BusinessException("新密码不能为空");
		if(!(newPwd.equals(newPwd2)))
			throw new BusinessException("新密码两次输入需一致");
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_pwd from user_message where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if(!(oldPwd.equals(rs.getString(1))))
					throw new BusinessException("原密码错误");
			rs.close();
			pst.close();
			sql = "update user_message set user_pwd = ? where user_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, user.getUser_name());
			pst.execute();
			pst.close();
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void BeVip() throws BaseException {
		if(BeanUser.isVip)
			throw new BusinessException("已经是Vip");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user_message set user_vip = ?,user_vipDeadLine = ? where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "是");
			pst.setDate(2, new java.sql.Date(System.currentTimeMillis() + 2592000000L));
			pst.setString(3, BeanUser.currentLoginUser.getUser_name());
			pst.execute();
			BeanUser.isVip = true;
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public Boolean isVip() throws BaseException {
		Boolean flag = false;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_vip from user_message where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if("是".equals(rs.getString(1)))
				flag = true;
			else
				flag = false;
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return flag;
	}
	
	public Boolean isVipDead() throws BaseException {
		Boolean CancelVip = false;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_vipDeadLine from user_message where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(rs.getDate(1) == null)
					break;
				int i = rs.getDate(1).compareTo(new java.sql.Date(System.currentTimeMillis()));
				if(i < 0) {
					CancelVip = false;
					JOptionPane.showMessageDialog(null, "Vip已到期");
				}
				else
					CancelVip = true;
				
			}
			rs.close();
			pst.close();
			if(CancelVip == false) {
				sql = "update user_message set user_vip = ?,user_vipDeadLine = ? where user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, "否");
				pst.setNull(2, 0);
				pst.setString(3, BeanUser.currentLoginUser.getUser_name());
				pst.execute();
				pst.close();
			}
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return CancelVip;
	}
	
	public void userAddAddress(String Province,String City,String Block,String Address,String Tel) throws BaseException {
		if(Province == null || "".equals(Province))
			throw new BusinessException("省份不能为空");
		if(City == null || "".equals(City))
			throw new BusinessException("市不能为空");
		if(Block == null || "".equals(Block))
			throw new BusinessException("区不能为空");
		if(Address == null || "".equals(Address))
			throw new BusinessException("详细地址不能为空");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(order_id) from user_address where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			int order_id;
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			sql = "insert into user_address(province,city,block,address,user_name,user_tel,order_id) values(?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, Province);
			pst.setString(2, City);
			pst.setString(3, Block);
			pst.setString(4, Address);
			pst.setString(5, BeanUser.currentLoginUser.getUser_name());
			pst.setString(6, Tel);
			pst.setInt(7, order_id);
			pst.execute();
			pst.close();
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public List<BeanUserAddress> loadUserAddress() throws BaseException {
		List<BeanUserAddress> result = new ArrayList<BeanUserAddress>();
		BeanUserAddress BUA = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from user_address where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BUA = new BeanUserAddress();
				BUA.setAddress_id(rs.getInt(1));
				BUA.setProvince(rs.getString(2));
				BUA.setCity(rs.getString(3));
				BUA.setBlock(rs.getString(4));
				BUA.setAddress(rs.getString(5));
				BUA.setUser_name(rs.getString(6));
				BUA.setUser_tel(rs.getString(7));
				BUA.setOrder_id(rs.getInt(8));
				result.add(BUA);
			}
			rs.close();
			pst.close();
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null) 
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}	
		return result;
	}
	
	public void delAddress(BeanUserAddress curAddress) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from user_address where user_name = ? and order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, curAddress.getUser_name());
			pst.setInt(2, curAddress.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update user_address set order_id = -order_id where order_id > ? and user_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, curAddress.getOrder_id());
			pst.setString(2, curAddress.getUser_name());
			pst.execute();
			pst.close();
			
			sql = "update user_address set order_id = -1 - order_id where user_name = ? and order_id < 0";
			pst = conn.prepareStatement(sql);
			pst.setString(1, curAddress.getUser_name());
			pst.execute();
			pst.close();
			
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null) 
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}
}