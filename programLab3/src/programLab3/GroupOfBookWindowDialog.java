/* Paweł Twardawa pazdziernik 2017 */
package programLab3;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.scene.Parent;

public class GroupOfBookWindowDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private GroupOfBook currentGroup;
	
	
	
	private static String enterGroupName(Window parent)
	{
		return JOptionPane.showInputDialog(parent, "Podaj nazwę grupy.");
	}
	
	private static GroupType chooseGroupType(Window parent, GroupType current_type)
	{
		Object[] types = GroupType.values();
		GroupType type = (GroupType)JOptionPane.showInputDialog(parent, "wybierz typ kolekcji", "zmien typ kolekcji" , 3 , null, types, current_type);
		return type;
	}
	
	public static GroupOfBook createNewGroupOfBook(Window parent)
	{
		String name = enterGroupName(parent);
		if((name == null) || (name.equals("")))
		{
			return null;
		}
		
		GroupType type = chooseGroupType(parent, null);
		GroupOfBook group;
		if(type == null)
			return null;
		try
		{
			group = new GroupOfBook(type, name);
		}
		catch(BooksException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Błąd", 0);
			return null;
		}
		GroupOfBookWindowDialog dialog = new GroupOfBookWindowDialog(parent, group);
		return dialog.currentGroup;	
	}
	
	public static void changeGroupOfBook(Window parent, GroupOfBook group)
	{
		new GroupOfBookWindowDialog(parent, group);
	}
	
	  JMenuBar menuBar = new JMenuBar();
	  JMenu menuBook = new JMenu("Lista książek");
	  JMenu menuSort = new JMenu("Sortowanie");
	  JMenu menuProperty = new JMenu("Właściwości");
	  JMenuItem menuNewBook = new JMenuItem("Dodaj nową książkę");
	  JMenuItem menuEditBook = new JMenuItem("Edytuj książkę");
	  JMenuItem menuDeleteBook = new JMenuItem("Usuń książkę");
	  JMenuItem menuLoadBook = new JMenuItem("Wczytaj książkę z pliku");
	  JMenuItem menuSaveBook = new JMenuItem("Zapisz książkę do pliku");
	  JMenuItem menuSortTitle = new JMenuItem("Sortuj wg. tytułu");
	  JMenuItem menuSortAutorFirstName = new JMenuItem("Sortuj wg. imienia autora");
	  JMenuItem menuSortAutorLastName = new JMenuItem("Sortuj wg. nazwiska autora");
	  JMenuItem menuSortPublishers = new JMenuItem("Sortuj wg. wydawnictwa");
	  JMenuItem menuSortPublicationYear = new JMenuItem("Sortuj wg. roku wydania");
	  JMenuItem menuSortPrice = new JMenuItem("Sortuj ceny");
	  JMenuItem menuSortGerne = new JMenuItem("Sortuj wg. rodzaju");
	  JMenuItem menuChangeName = new JMenuItem("Zmien nazwę");
	  JMenuItem menuChangeType = new JMenuItem("Zmień typ kolekcji");
	  JMenuItem menuAuthor = new JMenuItem("O programie");
	  
	  Font font = new Font("Arial", 1, 12);
	  
	  JLabel labelGroupName = new JLabel("Nazwa grupy: ");
	  JLabel labelGroupType = new JLabel("Rodzaj kolekcji: ");
	  JTextField fieldGroupName = new JTextField(15);
	  JTextField fieldGroupType = new JTextField(15);
	  
	  JButton buttonNewBook = new JButton("Dodaj nową ksążkę");
	  JButton buttonEditBook = new JButton("Edytuj książkę");
	  JButton buttonDeleteBook = new JButton("Usuń książkę");
	  JButton buttonLoadBook = new JButton("Wczytaj książkę z pliku");
	  JButton buttonSaveBook = new JButton("Zapisz książkę do pliku");
	  
	  ViewGroupOfBookList viewList;
	  
	  public GroupOfBookWindowDialog()
	  {
	  
	  }
	  
	  public GroupOfBookWindowDialog(Window parent, GroupOfBook group)
	  {
		  super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		  
		  setTitle("Modyfikacja grupy książki");
		  setDefaultCloseOperation(2);
		  setSize(450, 450);
		  setResizable(false);
		  setLocationRelativeTo(null);
		  
		  this.currentGroup = group;
		  
		  setJMenuBar(this.menuBar);
		  this.menuBar.add(this.menuBook);
		  this.menuBar.add(this.menuSort);
		  this.menuBar.add(this.menuProperty);
		  this.menuBar.add(this.menuAuthor);
		  
		  this.menuBook.add(this.menuNewBook);
		  this.menuBook.add(this.menuEditBook);
		  this.menuBook.add(this.menuDeleteBook);
		  this.menuBook.addSeparator();
		  this.menuBook.add(this.menuLoadBook);
		  this.menuBook.add(this.menuBook);
		  
		  this.menuSort.add(this.menuSortTitle);
		  this.menuSort.add(this.menuSortAutorFirstName);
		  this.menuSort.add(this.menuSortAutorLastName);
		  this.menuSort.add(this.menuSortPublishers);
		  this.menuSort.add(this.menuSortPublicationYear);
		  this.menuSort.add(this.menuSortPrice);
		  this.menuSort.add(this.menuSortGerne);
		  
		  this.menuProperty.add(this.menuChangeName);
		  this.menuProperty.add(this.menuChangeType);
		  
		  this.menuNewBook.addActionListener(this);
		  this.menuEditBook.addActionListener(this);
		  this.menuDeleteBook.addActionListener(this);
		  this.menuLoadBook.addActionListener(this);
		  this.menuBook.addActionListener(this);
		  this.menuSortTitle.addActionListener(this);
		  this.menuSortAutorFirstName.addActionListener(this);
		  this.menuSortAutorLastName.addActionListener(this);
		  this.menuSortPublishers.addActionListener(this);
		  this.menuSortPublicationYear.addActionListener(this);
		  this.menuSortPrice.addActionListener(this);
		  this.menuSortGerne.addActionListener(this);
		  this.menuChangeName.addActionListener(this);
		  this.menuChangeType.addActionListener(this);
		  this.menuAuthor.addActionListener(this);
		  
		  this.labelGroupName.setFont(this.font);
		  this.labelGroupType.setFont(this.font);
		  
		  this.fieldGroupName.setEditable(false);
		  this.fieldGroupType.setEditable(false);
		  
		  this.viewList = new ViewGroupOfBookList(currentGroup, 400, 250);
		  this.viewList.refreshView();
		  
		  this.buttonDeleteBook.addActionListener(this);
		  this.buttonEditBook.addActionListener(this);
		  this.buttonLoadBook.addActionListener(this);
		  this.buttonNewBook.addActionListener(this);
		  this.buttonSaveBook.addActionListener(this);
		  
		  JPanel panel = new JPanel();
		  
		  panel.add(this.labelGroupName);
		  panel.add(this.fieldGroupName);
		  panel.add(this.labelGroupType);
		  panel.add(this.fieldGroupType);
		  
		  panel.add(this.viewList);
		  
		  panel.add(this.buttonNewBook);
		  panel.add(this.buttonEditBook);
		  panel.add(this.buttonDeleteBook);
		  panel.add(this.buttonLoadBook);
		  panel.add(this.buttonSaveBook);
		  
		  this.fieldGroupName.setText(this.currentGroup.getName());
		  this.fieldGroupType.setText(this.currentGroup.getType().toString());
		  
		  setContentPane(panel);
		  setVisible(true);
	  }	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		try
		{
			if((source == this.menuNewBook) || (source == this.buttonNewBook))
			{
				Books newBook = WindowUserDialog.createNewBook(this);
				if(newBook != null)
					this.currentGroup.add(newBook);
				
			}
			if((source == this.menuEditBook) || (source == buttonEditBook))
			{
				int index = this.viewList.getSelectedIndex();
				if(index >= 0)
				{
					Iterator<Books> iterator = this.currentGroup.iterator();
					while( index-- >0)
						iterator.next();
					
					WindowUserDialog.changePersonData(this, (Books)iterator.next());
				}
			}
			if((source == menuDeleteBook) || (source == buttonDeleteBook))
			{
				int index = this.viewList.getSelectedIndex();
				if(index >= 0)
				{
					Iterator<Books> iterator = this.currentGroup.iterator();
					while( index-- >0)
						iterator.next();
					
					iterator.remove();
				}
			}
			if((source == this.menuLoadBook) || (source == buttonLoadBook))
			{
				JFileChooser chooser = new JFileChooser(".");
		        int returnVal = chooser.showOpenDialog(this);
		        if (returnVal == 0)
		        {
		          Books person = Books.fromFile(chooser.getSelectedFile().getName());
		          this.currentGroup.add(person);
		        }
			}
			if((source == this.menuSaveBook) || (source == buttonSaveBook))
			{
				int index = this.viewList.getSelectedIndex();
		        if (index >= 0)
		        {
		          Iterator<Books> iterator = this.currentGroup.iterator();
		          while (index-- > 0) {
		            iterator.next();
		          }
		          Books book = (Books)iterator.next();
		          
		          JFileChooser chooser = new JFileChooser(".");
		          int returnVal = chooser.showSaveDialog(this);
		          if (returnVal == 0) {
		            Books.toFile(chooser.getSelectedFile().getName(), book);
		          }
		        }
			}
			if(source == this.menuSortTitle)
			{
				this.currentGroup.sortTitle();
			}
			if(source == this.menuSortAutorFirstName)
			{
				this.currentGroup.sortAutorFirstName();
			}
			if(source == this.menuSortAutorLastName)
			{
				this.currentGroup.sortAutorLastName();
			}
			if(source == this.menuSortPublishers)
			{
				this.currentGroup.sortPublishers();
			}
			if(source == this.menuSortPublicationYear)
			{
				this.currentGroup.sortPublicationYear();
			}
			if(source == this.menuSortPrice)
			{
				this.currentGroup.sortPrice();
			}
			if(source == this.menuSortGerne)
			{
				this.currentGroup.sortGerne();
			}
			if(source == this.menuChangeName)
			{
				String name = this.enterGroupName(this);
				if(name == null)
					return;
				this.currentGroup.setName(name);
				this.fieldGroupName.setText(name);
			}
			if(source == this.menuChangeType)
			{
				GroupType type = chooseGroupType(this, this.currentGroup.getType());
				if(type == null)
					return;
				this.currentGroup.setType(type);
				this.fieldGroupType.setText(type.toString());
			}
			if(source == this.menuAuthor)
			{
				JOptionPane.showMessageDialog(this, " Edytor grupy książek \n\n Paweł Twardawa \n\n pazdziernik 2017 r." );
			}
		}
		catch 
		(BooksException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", 0);
		}
		this.viewList.refreshView();
		
	}

}
