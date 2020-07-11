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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanCoupon;
import xm.takeway.util.BaseException;

public class FrmUserGetCoupon extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("确认领取");
	private Button btnCancel = new Button("取消");
	
	private Object tblUserCouponTitle[] = BeanCoupon.tableUserCouponTitles;
	private Object tblCouponData[][];
	DefaultTableModel tabCouponModel = new DefaultTableModel();
	private JTable dataTableCoupon = new JTable(tabCouponModel);
	
	private BeanCoupon curCoupon = null;
	List<BeanCoupon> allCoupon = null;
	
	public void reloadCouponTable() {
		try {
			allCoupon = TakeawayUtil.couponManager.UserloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblCouponData = new Object[allCoupon.size()][BeanCoupon.tableUserCouponTitles.length];
		for(int i = 0;i < allCoupon.size();i++)
			for(int j = 0;j < BeanCoupon.tableUserCouponTitles.length;j++)
				tblCouponData[i][j] = allCoupon.get(i).UsergetCell(j);
		tabCouponModel.setDataVector(tblCouponData, tblUserCouponTitle);
		this.dataTableCoupon.invalidate();
		this.dataTableCoupon.repaint();
	}
	
	public FrmUserGetCoupon(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new  JScrollPane(this.dataTableCoupon), BorderLayout.CENTER);
		this.setSize(570, 220);
		this.dataTableCoupon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = FrmUserGetCoupon.this.dataTableCoupon.getSelectedRow();
				if(i < 0) {
					return;
				}
				curCoupon = allCoupon.get(i);
			}
		});
		this.reloadCouponTable();
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		}
		else if(e.getSource() == this.btnOk) {
			if(curCoupon == null) {
				JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.couponManager.UserGetCoupon(curCoupon);
				int flag = TakeawayUtil.couponManager.loadGetCouponFlag();
				if(flag == 0 || flag == 1)
				JOptionPane.showMessageDialog(null, "领取成功");
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

}
