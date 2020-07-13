package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import xm.takeway.TakeawayUtil;
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.util.BaseException;


public class FrmRootMain extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar = new JMenuBar(); ;
	private JMenu menu = new JMenu("菜单");
	private JMenu menu_check = new JMenu("查看");
	private JMenu menu_more = new JMenu("更多");
	
	private JMenuItem menuItem_addGoodsKind = new JMenuItem("添加商品类别");
	private JMenuItem menuItem_addGoods = new JMenuItem("添加商品");
	private JMenuItem menuItem_delGoods = new JMenuItem("删除商品");
	private JMenuItem menuItem_moneyOffWay = new JMenuItem("添加满减方案");
	private JMenuItem menuItem_addCoupon = new JMenuItem("添加优惠券");
	
	private JMenuItem menuItem_modifyPwd = new JMenuItem("修改密码");
	private JMenuItem menuItem_flash = new JMenuItem("刷新");
	
	
	private JPanel statusBar = new JPanel();
	
	private Object tblGoodsKindTitle[] = BeanGoodsKind.tableGoodsKindTitle;
	private Object tblGoodsKindData[][];
	DefaultTableModel tabGoodsKindModel = new DefaultTableModel();
	private JTable dataTableGoodsKind = new JTable(tabGoodsKindModel);
	private BeanGoodsKind curGoodsKind = null;
	List<BeanGoodsKind> allGoodsKind = null;
	
	private Object tblGoodsTitle[] = BeanGoodsDetails.tableRootGoodsTitles;
	private Object tblGoodsData[][];
	DefaultTableModel tabGoodsModel = new DefaultTableModel();
	private JTable dataTableGoods = new JTable(tabGoodsModel);
	private BeanGoodsDetails curGoods = null;
	List<BeanGoodsDetails> allGoods = null;
	
	private void reloadGoodsKindTable() {
		try {
			allGoodsKind = TakeawayUtil.rootManager.goodsKindloadAll();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblGoodsKindData = new Object[allGoodsKind.size()][BeanGoodsKind.tableGoodsKindTitle.length];
		for(int i = 0;i < allGoodsKind.size();i++)
			for(int j = 0;j < BeanGoodsKind.tableGoodsKindTitle.length;j++)
				tblGoodsKindData[i][j] = allGoodsKind.get(i).getCell(j);
		tabGoodsKindModel.setDataVector(tblGoodsKindData, tblGoodsKindTitle);
		this.dataTableGoodsKind.validate();
		this.dataTableGoodsKind.repaint();
	}
	
	private void reloadGoodsTable(int GoodsKindIdx) {
		if(GoodsKindIdx < 0) return;
		curGoodsKind = allGoodsKind.get(GoodsKindIdx);
		try {
			allGoods = TakeawayUtil.goodsManager.loadAll(curGoodsKind);
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblGoodsData = new Object[allGoods.size()][BeanGoodsDetails.tableRootGoodsTitles.length];
		for(int i = 0;i < allGoods.size();i++)
			for(int j = 0;j < BeanGoodsDetails.tableRootGoodsTitles.length;j++)
				tblGoodsData[i][j] = allGoods.get(i).RootgetCell(j);
		tabGoodsModel.setDataVector(tblGoodsData, tblGoodsTitle);
		this.dataTableGoods.validate();
		this.dataTableGoods.repaint();
	}
	
	public FrmRootMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖助手管理员系统");
		this.menu.add(this.menuItem_addGoodsKind); this.menuItem_addGoodsKind.addActionListener(this);
		this.menu.add(this.menuItem_addGoods); this.menuItem_addGoods.addActionListener(this);
		this.menu.add(this.menuItem_delGoods); this.menuItem_delGoods.addActionListener(this);
		this.menu.add(this.menuItem_moneyOffWay); this.menuItem_moneyOffWay.addActionListener(this);
		this.menu.add(this.menuItem_addCoupon); this.menuItem_addCoupon.addActionListener(this);
		
		this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
		this.menu_more.add(this.menuItem_flash); this.menuItem_flash.addActionListener(this);
		
	    menubar.add(menu);
	    menubar.add(menu_check);
	    menubar.add(menu_more);
	    this.setJMenuBar(menubar);
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableGoodsKind), BorderLayout.WEST);
	    
	    this.dataTableGoodsKind.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmRootMain.this.dataTableGoodsKind.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		FrmRootMain.this.reloadGoodsTable(i);
	    	}
	    });
	    this.dataTableGoods.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmRootMain.this.dataTableGoods.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		curGoods = allGoods.get(i);
	    	}
	    });
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
	    this.reloadGoodsKindTable();
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好! 管理员");
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.menuItem_addGoodsKind) {
			FrmRootAddGoodsKind FRAGK = new FrmRootAddGoodsKind(this,"添加商品类别",true);
			FRAGK.setVisible(true);
		} else if(e.getSource() == this.menuItem_addGoods) {
			if(curGoodsKind == null) {
				JOptionPane.showMessageDialog(null, "请选择商品类别");
				return;
			} 
			FrmRootAddGoods FRAG = new FrmRootAddGoods(curGoodsKind);
			FRAG.setVisible(true);
		} else if(e.getSource() == this.menuItem_delGoods) {
			
		} else if(e.getSource() == this.menuItem_moneyOffWay) {
			FrmRootAddMoneyOffWay FRAMOW = new FrmRootAddMoneyOffWay(this,"添加满减方案",true);
			FRAMOW.setVisible(true);
		} else if(e.getSource() == this.menuItem_addCoupon) {
			FrmCoupon FC = new FrmCoupon(this,"添加优惠券",true);
			FC.setVisible(true);
		} else if(e.getSource() == this.menuItem_modifyPwd) {
			FrmRootModifyPwd FRMP = new FrmRootModifyPwd(this,"修改密码",true);
			FRMP.setVisible(true);
		} else if(e.getSource() == this.menuItem_flash) {
			this.reloadGoodsKindTable();
			this.reloadGoodsTable(FrmRootMain.this.dataTableGoodsKind.getSelectedRow());
			this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}
	}
		
}
