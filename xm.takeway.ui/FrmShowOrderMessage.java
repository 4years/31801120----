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
import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;

public class FrmShowOrderMessage  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnDel = new Button("取消订单");
	private Button btnCancel = new Button("取消");
	
	private Object btlOrderTitle[] = BeanOrderMessage.tableOrderMessageTitles;
	private Object tblOrderData[][];
	DefaultTableModel tabOrderModel = new DefaultTableModel();
	private JTable dataTableOrder = new JTable(tabOrderModel);
	
	private BeanOrderMessage curOrder = null;
	List<BeanOrderMessage> allOrder = null;
	
	public void reloadOrderTable() {
		try {
			allOrder = TakeawayUtil.orderManager.NowloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData = new Object[allOrder.size()][BeanOrderMessage.tableOrderMessageTitles.length];
		for(int i = 0;i < allOrder.size();i++)
			for(int j = 0;j < BeanOrderMessage.tableOrderMessageTitles.length;j++)
				tblOrderData[i][j] = allOrder.get(i).getCell(j);
		tabOrderModel.setDataVector(tblOrderData, btlOrderTitle);
		this.dataTableOrder.invalidate();
		this.dataTableOrder.repaint();
	}
	
	public FrmShowOrderMessage(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnDel);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new  JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
		this.setSize(570, 220);
		this.dataTableOrder.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = FrmShowOrderMessage.this.dataTableOrder.getSelectedRow();
				if(i < 0) {
					return;
				}
				curOrder = allOrder.get(i);
			}
		});
		this.reloadOrderTable();
		// 屏幕居中显示
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
			if(curOrder == null) {
				JOptionPane.showMessageDialog(null, "请选取订单");
				return;
			}	
			try {
				TakeawayUtil.orderManager.DelOrder(curOrder);
				JOptionPane.showMessageDialog(null, "取消成功");
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadOrderTable();
			this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}
	}

}
