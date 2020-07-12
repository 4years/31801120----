package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import xm.takeway.itf.ShoppingCarManager;
import xm.takeway.model.BeanCoupon;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanMerchant;
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
			sql = "select id,num from user_shoppingCar where goods_name = ? and merchant_name = ? and user_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BGD.getGoods_name());
			pst.setString(2, BGD.getMerchant_Name());
			pst.setString(3, BeanUser.currentLoginUser.getUser_name());
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
				Boolean isVip = BeanUser.isVip;
				if(isVip) {
					sql = "insert into user_shoppingCar(order_id,goods_name,merchant_name,goods_price,num,user_name) values(?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, order_id);
					pst.setString(2, BGD.getGoods_name());
					pst.setString(3, BGD.getMerchant_Name());
					pst.setDouble(4, BGD.getGoods_sales());
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
				} else {
					pst.close();
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
	
	public void settlementShoppingCar(int address_order_id,int coupon_order_id,int moneyOffWay_order_id) throws BaseException {
		List<BeanShoppingCar> result = this.loadAll();
		BeanCoupon BC = new BeanCoupon();
		double couponOff = 0;			//优惠券优惠金额
		double moneyOff_much = 0;		//满减条件
		double moneyOff_OffMuch = 0;	//满减金额
		double origin_money = 0;		//原始订单价格
		double real_money = 0;			//实际支付价格(使用优惠后价格)
		int merchant_count = 0;			//统计购买商品所属的商家个数
		double rootCoupon_off = 0;
		int moneyOff_id = 0;
		int address_id = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			//检索购物车内商品所属商家，用于对优惠券使用的判断
			String sql = "select distinct merchant_name from user_shoppingCar where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			List<BeanMerchant> merchantResult = new ArrayList<BeanMerchant>();
			BeanMerchant merchant = null;
			while(rs.next()) {
				merchant_count++;
				merchant = new BeanMerchant();
				merchant.setMerchant_name(rs.getString(1));
				merchantResult.add(merchant);
			}
			rs.close();
			pst.close();
			
			sql = "select address_id from user_address where user_name = ? and order_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.setInt(2, address_order_id);
			rs = pst.executeQuery();
			if(rs.next())
				address_id = rs.getInt(1);
			
			//检索使用的优惠券的使用范围，coupon_order_id == 0表示没有使用优惠券
			int i;
			int flag = 0;
			if(coupon_order_id != 0) {
				sql = "select merchant_name,moneyOff_much,coupon_id from user_moneyOffHold where order_id = ? and user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, coupon_order_id);
				pst.setString(2, BeanUser.currentLoginUser.getUser_name());
				rs = pst.executeQuery();
				rs.next();
				BC.setAdd_user(rs.getString(1));
				couponOff = rs.getDouble(2);
				BC.setCoupon_id(rs.getInt(3));
				rs.close();
				pst.close();
				//检测使用的优惠券与商家是否匹配
				if("root".equals(BC.getAdd_user())) {
					flag = 1;
					if(merchant_count != 0)
						rootCoupon_off = couponOff / merchant_count;
				}
				else
					for(i = 0;i < merchantResult.size();i++) {
						if(BC.getAdd_user().equals(merchantResult.get(i).getMerchant_name())) {
							flag = 1;
							break;
						}
					}
				if(flag == 0)
					throw new BusinessException("该优惠券为" + BC.getAdd_user() + "专属");
				//检测满减方案与优惠券是否可叠加
				if(moneyOffWay_order_id != 0) {
					sql = "select moneyOff_overlay,moneyOff_much,moneyOff_OffMuch,moneyOff_id from merchant_moneyOffWay where order_id = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, moneyOffWay_order_id);
					rs = pst.executeQuery();
					rs.next();
					if("否".equals(rs.getString(1)))
						throw new BusinessException("该满减不可与优惠券共享");
					else {
						moneyOff_much = rs.getDouble(2);
						moneyOff_OffMuch = rs.getDouble(3);
						moneyOff_id = rs.getInt(4);
					}
					rs.close();
					pst.close();
				}
			} else {
				//获取使用的满减方案的满减信息
				if(moneyOffWay_order_id != 0) {
					rs.close();
					pst.close();
					sql = "select moneyOff_overlay,moneyOff_much,moneyOff_OffMuch from merchant_moneyOffWay where order_id = ?";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, moneyOffWay_order_id);
					rs = pst.executeQuery();
					rs.next();
					moneyOff_much = rs.getDouble(2);
					moneyOff_OffMuch = rs.getDouble(3);
				}
			}
			//检测优惠金额是否大于消费总价
			if(couponOff != 0) {
				double sum = 0;
				sql = "select goods_price,num from user_shoppingCar where merchant_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, BC.getAdd_user());
				rs = pst.executeQuery();
				while(rs.next()) {
					sum = sum + rs.getDouble(1) * rs.getInt(2);
				}
				if(couponOff > sum) 
					throw new BusinessException("优惠金额大于商品总金额，请重新选择优惠券");
				rs.close();
				pst.close();
			}
			//检测满减方案是否可使用
			if(moneyOff_much != 0 || moneyOff_OffMuch != 0) {
				if(origin_money < moneyOff_much) 
					throw new BusinessException("商品总金额未达到满减条件");
			}
			if(merchant_count != 0)
				moneyOff_OffMuch = moneyOff_OffMuch / (merchant_count * 1.0);
			
			//生成订单号
			int order_id = 0;
			sql = "select count(order_id) from user_order";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next())
				order_id = rs.getInt(1) + 1;
			else
				order_id = 1;
			rs.close();
			pst.close();
			//按商家生成订单
			for(i = 0;i < merchantResult.size();i++) {
				sql = "select goods_price,num from user_shoppingCar where merchant_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, merchantResult.get(i).getMerchant_name());
				rs = pst.executeQuery();
				while(rs.next()) 
					origin_money += rs.getDouble(1) * rs.getInt(2);
				rs.close();
				pst.close();
				real_money = 0;
				if(coupon_order_id != 0 && moneyOffWay_order_id != 0) {
					if(merchantResult.get(i).getMerchant_name().equals(BC.getAdd_user())) {
						real_money = origin_money - couponOff - moneyOff_OffMuch;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,moneyOff_id,uscoupon_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setInt(6, moneyOff_id);
						pst.setInt(7, BC.getCoupon_id());
						pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(10, address_id);
						pst.setString(11, "等待接单");	
						pst.execute();
						pst.close();
					} else if("root".equals(BC.getAdd_user())) {
						pst.close();
						real_money = origin_money - rootCoupon_off - moneyOff_OffMuch;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,moneyOff_id,uscoupon_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setInt(6, moneyOff_id);
						pst.setInt(7, BC.getCoupon_id());
						pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(10, address_id);
						pst.setString(11, "等待接单");	
						pst.execute();
						pst.close();
					} else {
						pst.close();
						real_money = origin_money - moneyOff_OffMuch;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,moneyOff_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setInt(6, moneyOff_id);
						pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(9, address_id);
						pst.setString(10, "等待接单");	
						pst.execute();
						pst.close();
					}
				} else if(coupon_order_id != 0 && moneyOffWay_order_id == 0) {
					if(merchantResult.get(i).getMerchant_name().equals(BC.getAdd_user())) {
						pst.close();
						real_money = origin_money - couponOff;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,uscoupon_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setInt(6, BC.getCoupon_id());
						pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(9, address_id);
						pst.setString(10, "等待接单");	
						pst.execute();
						pst.close();
					} else if("root".equals(BC.getAdd_user())){
						pst.close();
						real_money = origin_money - rootCoupon_off;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,uscoupon_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setInt(6, BC.getCoupon_id());
						pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(9, address_id);
						pst.setString(10, "等待接单");	
						pst.execute();
						pst.close();
					} else {
						pst.close();
						real_money = origin_money;
						if(real_money < 0)
							real_money = 0;
						sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(sql);
						pst.setInt(1, order_id);
						pst.setString(2, merchantResult.get(i).getMerchant_name());
						pst.setString(3, BeanUser.currentLoginUser.getUser_name());
						pst.setDouble(4, origin_money);
						pst.setDouble(5, real_money);
						pst.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
						pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
						pst.setInt(8, address_id);
						pst.setString(9, "等待接单");	
						pst.execute();
						pst.close();
					}
				} else if(coupon_order_id == 0 && moneyOffWay_order_id != 0) {
					pst.close();
					real_money = origin_money - moneyOff_OffMuch;
					if(real_money < 0)
						real_money = 0;
					sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,moneyOff_id,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, order_id);
					pst.setString(2, merchantResult.get(i).getMerchant_name());
					pst.setString(3, BeanUser.currentLoginUser.getUser_name());
					pst.setDouble(4, origin_money);
					pst.setDouble(5, real_money);
					pst.setInt(6, moneyOff_id);
					pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
					pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
					pst.setInt(9, address_id);
					pst.setString(10, "等待接单");
					pst.execute();
					pst.close();
				} else {
					pst.close();
					real_money = origin_money;
					if(real_money < 0)
						real_money = 0;
					sql = "insert into user_order(order_id,merchant_name,user_name,origin_money,real_money,order_time,require_time,address_id,order_state) values(?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, order_id);
					pst.setString(2, merchantResult.get(i).getMerchant_name());
					pst.setString(3, BeanUser.currentLoginUser.getUser_name());
					pst.setDouble(4, origin_money);
					pst.setDouble(5, real_money);
					pst.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
					pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis() + 1800000L));
					pst.setInt(8, address_id);
					pst.setString(9, "等待接单");	
					pst.execute();
					pst.close();
				}
				order_id++;
			}
			//将购物车商品按订单号加入订单详情 
			sql = "insert into user_orderDetail(order_id,goods_name,order_num,order_price) select user_order.order_id,goods_name,num,goods_price from user_shoppingCar,user_order where user_shoppingCar.merchant_name = user_order.merchant_name and user_order.user_name = user_shoppingCar.user_name order by user_order.order_id";
			pst = conn.prepareStatement(sql);
			pst.execute();
			pst.close();
		
			sql = "update merchant_goodsDetails set goods_num = goods_num - ? where goods_name = ? and merchant_name = ?";
			for(i = 0;i < result.size();i++) {
				pst = conn.prepareStatement(sql);
				pst.setInt(1, result.get(i).getNum());
				pst.setString(2, result.get(i).getGoods_name());
				pst.setString(3, result.get(i).getMerchant_name());
				pst.execute();
				pst.close();
			}
			
			sql = "update merchant_message set total_sales = total_sales + ? where merchant_name = ?";
			for(i = 0;i < result.size();i++) {
				pst = conn.prepareStatement(sql);
				pst.setInt(1, result.get(i).getNum());
				pst.setString(2, result.get(i).getMerchant_name());
				pst.execute();
				pst.close();
			}
			
			sql = "delete from user_shoppingCar where user_name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			pst.execute();
			pst.close();
			
			if(coupon_order_id != 0) {
				sql = "delete from user_moneyOffHold where user_name = ? and order_id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, BeanUser.currentLoginUser.getUser_name());
				pst.setInt(2, coupon_order_id);
				pst.execute();
				pst.close();
				
				sql = "update user_moneyOffHold set order_id = -order_id where order_id > ? and user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, coupon_order_id);
				pst.setString(2, BeanUser.currentLoginUser.getUser_name());
				pst.execute();
				pst.close();
				
				sql = "update user_moneyOffHold set order_id = -1 - order_id where order_id < 0 and user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, BeanUser.currentLoginUser.getUser_name());
				pst.execute();
				pst.close();
			}
			
			for(i = 0;i < merchantResult.size();i++) {
				sql = "update user_consumeCount set finished_consume = finished_consume + 1 where merchant_name = ? and user_name = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, merchantResult.get(i).getMerchant_name());
				pst.setString(2, BeanUser.currentLoginUser.getUser_name());
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

	
	
	
}