package xm.takeway.control;

import java.sql.Connection;
import java.sql.SQLException;

import xm.takeway.itf.RootManager;
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
}
