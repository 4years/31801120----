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

public class FrmShowCoupon extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnDel = new Button("É¾³ýÓÅ»ÝÈ¯");
	private Button btnCancel = new Button("È¡Ïû");
	
	private Object tblCouponTitle[] = BeanCoupon.tableMerchantCouponTitles;
	private Object tblCouponData[][];
	DefaultTableModel tabCouponModel = new DefaultTableModel();
	private JTable dataTableCoupon = new JTable(tabCouponModel);
	
	private BeanCoupon curCoupon = null;
	List<BeanCoupon> allCoupon = null;
	
	public void reloadCouponTable() {
		try {
			allCoupon = TakeawayUtil.couponManager.MerchantloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "´íÎó", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblCouponData = new Object[allCoupon.size()][BeanCoupon.tableMerchantCouponTitles.length];
		for(int i = 0;i < allCoupon.size();i++)
			for(int j = 0;j < BeanCoupon.tableMerchantCouponTitles.length;j++)
				tblCouponData[i][j] = allCoupon.get(i).getCell(j);
		tabCouponModel.setDataVector(tblCouponData, tblCouponTitle);
		this.dataTableCoupon.invalidate();
		this.dataTableCoupon.repaint();
	}
	public FrmShowCoupon(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnDel);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new  JScrollPane(this.dataTableCoupon), BorderLayout.CENTER);
		this.setSize(570, 220);
		this.dataTableCoupon.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmShowCoupon.this.dataTableCoupon.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		curCoupon = allCoupon.get(i);
	    	}
	    });
		this.reloadCouponTable();
		// ÆÁÄ»¾ÓÖÐÏÔÊ¾
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		btnDel.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if(e.getSource() == this.btnDel) {
			try {
				TakeawayUtil.couponManager.DelCoupon(curCoupon);
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "´íÎó",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadCouponTable();
			this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}
	}

}
