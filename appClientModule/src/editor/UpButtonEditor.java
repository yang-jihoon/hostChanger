package src.editor;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import src.HostChanger;

public class UpButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 4027406965785591701L;

	public UpButtonEditor(JCheckBox arg0) {
		super(arg0);
	}

		
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		
		int rowMinus1 = table.convertRowIndexToModel(row-1);
		int rowIndex = table.convertRowIndexToModel(row);
				
		String address  =  (String)table.getModel().getValueAt(rowIndex,1);
		if ("".equals(address.replaceAll(" ", ""))) {
			return null;
		} else {	
			if (row > 0) {
				Object row0 = table.getModel().getValueAt(rowIndex, 0);
				Object row1 = table.getModel().getValueAt(rowIndex, 1);
				Object row2 = table.getModel().getValueAt(rowIndex, 2);
				Object row3 = table.getModel().getValueAt(rowIndex, 3);
				Object row4 = table.getModel().getValueAt(rowIndex, 4);
		
				table.getModel().setValueAt(table.getModel().getValueAt(rowMinus1, 0), rowIndex, 0);
				table.getModel().setValueAt(table.getModel().getValueAt(rowMinus1, 1), rowIndex, 1);
				table.getModel().setValueAt(table.getModel().getValueAt(rowMinus1, 2), rowIndex, 2);
				table.getModel().setValueAt(table.getModel().getValueAt(rowMinus1, 3), rowIndex, 3);
				table.getModel().setValueAt(table.getModel().getValueAt(rowMinus1, 4), rowIndex, 4);
		
				table.getModel().setValueAt(row0, rowMinus1, 0);
				table.getModel().setValueAt(row1, rowMinus1, 1);
				table.getModel().setValueAt(row2, rowMinus1, 2);
				table.getModel().setValueAt(row3, rowMinus1, 3);
				table.getModel().setValueAt(row4, rowMinus1, 4);
				
				HostChanger.hostChanger.setTitle(HostChanger.TITLE+"*");
			} else {
				return null;
			}
					
			JButton jButoon = new JButton(HostChanger.resizeIcon(HostChanger.image.getUpImage(), 15, 20));
			jButoon.setToolTipText(HostChanger.UP);	
			
			boolean check  =  (Boolean)table.getModel().getValueAt(rowIndex,0);
			if (check) {
				jButoon.setBackground(HostChanger.selectColor);
			} else {
				jButoon.setBackground(HostChanger.unSelectColor);		
			}
			
			address = (String)table.getModel().getValueAt(rowIndex,1);
			if ("".equals(address.replaceAll(" ", ""))) {
				return null;
			} else {
				return jButoon;
			}
		}
		
	}
}
