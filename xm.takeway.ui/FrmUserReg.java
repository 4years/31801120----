package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;

public class FrmUserReg extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("注册");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelSex = new JLabel("性别：");
	private JRadioButton jradio1 = new JRadioButton("男");
	private JRadioButton jradio2 = new JRadioButton("女");
	private JLabel labelHollow = new JLabel("                                      ");
	private JLabel labelTel = new JLabel("电话：");
	private JLabel labelEmail = new JLabel("邮箱：");
	private JLabel labelCity = new JLabel("城市：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelPwd2 = new JLabel("确认密码：");
	
	private JTextField edtUserName = new JTextField(20);
	private JTextField edtTel = new JTextField(20);
	private JTextField edtEmail = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(18);
	
	public FrmUserReg(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserName);
		workPane.add(labelSex);
		ButtonGroup group = new ButtonGroup();
		group.add(jradio1);
		group.add(jradio2);
		workPane.add(this.jradio1);
		workPane.add(this.jradio2);
		workPane.add(labelHollow);
		workPane.add(labelTel);
		workPane.add(edtTel);
		workPane.add(labelEmail);
		workPane.add(edtEmail);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 250);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if(e.getSource() == this.btnOk){
			String username = this.edtUserName.getText();
			String userSex;
			if(this.jradio1.isSelected())
				userSex = this.jradio1.getText();
			else
				userSex = this.jradio2.getText();
			String userTel = this.edtTel.getText();
			String email = this.edtEmail.getText();
			String userCity = this.edtCity.getText();
			String pwd1 = new String(this.edtPwd.getPassword());
			String pwd2 = new String(this.edtPwd2.getPassword());
			try {
				BeanUser user = TakeawayUtil.userManager.reg(username,userSex,userTel,email,userCity,pwd1,pwd2);
				JOptionPane.showMessageDialog(null, "注册成功");
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}	
		}	
		
	}
}
