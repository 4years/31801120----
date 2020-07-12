package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xm.takeway.itf.RootManager;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.model.BeanMoneyOff;
import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MRootManager implements RootManager {
	public BeanRoot login(String name,String pwd) throws BaseException {
		if(!(name.equals("root")))
			throw new BusinessException("����Ա��½������");
		BeanRoot BR = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select root_pwd from root where root_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				if(rs.getString(1).equals(pwd));
				else
					throw new BusinessException("�������");
			BR = new BeanRoot();
			BR.setRoot_name(name);;
			BR.setRoot_pwd(pwd);
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
		return BR;
	}
	
	public BeanMoneyOff addMoneyOffWay(double moneyOff_much,double moneyOff_OffMuch,String moneyOff_overlay) throws BaseException {
		BeanMoneyOff BMO = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(order_id) from merchant_moneyOffWay";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			int order_id;
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			sql = "insert into merchant_moneyOffWay(moneyOff_much,moneyOff_OffMuch,moneyOff_overlay,order_id) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setDouble(1, moneyOff_much);
			pst.setDouble(2, moneyOff_OffMuch);
			pst.setString(3, moneyOff_overlay);
			pst.setInt(4, order_id);
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
		return BMO;
	}
	
	public List<BeanMoneyOff> moneyOffWayloadAll() throws BaseException {
		List<BeanMoneyOff> result = new ArrayList<BeanMoneyOff>();
		BeanMoneyOff BMO = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from merchant_moneyOffWay order by order_id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BMO = new BeanMoneyOff();
				BMO.setMoneyOff_id(rs.getInt(1));
				BMO.setMoneyOff_much(rs.getDouble(2));
				BMO.setMoneyOff_OffMuch(rs.getDouble(3));
				BMO.setMoneyOff_overlay(rs.getString(4));
				BMO.setOrder_id(rs.getInt(5));
				result.add(BMO);
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
	
	public void addGoodsKind(String kindName) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(order_id) from merchant_goodskind";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			int order_id;
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			
			sql = "select * from merchant_goodskind where kind_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, kindName);
			rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("������Ѵ���");
			
			sql = "insert into merchant_goodskind(kind_name,good_num,order_id) values(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, kindName);
			pst.setInt(2, 0);
			pst.setInt(3, order_id);
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
	
	public List<BeanGoodsKind> goodsKindloadAll() throws BaseException {
		List<BeanGoodsKind> result = new ArrayList<BeanGoodsKind>();
		BeanGoodsKind BGK = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select kind_id,kind_name,good_num,order_id from merchant_goodskind";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BGK = new BeanGoodsKind();
				BGK.setKind_id(rs.getInt(1));
				BGK.setKind_name(rs.getString(2));
				BGK.setGood_num(rs.getInt(3));
				BGK.setOrder_id(rs.getInt(4));
				result.add(BGK);
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
	
}
