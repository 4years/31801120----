package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class FrmUserSettlement extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private JButton btnSettlement = new JButton("结算");
	private JButton btnCancel = new JButton("取消");
	
	private JLabel labelTotalPrice = new JLabel("总金额：");
	
	public double total_Price() {
		double sum = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select goods_price,num from user_shoppingCar where user_name = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) 
				sum += rs.getDouble(1) * rs.getInt(2);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.rollback();
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return sum;
	}
	
	private JLabel labelTotal = new JLabel(String.valueOf(this.total_Price()));
	
	public FrmUserSettlement(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnSettlement);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelTotalPrice);
		workPane.add(labelTotal);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 160);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnSettlement.addActionListener(this);
		btnCancel.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if(e.getSource() == this.btnSettlement) {
			/*
			try {
				TakeawayUtil.shoppingCarManager.settlementShoppingCar();
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			*/
		}
		
	}
	

}
