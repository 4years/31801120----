package xm.takeway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class FrmRootMain extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar = new JMenuBar(); ;
	private JMenu menu = new JMenu("�˵�");
	private JMenu menu_check = new JMenu("�鿴");
	private JMenu menu_more = new JMenu("����");
	
	private JMenuItem menuItem_goodsKind = new JMenuItem("�����Ʒ���");
	private JMenuItem menuItem_moneyOffWay = new JMenuItem("�����������");
	
	
	private JPanel statusBar = new JPanel();
	
	
	public FrmRootMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("�������ֹ���Աϵͳ");
	    this.menu.add(this.menuItem_moneyOffWay); this.menuItem_moneyOffWay.addActionListener(this);
	    this.menu.add(this.menuItem_goodsKind); this.menuItem_goodsKind.addActionListener(this);
		
	    menubar.add(menu);
	    menubar.add(menu_check);
	    menubar.add(menu_more);
	    this.setJMenuBar(menubar);
	    

	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����! ����Ա");
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
		if(e.getSource() == this.menuItem_moneyOffWay) {
			FrmRootAddMoneyOffWay FRAMOW = new FrmRootAddMoneyOffWay(this,"�����������",true);
			FRAMOW.setVisible(true);
		}
	}
		
}
