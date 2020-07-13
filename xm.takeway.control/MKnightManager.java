package xm.takeway.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import xm.takeway.itf.KnightManager;
import xm.takeway.model.BeanKnight;
import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class MKnightManager implements KnightManager {
	public BeanKnight reg(String knight_name,String pwd,String pwd2) throws BaseException {
		if(knight_name.equals("root"))
			throw new BusinessException("����������Ϊroot");
		if(knight_name == null || "".equals(knight_name))
			throw new BusinessException("����������Ϊ��");
		if(pwd == null || "".equals(pwd) || pwd2 == null || "".equals(pwd2))
			throw new BusinessException("���벻��Ϊ��");
		if(!(pwd.equals(pwd2)))
			throw new BusinessException("�����������벻һ��");
		BeanKnight BK = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select knight_id from knight_message where knight_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, knight_name);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				throw new BusinessException("���������Ѵ���");
			rs.close();
			pst.close();
			sql = "insert into knight_message(knight_name,knight_pwd,hiredate,knight_rank) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, knight_name);
			pst.setString(2, pwd);
			pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			pst.setString(4, "����");
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
		
		return BK;
	}
	
	public BeanKnight login(String knight_name,String pwd) throws BaseException {
		if(knight_name == null || "".equals(knight_name))
			throw new BusinessException("�û�������Ϊ��");
		if(pwd == null || "".equals(pwd))
			throw new BusinessException("���벻��Ϊ��");
		Connection conn = null;
		BeanKnight BK = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from knight_message where knight_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, knight_name);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(pwd.equals(rs.getString(3))) {
					BK = new BeanKnight();
					BK.setKnight_id(rs.getInt(1));
					BK.setKnight_name(rs.getString(2));
					BK.setKnight_pwd(rs.getString(3));
					BK.setKnight_hiredate(rs.getDate(4));
					BK.setKnight_rank(rs.getString(5));
				}
				else
					throw new BusinessException("�������");
			}
			else 
				throw new BusinessException("�����ֲ�����");
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
		return BK;
	}
	
	public void GetOrder(BeanOrderMessage curOrder) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(knight_id) from knight_income where knight_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, BeanKnight.currentLoginKnight.getKnight_id());
			java.sql.ResultSet rs = pst.executeQuery();
			int count = 0;
			if(rs.next())
				count = rs.getInt(1);
			rs.close();
			pst.close();
			
			double income = 0;
			if(count < 100)
				income = 2;
			else if(count < 300)
				income = 3;
			else if(count < 450)
				income = 5;
			else if(count < 500)
				income = 6;
			else if(count < 550)
				income = 6.5;
			else if(count < 650)
				income = 7.5;
			else
				income = 8.5;
			if("����".equals(BeanKnight.currentLoginKnight.getKnight_rank()))
				income = income + 0.5;
			
			sql = "insert into knight_income(knight_id,order_id,order_time,income) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, BeanKnight.currentLoginKnight.getKnight_id());
			pst.setInt(2, curOrder.getOrder_id());
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setDouble(4, income);
			pst.execute();
			pst.close();
			
			sql = "update user_order set knight_name = ?,order_state = ? where order_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BeanKnight.currentLoginKnight.getKnight_name());
			pst.setInt(3, curOrder.getOrder_id());
			pst.setString(2, "������");
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
	
	static int flag = 0;
	
	public void checkMonth() throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select day(now())";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			int day = 0;
			if(rs.next())
				day = rs.getInt(1);
			rs.close();
			pst.close();
			if(day == 28)
				flag = 1;
			if(day == 1 && flag ==1) {
				sql = "delete from knight_income";
				pst = conn.prepareStatement(sql);
				pst.execute();
				pst.close();
				flag = 0;
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
	
	public void arriveOrder(BeanOrderMessage curOrder) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user_order set order_state = ? where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "���ʹ�");
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
}
