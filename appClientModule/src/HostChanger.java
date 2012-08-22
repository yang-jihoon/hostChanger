package src;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import src.domain.ImageDomain;
import src.domain.ReadHostResult;
import src.listener.Listener;

public class HostChanger extends JFrame {

	private static final long serialVersionUID = 653569156822502770L;

	public static String TITLE = "HostChanger";	
	public static String NOT_OPEND = "Host file is not opened!!";

	public static String OPEN = "Open";
	public static String REOPEN = "ReOpen";
	public static String SAVE = "Save";
	public static String ADD = "Add";
	public static String ENABLE_LIST = "Show Enabled List";
	public static String ABOUT = "About Host Changer";
	public static String DISABLED_SAVE = "Save & Disable All";
	public static String ENABLE_GROUP = "Enable Select Group";
	public static String COMBO_CHAGE = "comboBoxChanged";
	public static String SAVE_CONFIRM = "Do you want to save the change before the EXIT?";
	public static String EXIT = "Exit";	
	public static String SHOW = "Show Window";	
	public static String DEL = "DEL";	
	public static String UP = "UP";	
	public static String DOWN = "DOWN";
	public static String DEFAULT_SKIN = "Default Skin";	
	
	public static Color selectColor = new Color(0xEE, 0xFA, 0xE6);
	public static Color unSelectColor = Color.WHITE;
		
	public static JPanel mainPanel = new JPanel(new BorderLayout());
	public static JScrollPane mainScrollPane = new JScrollPane();
	
	public static JTextField addressTextField = new JTextField();
	public static JTextField hostNameTextField = new JTextField();
	public static JTextField groupNameTextField = new JTextField();
	public static JTextField commentTextField = new JTextField();
	
	public static JTable jTable = new JTable();
	public static JComboBox jHostBox = new JComboBox();
	
	public static SystemTray tray = SystemTray.getSystemTray();
	public static TrayIcon trayIcon;
	
	public static JMenuItem jEnableGroupMenuItem = new JMenuItem(ENABLE_GROUP);
	public static JMenuItem jOpenMenuItem = new JMenuItem(OPEN);

	public static ImageDomain image = new ImageDomain();
	
	public static JButton jUpBtn = new JButton(HostChanger.resizeIcon(image.getUpImage(), 15, 20));
	public static JButton jDownBtn = new JButton(HostChanger.resizeIcon(image.getDownImage(), 15, 20));
	public static JButton jDelBtn = new JButton(HostChanger.resizeIcon(image.getDelImage(), 20, 20));
	public static JButton jOpenBtn = new JButton(OPEN);
	public static JButton jEnableGroupBtn = new JButton(ENABLE_GROUP);
	
	public static HostChanger hostChanger;

	public static ReadHostResult readHostResult = new ReadHostResult();
	
	public HostChanger() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);		
		setSize(500,800);
		
		URL src = HostChanger.class.getResource("/img/icon/icon.png");
		setIconImage(new ImageIcon(src).getImage());
		
		// tray
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();	
			PopupMenu popUp = new PopupMenu();
			MenuItem showItem = new MenuItem(SHOW);
			showItem.addActionListener(new Listener());
			MenuItem disableItem = new MenuItem(DISABLED_SAVE);
			disableItem.addActionListener(new Listener());
			MenuItem exitItem = new MenuItem(EXIT);
	        exitItem.addActionListener(new Listener());
	        popUp.add(showItem);
	        popUp.add(disableItem);
	        popUp.add(exitItem);
	        
			trayIcon = new TrayIcon(new ImageIcon(src).getImage(),"HostChanger",popUp);		
			trayIcon.setImageAutoSize(true);
	        trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(true);
				}
	        });
	        try {
	        	tray.add(trayIcon);
	        } catch (Exception e ) {}
		}		
		
		// menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu jFileMenu = new JMenu("File");	
		jFileMenu.setMnemonic(KeyEvent.VK_F);	
		jOpenMenuItem.setIcon(resizeIcon(image.getOpenImage(), 20, 20));
		jOpenMenuItem.setMnemonic(KeyEvent.VK_O);
		jOpenMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		jOpenMenuItem.addActionListener(new Listener());
		jFileMenu.add(jOpenMenuItem);
		JMenuItem jSaveMenuItem = new JMenuItem(SAVE, resizeIcon(image.getSaveImage(), 20, 20));
		jSaveMenuItem.setMnemonic(KeyEvent.VK_S);
		jSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		jSaveMenuItem.addActionListener(new Listener());
		jFileMenu.add(jSaveMenuItem);
		jFileMenu.addSeparator();
		JMenuItem jExitMenuItem = new JMenuItem(EXIT, resizeIcon(image.getExitImage(), 20, 20));
		jExitMenuItem.setMnemonic(KeyEvent.VK_X);
		jExitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		jExitMenuItem.addActionListener(new Listener());
		jFileMenu.add(jExitMenuItem);
		menuBar.add(jFileMenu);
		
		JMenu jShowMenu = new JMenu("Show");
		jShowMenu.setMnemonic(KeyEvent.VK_S);
		JMenuItem jEnableListMenuItem = new JMenuItem(ENABLE_LIST, resizeIcon(image.getShowImage(), 20, 20));
		jEnableListMenuItem.setMnemonic(KeyEvent.VK_E);
		jEnableListMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		jEnableListMenuItem.addActionListener(new Listener());
		jShowMenu.add(jEnableListMenuItem);
		menuBar.add(jShowMenu);
		
		JMenu jEnableMenu = new JMenu("Enable");
		jEnableMenu.setMnemonic(KeyEvent.VK_E);
		jEnableGroupMenuItem.setIcon(resizeIcon(image.getEnableImage(), 20, 20));
		jEnableGroupMenuItem.setMnemonic(KeyEvent.VK_G);
		jEnableGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		jEnableGroupMenuItem.addActionListener(new Listener());
		jEnableMenu.add(jEnableGroupMenuItem);
		jEnableGroupMenuItem.setEnabled(false);
		JMenuItem jDisabledSaveGroupMenuItem = new JMenuItem(DISABLED_SAVE, resizeIcon(image.getDisableImage(), 20, 20));
		jDisabledSaveGroupMenuItem.setMnemonic(KeyEvent.VK_D);
		jDisabledSaveGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		jDisabledSaveGroupMenuItem.addActionListener(new Listener());
		jEnableMenu.add(jDisabledSaveGroupMenuItem);
		menuBar.add(jEnableMenu);		

		JMenu jHelpMenu = new JMenu("Help");
		jHelpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem jAboutMenuItem = new JMenuItem(ABOUT);
		jAboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		jAboutMenuItem.setMnemonic(KeyEvent.VK_A);
		jAboutMenuItem.addActionListener(new Listener());
		jHelpMenu.add(jAboutMenuItem);
		menuBar.add(jHelpMenu);
		
		setJMenuBar(menuBar);	
		
		// button		
		JPanel topPanel = new JPanel(new BorderLayout());		
		JPanel openSavePanel = new JPanel(new GridLayout(1, 3));
		
		jOpenBtn.setIcon(image.getOpenImage());	
		jOpenBtn.setToolTipText("Open HOSTS file (C:/WINDOWS/system32/drivers/etc)");
		jOpenBtn.addActionListener(new Listener());		
		openSavePanel.add(jOpenBtn);
		
		JButton jSaveBtn = new JButton(SAVE, image.getSaveImage());		
		jSaveBtn.setToolTipText("Save HOSTS file");
		jSaveBtn.addActionListener(new Listener());
		openSavePanel.add(jSaveBtn);

		JButton jEnableListBtn = new JButton(ENABLE_LIST, image.getShowImage());	
		jEnableListBtn.setToolTipText("Show Enabled List");
		jEnableListBtn.addActionListener(new Listener());
		openSavePanel.add(jEnableListBtn);
		
		JPanel disEnabledPanel = new JPanel(new GridLayout(1, 2));

		jEnableGroupBtn.setIcon(image.getEnableImage());
		jEnableGroupBtn.setToolTipText("Enable Selected Group (except default & Disable others)");
		jEnableGroupBtn.addActionListener(new Listener());
		jEnableGroupBtn.setEnabled(false);
		disEnabledPanel.add(jEnableGroupBtn);

		JButton jDisabledBtn = new JButton(DISABLED_SAVE, image.getDisableImage());
		jDisabledBtn.setToolTipText("Disable All & Save (except default)");
		jDisabledBtn.addActionListener(new Listener());
		disEnabledPanel.add(jDisabledBtn);
		
		topPanel.add(openSavePanel,BorderLayout.NORTH);
		topPanel.add(disEnabledPanel,BorderLayout.CENTER);
				
		jHostBox.addActionListener(new Listener());	
		jHostBox.setToolTipText("Change HOST Group");
		topPanel.add(jHostBox, BorderLayout.SOUTH);
				
		mainPanel.add(mainScrollPane);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel textPanel = new JPanel(new GridLayout(1, 4));

		addressTextField.setToolTipText("Address");
		hostNameTextField.setToolTipText("HOST");
		groupNameTextField.setToolTipText("GroupName");
		commentTextField.setToolTipText("Comment");
		textPanel.add(addressTextField);
		textPanel.add(hostNameTextField);
		textPanel.add(groupNameTextField);
		textPanel.add(commentTextField);
		
		JButton jAddBtn = new JButton(ADD, image.getAddImage());
		jAddBtn.setToolTipText("Add new HOST");
		jAddBtn.addActionListener(new Listener());
		textPanel.add(jAddBtn);
		bottomPanel.add(textPanel,BorderLayout.CENTER);
		
		add(topPanel,BorderLayout.NORTH);
		add(mainPanel,BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);

		setTitle(TITLE);
	}
	
	public static ImageIcon resizeIcon(ImageIcon orgIcon, int width, int height){
		Image newimg = orgIcon.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(); 
		newIcon.setImage(newimg);
		return newIcon;
	}
	
	public static ImageIcon string2Image(String text) {		
		BufferedImage bimg = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);	
		Graphics2D graphic = bimg.createGraphics();
		graphic.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
		graphic.setColor(Color.black);
		graphic.drawRect(0, 0, 14, 14);
		graphic.drawString(text,4,12);	
		graphic.dispose();
         
		return new ImageIcon(bimg);
	}
	
	public static void main(String[] args) {
		hostChanger = new HostChanger();
		hostChanger.setVisible(true);
	}
	
}