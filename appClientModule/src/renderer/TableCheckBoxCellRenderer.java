package src.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import src.HostChanger;

public class TableCheckBoxCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -4777995180556473971L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex){
		JCheckBox checkBox = new JCheckBox();			
		checkBox.setSelected((Boolean) value);
		
		if ((Boolean)value) {
			checkBox.setBackground(HostChanger.selectColor);	
		} else {
			checkBox.setBackground(HostChanger.unSelectColor);				
		}
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		table.repaint();
		
		//checkBox.setIcon(HostChanger.string2Image("#"));
		//checkBox.setSelectedIcon(HostChanger.string2Image(""));
		
		return checkBox;
	}
}
