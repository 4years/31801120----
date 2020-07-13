package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;

public class FrmOrderComment extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelOrder_id = new JLabel("������ţ�");
	private JLabel labelMerchant_name = new JLabel("�̼�����");
	private JLabel labelKnight_name = new JLabel("��������");
	private JLabel labelSettle = new JLabel("�����");
	private JLabel labelOrder_state = new JLabel("����״̬��");
	private JLabel labelComment = new JLabel("�������ۣ�");
	private JLabel labelHollow = new JLabel("            ");
	
	private JLabel edtOrder_id = null;
	private JLabel edtMerchant_name = null;
	private JLabel edtKnight_name = null;
	private JLabel edtSettle = null;
	private JLabel edtOrder_state = null;
	private JComboBox Comment = new JComboBox();
	private BeanOrderMessage curOrder = null;
	
	public FrmOrderComment(BeanOrderMessage Order) {
		curOrder = Order;
		edtOrder_id = new JLabel(String.valueOf(curOrder.getOrder_id()) + "                                  ");
		edtMerchant_name = new JLabel(curOrder.getMerchant_name() + "                        ");
		edtKnight_name = new JLabel(curOrder.getKnight_name() + "                           ");
		edtSettle = new JLabel(String.valueOf(curOrder.getReal_money()) + "                                ");
		edtOrder_state = new JLabel(curOrder.getOrder_state() + "                           ");
		
		this.setTitle("��������");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelOrder_id);
		workPane.add(edtOrder_id);
		workPane.add(labelMerchant_name);
		workPane.add(edtMerchant_name);
		workPane.add(labelKnight_name);
		workPane.add(edtKnight_name);
		workPane.add(labelSettle);
		workPane.add(edtSettle);
		workPane.add(labelOrder_state);
		workPane.add(edtOrder_state);
		workPane.add(labelComment);
		Comment.addItem("δ����");
		Comment.addItem("����");
		Comment.addItem("����");
		workPane.add(Comment);
		workPane.add(labelHollow);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 210);
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
			try {
				TakeawayUtil.orderManager.KnightComment(curOrder, Comment.getSelectedItem().toString());
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
}
