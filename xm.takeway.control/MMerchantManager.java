package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import xm.takeway.itf.MerchantManager;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MMerchantManager implements MerchantManager {
	public BeanMerchant reg(String merchantName,String merchantRank,double avg_consume,int total_sales,String pwd,String pwd2) throws BaseException {
		Connection conn = null;
		if(merchantName.equals("root"))
			throw new BusinessException("商家名不能为root");
		if("".equals(merchantName) || merchantName == null)
			throw new BusinessException("商家名不能为空");
		if("".equals(pwd) || pwd == null || "".equals(pwd2) || pwd2 == null)
			throw new BusinessException("密码不能为空");
		if(!(pwd.equals(pwd2)))
			throw new BusinessException("两次密码输入需一致");
		BeanMerchant BM = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select merchant_id from merchant_message where merchant_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, merchantName);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("该商家名已存在");
			rs.close();
			pst.close();
			
			sql = "insert into merchant_message(merchant_name,merchant_pwd,merchant_rank,avg_consume,total_sales) values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, merchantName);
			pst.setString(2, pwd);
			pst.setString(3, merchantRank);
			pst.setDouble(4, avg_consume);
			pst.setInt(5, total_sales);
			pst.execute();
			pst.close();
			conn.commit();
			BM = new BeanMerchant();
			BM.setMerchant_name(merchantName);
			BM.setMerchant_pwd(pwd);
			BM.setMerchant_rank(merchantRank);
			BM.setAvg_consume(avg_consume);
			BM.setTotal_sales(total_sales);
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
		return BM;
	}
	
	public BeanMerchant login(String merchantName,String pwd) throws BaseException {
		Connection conn = null;
		if(merchantName == null || "".equals(merchantName))
			throw new BusinessException("用户名不能为空");
		if(pwd == null || "".equals(pwd))
			throw new BusinessException("密码不能为空");
		BeanMerchant BM = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select merchant_pwd from merchant_message where merchant_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, merchantName);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(!pwd.equals(rs.getString(1)))
					throw new BusinessException("密码错误");
				else {
					BM = new BeanMerchant();
					BM.setMerchant_name(merchantName);
					BM.setMerchant_pwd(pwd);
				}
			}
			else
				throw new BusinessException("该用户不存在");
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
		return BM;
	}
	
	public void changpwd(BeanMerchant merchant,String oldpwd,String newpwd,String newpwd2) throws BaseException {
		if("".equals(newpwd) || newpwd == null || "".equals(newpwd2) || newpwd2 == null)
			throw new BusinessException("新密码不能为空");
		if(!(newpwd.equals(newpwd2)))
			throw new BusinessException("两次密码输入不一致");
		if(oldpwd.equals(newpwd))
			throw new BusinessException("新密码不可与原密码一致");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select merchant_pwd from merchant_message where merchant_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, merchant.getMerchant_name());
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if(!(oldpwd.equals(rs.getString(1))))
				throw new BusinessException("原密码错误");
			rs.close();
			pst.close();
			sql = "update merchant_message set merchant_pwd = ? where merchant_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newpwd);
			pst.setString(2, merchant.getMerchant_name());
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
	}
	
	public List<BeanMerchant> loadAll() throws BaseException {
		Connection conn = null;
		List<BeanMerchant> result = new ArrayList<BeanMerchant>();
		BeanMerchant BM = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select merchant_id,merchant_name,merchant_rank,avg_consume,total_sales from merchant_message";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BM = new BeanMerchant();
				BM.setMerchant_id(rs.getInt(1));
				BM.setMerchant_name(rs.getString(2));
				BM.setMerchant_rank(rs.getString(3));
				BM.setAvg_consume(rs.getDouble(4));
				BM.setTotal_sales(rs.getInt(5));
				result.add(BM);
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
