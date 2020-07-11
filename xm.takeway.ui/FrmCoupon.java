package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.util.BaseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmCoupon extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确认");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelmoneyOff = new JLabel("优惠金额：");
	private JLabel labelcount = new JLabel("需要集单数：");
	private JLabel labelbeginDate = new JLabel("优惠券生效日期：");
	private JLabel labelendDate = new JLabel("优惠券失效日期：");
	private JLabel labelCouponNum = new JLabel("优惠券数量：");
	
	private JTextField edtmoneyOff = new JTextField(18);
	private JTextField edtcount = new JTextField(17);
	private JTextField edtbeginDate = new JTextField(15);
	private JTextField edtendDate = new JTextField(15);
	private JTextField edtCouponNum = new JTextField(17);
	
	public FrmCoupon(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(this.btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelmoneyOff);
		workPane.add(edtmoneyOff);
		workPane.add(labelcount);
		workPane.add(edtcount);
		workPane.add(labelbeginDate);
		workPane.add(edtbeginDate);
		workPane.add(labelendDate);
		workPane.add(edtendDate);
		workPane.add(labelCouponNum);
		workPane.add(edtCouponNum);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 230);
		//屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
			(int) (height - this.getHeight()) / 2);
		this.validate();
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if(e.getSource() == this.btnOk) {
			double moneyOff = Double.valueOf(this.edtmoneyOff.getText());
			int consumeCount = Integer.valueOf(this.edtcount.getText());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date beginDate = null;
			Date endDate = null;
			int couponNum = Integer.valueOf(this.edtCouponNum.getText());
			try {
				beginDate = sdf.parse(this.edtbeginDate.getText());
				endDate = sdf.parse(this.edtendDate.getText());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				java.sql.Date SbeginDate = new java.sql.Date(beginDate.getTime());
				java.sql.Date SendDate = new java.sql.Date(endDate.getTime());
				TakeawayUtil.couponManager.addCoupon(moneyOff,consumeCount,SbeginDate,SendDate,couponNum);
				JOptionPane.showMessageDialog(null, "添加成功");
				this.setVisible(false);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

}
