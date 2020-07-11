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

public class FrmUserAddAddress extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelProvince = new JLabel("ʡ��");
	private JLabel labelCity = new JLabel("�У�");
	private JLabel labelBlock = new JLabel("����");
	private JLabel labelAddress = new JLabel("��ϸ��ַ��");
	private JLabel labelTel = new JLabel("��ϵ��ʽ��");
	
	private JTextField edtProvince = new JTextField(21);
	private JTextField edtCity = new JTextField(21);
	private JTextField edtBlock = new JTextField(21);
	private JTextField edtAddress = new JTextField(18);
	private JTextField edtTel = new JTextField(18);
	
	public FrmUserAddAddress(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(this.btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelProvince);
		workPane.add(edtProvince);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelBlock);
		workPane.add(edtBlock);
		workPane.add(labelAddress);
		workPane.add(edtAddress);
		workPane.add(labelTel);
		workPane.add(edtTel);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 230);
		//��Ļ������ʾ
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
			String Province = this.edtProvince.getText();
			String City = this.edtCity.getText();
			String Block = this.edtBlock.getText();
			String Address = this.edtAddress.getText();
			String Tel = this.edtTel.getText();
			try {
				TakeawayUtil.userManager.userAddAddress(Province, City, Block, Address,Tel);
				JOptionPane.showMessageDialog(null, "��ַ��ӳɹ�");
				this.setVisible(false);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
					
		}
		
	}
	
	


}
