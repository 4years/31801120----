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
import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanGoodsKind;
import xm.takeway.util.BaseException;

public class FrmMerchantAddGoods extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("添加");
	private Button btnCancel = new Button("取消");
	
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
	
	public FrmMerchantAddGoods() {
		this.setTitle("进货");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(this.btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(this.dataTableGoodsKind), BorderLayout.WEST);
	    
	    this.dataTableGoodsKind.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmMerchantAddGoods.this.dataTableGoodsKind.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		FrmMerchantAddGoods.this.reloadGoodsTable(i);
	    	}
	    });
	    this.dataTableGoods.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i = FrmMerchantAddGoods.this.dataTableGoods.getSelectedRow();
	    		if(i < 0) {
	    			return;
	    		}
	    		curGoods = allGoods.get(i);
	    	}
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
	    this.reloadGoodsKindTable();
	    this.setSize(900, 500);
		//屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
			(int) (height - this.getHeight()) / 2);
		this.validate();
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if(e.getSource() == this.btnOk) {
			if(curGoods == null) {
				JOptionPane.showMessageDialog(null, "请选择商品");
				return;
			}
			FrmAddGoodsNum FAGN = new FrmAddGoodsNum(curGoodsKind,curGoods);
			this.setVisible(false);
			FAGN.setVisible(true);
		}
	}
}
