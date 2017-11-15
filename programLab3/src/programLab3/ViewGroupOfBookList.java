/* Pawe³ Twardawa pazdziernik 2017 */
package programLab3;

import java.awt.Dimension;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewGroupOfBookList extends JScrollPane implements Iterable<Books> {

	private static final long serialVersionUID = 1L;
	
	private GroupOfBook group;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ViewGroupOfBookList(GroupOfBook group, int width, int height)
	{
		this.group = group;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createTitledBorder("Lista ksi¹¿ek"));
		
		String[] tableHeader = {"Tytu³", "Imie autora", "Nazwisko autora", "wydawnictwo", "Rok wydania", "Cana", "Gatunek"};
		this.tableModel = new  DefaultTableModel(tableHeader, 0);
		this.table = new JTable(this.tableModel)
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) 
			{
				return false;
			}		
		};
		this.table.setSelectionMode(0);
		this.table.setRowSelectionAllowed(true);
		setViewportView(this.table);
	}

	void refreshView()
	{
		this.tableModel.setRowCount(0);
		for (Books b : this.group)
	//	Books b = (Books) iterator;
	    {
			String[] row = { b.getTitle(), b.getAutorFirstName(), b.getAutorLastName(), b.getPublishers(), b.getPublicationYear()+"", b.getPrice()+"", b.getGerne().toString() };
			this.tableModel.addRow(row);
		}
	}
	
	int getSelectedIndex()
	{
		int index = this.table.getSelectedRow();
		if(index < 0)
			JOptionPane.showMessageDialog(this, "¯adna ksi¹¿ka nie jest zaznaczona. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
		return index;
	}
	
}
