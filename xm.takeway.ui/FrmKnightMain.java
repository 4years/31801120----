package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;

public class FrmKnightMain  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnGet = new Button("接单");
	private Button btnExit = new Button("退出");
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu = new JMenu("菜单");
	private JMenu menu_more = new JMenu("更多");
	
	private JMenuItem menuItem_arrive = new JMenuItem("已送达");
	private JMenuItem menuItem_flash = new JMenuItem("刷新");
	
	private Object tblOrderTitle[] = BeanOrderMessage.tableOrderKnightTitles;
	private Object tblOrderData[][];
	DefaultTableModel tabOrderModel = new DefaultTableModel();
	private JTable dataTableOrder = new JTable(tabOrderModel);
	private BeanOrderMessage curOrder = null;
	List<BeanOrderMessage> allOrder = null;
	
	private Object tblOrderTitle2[] = BeanOrderMessage.tableOrderKnightTitles;
	private Object tblOrderData2[][];
	DefaultTableModel tabOrderModel2 = new DefaultTableModel();
	private JTable dataTableOrder2 = new JTable(tabOrderModel2);
	private BeanOrderMessage curOrder2 = null;
	List<BeanOrderMessage> allOrder2 = null;
	
	private void reloadGetOrderTable() {
		try {
			allOrder2 = TakeawayUtil.orderManager.KnightGetloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData2 = new Object[allOrder2.size()][BeanOrderMessage.tableOrderKnightTitles.length];
		for(int i = 0;i < allOrder2.size();i++)
			for(int j = 0;j < BeanOrderMessage.tableOrderKnightTitles.length;j++)
				tblOrderData2[i][j] = allOrder2.get(i).KnightgetCell(j);
		tabOrderModel2.setDataVector(tblOrderData2, tblOrderTitle2);
		this.dataTableOrder2.validate();
		this.dataTableOrder2.repaint();
	}
	
	private void reloadOrderTable() {
		try {
			allOrder = TakeawayUtil.orderManager.KnightloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData = new Object[allOrder.size()][BeanOrderMessage.tableOrderKnightTitles.length];
		for(int i = 0;i < allOrder.size();i++)
			for(int j = 0;j < BeanOrderMessage.tableOrderKnightTitles.length;j++)
				tblOrderData[i][j] = allOrder.get(i).KnightgetCell(j);
		tabOrderModel.setDataVector(tblOrderData, tblOrderTitle);
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}
	
	public FrmKnightMain() {
		this.setTitle("骑手接单系统");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnGet);
		toolBar.add(btnExit);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.WEST);
		
		this.setSize(908,500);
		
		this.menu.add(this.menuItem_arrive); this.menuItem_arrive.addActionListener(this);
		this.menu_more.add(this.menuItem_flash); this.menuItem_flash.addActionListener(this);
		
		this.dataTableOrder.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = FrmKnightMain.this.dataTableOrder.getSelectedRow();
				if(i < 0) {
					return;
				}
				curOrder = allOrder.get(i);
			}
		});
		
		this.dataTableOrder2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = FrmKnightMain.this.dataTableOrder2.getSelectedRow();
				if(i < 0) {
					return;
				}
				curOrder2 = allOrder2.get(i);
			}
		});
		
		menubar.add(menu);
		menubar.add(menu_more);
		this.setJMenuBar(menubar);
		
		try {
			TakeawayUtil.knightManager.checkMonth();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.reloadOrderTable();
		this.reloadGetOrderTable();
		this.getContentPane().add(new JScrollPane(this.dataTableOrder2), BorderLayout.CENTER);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		btnGet.addActionListener(this);
		btnExit.addActionListener(this);
		this.addWindowListener(new WindowAdapter(){   
			public void windowClosing(WindowEvent e){ 
				System.exit(0);
	        }
		});

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnExit) {
			System.exit(0);
		} else if(e.getSource() == this.btnGet) {
			if(curOrder == null) {
				JOptionPane.showMessageDialog(null, "请选择订单");
				return;
			}
			try {
				TakeawayUtil.knightManager.GetOrder(curOrder);
				JOptionPane.showConfirmDialog(null, "已接单");
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_arrive) {
			if(curOrder2 == null) {
				JOptionPane.showMessageDialog(null, "请选择订单");
				return;
			}
			try {
				TakeawayUtil.knightManager.arriveOrder(curOrder2);
				JOptionPane.showMessageDialog(null, "已送达");
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_flash) {
			this.reloadGetOrderTable();
			this.reloadOrderTable();
			this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}
		
	}
	
}
