package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xm.takeway.itf.RootManager;
import xm.takeway.model.BeanMoneyOff;
import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MRootManager implements RootManager {
	public BeanRoot login(String name,String pwd) throws BaseException {
		if(!(name.equals("root")))
			throw new BusinessException("π‹¿Ì‘±µ«¬Ω√˚¥ÌŒÛ");
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
					throw new BusinessException("√‹¬Î¥ÌŒÛ");
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
}
