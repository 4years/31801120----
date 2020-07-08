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
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public class FrmMerchantModifyPwd extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("修改");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelOldPwd = new JLabel("原密码：");
	private JLabel labelNewPwd = new JLabel("新密码：");
	private JLabel labelNewPwd2 = new JLabel("确认密码：");
	
	private JTextField edtOldPwd = new JTextField(19);
	private JPasswordField edtNewPwd = new JPasswordField(19);
	private JPasswordField edtNewPwd2 = new JPasswordField(18);
	
	public FrmMerchantModifyPwd(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelOldPwd);
		workPane.add(edtOldPwd);
		workPane.add(labelNewPwd);
		workPane.add(edtNewPwd);
		workPane.add(labelNewPwd2);
		workPane.add(edtNewPwd2);
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
		if(e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if(e.getSource() == this.btnOk){
			String OldPwd = this.edtOldPwd.getText();
			String NewPwd = this.edtNewPwd.getText();
			String NewPwd2 = this.edtNewPwd2.getText();
			try {
				TakeawayUtil.merchantManager.changpwd(BeanMerchant.currentLoginMerchant, OldPwd, NewPwd, NewPwd2);
				JOptionPane.showMessageDialog(null,"修改成功");
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

}
