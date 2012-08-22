package src.renderer;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import src.HostChanger;

public class TableStringCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 5450591232733607592L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex){
		Component comp = super.getTableCellRendererComponent(
                table,  value, isSelected, hasFocus, rowIndex, columnIndex);	
		
		boolean check  =  (Boolean)table.getModel().getValueAt(table.convertRowIndexToModel(rowIndex),0);
		
		if (check) {
			comp.setBackground(HostChanger.selectColor);
			comp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));			
		} else {
			comp.setBackground(HostChanger.unSelectColor);		
		}		
		((JComponent)comp).setToolTipText((String)table.getModel().getValueAt(table.convertRowIndexToModel(rowIndex), columnIndex));
		
		
		
		return comp;		
	}
}
