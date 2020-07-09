package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import xm.takeway.itf.CouponManager;
import xm.takeway.model.BeanCoupon;
import xm.takeway.model.BeanMerchant;
import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;
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
	
}
