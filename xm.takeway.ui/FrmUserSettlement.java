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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanCoupon;
import xm.takeway.model.BeanMoneyOff;
import xm.takeway.model.BeanUser;
import xm.takeway.model.BeanUserAddress;
import xm.takeway.util.BaseException;
import xm.takeway.util.DBUtil;
import xm.takeway.util.DbException;

public class FrmUserSettlement extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private JButton btnSettlement = new JButton("结算");
	private JButton btnCancel = new JButton("取消");
	
	private JLabel labelTotalPrice = new JLabel("总金额： ");
	private JLabel labelHollow = new JLabel("                                                                    ");
	private JLabel labelHollow2 = new JLabel("                          ");
	private JLabel labelAddress = new JLabel("收货地址：");
	private JLabel labelCoupon = new JLabel("优惠券：");
	private JLabel labelmoneyOffWay = new JLabel("满减方案：");
	
	private JComboBox address = new JComboBox();
	private JComboBox Coupon = new JComboBox();
	private JComboBox moneyOffWay = new JComboBox();
	
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
		workPane.add(labelHollow);
		
		workPane.add(labelAddress);
		List<BeanUserAddress> addressResult = new ArrayList<BeanUserAddress>();
		try {
			addressResult = TakeawayUtil.userManager.loadUserAddress();
		} catch(BaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		address.addItem("---请选择---");
		int i;
		for(i = 0;i < addressResult.size();i++) {
			address.addItem(addressResult.get(i).getOrder_id() + " " + addressResult.get(i).getProvince() + addressResult.get(i).getCity() + addressResult.get(i).getBlock() + addressResult.get(i).getAddress() + " " + addressResult.get(i).getUser_tel());
		}
		workPane.add(address);
		
		workPane.add(labelCoupon);
		List<BeanCoupon> couponResult = new ArrayList<BeanCoupon>();
		
		workPane.add(labelmoneyOffWay);
		List<BeanMoneyOff> moneyOffResult = new ArrayList<BeanMoneyOff>();
		try {
			moneyOffResult = TakeawayUtil.rootManager.moneyOffWayloadAll();
		} catch(BaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		moneyOffWay.addItem("---请选择---");
		for(i = 0;i < moneyOffResult.size();i++) {
			if("是".equals(moneyOffResult.get(i).getMoneyOff_overlay()))
				moneyOffWay.addItem(moneyOffResult.get(i).getOrder_id() + ". 满" + moneyOffResult.get(i).getMoneyOff_much() + "减" + moneyOffResult.get(i).getMoneyOff_OffMuch() + " 可叠加");
			else
				moneyOffWay.addItem(moneyOffResult.get(i).getOrder_id() + ". 满" + moneyOffResult.get(i).getMoneyOff_much() + "减" + moneyOffResult.get(i).getMoneyOff_OffMuch() + " 不可叠加");
		}
		workPane.add(moneyOffWay);
		workPane.add(labelHollow2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 180);
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
			try {
				String str = "";
				int i = 0;
				while(String.valueOf(this.address.getSelectedItem()).charAt(i) != ' ') {
					str = str + String.valueOf(this.address.getSelectedItem()).charAt(i);
					i++;
				}
				int address_id = 0;
				try {
					address_id = Integer.parseInt(str.replaceAll(" ", ""));
				} catch (NumberFormatException e1) {
				    e1.printStackTrace();
				}
				TakeawayUtil.shoppingCarManager.settlementShoppingCar(address_id);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}	
		}	
	}
}
