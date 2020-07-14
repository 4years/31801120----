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
	
	private JButton btnSettlement = new JButton("Ω·À„");
	private JButton btnCancel = new JButton("»°œ˚");
	
	private JLabel labelTotalPrice = new JLabel("◊‹Ω∂Ó£∫ ");
	private JLabel labelHollow = new JLabel("                                                                    ");
	private JLabel labelHollow2 = new JLabel("                          ");
	private JLabel labelHollow3 = new JLabel("                 ");
	private JLabel labelAddress = new JLabel("≈‰ÀÕµÿ÷∑£∫");
	private JLabel labelCoupon = new JLabel("”≈ª›»Ø£∫");
	private JLabel labelmoneyOffWay = new JLabel("¬˙ºı∑Ω∞∏£∫");
	
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
			sum = (double)Math.round(sum * 100) / 100;
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
			JOptionPane.showMessageDialog(null, e1.getMessage(), "¥ÌŒÛ",JOptionPane.ERROR_MESSAGE);
			return;
		}
		address.addItem("---«Î—°‘Ò---");
		int i;
		for(i = 0;i < addressResult.size();i++) {
			address.addItem(addressResult.get(i).getOrder_id() + ". " + addressResult.get(i).getProvince() + addressResult.get(i).getCity() + addressResult.get(i).getBlock() + addressResult.get(i).getAddress() + " " + addressResult.get(i).getUser_tel());
		}
		workPane.add(address);
		
		workPane.add(labelCoupon);
		List<BeanCoupon> couponResult = new ArrayList<BeanCoupon>();
		try {
			couponResult = TakeawayUtil.couponManager.UserHoldloadAll();
		} catch(BaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "¥ÌŒÛ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Coupon.addItem("---«Î—°‘Ò---");
		for(i = 0;i < couponResult.size();i++) {
			Coupon.addItem(couponResult.get(i).getOrder_id() + ". " + couponResult.get(i).getMoneyOff_much() + "‘™”≈ª›»Ø (" + couponResult.get(i).getUseArea() + ")");
		}
		workPane.add(Coupon);
		workPane.add(labelHollow3);
		
		workPane.add(labelmoneyOffWay);
		List<BeanMoneyOff> moneyOffResult = new ArrayList<BeanMoneyOff>();
		try {
			moneyOffResult = TakeawayUtil.rootManager.moneyOffWayloadAll();
		} catch(BaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "¥ÌŒÛ",JOptionPane.ERROR_MESSAGE);
			return;
		}
		moneyOffWay.addItem("---«Î—°‘Ò---");
		for(i = 0;i < moneyOffResult.size();i++) {
			if(" «".equals(moneyOffResult.get(i).getMoneyOff_overlay()))
				moneyOffWay.addItem(moneyOffResult.get(i).getOrder_id() + ". ¬˙" + moneyOffResult.get(i).getMoneyOff_much() + "ºı" + moneyOffResult.get(i).getMoneyOff_OffMuch() + " ø…µ˛º”");
			else
				moneyOffWay.addItem(moneyOffResult.get(i).getOrder_id() + ". ¬˙" + moneyOffResult.get(i).getMoneyOff_much() + "ºı" + moneyOffResult.get(i).getMoneyOff_OffMuch() + " ≤ªø…µ˛º”");
		}
		workPane.add(moneyOffWay);
		workPane.add(labelHollow2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 180);
		// ∆¡ƒªæ”÷–œ‘ æ
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
				int address_order_id = 0;
				if("---«Î—°‘Ò---".equals(this.address.getSelectedItem().toString())) {
					JOptionPane.showMessageDialog(null, "«Î—°‘Ò≈‰ÀÕµÿ÷∑", "¥ÌŒÛ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					while(String.valueOf(this.address.getSelectedItem()).charAt(i) != '.') {
						str = str + String.valueOf(this.address.getSelectedItem()).charAt(i);
						i++;
					}
					try {
						address_order_id = Integer.parseInt(str.replaceAll(" ", ""));
					} catch (NumberFormatException e1) {
					    e1.printStackTrace();
					}
				}
				
				String str1 = "";
				i = 0;
				int coupon_order_id = 0;
				if("---«Î—°‘Ò---".equals(this.Coupon.getSelectedItem().toString()));
				else {
					while(String.valueOf(this.Coupon.getSelectedItem()).charAt(i) != '.') {
						str1 = str1 + String.valueOf(this.Coupon.getSelectedItem()).charAt(i);
						i++;
					}
					try {
						coupon_order_id = Integer.parseInt(str1.replace(" ", ""));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					}
				}
				
				String str2 = "";
				i = 0;
				int moneyOffWay_order_id = 0;
				if("---«Î—°‘Ò---".equals(this.moneyOffWay.getSelectedItem().toString()));
				else {
					while(String.valueOf(this.moneyOffWay.getSelectedItem()).charAt(i) != '.') {
						str2 = str2 + String.valueOf(this.moneyOffWay.getSelectedItem()).charAt(i);
						i++;
					}
					try {
						moneyOffWay_order_id = Integer.parseInt(str2.replace(" ", ""));
					} catch(NumberFormatException e1) {
						e1.printStackTrace();
					}
				}		
				TakeawayUtil.shoppingCarManager.settlementShoppingCar(address_order_id,coupon_order_id,moneyOffWay_order_id);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"¥ÌŒÛ",JOptionPane.ERROR_MESSAGE);
				return;
			}	
		}	
	}
}
