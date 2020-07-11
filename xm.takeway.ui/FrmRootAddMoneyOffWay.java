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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.util.BaseException;

public class FrmRootAddMoneyOffWay extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelHollow = new JLabel("                                                                ");
	private JLabel labelSatisfy = new JLabel("�� "); 
	private JLabel labelOff = new JLabel(" �� ");
	private JLabel labelOverlay = new JLabel("�Ƿ�����Ż�ȯ����   ");
	
	private JTextField edtSatisfy = new JTextField(6);
	private JTextField edtOff = new JTextField(6);
	
	private JRadioButton jradio1 = new JRadioButton("��");
	private JRadioButton jradio2 = new JRadioButton("��");
	
	public FrmRootAddMoneyOffWay(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(this.btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelHollow);
		workPane.add(labelSatisfy);
		workPane.add(edtSatisfy);
		workPane.add(labelOff);
		workPane.add(edtOff);
		workPane.add(labelOverlay);
		ButtonGroup group = new ButtonGroup();
		group.add(jradio1);
		group.add(jradio2);
		workPane.add(jradio1);
		workPane.add(jradio2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 160);
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
			if(this.edtOff.getText() == null || "".equals(this.edtOff.getText()) || this.edtSatisfy.getText() == null || "".equals(this.edtSatisfy.getText())) {
				JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ��", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			double moneyOff_much = Double.valueOf(this.edtSatisfy.getText());
			double moneyOff_OffMuch = Double.valueOf(this.edtOff.getText());
			String moneyOff_overlay = null;
			if(this.jradio1.isSelected()) {
				moneyOff_overlay = this.jradio1.getText();
			} else if(this.jradio2.isSelected()) {
				moneyOff_overlay = this.jradio2.getText();
			} else {
				JOptionPane.showMessageDialog(null, "��ѡ���Ƿ�����Ż�ȯ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.rootManager.addMoneyOffWay(moneyOff_much, moneyOff_OffMuch, moneyOff_overlay);
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

}
