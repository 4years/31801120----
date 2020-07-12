package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.util.BaseException;

public class FrmRootAddGoods extends JFrame implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelGoodsName = new JLabel("��Ʒ���ƣ�");
	private JLabel labelGoodsPrice = new JLabel("��Ʒ���ۣ�");
	private JLabel labelVipPrice = new JLabel("��Ա���ۣ�");
	private JLabel labelGoodsNum = new JLabel("������      ");
	
	private JTextField edtGoodsName = new JTextField(18);
	private JTextField edtGoodsPrice = new JTextField(18);
	private JTextField edtVipPrice = new JTextField(18);
	private JTextField edtGoodsNum = new JTextField(18);
	
	private int kind_id;
	
	public FrmRootAddGoods(BeanGoodsKind curGoodsKind) {
		this.setTitle("�����Ʒ");
		kind_id = curGoodsKind.getKind_id();
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelGoodsName);
		workPane.add(edtGoodsName);
		workPane.add(labelGoodsPrice);
		workPane.add(edtGoodsPrice);
		workPane.add(labelVipPrice);
		workPane.add(edtVipPrice);
		workPane.add(labelGoodsNum);
		workPane.add(edtGoodsNum);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 220);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if(e.getSource() == this.btnOk) {
			String goods_name = this.edtGoodsName.getText();
			double goods_price = Double.valueOf(this.edtGoodsPrice.getText());
			double goods_sales = Double.valueOf(this.edtVipPrice.getText());
			int goods_num = Integer.valueOf(this.edtGoodsNum.getText());
			try {
				TakeawayUtil.goodsManager.addGoods(kind_id, goods_name, goods_price, goods_sales, goods_num);
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
				this.setVisible(false);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
