package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public class FrmMerchantReg extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("注册");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelUser = new JLabel("商家名：");
	private JLabel labelRank = new JLabel("星级：   ");
	private JLabel labelHollow = new JLabel("                           ");
	private JLabel labelavgC = new JLabel("人均消费：");
	private JLabel labelToS = new JLabel("总销量：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelPwd2 = new JLabel("确认密码：");
	
	private JTextField edtUser = new JTextField(19);
	JComboBox jcb = new JComboBox();
	private JTextField edtavgC = new JTextField(18);
	private JTextField edtToS = new JTextField(19);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(18);
	
	public FrmMerchantReg(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUser);
		workPane.add(labelRank);
		jcb.addItem("--请选择--");
		jcb.addItem("1星");
		jcb.addItem("2星");
		jcb.addItem("3星");
		jcb.addItem("4星");
		jcb.addItem("5星");
		workPane.add(jcb);
		workPane.add(labelHollow);
		workPane.add(labelavgC);
		workPane.add(edtavgC);
		workPane.add(labelToS);
		workPane.add(edtToS);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 250);
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
		if(e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if(e.getSource() == this.btnOk) {
			String merchantName = this.edtUser.getText();
			String merchantRank = String.valueOf(this.jcb.getSelectedItem());
			String pwd = this.edtPwd.getText();
			String pwd2 = this.edtPwd2.getText();
			try {
				int avg_consume = Integer.parseInt(this.edtavgC.getText());
				int total_sales = Integer.parseInt(this.edtToS.getText());
				BeanMerchant merchant = TakeawayUtil.merchantManager.reg(merchantName, merchantRank, avg_consume, total_sales, pwd, pwd2);
				JOptionPane.showMessageDialog(null, "注册成功");
				this.setVisible(false);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			} catch(NumberFormatException e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
