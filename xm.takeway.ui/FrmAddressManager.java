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
import xm.takeway.model.BeanUserAddress;
import xm.takeway.util.BaseException;

public class FrmAddressManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnDel = new Button("É¾³ýµØÖ·");
	private Button btnCancel = new Button("È¡Ïû");
	
	private Object tblAddressTitle[] = BeanUserAddress.tableAddressTitles;
	private Object tblAddressData[][];
	DefaultTableModel tabAddressModel = new DefaultTableModel();
	private JTable dataTableAddress = new JTable(tabAddressModel);
	
	private BeanUserAddress curAddress = null;
	List<BeanUserAddress> allAddress = null;
	
	public void reloadAddressTable() {
		try {
			allAddress = TakeawayUtil.userManager.loadUserAddress();
		} catch(BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "´íÎó", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblAddressData = new Object[allAddress.size()][BeanUserAddress.tableAddressTitles.length];
		for(int i = 0;i < allAddress.size();i++)
			for(int j = 0;j < BeanUserAddress.tableAddressTitles.length;j++)
				tblAddressData[i][j] = allAddress.get(i).getCell(j);
		tabAddressModel.setDataVector(tblAddressData, tblAddressTitle);
		this.dataTableAddress.invalidate();
		this.dataTableAddress.repaint();
		
	}
	
	public FrmAddressManager(Frame f,String s,Boolean b) {
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnDel);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.getContentPane().add(new  JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
		this.setSize(570, 220);
		this.dataTableAddress.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = FrmAddressManager.this.dataTableAddress.getSelectedRow();
				if(i < 0) {
					return;
				}
				curAddress = allAddress.get(i);
			}
		});
		this.reloadAddressTable();
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
			if(curAddress == null) {
				JOptionPane.showMessageDialog(null, "ÇëÑ¡ÔñµØÖ·", "´íÎó",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeawayUtil.userManager.delAddress(curAddress);
				JOptionPane.showMessageDialog(null, "É¾³ý³É¹¦");
			} catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "´íÎó",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadAddressTable();
			this.validate();
			this.repaint();
		    this.setVisible(false);
		    this.setVisible(true);
		}
		
	}
	
}
