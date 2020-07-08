package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
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
import xm.takeway.util.BaseException;

public class FrmAddGoods extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelKind = new JLabel("商品类别：");
	private JLabel labelGoodsName = new JLabel("商品名称：");
	private JLabel labelGoodsPrice = new JLabel("商品单价：");
	private JLabel labelVipPrice = new JLabel("会员单价：");
	private JLabel labelGoodsNum = new JLabel("数量：      ");
	
	private JTextField edtKind = new JTextField(18);
	private JTextField edtGoodsName = new JTextField(18);
	private JTextField edtGoodsPrice = new JTextField(18);
	private JTextField edtVipPrice = new JTextField(18);
	private JTextField edtGoodsNum = new JTextField(18);
	
	public FrmAddGoods(JFrame f, String s, boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelKind);
		workPane.add(edtKind);
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
		// 屏幕居中显示
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
			return;
		}
		else if(e.getSource() == this.btnOk){
			int kind_id = Integer.valueOf(this.edtKind.getText());
			String goods_name = this.edtGoodsName.getText();
			double goods_price = Double.valueOf(this.edtGoodsPrice.getText());
			double goods_sales = Double.valueOf(this.edtVipPrice.getText());
			int goods_num = Integer.valueOf(this.edtGoodsNum.getText());
			try {
				TakeawayUtil.goodsManager.addGoods(kind_id, goods_name, goods_price, goods_sales,goods_num);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

}
