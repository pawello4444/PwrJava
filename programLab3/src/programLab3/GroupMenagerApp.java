/* Pawe³ Twardawa pazdziernik 2017 */
package programLab3;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.util.*;
import javax.swing.*;

import programLab3.Observer;

import java.awt.event.WindowEvent;



public class GroupMenagerApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String ALL_GROUPS_FILE = "LISTA_GRUP.BIN";
	
	
	
	public static void main(String[] args) {
		new GroupMenagerApp();
	}

	private List<GroupOfBook> currentList = new ArrayList<GroupOfBook>();
	
	////////////////////////////////////////////////////////////////////////////////

	public void notifyAllObserver() throws BooksException
	{
		Iterator<GroupOfBook> iterator = this.currentList.iterator();
		
		for(GroupOfBook observer : currentList)
		{
			
			observer.notifyObserver();
			
		}
	}
	////////////////////////////////////////////////////////////////////////////////
	
	JMenuBar menuBar        = new JMenuBar();
	JMenu menuGroups        = new JMenu("Grupy");
	JMenu menuSpecialGroups = new JMenu("Grupy specjalne");
	JMenu menuAbout         = new JMenu("O programie");
	
	JMenuItem menuNewGroup           = new JMenuItem("Utwórz grupê");
	JMenuItem menuEditGroup          = new JMenuItem("Edytuj grupê");
	JMenuItem menuDeleteGroup        = new JMenuItem("Usuñ grupê");
	JMenuItem menuLoadGroup          = new JMenuItem("za³aduj grupê z pliku");
	JMenuItem menuSaveGroup          = new JMenuItem("Zapisz grupê do pliku");
	
	JMenuItem menuGroupUnion         = new JMenuItem("Po³¹czenie grup");
	JMenuItem menuGroupIntersection  = new JMenuItem("Czêœæ wspólna grup");
	JMenuItem menuGroupDifference    = new JMenuItem("Ró¿nica grup");
	JMenuItem menuGroupSymmetricDiff = new JMenuItem("Ró¿nica symetryczna grup");

	JMenuItem menuAuthor             = new JMenuItem("Autor");
	
	JButton buttonNewGroup = new JButton("Utwórz");
	JButton buttonEditGroup = new JButton("Edytuj");
	JButton buttonDeleteGroup = new JButton("Usuñ ");
	JButton buttonLoadGroup = new JButton("Otwórz");
	JButton buttonSaveGroup = new JButton("Zapisz");

	JButton buttonUnion = new JButton("Suma");
	JButton buttonIntersection = new JButton("Iloczyn");
	JButton buttonDifference = new JButton("Ró¿nica");
	JButton buttonSymmetricDiff = new JButton("Ró¿nica symetryczna");
	
	ViewGroupList viewList;
	
	@SuppressWarnings("unchecked")
	void loadGroupListFromFile(String file_name) throws BooksException
	{
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file_name)))
		{
				currentList = (List<GroupOfBook>)in.readObject();
		} 
		catch (FileNotFoundException e) 
		{
			throw new BooksException("Nie odnaleziono pliku " + file_name);
		} 
		catch (Exception e) 
		{
			throw new BooksException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}
	
	void saveGroupListToFile(String file_name) throws BooksException
	{
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_name)))
		{
			out.writeObject(currentList);
		}
		catch(FileNotFoundException e)
		{
			throw new BooksException("Nie odnaleziono pliku " + file_name);
		}
		catch(IOException e)
		{
			throw new BooksException("Wyst¹pi³ b³¹d podczas zapisu danych do pliku.");
		}
	}
	
	public GroupMenagerApp()
	{
		setTitle("GroupMenager - zarz¹dzanie grupami ksi¹¿ek.");
		setSize(450, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent event)
			{
				try {
					saveGroupListToFile(ALL_GROUPS_FILE);
					JOptionPane.showMessageDialog(null, "Dane zosta³y zapisane do pliku " + ALL_GROUPS_FILE);
				} 
				catch (BooksException e)
				{
					JOptionPane.showConfirmDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
			}
			@Override
			public void windowClosing(WindowEvent event)
			{
				windowClosed(event);
			}
		});
		
		try {
			loadGroupListFromFile(ALL_GROUPS_FILE);
			JOptionPane.showMessageDialog(null, "Dane zosta³y wczytane z pliku" + ALL_GROUPS_FILE);
		}
		catch(BooksException e)
		{
			JOptionPane.showConfirmDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		setJMenuBar(menuBar);
		menuBar.add(menuGroups);
		menuBar.add(menuSpecialGroups);
		menuBar.add(menuAbout);
		
		menuGroups.add(menuNewGroup);
		menuGroups.add(menuEditGroup);
		menuGroups.add(menuDeleteGroup);
		menuGroups.addSeparator();
		menuGroups.add(menuLoadGroup);
		menuGroups.add(menuSaveGroup);
		
		menuSpecialGroups.add(menuGroupUnion);
		menuSpecialGroups.add(menuGroupIntersection);
		menuSpecialGroups.add(menuGroupDifference);
		menuSpecialGroups.add(menuGroupSymmetricDiff);
		
		menuAbout.add(menuAuthor);
		
		
		menuNewGroup.addActionListener(this);
		menuEditGroup.addActionListener(this);
		menuDeleteGroup.addActionListener(this);
		menuLoadGroup.addActionListener(this);
		menuSaveGroup.addActionListener(this);
		menuGroupUnion.addActionListener(this);
		menuGroupIntersection.addActionListener(this);
		menuGroupDifference.addActionListener(this);
		menuGroupSymmetricDiff.addActionListener(this);
		menuAuthor.addActionListener(this);
		
		buttonNewGroup.addActionListener(this);
		buttonEditGroup.addActionListener(this);
		buttonDeleteGroup.addActionListener(this);
		buttonLoadGroup.addActionListener(this);
		buttonSaveGroup.addActionListener(this);
		buttonUnion.addActionListener(this);
		buttonIntersection.addActionListener(this);
		buttonDifference.addActionListener(this);
		buttonSymmetricDiff.addActionListener(this);
		
		viewList = new ViewGroupList(currentList, 400, 250);
		viewList.refreshView();
		
		JPanel panel = new JPanel();
		
		panel.add(viewList);
		panel.add(buttonNewGroup);
		panel.add(buttonEditGroup);
		panel.add(buttonDeleteGroup);
		panel.add(buttonLoadGroup);
		panel.add(buttonSaveGroup);
		panel.add(buttonUnion);
		panel.add(buttonIntersection);
		panel.add(buttonDifference);
		panel.add(buttonSymmetricDiff);
		
		setContentPane(panel);
		
		setVisible(true);
	}
	
	private GroupOfBook chooseGroup(Window parent, String message)
	{
		Object[] groups = currentList.toArray();
		GroupOfBook group = (GroupOfBook)JOptionPane.showInputDialog(parent, message, "Wybierz grupe", JOptionPane.QUESTION_MESSAGE, null, groups, null);
		return group;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		try {
			if(source == menuNewGroup || source == buttonNewGroup)
			{
				GroupOfBook group = new GroupOfBookWindowDialog().createNewGroupOfBook(this);
				if(group != null)
					currentList.add(group);
				
			}
			if(source == menuEditGroup || source == buttonEditGroup)
			{
				int index =  viewList.getSelectedIndex();
				if(index >=0)
				{
					Iterator<GroupOfBook> iterator = currentList.iterator();
						
					while(index-- > 0)
					{
						iterator.next();
					}
						
					new GroupOfBookWindowDialog(this, iterator.next());
					this.notifyAllObserver();
				}
			}
			
			if(source == menuDeleteGroup || source == buttonDeleteGroup)
			{
				int index =  viewList.getSelectedIndex();
				if(index >= 0)
				{
					Iterator<GroupOfBook> iterator = currentList.iterator();
					while(index-- >=0)
					{
						iterator.next();
					}
					iterator.remove();
				}
			}
			if(source == menuLoadGroup || source == buttonLoadGroup)
			{
				JFileChooser chooser = new JFileChooser(".");
				int returnVal = chooser.showOpenDialog(this);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					GroupOfBook group = GroupOfBook.readFromFile(chooser.getSelectedFile().getName());
					this.currentList.add(group);
					//this.viewList.refreshView();
				}
			}
			if(source == menuSaveGroup || source == buttonSaveGroup)
			{
				int index = viewList.getSelectedIndex();
				if(index >=0)
				{
					Iterator<GroupOfBook> iterator = currentList.iterator();
					while(index-- > 0)
						iterator.next();
					GroupOfBook group = iterator.next();
					
					JFileChooser chooser = new JFileChooser(".");
					int returnVal = chooser.showSaveDialog(this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
						GroupOfBook.printToFile(chooser.getSelectedFile().getName(), group);
					}
				}
			}
			if (source == menuGroupUnion || source == buttonUnion) 
			{
				String message1 = 
						"SUMA GRUP\n\n" + 
			            "grupa pierwsza";
				String message2 = 
						"SUMA GRUP\n\n" + 
					    "grupa druga" ;
				GroupOfBook group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfBook group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add(GroupOfBook.createGroupOr(group1, group2));
				
			}
			if (source == menuGroupIntersection || source == buttonIntersection) {
				String message1 = 
						"ILOCZYN GRUP\n\n" + 
				        "grupa pierwsza" ;
				String message2 = 
						"ILOCZYN GRUP\n\n" + 
						"grupa druga" ;
				GroupOfBook group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfBook group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfBook.createGroupAnd(group1, group2) );
			}
			
			if (source == menuGroupDifference || source == buttonDifference) {
				String message1 = 
						"RÓ¯NICA GRUP\n\n" + 
				        "grupa pierwsza";
				String message2 = 
						"RÓ¯NICA GRUP\n\n" + 
						"grupa druga";
				GroupOfBook group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfBook group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfBook.createGroupSub(group1, group2) );
			}
			
			if (source == menuGroupSymmetricDiff || source == buttonSymmetricDiff) {
				String message1 = "RÓ¯NICA SYMETRYCZNA GRUP\n\n"
						+ "grupa pierwsza";
				String message2 = "RÓ¯NICA SYMETRYCZNA GRUP\n\n"
						+ "grupa druga";
				GroupOfBook group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfBook group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfBook.createGroupXor(group1, group2) );
			}
			if(source == this.menuAuthor)
			{
				JOptionPane.showMessageDialog(this, " Edytor grupy ksi¹¿ek \n\n Pawe³ Twardawa \n\n pazdziernik 2017 r." );
			}
			
		}
		catch(BooksException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		viewList.refreshView();
	}


}
