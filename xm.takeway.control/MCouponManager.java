package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.Date;

import xm.takeway.itf.CouponManager;
import xm.takeway.model.BeanCoupon;
import xm.takeway.model.BeanMerchant;
import xm.takeway.model.BeanRoot;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MCouponManager implements CouponManager {
	public BeanCoupon addCoupon(double moneyOff_much,int consume_count,Date beginDate,Date endDate,int couponNum) throws BaseException {
		BeanCoupon BC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(order_id) from merchant_moneyOffMessage";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			int order_id;
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			rs.close();
			pst.close();

			sql = "insert into merchant_moneyOffMessage(moneyOff_much,consume_count,sales_begin_date,sales_end_date,useArea,order_id,coupon_num,add_user,recevied) values(?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setDouble(1, moneyOff_much);
			pst.setInt(2, consume_count);
			pst.setDate(3, beginDate);
			pst.setDate(4, endDate);
			if(BeanRoot.currentLoginRoot != null) {
				pst.setString(5, "全场通用券");
				pst.setString(8, BeanRoot.currentLoginRoot.getRoot_name());
			}
			else {
				pst.setString(5, BeanMerchant.currentLoginMerchant.getMerchant_name() + " 专属");
				pst.setString(8, BeanMerchant.currentLoginMerchant.getMerchant_name());
			}
			pst.setInt(6, order_id);
			pst.setInt(7, couponNum);
			pst.setInt(9, 0);
			pst.execute();
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
		return BC;
	}
	
	public List<BeanCoupon> MerchantloadAll() throws BaseException {
		List<BeanCoupon> result = new ArrayList<BeanCoupon>();
		BeanCoupon BC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from merchant_moneyOffMessage where add_user = ? order by order_id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanMerchant.currentLoginMerchant.getMerchant_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BC = new BeanCoupon();
				BC.setCoupon_id(rs.getInt(1));
				BC.setMoneyOff_much(rs.getDouble(2));
				BC.setConsume_count(rs.getInt(3));
				BC.setSales_begin_date(rs.getDate(4));
				BC.setSales_end_date(rs.getDate(5));
				BC.setCoupon_num(rs.getInt(8));
				BC.setOrder_id(rs.getInt(7));
				BC.setUseArea(rs.getString(6));
				BC.setAdd_user(rs.getString(9));
				BC.setRecevied(rs.getInt(10));
				result.add(BC);
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
		
		return result;
	}
	
	public void DelCoupon(BeanCoupon coupon) throws BaseException {
		if((coupon.getRecevied() != 0 || coupon.getRecevied() != coupon.getCoupon_num()) && coupon.getSales_end_date().after(new java.sql.Date(System.currentTimeMillis())))
			throw new BusinessException("该优惠券已有用户持有");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from merchant_moneyOffMessage where coupon_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, coupon.getCoupon_id());
			pst.execute();
			pst.close();
			
			sql = "update merchant_moneyOffMessage set order_id = -order_id where order_id > ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, coupon.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update merchant_moneyOffMessage set order_id = -1 - order_id where order_id < 0";
			pst = conn.prepareStatement(sql);
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
	
	public List<BeanCoupon> UserloadAll() throws BaseException {
		List<BeanCoupon> result = new ArrayList<BeanCoupon>();
		BeanCoupon BC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from merchant_moneyOffMessage";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BC = new BeanCoupon();
				BC.setCoupon_id(rs.getInt(1));
				BC.setOrder_id(rs.getInt(7));
				BC.setMoneyOff_much(rs.getDouble(2));
				BC.setConsume_count(rs.getInt(3));
				BC.setSales_begin_date(rs.getDate(4));
				BC.setSales_end_date(rs.getDate(5));
				BC.setUseArea(rs.getString(6));
				BC.setAdd_user(rs.getString(9));
				BC.setCoupon_num(rs.getInt(8));
				BC.setRecevied(rs.getInt(10));
				BC.setUnrecevied(rs.getInt(8) - rs.getInt(10));
				result.add(BC);
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
		return result;
	}
	
	int flag = 0;
	public void UserGetCoupon(BeanCoupon curCoupon) throws BaseException {
		if(curCoupon.getUnrecevied() == 0)
			throw new BusinessException("手慢啦，该优惠券已领完");
		int needConsume = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from user_moneyOffHold where user_name = ? and coupon_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.setInt(2, curCoupon.getCoupon_id());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该优惠券你已经领取过了");
			rs.close();
			pst.close();
			
			int order_id = 0;
			sql = "select count(order_id) from user_moneyOffHold where user_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			rs = pst.executeQuery();
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			if(curCoupon.getConsume_count() == 0) {
				sql = "insert into user_moneyOffHold(user_name,coupon_id,merchant_name,moneyOff_much,number,sales_begin_date,sales_end_date,order_id,UseArea) values(?,?,?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, BeanUser.currentLoginUser.getUser_name());
				pst.setInt(2, curCoupon.getCoupon_id());
				pst.setString(3, curCoupon.getAdd_user());
				pst.setDouble(4, curCoupon.getMoneyOff_much());
				pst.setInt(5, 1);
				pst.setDate(6, curCoupon.getSales_begin_date());
				pst.setDate(7, curCoupon.getSales_end_date());
				pst.setInt(8, order_id);
				pst.setString(9, curCoupon.getUseArea());
				pst.execute();
				pst.close();
				
				sql = "update merchant_moneyOffMessage set recevied = recevied + 1 where coupon_id = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, curCoupon.getCoupon_id());
				pst.execute();
				pst.close();
			} else if(curCoupon.getConsume_count() != 0) {
				pst.close();
				sql = "select finished_consume from user_consumeCount where user_name = ? and merchant_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, BeanUser.currentLoginUser.getUser_name());
				pst.setString(2, curCoupon.getAdd_user());
				rs = pst.executeQuery();
				if(rs.next()) {
					if(rs.getInt(1) >= curCoupon.getConsume_count()) 
						flag = 1;
					else {
						needConsume = curCoupon.getConsume_count() - rs.getInt(1);
						throw new BusinessException("还需集" + String.valueOf(needConsume) + "单才能领取");
					}
				}
				else {
					flag = 2;
				}
				rs.close();
				pst.close();
				if(flag == 1) {
					sql = "insert into user_moneyOffHold(user_name,coupon_id,merchant_name,moneyOff_much,number,sales_begin_date,sales_end_date,order_id,UseArea) values(?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, BeanUser.currentLoginUser.getUser_name());
					pst.setInt(2, curCoupon.getCoupon_id());
					pst.setString(3, curCoupon.getAdd_user());
					pst.setDouble(4, curCoupon.getMoneyOff_much());
					pst.setInt(5, 1);
					pst.setDate(6, curCoupon.getSales_begin_date());
					pst.setDate(7, curCoupon.getSales_end_date());
					pst.setInt(8, order_id);
					pst.setString(9, curCoupon.getUseArea());
					pst.execute();
					pst.close();
					
					sql = "update merchant_moneyOffMessage set recevied = recevied + 1 where coupon_id = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, curCoupon.getCoupon_id());
					pst.execute();
					pst.close();
					
					sql = "update user_consumeCount set finished_consume = finished_consume - ? where user_name = ? and merchant_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, curCoupon.getConsume_count());
					pst.setString(2, BeanUser.currentLoginUser.getUser_name());
					pst.setString(3, curCoupon.getAdd_user());
					pst.execute();
					pst.close();
				} else if(flag == 2) {
					pst.close();
					sql = "insert into user_consumeCount(user_name,merchant_name,finished_consume) values(?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, BeanUser.currentLoginUser.getUser_name());
					pst.setString(2, curCoupon.getAdd_user());
					pst.setInt(3, 0);
					pst.execute();
					pst.close();
					//conn.commit();
					sql = "select finished_consume from user_consumeCount where user_name = ? and merchant_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, BeanUser.currentLoginUser.getUser_name());
					pst.setString(2, curCoupon.getAdd_user());
					rs = pst.executeQuery();
					if(rs.next())
						needConsume = curCoupon.getConsume_count() - rs.getInt(1);
					rs.close();
					pst.close();
					JOptionPane.showMessageDialog(null, "还需集" + String.valueOf(needConsume) + "单才能领取");
				}
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
	}
	public int loadGetCouponFlag() {
		return flag;
	}
	
	public List<BeanCoupon> UserHoldloadAll() throws BaseException {
		List<BeanCoupon> result = new ArrayList<BeanCoupon>();
		BeanCoupon BC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from user_moneyOffHold where user_name = ? and statu = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.setString(2, "可使用");
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BC = new BeanCoupon();
				BC.setCoupon_id(rs.getInt(2));
				BC.setAdd_user(rs.getString(3));
				BC.setMoneyOff_much(rs.getDouble(4));
				BC.setSales_begin_date(rs.getDate(6));
				BC.setSales_end_date(rs.getDate(7));
				BC.setOrder_id(rs.getInt(8));
				BC.setUseArea(rs.getString(10));
				result.add(BC);
			}
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if(conn != null) 
				try	{
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public void setCouponStatu() throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user_moneyOffHold set statu = ? where sales_begin_date < now()";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "未到使用期");
			pst.execute();
			pst.close();
			
			sql = "update user_moneyOffHold set statu = ? where sales_begin_date <= curdate() and sales_end_date >= curdate()";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "可使用");
			pst.execute();
			pst.close();
			
			sql = "update user_moneyOffHold set statu = ? where sales_end_date <= curdate()";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "已过期");
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
