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
	private JMenu menu = new JMenu("�˵�");
//	private JMenu menu_order = new JMenu("����ʽ");
	private JMenu menu_more = new JMenu("����");
	//�˵��б�
	private JMenuItem menuItem_addGoods = new JMenuItem("�����Ʒ");
	private JMenuItem menuItem_delGoods = new JMenuItem("ɾ����Ʒ");
	private JMenuItem menuItem_addCoupon = new JMenuItem("����Ż�ȯ");
	private JMenuItem menuItem_showCoupon = new JMenuItem("�鿴�Ż�ȯ");
	//�����б�
//	private JMenuItem menuItem_orderByPrice_Up = new JMenuItem("���۸�����");
//	private JMenuItem menuItem_orderByPrice_Down = new JMenuItem("���۸���");
	//�����б�
	private JMenuItem menuItem_modifyPwd = new JMenuItem("�޸�����");
	private JMenuItem menuItem_flash = new JMenuItem("ˢ��");
	
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
		this.setTitle("���������̼ҹ���ϵͳ");
	    //�˵��б�
	    this.menu.add(this.menuItem_addGoods); this.menuItem_addGoods.addActionListener(this);
	    this.menu.add(this.menuItem_delGoods); this.menuItem_delGoods.addActionListener(this);
	    this.menu.add(this.menuItem_addCoupon); this.menuItem_addCoupon.addActionListener(this);
	    this.menu.add(this.menuItem_showCoupon); this.menuItem_showCoupon.addActionListener(this);
	    //�����б�
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
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����! " + BeanMerchant.currentLoginMerchant.getMerchant_name());//�޸ĳ�   ���ã�+��½�û���
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
			FrmAddGoods dlg = new FrmAddGoods(this,"�����Ʒ",true);
			dlg.setVisible(true);
		} else if(e.getSource() == this.menuItem_delGoods) {
			if(this.curGoods == null) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.goodsManager.delGoods(this.curGoods);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_addCoupon) {
			FrmCoupon FC = new FrmCoupon(this,"����Ż�ȯ",true);
			FC.setVisible(true);
		} else if(e.getSource() == this.menuItem_showCoupon) {
			FrmShowCoupon FSC = new FrmShowCoupon(this,"�Ż�ȯ�鿴",true);
			FSC.setVisible(true);
		} else if(e.getSource() == this.menuItem_modifyPwd) {
			FrmMerchantModifyPwd FMMP = new FrmMerchantModifyPwd(this,"�޸�����",true);
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
