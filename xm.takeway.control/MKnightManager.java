package xm.takeway.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import xm.takeway.itf.KnightManager;
import xm.takeway.model.BeanKnight;
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
}
