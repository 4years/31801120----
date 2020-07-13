package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xm.takeway.itf.RootManager;
import xm.takeway.model.BeanGoodsDetails;
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
			throw new BusinessException("管理员登陆名错误");
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
					throw new BusinessException("密码错误");
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
				throw new BusinessException("该类别已存在");
			
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
	
	public void modifyPwd(String Name,String pwd,String pwd2,String identity) throws BaseException {
		if(!(pwd.equals(pwd2)))
				throw new BusinessException("两次密码输入不一致");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			if("用户".equals(identity)) {
				sql = "select user_id from user_message where user_name = ?";
				java.sql.PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, Name);
				java.sql.ResultSet rs = pst.executeQuery();
				if(!rs.next())
					throw new BusinessException("该用户不存在");
				rs.close();
				pst.close();
				sql = "update user_message set user_pwd = ? where user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, pwd);
				pst.setString(2, Name);
				pst.execute();
				pst.close();
			} else if("商家".equals(identity)) {
				sql = "select merchant_id from merchant_message where merchant_name = ?";
				java.sql.PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, Name);
				java.sql.ResultSet rs = pst.executeQuery();
				if(!rs.next())
					throw new BusinessException("该商家不存在");
				sql = "update merchant_message ser merchant_pwd = ? where merchant_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, pwd);
				pst.setString(2, Name);
				pst.execute();
				pst.close();
			} else if("骑手".equals(identity)) {
				sql = "select knight_id from knight_message where knight_name = ?";
				java.sql.PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, Name);
				java.sql.ResultSet rs = pst.executeQuery();
				if(!rs.next())
					throw new BusinessException("该骑手不存在");
				sql = "update knight_message ser knight_pwd = ? where knightt_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, pwd);
				pst.setString(2, Name);
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
	}
	
	public void delGoods(int kind_id,BeanGoodsDetails curGoods) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from root_goodsDetails where kind_id = ? and goods_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, kind_id);
			pst.setString(2, curGoods.getGoods_name());
			pst.execute();
			pst.close();
			
			sql = "update root_goodsDetails set order_id = -order_id where kind_id = ? and order_id > ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, kind_id);
			pst.setInt(2, curGoods.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update root_goodsDetails set order_id = -1 - order_id where kind_id = ? and order_id < 0";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, kind_id);
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
