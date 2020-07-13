package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xm.takeway.itf.OrderManager;
import xm.takeway.model.BeanKnight;
import xm.takeway.model.BeanKnightShowOrder;
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
			String sql = "select order_id,merchant_name,knight_name,origin_money,real_money,order_state from user_order where user_name = ? and (order_state != ? and order_state != ?)";
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
	
	public List<BeanOrderMessage> KnightloadAll() throws BaseException {
		List<BeanOrderMessage> result = new ArrayList<BeanOrderMessage>();
		List<BeanKnightShowOrder> OrderResult = new ArrayList<BeanKnightShowOrder>();
		List<BeanKnightShowOrder> OrderResult2 = new ArrayList<BeanKnightShowOrder>();
		BeanOrderMessage BOM = null;
		BeanKnightShowOrder BKSO = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from user_order where order_state = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "等待接单");
			java.sql.ResultSet rs = pst.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				BKSO = new BeanKnightShowOrder();
				BKSO.setUser_name(rs.getString(3));
				BKSO.setAddress_id(rs.getInt(11));
				OrderResult.add(BKSO);
			}
			for(int i = 0;i < count;i++) {
				sql = "select user_sex from user_message where user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, OrderResult.get(i).getUser_name());
				rs = pst.executeQuery();
				rs.next();
				BKSO = new BeanKnightShowOrder();
				BKSO.setUser_sex(rs.getString(1));
				rs.close();
				pst.close();
				
				sql = "select city,block,address from user_address where address_id = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, OrderResult.get(i).getAddress_id());
				rs = pst.executeQuery();
				rs.next();
				BKSO.setAddress(rs.getString(1) + rs.getString(2) + rs.getString(3));
				rs.close();
				pst.close();
				OrderResult2.add(BKSO);
			}
			
			sql = "select * from user_order where order_state = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "等待接单");
			rs = pst.executeQuery();
			for(int i = 0;i < count;i++) {
				rs.next();
				BOM = new BeanOrderMessage();
				BOM.setOrder_id(rs.getInt(1));
				BOM.setMerchant_name(rs.getString(2));
				if("男".equals(OrderResult2.get(i).getUser_sex()))
					BOM.setUser_name(rs.getString(3) + "先生");
				else
					BOM.setUser_name(rs.getString(3) + "女士");
				BOM.setAddress(OrderResult2.get(i).getAddress());
				BOM.setOrder_state(rs.getString(12));
				result.add(BOM);
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
	
	public List<BeanOrderMessage> KnightGetloadAll() throws BaseException {
		List<BeanOrderMessage> result = new ArrayList<BeanOrderMessage>();
		List<BeanKnightShowOrder> OrderResult = new ArrayList<BeanKnightShowOrder>();
		List<BeanKnightShowOrder> OrderResult2 = new ArrayList<BeanKnightShowOrder>();
		BeanOrderMessage BOM = null;
		BeanKnightShowOrder BKSO = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from user_order where knight_name = ? and order_state != ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanKnight.currentLoginKnight.getKnight_name());
			pst.setString(2, "已送达");
			java.sql.ResultSet rs = pst.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				BKSO = new BeanKnightShowOrder();
				BKSO.setUser_name(rs.getString(3));
				BKSO.setAddress_id(rs.getInt(11));
				OrderResult.add(BKSO);
			}
			for(int i = 0;i < count;i++) {
				sql = "select user_sex from user_message where user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, OrderResult.get(i).getUser_name());
				rs = pst.executeQuery();
				rs.next();
				BKSO = new BeanKnightShowOrder();
				BKSO.setUser_sex(rs.getString(1));
				rs.close();
				pst.close();
				
				sql = "select city,block,address from user_address where address_id = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, OrderResult.get(i).getAddress_id());
				rs = pst.executeQuery();
				rs.next();
				BKSO.setAddress(rs.getString(1) + rs.getString(2) + rs.getString(3));
				rs.close();
				pst.close();
				OrderResult2.add(BKSO);
			}
			
			sql = "select * from user_order where order_state != ? and order_state != ? and knight_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "等待接单");
			pst.setString(2, "已送达");
			pst.setString(3, BeanKnight.currentLoginKnight.getKnight_name());
			rs = pst.executeQuery();
			for(int i = 0;i < count;i++) {
				rs.next();
				BOM = new BeanOrderMessage();
				BOM.setOrder_id(rs.getInt(1));
				BOM.setMerchant_name(rs.getString(2));
				if("男".equals(OrderResult2.get(i).getUser_sex()))
					BOM.setUser_name(rs.getString(3) + "先生");
				else
					BOM.setUser_name(rs.getString(3) + "女士");
				BOM.setAddress(OrderResult2.get(i).getAddress());
				BOM.setOrder_state(rs.getString(12));
				result.add(BOM);
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
	
	public void KnightComment(BeanOrderMessage curOrder,String Comment) throws BaseException {
		if("订单已取消".equals(curOrder.getOrder_state()))
			throw new BusinessException("该订单已取消");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select user_comment from knight_income where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, curOrder.getOrder_id());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				if("差评".equals(rs.getString(1)) || "好评".equals(rs.getString(1)))
					throw new BusinessException("该订单已评价");
			rs.close();
			pst.close();
			
			double ex_income = 0;
			if("好评".equals(Comment))
				ex_income = 0.5;
			else if("差评".equals(Comment))
				ex_income = -20;
			
			sql = "update knight_income set user_comment = ?,income = income + ? where order_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, Comment);
			pst.setDouble(2, ex_income);
			pst.setInt(3, curOrder.getOrder_id());
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
