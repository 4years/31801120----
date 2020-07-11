package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public class FrmAddShoppingCar extends JDialog implements ActionListener {
	public int flag = 0;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("加入购物车");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelNum = new JLabel("购买数量");
	private JTextField edtNum = new JTextField(18);

	public FrmAddShoppingCar(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelNum);
		workPane.add(edtNum);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 180);
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
		if(e.getSource() == this.btnOk) {
			if(this.edtNum.getText() == null || "".equals(this.edtNum.getText())) {
				JOptionPane.showMessageDialog(null, "请输入数量", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			flag = 1;
			this.setVisible(false);
		} else if(e.getSource() == this.btnCancel) {
			flag = 2;
			this.setVisible(false);
		}	
	}
	
	public int loadFlag() {
		return flag;
	}
	
	public int loadNum() {
		return Integer.valueOf(this.edtNum.getText());
	}
}
