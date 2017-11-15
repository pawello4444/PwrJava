/* Pawe³ Twardawa pazdziernik 2017 */
package programLab3;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ViewGroupList extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private List<GroupOfBook> list;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ViewGroupList(List<GroupOfBook> list, int width, int height)
	{
		this.list = list;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createTitledBorder("Lista grup:"));
		
		String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba osób" };
		tableModel = new DefaultTableModel(tableHeader, 0);
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;
			
			@Override 
			public boolean isCellEditable(int rowIndex, int colIndex)
			{
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		setViewportView(table);
	}
	
	void refreshView()
	{
		tableModel.setRowCount(0);
		for(GroupOfBook group : list)
		{
			if(group != null)
			{
				String[] row = {group.getName(), group.getType().toString(), "" + group.size() };
				tableModel.addRow(row);
			}
		}
	}
	
	int getSelectedIndex() {
		int index = table.getSelectedRow();
		if(index <0)
		{
			JOptionPane.showMessageDialog(this, "¯adna grupa nie jest zanaczona.", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		return index;
	}
}
