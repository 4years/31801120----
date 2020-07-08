package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
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
import xm.takeway.model.BeanShoppingCar;
import xm.takeway.model.BeanUser;
import xm.takeway.util.BaseException;


public class FrmUserMain extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menubar = new JMenuBar(); ;
	private JMenu menu = new JMenu("�˵�");
	private JMenu menu_order = new JMenu("����ʽ");
	private JMenu menu_orderDetails = new JMenu("��������");
	private JMenu menu_Address = new JMenu("��ַ����");
	private JMenu menu_more = new JMenu("����");
	//�˵��б�
	private JMenuItem menuItem_addGoodsToShoppingCar = new JMenuItem("���빺�ﳵ");
	private JMenuItem menuItem_delGoods = new JMenuItem("ɾ�����ﳵ��Ʒ");
	private JMenuItem menuItem_settlement = new JMenuItem("����");
	//�����б�
	private JMenuItem menuItem_orderByPrice_Up = new JMenuItem("���۸�����");
	private JMenuItem menuItem_orderByPrice_Down = new JMenuItem("���۸���");
	private JMenuItem menuItem_orderByKind = new JMenuItem("���������");
	//���������б�
	private JMenuItem menuItem_nowOrder = new JMenuItem("��ǰ����");
	private JMenuItem menuItem_historyOrder = new JMenuItem("��ʷ����");
	//��ַ�����б�
	private JMenuItem menuItem_addAddress = new JMenuItem("����ջ���ַ");
	private JMenuItem menuItem_addressManager = new JMenuItem("��ַ����");
	//�����б�
	private JMenuItem menuItem_BeVip = new JMenuItem("��ΪVIP");
	private JMenuItem menuItem_modifyPwd = new JMenuItem("�޸�����");
	private JMenuItem menuItem_flash = new JMenuItem("ˢ��");
	
//	private FrmLogin dlgLogin = null;
	private JPanel statusBar = new JPanel();
	
	private Object tblMerchantTitle[] = BeanMerchant.tableMerchantTitles;
	private Object tblGoodsTitle[] = BeanGoodsDetails.tableGoodsTitles;
	private Object tblShoppingCarTitle[] = BeanShoppingCar.tableShoppingCarTitles;
	private Object tblShoppingCar[] = BeanShoppingCar.tableShoppingCar;
	private Object tblMerchantData[][];
	private Object tblGoodsData[][];
	private Object tblShoppingCarData[][];
	DefaultTableModel tabMerchantModel = new DefaultTableModel();
	DefaultTableModel tabGoodsModel = new DefaultTableModel();
	DefaultTableModel tabShoppingCarModel = new DefaultTableModel();
	private JTable dataTableMerchant = new JTable(tabMerchantModel);
	private JTable dataTableGoods = new JTable(tabGoodsModel);
	private JTable dataTableShoppingCar = new JTable(tabShoppingCarModel);
	
	private BeanGoodsDetails curGoods = null;
	private BeanMerchant curMerchant = null;
	private BeanShoppingCar curShoppingCar = null;
	List<BeanGoodsDetails> allGoods = null;
	List<BeanMerchant> allMerchant = null;
	List<BeanShoppingCar> allShoppingCar = null;
	
	private void reloadMerchantTable(){
		try {
			allMerchant = TakeawayUtil.merchantManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblMerchantData =  new Object[allMerchant.size()][BeanMerchant.tableMerchantTitles.length];
		for(int i = 0;i < allMerchant.size();i++){
			for(int j = 0;j < BeanMerchant.tableMerchantTitles.length;j++)
				tblMerchantData[i][j] = allMerchant.get(i).getCell(j);
		}
		tabMerchantModel.setDataVector(tblMerchantData,tblMerchantTitle);
		this.dataTableMerchant.validate();
		this.dataTableMerchant.repaint();
	}
	
	private void reloadGoodsTabel(int MerchantIdx){
		if(MerchantIdx < 0) return;
		curMerchant = allMerchant.get(MerchantIdx);
		try {
			allGoods = TakeawayUtil.goodsManager.loadAll(curMerchant);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblGoodsData = new Object[allGoods.size()][BeanGoodsDetails.tableGoodsTitles.length];
		for(int i = 0;i < allGoods.size();i++) {
			for(int j = 0;j < BeanGoodsDetails.tableGoodsTitles.length;j++)
				tblGoodsData[i][j] = allGoods.get(i).getCell(j);
		}
		tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
		this.dataTableGoods.validate();
		this.dataTableGoods.repaint();
	}
	
	private void reloadShoppingCarTable() {
		try {
			allShoppingCar = TakeawayUtil.shoppingCarManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblShoppingCarData =  new Object[allShoppingCar.size()][BeanShoppingCar.tableShoppingCarTitles.length];
		for(int i = 0;i < allShoppingCar.size();i++){
			for(int j = 0;j < BeanShoppingCar.tableShoppingCarTitles.length;j++)
				tblShoppingCarData[i][j] = allShoppingCar.get(i).getCell(j);
		}
		tabShoppingCarModel.setDataVector(tblShoppingCarData, tblShoppingCarTitle);
		this.dataTableShoppingCar.validate();
		this.dataTableShoppingCar.repaint();
	}
	
	public FrmUserMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("�û�����ϵͳ");
	    //�˵� ������
	    this.menu.add(this.menuItem_addGoodsToShoppingCar); this.menuItem_addGoodsToShoppingCar.addActionListener(this);
	    this.menu.add(this.menuItem_delGoods); this.menuItem_delGoods.addActionListener(this);
	    this.menu.add(this.menuItem_settlement); this.menuItem_settlement.addActionListener(this);
	    //���� ������
	    this.menu_order.add(this.menuItem_orderByPrice_Up); this.menuItem_orderByPrice_Up.addActionListener(this);
	    this.menu_order.add(this.menuItem_orderByPrice_Down); this.menuItem_orderByPrice_Down.addActionListener(this);
	    this.menu_order.add(this.menuItem_orderByKind); this.menuItem_orderByKind.addActionListener(this);
	    //�������� ������
	    this.menu_orderDetails.add(this.menuItem_nowOrder); this.menuItem_nowOrder.addActionListener(this);
	    this.menu_orderDetails.add(this.menuItem_historyOrder); this.menuItem_historyOrder.addActionListener(this);
	    //��ַ���� ������
	    this.menu_Address.add(this.menuItem_addAddress); this.menuItem_addAddress.addActionListener(this);
	    this.menu_Address.add(this.menuItem_addressManager); this.menuItem_addressManager.addActionListener(this);
	    //���� ������
	    this.menu_more.add(this.menuItem_BeVip); this.menuItem_BeVip.addActionListener(this);
	    this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    this.menu_more.add(this.menuItem_flash); this.menuItem_flash.addActionListener(this);
	    
	    menubar.add(menu);
	    menubar.add(menu_order);
	    menubar.add(menu_orderDetails);
	    menubar.add(menu_Address);
	    menubar.add(menu_more);
	    this.setJMenuBar(menubar);
	    //����������ʾ�̼���Ϣ
	    this.getContentPane().add(new JScrollPane(this.dataTableMerchant), BorderLayout.WEST);
	    
	    this.dataTableMerchant.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmUserMain.this.dataTableMerchant.getSelectedRow();
				if(i < 0) {
					return;
				}
				FrmUserMain.this.reloadGoodsTabel(i);
			}

	    });
	    this.dataTableGoods.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmUserMain.this.dataTableGoods.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		curGoods = allGoods.get(i);
	    	}
	    });
	    this.dataTableShoppingCar.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmUserMain.this.dataTableShoppingCar.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		curShoppingCar = allShoppingCar.get(i);
	    	}
	    });
	    //��������������ʾѡ���̼ҵ���Ʒ��Ϣ
	    this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
	    //���������Ҳ���ʾ���ﳵ��Ϣ
	    this.getContentPane().add(new JScrollPane(this.dataTableShoppingCar), BorderLayout.EAST);
	    this.reloadMerchantTable();
	    this.reloadShoppingCarTable();
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    BeanUser.isVip = false;
	    try {
			if(TakeawayUtil.userManager.isVipDead())
			try {
				BeanUser.isVip = TakeawayUtil.userManager.isVip();
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				BeanUser.isVip = false;
			}
		} catch (HeadlessException | BaseException e1) {
			e1.printStackTrace();
		}
	    JLabel label = new JLabel("����! " + BeanUser.currentLoginUser.getUser_name());
	    if(BeanUser.isVip) 
	    	label = new JLabel("����! �𾴵�Vip�û� " + BeanUser.currentLoginUser.getUser_name());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	
	public BeanGoodsDetails loadcurGoods() {
		BeanGoodsDetails BGD = new BeanGoodsDetails();
		BGD = this.curGoods;
		System.out.println(curGoods.getGoods_name());
		return BGD;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.menuItem_modifyPwd) {
			FrmUserModifyPwd FUMP = new FrmUserModifyPwd(this,"�޸�����",true);
			FUMP.setVisible(true);
		} else if(e.getSource() == this.menuItem_addGoodsToShoppingCar) {
			if(curGoods == null) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				FrmAddShoppingCar FASC = new FrmAddShoppingCar(this,"ѡ��������",true);
				FASC.setVisible(true);
				if(FASC.loadFlag() == 1) {
					int num = FASC.loadNum();
					TakeawayUtil.shoppingCarManager.addGoods(this.curGoods,num);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_delGoods) {
			if(curShoppingCar == null) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.shoppingCarManager.delGoodsFromShoppingCar(curShoppingCar);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_settlement) {
			FrmUserSettlement FU = new FrmUserSettlement(this,"����",true);
			FU.setVisible(true);
		} else if(e.getSource() == this.menuItem_addAddress) {
			FrmUserAddAddress FUAA = new FrmUserAddAddress(this,"��ӵ�ַ",true);
			FUAA.setVisible(true);
		} else if(e.getSource() == this.menuItem_BeVip) {
			try {
				TakeawayUtil.userManager.BeVip();
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if(e.getSource() == this.menuItem_flash) {
			this.reloadMerchantTable();
		    this.reloadShoppingCarTable();
		    this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}

	}
}
