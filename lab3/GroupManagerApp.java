
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*
 * Program: Aplikacja okienkowa z GUI, która umo¿liwia 
 *          zarz¹dzanie grupami obiektów klasy Person.
 *    Plik: GroupManagerApp.java
 *          
 *   Autor: Pawe³ Rogalinski
 *    Data: pazdziernik 2017 r.
 */

public class GroupManagerApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE = 
			"Program do zarz¹dzania grupami osób " + 
	        "- wersja okienkowa\n\n" + 
	        "Autor: Pawe³ Rogalinski\n" + 
			"Data:  paŸdziernik 2017 r.\n";
	
	// Nazwa pliku w którym s¹ zapisywane automatycznie dane przy
	// zamykaniu aplikacji i z którego s¹ czytane dane po uruchomieniu.
	private static final String ALL_GROUPS_FILE = "LISTA_GRUP.BIN"; 
	
	// Utworzenie obiektu reprezentuj¹cego g³ówne okno aplikacji.
	// Po utworzeniu obiektu na pulpicie zostanie wyœwietlone
	// g³ówne okno aplikacji.
	public static void main(String[] args) {	
		new GroupManagerApp();
	}

	
	WindowAdapter windowListener = new WindowAdapter() {
		
		@Override
		public void windowClosed(WindowEvent e) {
			// Wywo³ywane gdy okno aplikacji jest zamykane za pomoc¹
			// wywo³ania metody dispose()
			
			JOptionPane.showMessageDialog(null, "Program zakoñczy³ dzia³anie!");
			
		}


		@Override
		public void windowClosing(WindowEvent e) {
			// Wywo³ywane gdy okno aplikacji jest  zamykane za pomoc¹
			// systemowego menu okna tzn. krzy¿yk w naro¿niku)
			windowClosed(e);
		}

	};
	
	
	
	// Zbiór grup osób, którymi zarz¹dza aplikacja
	private List<GroupOfPeople> currentList = new ArrayList<GroupOfPeople>();
	
	// Pasek menu wyœwietlany na panelu w g³ównym oknie aplikacji
	JMenuBar menuBar        = new JMenuBar();
	JMenu menuGroups        = new JMenu("Grupy");
	JMenu menuSpecialGroups = new JMenu("Grupy specjalne");
	JMenu menuAbout         = new JMenu("O programie");
	
	// Opcje wyœwietlane na panelu w g³ównym oknie aplikacji
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
	
	// Przyciski wyœwietlane na panelu w g³ównym oknie aplikacji
	JButton buttonNewGroup = new JButton("Utwórz");
	JButton buttonEditGroup = new JButton("Edytuj");
	JButton buttonDeleteGroup = new JButton(" Unuñ ");
	JButton buttonLoadGroup = new JButton("Otwórz");
	JButton buttonSavegroup = new JButton("Zapisz");

	JButton buttonUnion = new JButton("Suma");
	JButton buttonIntersection = new JButton("Iloczyn");
	JButton buttonDifference = new JButton("Ró¿nica");
	JButton buttonSymmetricDiff = new JButton("Ró¿nica symetryczna");
		
			
	// Widok tabeli z list¹ grup wyœwietlany 
	// na panelu w oknie g³ównym aplikacji
	ViewGroupList viewList;


	public GroupManagerApp() {
		// Konfiguracja parametrów g³ównego okna aplikacji
		setTitle("GroupManager - zarz¹dzanie grupami osób");
		setSize(450, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Dodanie s³uchacza zdarzeñ od okna aplikacji, który
		// umo¿liwi automatyczny zapis danych do pliku,
		// gdy g³ówne okno aplikacji jest zamykane.
		addWindowListener(new WindowAdapter() {
			// To jest definicja anonimowej klasy (klasy bez nazwy)
			// która dziedziczy po klasie WindowAdapter i przedefiniowuje
			// metody windowClosed oraz windowClosing.

			@Override
			public void windowClosed(WindowEvent event) {
				// Wywo³ywane gdy okno aplikacji jest zamykane za pomoc¹
				// wywo³ania metody dispose()
				try {
					saveGroupListToFile(ALL_GROUPS_FILE);
					JOptionPane.showMessageDialog(null, "Dane zosta³y zapisane do pliku " + ALL_GROUPS_FILE);
				} catch (PersonException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Wywo³ywane gdy okno aplikacji jest zamykane za pomoc¹
				// systemowego menu okna tzn. krzy¿yk w naro¿niku)
				windowClosed(e);
			}

		} // koniec klasy anonimowej
		); // koniec wywo³ania metody addWindowListener

		try {
			// Automatyczne za³adowanie danych z pliku zanim
			// g³ówne okno aplikacji zostanie pokazane na ekranie
			loadGroupListFromFile(ALL_GROUPS_FILE);
			JOptionPane.showMessageDialog(null, "Dane zosta³y wczytane z pliku " + ALL_GROUPS_FILE);
		} catch (PersonException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}		
		
	
		// Utworzenie i konfiguracja menu aplikacji
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
		
		// Dodanie s³uchaczy zdarzeñ do wszystkich opcji menu.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		// zaimplementowana w tej klasie i wywo³ana dla
		// bie¿¹cej instancji okna aplikacji - referencja this
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
		
		// Dodanie s³uchaczy zdarzeñ do wszystkich przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		// zaimplementowana w tej klasie i wywo³ana dla
		// bie¿¹cej instancji okna aplikacji - referencja this
		buttonNewGroup.addActionListener(this);
		buttonEditGroup.addActionListener(this);
		buttonDeleteGroup.addActionListener(this);
		buttonLoadGroup.addActionListener(this);
		buttonSavegroup.addActionListener(this);
		buttonUnion.addActionListener(this);
		buttonIntersection.addActionListener(this);
		buttonDifference.addActionListener(this);
		buttonSymmetricDiff.addActionListener(this);
		
		// Utwotrzenie tabeli z list¹ osób nale¿¹cych do grupy
		viewList = new ViewGroupList(currentList, 400, 250);
		viewList.refreshView();
		
		// Utworzenie g³ównego panelu okna aplikacji.
		// Domyœlnym mened¿erem rozk³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();

		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponentów GUI.
		panel.add(viewList);
		panel.add(buttonNewGroup);
		panel.add(buttonEditGroup);
		panel.add(buttonDeleteGroup);
		panel.add(buttonLoadGroup);
		panel.add(buttonSavegroup);
		panel.add(buttonUnion);
		panel.add(buttonIntersection);
		panel.add(buttonDifference);
		panel.add(buttonSymmetricDiff);

		// Umieszczenie Panelu w g³ównym oknie aplikacji.
		setContentPane(panel);
			
		// Pokazanie na ekranie g³ównego okna aplikacji
		// UWAGA: T¹ instrukcjê nale¿y wykonaæ jako ostatni¹
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponentów GUI.
		// Od tego momentu aplikacja uruchamia g³ówn¹ pêtlê zdarzeñ
		// która dzia³a w nowym w¹tku niezale¿nie od pozosta³ej czêœci programu.
		setVisible(true);
	}
	
	
	
	@SuppressWarnings("unchecked")
	void loadGroupListFromFile(String file_name) throws PersonException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file_name))) {
		currentList = (List<GroupOfPeople>)in.readObject();
		} catch (FileNotFoundException e) {
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		} catch (Exception e) {
			throw new PersonException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}
	
	
	void saveGroupListToFile(String file_name) throws PersonException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_name))) {
			out.writeObject(currentList);
		} catch (FileNotFoundException e) {
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		} catch (IOException e) {
			throw new PersonException("Wyst¹pi³ b³¹d podczas zapisu danych do pliku.");
		}
	}
	
	
	//  Metoda tworzy okno dialogowe do wyboru grupy podczas tworzenia
	//  grup specjalnych i innych operacji na grupach
	private  GroupOfPeople chooseGroup(Window parent, String message){
		Object[] groups = currentList.toArray();
		GroupOfPeople group = (GroupOfPeople)JOptionPane.showInputDialog(
		                    parent, message,
		                    "Wybierz grupê",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null,
		                    groups,
		                    null);
		return group;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, który wygenerowa³ zdarzenie.
		Object source = event.getSource();
		
		try {
			if (source == menuNewGroup || source == buttonNewGroup) {
				GroupOfPeople group = GroupOfPeopleWindowDialog.createNewGroupOfPeople(this);
				if (group != null) {
					currentList.add(group);
				}
			}
			
			if (source == menuEditGroup || source == buttonEditGroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfPeople> iterator = currentList.iterator();
					while (index-- > 0)
						iterator.next();
					new GroupOfPeopleWindowDialog(this, iterator.next());
				}
			}
			
			if (source == menuDeleteGroup || source == buttonDeleteGroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfPeople> iterator = currentList.iterator();
					while (index-- >= 0)
						iterator.next();
					iterator.remove();
				}
			}
			
			if (source == menuLoadGroup || source == buttonLoadGroup) {
				JFileChooser chooser = new JFileChooser(".");
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					GroupOfPeople group = GroupOfPeople.readFromFile(chooser.getSelectedFile().getName());
					currentList.add(group);
				}
			}
			
			if (source == menuSaveGroup || source == buttonSavegroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfPeople> iterator = currentList.iterator();
					while (index-- > 0)
						iterator.next();
					GroupOfPeople group = iterator.next();

					JFileChooser chooser = new JFileChooser(".");
					int returnVal = chooser.showSaveDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						GroupOfPeople.printToFile( chooser.getSelectedFile().getName(), group );
					}
				}
			}
			
			if (source == menuGroupUnion || source == buttonUnion) {
				String message1 = 
						"SUMA GRUP\n\n" + 
			            "Tworzenie grupy zawieraj¹cej wszystkie osoby z grupy pierwszej\n" + 
						"oraz wszystkie osoby z grupy drugiej.\n" + 
			            "Wybierz pierwsz¹ grupê:";
				String message2 = 
						"SUMA GRUP\n\n" + 
					    "Tworzenie grupy zawieraj¹cej wszystkie osoby z grupy pierwszej\n" + 
						"oraz wszystkie osoby z grupy drugiej.\n" + 
					    "Wybierz drug¹ grupê:";
				GroupOfPeople group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfPeople group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfPeople.createGroupUnion(group1, group2) );
			}
			
			if (source == menuGroupIntersection || source == buttonIntersection) {
				String message1 = 
						"ILOCZYN GRUP\n\n" + 
				        "Tworzenie grupy osób, które nale¿¹ zarówno do grupy pierwszej,\n" +
						"jak i do grupy drugiej.\n" + 
				        "Wybierz pierwsz¹ grupê:";
				String message2 = 
						"ILOCZYN GRUP\n\n" + 
						"Tworzenie grupy osób, które nale¿¹ zarówno do grupy pierwszej,\n" +
						"jak i do grupy drugiej.\n" + 
						"Wybierz drug¹ grupê:";
				GroupOfPeople group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfPeople group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfPeople.createGroupIntersection(group1, group2) );
			}
			
			if (source == menuGroupDifference || source == buttonDifference) {
				String message1 = 
						"RÓ¯NICA GRUP\n\n" + 
				        "Tworzenie grupy osób, które nale¿¹ do grupy pierwszej\n" +
						"i nie ma ich w grupie drugiej.\n" + 
				        "Wybierz pierwsz¹ grupê:";
				String message2 = 
						"RÓ¯NICA GRUP\n\n" + 
						"Tworzenie grupy osób, które nale¿¹ do grupy pierwszej\n" +
						"i nie ma ich w grupie drugiej.\n" + 
						"Wybierz drug¹ grupê:";
				GroupOfPeople group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfPeople group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfPeople.createGroupDifference(group1, group2) );
			}
			
			if (source == menuGroupSymmetricDiff || source == buttonSymmetricDiff) {
				String message1 = "RÓ¯NICA SYMETRYCZNA GRUP\n\n"
						+ "Tworzenie grupy zawieraj¹cej osoby nale¿¹ce tylko do jednej z dwóch grup,\n"
						+ "Wybierz pierwsz¹ grupê:";
				String message2 = "RÓ¯NICA SYMETRYCZNA GRUP\n\n"
						+ "Tworzenie grupy zawieraj¹cej osoby nale¿¹ce tylko do jednej z dwóch grup,\n"
						+ "Wybierz drug¹ grupê:";
				GroupOfPeople group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfPeople group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfPeople.createGroupSymmetricDiff(group1, group2) );
			}
			
			if (source == menuAuthor) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			
		} catch (PersonException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}

		// Aktualizacja zawartoœci tabeli z list¹ grup.
		viewList.refreshView();
	}

} // koniec klasy GroupManagerApp



/*
 * Pomocnicza klasa do wyœwietlania listy grup
 * w postaci tabeli na panelu okna g³ównego
 */
class ViewGroupList extends JScrollPane {
private static final long serialVersionUID = 1L;
	
	private List<GroupOfPeople> list;
	private JTable table;
	private DefaultTableModel tableModel;

	public ViewGroupList(List<GroupOfPeople> list, int width, int height){
		this.list = list;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createTitledBorder("Lista grup:"));
		
		String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba osób" };
		tableModel = new DefaultTableModel(tableHeader, 0);
		table = new JTable(tableModel) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Blokada mo¿liwoœci edycji komórek tabeli
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		setViewportView(table);
	}
	
	void refreshView(){
		tableModel.setRowCount(0);
		for (GroupOfPeople group : list) {
			if (group != null) {
				String[] row = { group.getName(), group.getType().toString(), "" + group.size() };
				tableModel.addRow(row);
			}
		}
	}
	
	int getSelectedIndex(){
		int index = table.getSelectedRow();
		if (index<0) {
			JOptionPane.showMessageDialog(this, "¯adana grupa nie jest zaznaczona.", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		return index;
	}

} // koniec klasy ViewGroupList