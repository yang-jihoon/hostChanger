package src.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class CheckBoxCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 8050345533231681637L;

	public CheckBoxCellEditor(JCheckBox jCheckBox) {
		super(jCheckBox);
		jCheckBox.setHorizontalAlignment(JLabel.CENTER);

		//jCheckBox.setIcon(HostChanger.string2Image("#"));
		//jCheckBox.setSelectedIcon(HostChanger.string2Image(""));
		//jCheckBox.setPressedIcon(HostChanger.string2Image("#"));
	}
}
