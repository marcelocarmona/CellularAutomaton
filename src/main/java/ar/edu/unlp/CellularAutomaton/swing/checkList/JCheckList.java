package ar.edu.unlp.CellularAutomaton.swing.checkList;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import ar.edu.unlp.CellularAutomaton.model.RuleValue;

/**
 * @author mclo
 */
public class JCheckList extends JList<RuleValue>{

	private static final long serialVersionUID = 1L;

	
	/**
	 * Set Model
	 * Add Listener
	 * Set Cell Renderer
	 */
	public JCheckList(RuleCheckListModel ruleCheckListModel) {
		setModel(ruleCheckListModel);
		addMouseListener(new CheckListMouseAdapter());
		setCellRenderer(new CheckListRenderer());
		
		//visual
		setBackground(null);
		setBorder(new TitledBorder(null, ruleCheckListModel.getName(), TitledBorder.CENTER, TitledBorder.TOP, null, null));
//		setPreferredSize(new Dimension(100,100));
		
		
	}
	
	
	/**
	 * Mouse events
	 * @author mclo
	 */
	private class CheckListMouseAdapter extends MouseAdapter{
       public void mousePressed(MouseEvent event)
       {
           JList<?> list = (JList<?>) event.getSource();
           
           // Get index of item clicked
           int index = list.locationToIndex(event.getPoint());
           RuleValue value = (RuleValue)
              list.getModel().getElementAt(index);
           
           // Toggle selected state
           value.setSelected(! value.isSelected());
           
           // Repaint cell
           list.repaint(list.getCellBounds(index, index));
       }
    }
	
	
	/**
	 * Rendering list cell with a jcheckbox 
	 * @author mclo
	 */
	private class CheckListRenderer extends JCheckBox implements ListCellRenderer<RuleValue> {
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(
				JList<? extends RuleValue> list, RuleValue value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setEnabled(list.isEnabled());
			setSelected(((RuleValue) value).isSelected());
			setFont(list.getFont());
			setBackground(null);
			setForeground(null);
			setText(value.toString());
			setMargin(new Insets(0, 20, 0, 20));
			return this;
		}
	}

}
