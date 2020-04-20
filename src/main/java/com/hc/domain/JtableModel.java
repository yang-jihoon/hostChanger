package com.hc.domain;

import com.hc.HostChanger;
import com.hc.listener.Listener;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class JtableModel extends AbstractTableModel {
	private static final long serialVersionUID = 193481215969118388L;
	
	private String[] columnNames = {"Use","Address","HOST","Group","Comment","U","D","DEL"};
	private List<HostInfo> itemList;
	
	public JtableModel(List<HostInfo> itemList) {
		this.itemList = itemList;
	}
			
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return itemList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return itemList.get(rowIndex).getObject(columnIndex);
	}
		

	@Override
	public void setValueAt(Object object, int rowIndex, int columnIndex) {	
		
		if (columnIndex == 1) {	
			String orgString = (String)getValueAt(rowIndex,columnIndex);
			if (!Listener.isIpAddress((String)object) && !"".equals(object)) {
				JOptionPane.showMessageDialog(HostChanger.hostChanger, "Address is Wrong!!");
			} else {
				itemList.get(rowIndex).setObject(columnIndex,object);
				if (!orgString.equals(getValueAt(rowIndex, columnIndex))) {
					HostChanger.hostChanger.setTitle(HostChanger.TITLE+"*");
				}
			}
		} else if (columnIndex == 0) {			
			itemList.get(rowIndex).setObject(columnIndex,object);
			String hostName = (String)getValueAt(rowIndex,2);
			String address = (String)getValueAt(rowIndex,1);
			
			if ((Boolean)object) {
				for (HostInfo hostInfo : HostChanger.readHostResult.getHostInfo()) {
					if (hostInfo.getHostName().equals(hostName) && !hostInfo.getAddress().equals(address) ){						
						hostInfo.setSelected(false);
					}
				}
			}
			
			HostChanger.jTable.repaint();
			
			HostChanger.hostChanger.setTitle(HostChanger.TITLE+"*");

		} else if (columnIndex == 5) {
		} else if (columnIndex == 6) {
		} else if (columnIndex == 7) {
		} else {	
			String orgString = (String)getValueAt(rowIndex,columnIndex);
			itemList.get(rowIndex).setObject(columnIndex,object);
			
			if (!orgString.equals(getValueAt(rowIndex, columnIndex))) {
				HostChanger.hostChanger.setTitle(HostChanger.TITLE+"*");	
				
				if (columnIndex == 3) {
					List<String> hostGroup = HostChanger.readHostResult.getHostGroup();
					
					if (!hostGroup.contains(object)){
						hostGroup.add((String)object);
					} else {
						hostGroup.remove(orgString);
					}
				}
			}
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
	
	@Override
	public String getColumnName(int columnIndex) {
	      return columnNames[columnIndex];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {				
	    return getValueAt(0, columnIndex).getClass();
	}
}
