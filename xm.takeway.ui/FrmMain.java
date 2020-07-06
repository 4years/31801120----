package xm.takeway.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private FrmLogin dlgLogin = null;
	private JPanel statusBar = new JPanel();
	
	public FrmMain() {
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("外卖助手");
		dlgLogin = new FrmLogin(this,"外卖助手登陆",true);
		dlgLogin.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
