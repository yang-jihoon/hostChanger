
package src.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import src.HostChanger;
import src.domain.HostInfo;
import src.domain.JtableModel;
import src.domain.ReadHostResult;
import src.editor.DelButtonEditor;
import src.editor.CheckBoxCellEditor;
import src.editor.DownButtonEditor;
import src.editor.UpButtonEditor;
import src.renderer.TableButtonCellRenderer;
import src.renderer.TableCheckBoxCellRenderer;
import src.renderer.TableStringCellRenderer;

public class Listener implements ActionListener {
	private static String FILE_PATH = "C:/WINDOWS/system32/drivers/etc/";
	private static String HOST_FILE = FILE_PATH+"HOSTS";
	private static String LOG_FILE = FILE_PATH+"HostChanger.log";
	
	public void actionPerformed(ActionEvent e) {
		HostChanger hostChanger = HostChanger.hostChanger;	

		System.out.println("Command: " + e.getActionCommand());
		if (HostChanger.OPEN.equals(e.getActionCommand())) {
			try {										
				HostChanger.readHostResult = readHostFile(HOST_FILE);					
				refreshHostBox();
				HostChanger.jOpenMenuItem.setText(HostChanger.REOPEN);
				HostChanger.jOpenMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
				HostChanger.jOpenBtn.setText(HostChanger.REOPEN);
				HostChanger.OPEN = HostChanger.REOPEN;
			} catch (IOException e1) {					
				e1.printStackTrace();
			}
		} else if (HostChanger.SAVE.equals(e.getActionCommand())) {
			try {
				if (HostChanger.readHostResult.getHostInfo().size() > 0 ) {
					makeHostFile(HostChanger.readHostResult,HOST_FILE);
				} else {
					JOptionPane.showMessageDialog(hostChanger, HostChanger.NOT_OPEND, HostChanger.SAVE, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (HostChanger.ENABLE_LIST.equals(e.getActionCommand())) {
			if (HostChanger.readHostResult.getHostInfo().size() > 0 ) {
				HostChanger.jHostBox.setSelectedIndex(0);
				changeHostList("Enabled List");	
			} else {
				JOptionPane.showMessageDialog(hostChanger, HostChanger.NOT_OPEND, HostChanger.ENABLE_LIST, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			}
		} else if (HostChanger.DISABLED_SAVE.equals(e.getActionCommand())) {
			for (HostInfo hostInfo : HostChanger.readHostResult.getHostInfo()) {
				if (!"default".equals(hostInfo.getGroupName())) {
					hostInfo.setSelected(false);
				}
			}
			//refreshHostBox();
			if (HostChanger.readHostResult.getHostInfo().size() > 0 ) {
				try {
					makeHostFile(HostChanger.readHostResult,HOST_FILE);
				} catch (IOException e1) {						
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(hostChanger, HostChanger.NOT_OPEND, HostChanger.DISABLED_SAVE, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			}
			HostChanger.hostChanger.setTitle(HostChanger.TITLE);
		} else if (HostChanger.COMBO_CHAGE.equals(e.getActionCommand())) {
			String selectedItem = (String) HostChanger.jHostBox.getSelectedItem();
			if (selectedItem != null && !"".equals(selectedItem)) {		
				System.out.println("Command: " + e.getActionCommand() + " box :"+selectedItem);			
				changeHostList(selectedItem);
			}		
			if ("ALL".equals(selectedItem) || "default".equals(selectedItem)) {
				HostChanger.jEnableGroupBtn.setEnabled(false);
				HostChanger.jEnableGroupMenuItem.setEnabled(false);				
			} else {
				HostChanger.jEnableGroupBtn.setEnabled(true);
				HostChanger.jEnableGroupMenuItem.setEnabled(true);
			}
		} else if (HostChanger.ADD.equals(e.getActionCommand())) {	
			JTextField addressTextField = HostChanger.addressTextField;
			JTextField hostNameTextField = HostChanger.hostNameTextField;
			JTextField groupNameTextField = HostChanger.groupNameTextField;
			JTextField commentTextField = HostChanger.commentTextField;
			
			if (HostChanger.readHostResult.getHostInfo().size() == 0 ) {
				JOptionPane.showMessageDialog(hostChanger, HostChanger.NOT_OPEND, HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if ("".equals(addressTextField.getText().trim())) {
				addressTextField.requestFocus();
				JOptionPane.showMessageDialog(hostChanger, "Address is Empty!!", HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if (!isIpAddress(addressTextField.getText().trim())) {
				addressTextField.requestFocus();
				JOptionPane.showMessageDialog(hostChanger, "Address is Wrong!!", HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if ("".equals(hostNameTextField.getText().trim())) {
				hostNameTextField.requestFocus();
				JOptionPane.showMessageDialog(hostChanger, "HOST is Empty!!", HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if ("".equals(groupNameTextField.getText().trim())) {
				groupNameTextField.requestFocus();
				JOptionPane.showMessageDialog(hostChanger, "GroupName is Empty!!", HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if (HostChanger.readHostResult.getHostInfo().size() > 0 ) {
				HostInfo newHostInfo = new HostInfo();
				newHostInfo.setAddress(addressTextField.getText().trim());
				newHostInfo.setHostName(hostNameTextField.getText().trim());
				newHostInfo.setGroupName(groupNameTextField.getText().trim());
				newHostInfo.setComment(commentTextField.getText().trim());
				
				if (HostChanger.readHostResult.getHostInfo().contains(newHostInfo)) {
					JOptionPane.showMessageDialog(hostChanger, "Host is already Exist!!", HostChanger.ADD, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());					
				} else {						
					HostChanger.readHostResult.getHostInfo().add(newHostInfo);
					
					List<String> hostGroup = HostChanger.readHostResult.getHostGroup();
					
					if (!hostGroup.contains(newHostInfo.getGroupName())){
						hostGroup.add(newHostInfo.getGroupName());
						HostChanger.jHostBox.addItem(newHostInfo.getGroupName());
					}					
					
					String selectedItem = (String) HostChanger.jHostBox.getSelectedItem();
					if (selectedItem != null && !"".equals(selectedItem)) {					
						changeHostList(selectedItem);
					}
					
					addressTextField.setText("");
					hostNameTextField.setText("");
					groupNameTextField.setText("");
					commentTextField.setText("");
					
					hostChanger.setTitle(HostChanger.TITLE+"*");						
				}
			}			
		} else if (HostChanger.EXIT.equals(e.getActionCommand())) {
			int answer = JOptionPane.OK_OPTION;
			
			if ((HostChanger.TITLE+"*").equals(hostChanger.getTitle())) {			
				answer = JOptionPane.showConfirmDialog(hostChanger, HostChanger.SAVE_CONFIRM, HostChanger.EXIT, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			}
			
			if (answer == JOptionPane.OK_OPTION || answer == JOptionPane.NO_OPTION) {
				if (answer == JOptionPane.OK_OPTION ) {
					if (HostChanger.readHostResult.getHostInfo().size() > 0 ) {
						try {
							makeHostFile(HostChanger.readHostResult,HOST_FILE);
						} catch (IOException e1) {						
							e1.printStackTrace();
						}
					}
				}
				HostChanger.tray.remove(HostChanger.trayIcon);
	            System.exit(0);
			} else if (answer == JOptionPane.CANCEL_OPTION) {				
			}			
		} else if (HostChanger.SHOW.equals(e.getActionCommand())) {
			hostChanger.setVisible(true);
		} else if (HostChanger.ENABLE_GROUP.equals(e.getActionCommand())) {
			String selectedItem = (String) HostChanger.jHostBox.getSelectedItem();
			if (HostChanger.readHostResult.getHostInfo().size() == 0 ) {
				JOptionPane.showMessageDialog(hostChanger, HostChanger.NOT_OPEND, HostChanger.ENABLE_GROUP, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
			} else if (!"ALL".equals(selectedItem) && !"default".equals(selectedItem)) {
				for (HostInfo hostInfo : HostChanger.readHostResult.getHostInfo()) {
					if (selectedItem.equals(hostInfo.getGroupName())) {
						hostInfo.setSelected(true);
					} else if (!"default".equals(hostInfo.getGroupName())) {
						hostInfo.setSelected(false);
					} 
				}
				hostChanger.setTitle(HostChanger.TITLE+"*");
				HostChanger.jTable.repaint();
			}
		} else if (HostChanger.ABOUT.equals(e.getActionCommand())) {
			StringBuffer about = new StringBuffer();
			about.append("<html><ol>");
			about.append("<li>Open / ReOpen : HOST 파일 열기");
			about.append("<li>Save : 수정된 내용 저장 ");
			about.append("<br>( 수정내용 있으면 프로그램 타이틀에 * 표시 )");
			about.append("<li>Enabled List : HOST 설정된 리스트 보기 ");
			about.append("<li>Save & Disable : 모든 HOST 설정 해제후, 저장  ");
			about.append("<br>( 그룹이 default인 HOST 제외 )");
			about.append("<li>Change Host Group : 그룹별  Host 보기 ");
			about.append("<li>Add : 새로운 HOST 설정 추가 ");
			about.append("<li>Edit : 변경하고자 하는 항목을 더블클릭 후 내용 변경  ");
			about.append("<li>Delete : DEL 버튼 클릭 ");
			about.append("<li>Enable Select Group : 선택되어 있는 그룹  HOST 설정  ");
			about.append("<br>( 다른 그룹  Disable 후, default 그룹 제외 )");
			about.append("<li>U, D : HOST 위치 이동  ");
			about.append("</ol></html>");
 
			JOptionPane.showMessageDialog(hostChanger, about.toString(), HostChanger.ABOUT, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());		
		} else if (HostChanger.DEL.equals(e.getActionCommand())) {			
		} else if (HostChanger.UP.equals(e.getActionCommand())) {		
		} else if (HostChanger.DOWN.equals(e.getActionCommand())) {	
		}
	}	
		
	public static void changeHostList(String flag) {
		System.out.println("Command: " + "changeHostList" + " :" +flag);
		
		List<HostInfo> itemList = new ArrayList<HostInfo>();
		for (HostInfo hostInfo : HostChanger.readHostResult.getHostInfo()) {
			if (Listener.isIpAddress(hostInfo.getAddress().trim())) {
				if ("ALL".equals(flag)){
					itemList.add(hostInfo);
				} else if ("Enabled List".equals(flag)) {
					if (hostInfo.isSelected()) {
						itemList.add(hostInfo);
					}
				} else {					
					if (hostInfo.getGroupName().length() > 0 && flag.equals(hostInfo.getGroupName())){						
						itemList.add(hostInfo);						
					}
				}
			}
		}		
		JTable jTable = HostChanger.jTable;
		jTable = new JTable(new JtableModel(itemList));
		
		jTable.setRowHeight(25);
		jTable.setDefaultRenderer(String.class, new TableStringCellRenderer());
		
		jTable.getTableHeader().setReorderingAllowed(false);
		
		jTable.setAutoCreateRowSorter(true);		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable.getModel());
		jTable.setRowSorter(sorter);
		
		jTable.getColumnModel().getColumn(0).setMaxWidth(40);
		jTable.getColumnModel().getColumn(0).setResizable(false);
		jTable.getColumnModel().getColumn(0).setCellRenderer(new TableCheckBoxCellRenderer());		
		jTable.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor(new JCheckBox()));
		
		
		jTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(150);		
		
		jTable.getColumnModel().getColumn(5).setMinWidth(20);
		jTable.getColumnModel().getColumn(5).setMaxWidth(20);
		jTable.getColumnModel().getColumn(5).setResizable(false);
		jTable.getColumnModel().getColumn(5).setCellRenderer(new TableButtonCellRenderer());
		jTable.getColumnModel().getColumn(5).setCellEditor(new UpButtonEditor(new JCheckBox()));
		
		jTable.getColumnModel().getColumn(6).setMinWidth(20);
		jTable.getColumnModel().getColumn(6).setMaxWidth(20);
		jTable.getColumnModel().getColumn(6).setResizable(false);
		jTable.getColumnModel().getColumn(6).setCellRenderer(new TableButtonCellRenderer());
		jTable.getColumnModel().getColumn(6).setCellEditor(new DownButtonEditor(new JCheckBox()));

		jTable.getColumnModel().getColumn(7).setMinWidth(40);
		jTable.getColumnModel().getColumn(7).setMaxWidth(40);
		jTable.getColumnModel().getColumn(7).setResizable(false);
		jTable.getColumnModel().getColumn(7).setCellRenderer(new TableButtonCellRenderer());
		jTable.getColumnModel().getColumn(7).setCellEditor(new DelButtonEditor(new JCheckBox()));
		
		HostChanger.mainScrollPane = new JScrollPane(jTable);		
		HostChanger.mainScrollPane.setViewportView(jTable);		
		
		JPanel mainPanel = HostChanger.mainPanel;
		mainPanel.removeAll();
		mainPanel.invalidate();
		mainPanel.add(HostChanger.mainScrollPane);
		mainPanel.revalidate();		
	}		
		
	private void refreshHostBox() {
		System.out.println("Command: " + "refreshHostBox");		
		HostChanger.jHostBox.removeAllItems();
		HostChanger.jHostBox.addItem("ALL");
		for (String hostGroup : HostChanger.readHostResult.getHostGroup() ) {
			HostChanger.jHostBox.addItem(hostGroup);
		}		
	}
		
	private ReadHostResult readHostFile(String filePath) throws IOException {		
		File file = null;
		BufferedReader fReader = new BufferedReader(new InputStreamReader(System.in));
		
		ReadHostResult readHostResult = new ReadHostResult();
		List<String> hostGroup = new ArrayList<String>();
		List<HostInfo> hostInfoList = new ArrayList<HostInfo>();
		try {
			file = new File(filePath);
				        		
			fReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));			
			String tmpLine = "";
			while((tmpLine = fReader.readLine()) != null) {
				HostInfo hostInfo = new HostInfo();
				if (!"".equals(tmpLine)) {				
					String tmp[] = tmpLine.split(" ", 3);
					
					if (tmp.length > 0) {
						if (!"#".equals(tmp[0].substring(0, 1))) {
							hostInfo.setSelected(true);
						}
						hostInfo.setAddress(tmp[0].trim().replaceAll("#", ""));
					}
					if (tmp.length > 1) {
						hostInfo.setHostName(tmp[1].trim());
					}
					if (tmp.length > 2) {					
						if (isIpAddress(hostInfo.getAddress().trim())){
							String tmpGroup[] = tmp[2].split("#", 3);
							if (tmpGroup.length > 1){
								String groupName = tmpGroup[1].trim().toLowerCase();
								if (!hostGroup.contains(groupName)){
									hostGroup.add(groupName);
								}
								hostInfo.setGroupName(groupName);
								if (tmpGroup.length > 2){
									hostInfo.setComment(tmpGroup[2].trim().toLowerCase());
								}
							} else {
								hostInfo.setComment(tmp[2].trim());
							}
						} else {
							hostInfo.setComment(tmp[2].trim());
						}
					}
					
					hostInfoList.add(hostInfo);
				}
			}
		} catch (Exception e) {
			writeLog("HOST file Loading fail "+e,LOG_FILE);
			JOptionPane.showMessageDialog(HostChanger.hostChanger, "HOST file Loading fail...", HostChanger.OPEN, JOptionPane.INFORMATION_MESSAGE, HostChanger.image.getInfoImage());
		} finally {
			fReader.close();
		}	
		readHostResult.setHostInfo(hostInfoList);
		readHostResult.setHostGroup(hostGroup);
		
		HostChanger.hostChanger.setTitle(HostChanger.TITLE);
				
		return readHostResult;
	}

	private void makeHostFile(ReadHostResult readHostResult, String filePath) throws IOException {
		File file = new File(filePath);

		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter os = new OutputStreamWriter(fos, "utf-8");
		BufferedWriter out = new BufferedWriter(os);
							
		for (HostInfo hostInfo : readHostResult.getHostInfo()) {
			StringBuffer line = new StringBuffer("");
			
			if (!hostInfo.isSelected()) {
				line.append("#");
			}
			
			if (!"".equals(hostInfo.getAddress())) {				
				line.append(hostInfo.getAddress());
			}
			if (!"".equals(hostInfo.getHostName())) {
				line.append(" "+hostInfo.getHostName());
			}
			if (!"".equals(hostInfo.getGroupName()) && isIpAddress(hostInfo.getAddress())) {
				line.append(" # "+hostInfo.getGroupName());
			} else {
				line.append(" "+hostInfo.getGroupName());
			}
			if (!"".equals(hostInfo.getComment())&& isIpAddress(hostInfo.getAddress())) {
				line.append(" # "+hostInfo.getComment());
			} else {
				line.append(" "+hostInfo.getComment());
			}
			
			if (!"".equals(line.toString().replaceAll(" ", "").replaceAll("#", ""))) {
				out.write(line.toString());
				out.newLine();
			}
		}
		
		out.close();
		os.close();
		fos.close();
		
		HostChanger.hostChanger.setTitle(HostChanger.TITLE);
	}	

	private void writeLog(String log, String filePath) throws IOException {
		File file = new File(filePath);

		FileOutputStream fos = new FileOutputStream(file, true);
		OutputStreamWriter os = new OutputStreamWriter(fos, "euc-kr");
		BufferedWriter out = new BufferedWriter(os);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String currentTime = df.format(new GregorianCalendar().getTime());
							
		out.write(currentTime+" "+log);
		out.newLine();
		
		out.close();
		os.close();
		fos.close();
	}	
	
	public static boolean isIpAddress(String address) {
		return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", address);		
	}
}
