package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
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
import xm.takeway.util.BaseException;

public class FrmRootModifyPwd extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("修改");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelName = new JLabel("用户名：");
	private JLabel labelNewPwd = new JLabel("新密码：");
	private JLabel labelNewPwd2 = new JLabel("确认密码：");
	
	private JRadioButton Jradio1 = new JRadioButton("用户");
	private JRadioButton Jradio2 = new JRadioButton("商家");
	private JRadioButton Jradio3 = new JRadioButton("骑手");
	
	private JTextField edtName = new JTextField(19);
	private JPasswordField edtNewPwd = new JPasswordField(19);
	private JPasswordField edtNewPwd2 = new JPasswordField(18);
	
	public FrmRootModifyPwd(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelNewPwd);
		workPane.add(edtNewPwd);
		workPane.add(labelNewPwd2);
		workPane.add(edtNewPwd2);
		ButtonGroup group = new ButtonGroup();
		group.add(Jradio1);
		group.add(Jradio2);
		group.add(Jradio3);
		workPane.add(Jradio1);
		workPane.add(Jradio2);
		workPane.add(Jradio3);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 180);
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
			String Name = this.edtName.getText();
			String pwd = new String(this.edtNewPwd.getPassword());
			String pwd2 = new String(this.edtNewPwd2.getPassword());
			if(Jradio1.isSelected()) {
				String identity = Jradio1.getText();
				try {
					TakeawayUtil.rootManager.modifyPwd(Name, pwd, pwd2, identity);
				} catch(BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}	
			} else if(Jradio2.isSelected()) {
				String identity = Jradio2.getText();
				try {
					TakeawayUtil.rootManager.modifyPwd(Name, pwd, pwd2, identity);
				} catch(BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if(Jradio3.isSelected()) {
				String identity = Jradio3.getText();
				try {
					TakeawayUtil.rootManager.modifyPwd(Name, pwd, pwd2, identity);
				} catch(BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "请选择修改身份");
				return;
			}
			JOptionPane.showMessageDialog(null, "修改成功");
			this.setVisible(false);
		}
	}
}
