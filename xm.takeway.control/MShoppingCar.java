package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import xm.takeway.itf.ShoppingCarManager;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanShoppingCar;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MShoppingCar implements ShoppingCarManager {
	
	public BeanShoppingCar addGoods(BeanGoodsDetails BGD,int num) throws BaseException {
		if(BGD.getGoods_num() < num)
			throw new BusinessException("购买数量大于库存，请重新输入数量");
		BeanShoppingCar BSC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(order_id) from user_shoppingCar where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			int order_id;
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 0;
			rs.close();
			pst.close();
			sql = "select id,num from user_shoppingCar where goods_name = ? and merchant_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BGD.getGoods_name());
			pst.setString(2, BGD.getMerchant_Name());
			rs = pst.executeQuery();
			int flag = 0;
			if(rs.next()) {
				num += rs.getInt(2);
				if(num > BGD.getGoods_num())
					throw new BusinessException("购买数量大于库存，请重新输入数量");
				flag = 1;
			}
			rs.close();
			pst.close();
			if(flag == 0) {
				sql = "insert into user_shoppingCar(order_id,goods_name,merchant_name,goods_price,num,user_name) values(?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, order_id);
				pst.setString(2, BGD.getGoods_name());
				pst.setString(3, BGD.getMerchant_Name());
				pst.setDouble(4, BGD.getGoods_price());
				pst.setInt(5, num);
				pst.setString(6, BeanUser.currentLoginUser.getUser_name());
				pst.execute();
				pst.close();
				BSC = new BeanShoppingCar();
				BSC.setOrder_id(order_id);
				BSC.setGoods_name(BGD.getGoods_name());
				BSC.setMerchant_name(BGD.getMerchant_Name());
				BSC.setGoods_price(BGD.getGoods_price());
				BSC.setNum(num);	
			}
			else {
				pst.close();
				sql = "update user_shoppingCar set num = ? where goods_name = ? and merchant_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, num);
				pst.setString(2, BGD.getGoods_name());
				pst.setString(3, BGD.getMerchant_Name());
				pst.execute();
				pst.close();
			}
			conn.commit();
			JOptionPane.showMessageDialog(null, "加入购物车成功");
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
		return BSC;
	}
	
	public List<BeanShoppingCar> loadAll() throws BaseException {
		List<BeanShoppingCar> result = new ArrayList<BeanShoppingCar>();
		BeanShoppingCar BSC = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select order_id,goods_name,merchant_name,goods_price,num from user_shoppingCar where user_name = ? order by order_id";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				BSC = new BeanShoppingCar();
				BSC.setOrder_id(rs.getInt(1));
				BSC.setGoods_name(rs.getString(2));
				BSC.setMerchant_name(rs.getString(3));
				BSC.setGoods_price(rs.getDouble(4));
				BSC.setNum(rs.getInt(5));
				result.add(BSC);
			}
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try{
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public void delGoodsFromShoppingCar(BeanShoppingCar shoppingCar) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql ="delete from user_shoppingCar where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, shoppingCar.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update user_shoppingCar set order_id = -order_id where order_id > ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, shoppingCar.getOrder_id());
			pst.execute();
			pst.close();
			
			sql = "update user_shoppingCar set order_id = -1 - order_id where order_id < 0";
			pst = conn.prepareStatement(sql);
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
	
}
