package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import xm.takeway.itf.GoodsManager;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MGoodsManager implements GoodsManager {
	public BeanGoodsDetails addGoods(int kind_id,String goods_name,double goods_price,double goods_sales,int goods_num) throws BaseException {
		Connection conn = null;
		if(goods_name == null || "".equals(goods_name))
			throw new BusinessException("商品名不能为空");
		BeanGoodsDetails BGD = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			int order_id;
			String sql = "select count(order_id) from root_goodsDetails where kind_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, kind_id);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			rs.close();
			pst.close();
			
			sql = "insert into root_goodsDetails(kind_id,goods_name,goods_price,goods_sales,goods_num,order_id) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, kind_id);
			pst.setString(2, goods_name);
			pst.setDouble(3, goods_price);
			pst.setDouble(4, goods_sales);
			pst.setInt(5, goods_num);
			pst.setInt(6, order_id);
			pst.execute();
			pst.close();
			
			sql = "update merchant_goodskind set good_num = good_num + 1 where kind_id = ?";
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
		return BGD;
	}
	
	public List<BeanGoodsDetails> loadAll() throws BaseException {
		List<BeanGoodsDetails> result = new ArrayList<BeanGoodsDetails>();
		BeanGoodsDetails BGD = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select goods_id,kind_id,merchant_name,goods_name,goods_price,goods_sales,goods_num,order_id from merchant_goodsDetails where merchant_name = ? order by order_id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanMerchant.currentLoginMerchant.getMerchant_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BGD = new BeanGoodsDetails();
				BGD.setGoods_id(rs.getInt(1));
				BGD.setKind_id(rs.getInt(2));
				BGD.setMerchant_Name(rs.getString(3));
				BGD.setGoods_name(rs.getString(4));
				BGD.setGoods_price(rs.getDouble(5));
				BGD.setGoods_sales(rs.getDouble(6));
				BGD.setGoods_num(rs.getInt(7));
				BGD.setOrder_id(rs.getInt(8));
				result.add(BGD);
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
	
	public List<BeanGoodsDetails> loadAll(BeanMerchant merchant) throws BaseException {
		List<BeanGoodsDetails> result = new ArrayList<BeanGoodsDetails>();
		BeanGoodsDetails BGD = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select goods_id,kind_id,merchant_name,goods_name,goods_price,goods_sales,goods_num,order_id from merchant_goodsDetails where merchant_name = ? order by order_id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, merchant.getMerchant_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BGD = new BeanGoodsDetails();
				BGD.setGoods_id(rs.getInt(1));
				BGD.setKind_id(rs.getInt(2));
				BGD.setMerchant_Name(rs.getString(3));
				BGD.setGoods_name(rs.getString(4));
				BGD.setGoods_price(rs.getDouble(5));
				BGD.setGoods_sales(rs.getDouble(6));
				BGD.setGoods_num(rs.getInt(7));
				BGD.setOrder_id(rs.getInt(8));
				result.add(BGD);
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
	
	public void delGoods(BeanGoodsDetails goodsdetails) throws BaseException {
		if(goodsdetails.getGoods_num() != 0)
			throw new BusinessException("该商品还有余量");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from merchant_goodsDetails where goods_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, goodsdetails.getGoods_id());
			pst.execute();
			pst.close();
			
			sql = "update merchant_goodsDetails set order_id = - order_id where merchant_name = ? and order_id > ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, goodsdetails.getMerchant_Name());
			pst.setInt(2, goodsdetails.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update merchant_goodsDetails set order_id = -1 - order_id where merchant_name = ? and order_id < 0";
			pst = conn.prepareStatement(sql);
			pst.setString(1, goodsdetails.getMerchant_Name());
			pst.execute();
			pst.close();
					
			conn.commit();
			JOptionPane.showMessageDialog(null, "删除成功");
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
	
	public List<BeanGoodsDetails> loadAll(BeanGoodsKind curGoodsKind) throws BaseException {
		List<BeanGoodsDetails> result = new ArrayList<BeanGoodsDetails>();
		BeanGoodsDetails BGD = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from root_goodsDetails where kind_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, curGoodsKind.getKind_id());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BGD = new BeanGoodsDetails();
				BGD.setGoods_id(rs.getInt(1));
				BGD.setKind_id(rs.getInt(2));
				BGD.setGoods_name(rs.getString(3));
				BGD.setGoods_price(rs.getDouble(4));
				BGD.setGoods_sales(rs.getDouble(5));
				BGD.setGoods_num(rs.getInt(6));
				BGD.setOrder_id(rs.getInt(7));
				result.add(BGD);
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
