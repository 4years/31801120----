package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.util.BaseException;

public class FrmAddGoodsNum extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelHollow = new JLabel("                                                                 ");
	private JLabel labelNum = new JLabel("数量：");
	private JTextField edtNum = new JTextField(16);
	
	private int kind_id = 0;
	BeanGoodsDetails curGoods = null;
	public FrmAddGoodsNum(BeanGoodsKind curGoodsKind,BeanGoodsDetails Goods) {
		this.setTitle("进货数量");
		kind_id = curGoodsKind.getKind_id();
		curGoods = Goods;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelHollow);
		workPane.add(labelNum);
		workPane.add(edtNum);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(270, 140);
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
			FrmMerchantAddGoods FMAG = new FrmMerchantAddGoods();
			FMAG.setVisible(true);
		} else if(e.getSource() == this.btnOk) {
			int num = Integer.valueOf(this.edtNum.getText());
			try {
				TakeawayUtil.goodsManager.addMerchantGoods(kind_id, curGoods, num);
				JOptionPane.showMessageDialog(null, "进货成功");
				this.setVisible(false);
				FrmMerchantAddGoods FMAG = new FrmMerchantAddGoods();
				FMAG.setVisible(true);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} 
	}
}