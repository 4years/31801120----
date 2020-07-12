package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xm.takeway.itf.OrderManager;
import xm.takeway.model.BeanOrderMessage;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MOrderManager implements OrderManager {
	public List<BeanOrderMessage> NowloadAll() throws BaseException {
		List<BeanOrderMessage> result = new ArrayList<BeanOrderMessage>();
		BeanOrderMessage BOM = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select order_id,merchant_name,knight_name,origin_money,real_money,order_state from user_order where user_name = ? and (order_state != ? or order_state != ?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.setString(2, "已送达");
			pst.setString(3, "订单已取消");
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BOM = new BeanOrderMessage();
				BOM.setOrder_id(rs.getInt(1));
				BOM.setMerchant_name(rs.getString(2));
				BOM.setKnight_name(rs.getString(3));
				BOM.setOrigin_money(rs.getDouble(4));
				BOM.setReal_money(rs.getDouble(5));
				BOM.setOrder_state(rs.getString(6));
				result.add(BOM);
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
	
	public void DelOrder(BeanOrderMessage curOrder) throws BaseException {
		if(curOrder.getKnight_name() != null)
			throw new BusinessException("该订单已被骑手接下");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user_order set order_state = ? where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "订单已取消");
			pst.setInt(2, curOrder.getOrder_id());
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
	
	public List<BeanOrderMessage> HistoryloadAll() throws BaseException {
		List<BeanOrderMessage> result = new ArrayList<BeanOrderMessage>();
		BeanOrderMessage BOM = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select order_id,merchant_name,knight_name,origin_money,real_money,order_state from user_order where user_name = ? and (order_state = ? or order_state = ?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.setString(2, "已送达");
			pst.setString(3, "订单已取消");
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BOM = new BeanOrderMessage();
				BOM.setOrder_id(rs.getInt(1));
				BOM.setMerchant_name(rs.getString(2));
				BOM.setKnight_name(rs.getString(3));
				BOM.setOrigin_money(rs.getDouble(4));
				BOM.setReal_money(rs.getDouble(5));
				BOM.setOrder_state(rs.getString(6));
				result.add(BOM);
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
}
