package com.hc.renderer;

import com.hc.HostChanger;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableButtonCellRenderer extends DefaultTableCellRenderer {		

	private static final long serialVersionUID = 5423211019242436554L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex){
						
		boolean check  =  (Boolean)table.getModel().getValueAt(table.convertRowIndexToModel(rowIndex),0);
		if (check) {
			((JButton)value).setBackground(HostChanger.selectColor); //B8CFE5
		} else {
			((JButton)value).setBackground(HostChanger.unSelectColor);		
		}
		
		String address  =  (String)table.getModel().getValueAt(table.convertRowIndexToModel(rowIndex),1);
		if ("".equals(address.replaceAll(" ", "")) 
				|| (columnIndex == 5 && rowIndex == 0)
				|| (columnIndex == 6 && rowIndex == table.getModel().getRowCount()-1)) {
			//((JButton)value).setIcon(new ImageIcon());
			JButton emptyBtn = new JButton(); 
			emptyBtn.setBackground(((JButton)value).getBackground());
			
			return emptyBtn;
		} else {			
			return (JButton)value;
		}
	}
}
