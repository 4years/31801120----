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
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;


public class FrmMerchantMain extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar = new JMenuBar(); ;
	private JMenu menu = new JMenu("菜单");
//	private JMenu menu_order = new JMenu("排序方式");
	private JMenu menu_more = new JMenu("更多");
	//菜单列表
	private JMenuItem menuItem_addGoods = new JMenuItem("添加商品");
	private JMenuItem menuItem_delGoods = new JMenuItem("删除商品");
	private JMenuItem menuItem_addCoupon = new JMenuItem("添加优惠券");
	private JMenuItem menuItem_showCoupon = new JMenuItem("查看优惠券");
	//排序列表
//	private JMenuItem menuItem_orderByPrice_Up = new JMenuItem("按价格升序");
//	private JMenuItem menuItem_orderByPrice_Down = new JMenuItem("按价格降序");
	//更多列表
	private JMenuItem menuItem_modifyPwd = new JMenuItem("修改密码");
	private JMenuItem menuItem_flash = new JMenuItem("刷新");
	
	private JPanel statusBar = new JPanel();
	
	private Object tblGoodsTitle[] = BeanGoodsDetails.tableGoodsTitles;
	private Object tblGoodsData[][];
	DefaultTableModel tabGoodsModel = new DefaultTableModel();
	private JTable dataTableGoods = new JTable(tabGoodsModel);
	
	private BeanGoodsDetails curGoods = null;
	List<BeanGoodsDetails> allGoods = null;
	
	private void reloadGoodsTable(){
		try {
			allGoods = TakeawayUtil.goodsManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblGoodsData =  new Object[allGoods.size()][BeanGoodsDetails.tableGoodsTitles.length];
		for(int i = 0;i < allGoods.size();i++){
			for(int j = 0;j < BeanGoodsDetails.tableGoodsTitles.length;j++)
				tblGoodsData[i][j] = allGoods.get(i).getCell(j);
		}
		tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
		this.dataTableGoods.validate();
		this.dataTableGoods.repaint();
	}
	
	public FrmMerchantMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖助手商家管理系统");
	    //菜单列表
	    this.menu.add(this.menuItem_addGoods); this.menuItem_addGoods.addActionListener(this);
	    this.menu.add(this.menuItem_delGoods); this.menuItem_delGoods.addActionListener(this);
	    this.menu.add(this.menuItem_addCoupon); this.menuItem_addCoupon.addActionListener(this);
	    this.menu.add(this.menuItem_showCoupon); this.menuItem_showCoupon.addActionListener(this);
	    //更多列表
	    this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    this.menu_more.add(this.menuItem_flash); this.menuItem_flash.addActionListener(this);
	    
	    menubar.add(menu);
	    menubar.add(menu_more);
	    this.setJMenuBar(menubar);
	    
	   //this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.WEST);
	   this.dataTableGoods.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmMerchantMain.this.dataTableGoods.getSelectedRow();
				if(i < 0) {
					return;
				}
				curGoods = allGoods.get(i);
			}
	    	
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
	    
	    this.reloadGoodsTable();
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好! " + BeanMerchant.currentLoginMerchant.getMerchant_name());//修改成   您好！+登陆用户名
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
		if(e.getSource() == this.menuItem_addGoods) {
			FrmAddGoods dlg = new FrmAddGoods(this,"添加商品",true);
			dlg.setVisible(true);
		} else if(e.getSource() == this.menuItem_delGoods) {
			if(this.curGoods == null) {
				JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.goodsManager.delGoods(this.curGoods);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_addCoupon) {
			FrmCoupon FC = new FrmCoupon(this,"添加优惠券",true);
			FC.setVisible(true);
		} else if(e.getSource() == this.menuItem_showCoupon) {
			FrmShowCoupon FSC = new FrmShowCoupon(this,"优惠券查看",true);
			FSC.setVisible(true);
		} else if(e.getSource() == this.menuItem_modifyPwd) {
			FrmMerchantModifyPwd FMMP = new FrmMerchantModifyPwd(this,"修改密码",true);
			FMMP.setVisible(true);	
		} else if(e.getSource() == this.menuItem_flash) {
			this.validate();
			this.repaint();
			this.reloadGoodsTable();
			this.setVisible(false);
			this.setVisible(true);
		}
	}
}
