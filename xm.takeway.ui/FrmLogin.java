package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanKnight;
import xm.takeway.model.BeanMerchant;
import xm.takeway.model.BeanRoot;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;
import xm.takeway.util.BusinessException;

public class FrmLogin extends JDialog implements ActionListener {
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	
	private JButton btnLogin = new JButton("登陆");
	private JButton btnCancel = new JButton("退出");
	private JButton btnRegister = new JButton("注册");
	private JRadioButton jradio1 = new JRadioButton("用户");
	private JRadioButton jradio2 = new JRadioButton("商家");
	private JRadioButton jradio3 = new JRadioButton("骑手");
	private JRadioButton jradio4 = new JRadioButton("管理员");
	
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelIDChose = new JLabel("身份选择：");
	private JTextField edtUserName = new JTextField(22);
	private JPasswordField edtPwd = new JPasswordField(22);
	
	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserName);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		
		ButtonGroup group = new ButtonGroup();
		group.add(jradio1);
		group.add(jradio2);
		group.add(jradio3);
		group.add(jradio4);
		workPane.add(labelIDChose);
		workPane.add(this.jradio1);
		workPane.add(this.jradio2);
		workPane.add(this.jradio3);
		workPane.add(this.jradio4);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(340, 160);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			if(this.jradio1.isSelected()) {
				String userName = this.edtUserName.getText();
				String pwd = new String(this.edtPwd.getPassword());
				try {
					BeanUser.currentLoginUser = TakeawayUtil.userManager.login(userName, pwd);
				} catch(BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
				FrmUserMain FUM = new FrmUserMain();
				FUM.setVisible(true);
			} else if(this.jradio2.isSelected()) {
				String merchantName = this.edtUserName.getText();
				String pwd = new String(this.edtPwd.getPassword());
				try {
					BeanMerchant.currentLoginMerchant = TakeawayUtil.merchantManager.login(merchantName, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
				FrmMerchantMain FMM = new FrmMerchantMain();
				FMM.setVisible(true);
			} else if(this.jradio3.isSelected()) {
				String knightName = this.edtUserName.getText();
				String pwd = new String(this.edtPwd.getPassword());
				try {
					BeanKnight.currentLoginKnight = TakeawayUtil.knightManager.login(knightName, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
			} else if(this.jradio4.isSelected()){
				String rootName = this.edtUserName.getText();
				String pwd = new String(this.edtPwd.getPassword());
				try {
					BeanRoot.currentLoginRoot = TakeawayUtil.rootManager.login(rootName, pwd);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(false);
				System.out.println(BeanRoot.currentLoginRoot.getRoot_name());
			} else {
				JOptionPane.showMessageDialog(null, "请选择登陆身份");
			}
			
		} else if(e.getSource() == this.btnRegister) {
			if(this.jradio1.isSelected()) {
				FrmUserReg FUR = new FrmUserReg(this,"用户注册",true);
				FUR.setVisible(true);
			} else if(this.jradio2.isSelected()) {
				FrmMerchantReg FMR = new FrmMerchantReg(this,"商家注册",true);
				FMR.setVisible(true);	
			} else if(this.jradio3.isSelected()) {
				FrmKnightReg FKR = new FrmKnightReg(this,"骑手注册",true);
				FKR.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "请选择注册身份");
			}
		}  else if(e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}
}
