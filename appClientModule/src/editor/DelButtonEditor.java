package src.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import src.HostChanger;
import src.listener.Listener;

public class DelButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = -8259702896761924202L;

	public DelButtonEditor(JCheckBox arg0) {
		super(arg0);
	}
		
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		
		((JButton)value).addActionListener(new Listener());

		table.getModel().setValueAt(false, table.convertRowIndexToModel(row), 0);
		table.getModel().setValueAt("", table.convertRowIndexToModel(row), 1);
		table.getModel().setValueAt("", table.convertRowIndexToModel(row), 2);
		table.getModel().setValueAt("", table.convertRowIndexToModel(row), 3);
		table.getModel().setValueAt("", table.convertRowIndexToModel(row), 4);
		HostChanger.hostChanger.setTitle(HostChanger.TITLE+"*");
				
		return (JButton)value;
	}		
}
