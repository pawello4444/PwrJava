/*
 *   Program: Aplikacja okienkowa z GUI, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Books.
 *    Plik: BookWindowApp.java
 *          
 *   Autor: Pawe³ Twardawa
 *    Data: pazdziernik 2017 r.
 */
package programLab3;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class BookWindowApp extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private Books currentBook;
	
	private static final String GREETING_MESSAGE = 
			"Program Books - wersja okienkowa\n" + 
	        "Autor: Pawe³ Twardawa\n" + 
			"Data:  paŸdziernik 2017 r.\n";

	public static void main(String[] args) 
	{	
		new BookWindowApp();
	}
	
	Font font = new Font("MonoSpacde", Font.BOLD, 12);

	
	JLabel titleLabel = new JLabel("Tytu³: ");
	JLabel firstNameLabel = new JLabel("Imiê autora: ");
	JLabel lastNameLabel = new JLabel("Nazwisko autora: ");
	JLabel publishersLabel = new JLabel("Wydawnictwo: ");
	JLabel publicationYearLabel = new JLabel("Rok wydania: ");
	JLabel priceLabel = new JLabel("Cena: ");
	JLabel gerneLabel = new JLabel("Gatunek: ");
	
	JTextField titleField = new JTextField(10);
	JTextField firstNameField = new JTextField(10);
	JTextField lastNameField = new JTextField(10);
	JTextField publishersField = new JTextField(10);
	JTextField publicationYearField = new JTextField(10);
	JTextField priceField = new JTextField(10);
	JTextField gerneField = new JTextField(10);
	
	JButton newButton = new JButton("Nowa ksi¹¿ka");
	JButton editButton = new JButton("Zmieñ dane");
	JButton deleteButton = new JButton("Usuñ ksi¹¿kê");
	JButton saveButton = new JButton("Zapisz do pliku");
	JButton loadButton = new JButton("Wczytaj z pliku");
	JButton saveSerializableButton = new JButton("Zapisz do pliku binarnie");
	JButton loadSerializableButton = new JButton("Wczytaj z pliku binarnego");
	JButton infoButton = new JButton("O programie");
	JButton exitButton = new JButton("Zakoñcz aplikacjê");
	
	
	
	JMenuBar menuBar = new JMenuBar();
	
	JMenu fileJMenu = new JMenu("Plik");
	JMenu editJMenu = new JMenu("Edycja");
	JMenu saveJMenu = new JMenu("Zapisz");
	JMenu helpJMenu = new JMenu("Pomoc");
	JMenu openJMenu = new JMenu("Otwórz");
	
	
	JMenuItem fromFileJMenuItem = new JMenuItem("Z pliku tekstowego");
	JMenuItem fromSerializableFileJMenuItem = new JMenuItem("Z pliku binarnego");
	JMenuItem newJMenuItem = new JMenuItem("Nowa ksi¹¿ka");
	JMenuItem editJMenuItem = new JMenuItem("Edytuj");
	JMenuItem deleteJMenuItem = new JMenuItem("Usuñ");
	JMenuItem toFileJMenuItem = new JMenuItem("do pliku tekstowego");
	JMenuItem toSerializableFileJMenuItem = new JMenuItem("do pliku binarnego");
	JMenuItem infoJMenuItem = new JMenuItem("O programie");
	JMenuItem exitJMenuItem = new JMenuItem("Zamknij");
	
	public BookWindowApp()
	{
		setTitle("BookWindowApp");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(510,300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		titleLabel.setFont(font);
		firstNameLabel.setFont(font);
		lastNameLabel.setFont(font);
		publishersLabel.setFont(font);
		publicationYearLabel.setFont(font);
		priceLabel.setFont(font);
		gerneLabel.setFont(font);
		
		titleField.setEditable(false);
		firstNameField.setEditable(false);
		lastNameField.setEditable(false);
		publishersField.setEditable(false);
		publicationYearField.setEditable(false);
		priceField.setEditable(false);
		gerneField.setEditable(false);
		
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		saveSerializableButton.addActionListener(this);
		loadSerializableButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		titleLabel.setBounds(10, 10, 110, 20);
		firstNameLabel.setBounds(10, 40, 110, 20);
		lastNameLabel.setBounds(10, 70, 110, 20);
		publishersLabel.setBounds(10, 100, 110, 20);
		publicationYearLabel.setBounds(10, 130, 110, 20);
		priceLabel.setBounds(10, 160, 110, 20);
		gerneLabel.setBounds(10, 190, 110, 20);
		
		titleField.setBounds(120, 10, 150, 20);
		firstNameField.setBounds(120, 40, 150, 20);
		lastNameField.setBounds(120, 70, 150, 20);
		publishersField.setBounds(120, 100, 150, 20);
		publicationYearField.setBounds(120, 130, 150, 20);
		priceField.setBounds(120, 160, 150, 20);
		gerneField.setBounds(120, 190, 150, 20);
		
		newButton.setBounds(300, 10, 180, 20);
		editButton.setBounds(300, 32, 180, 20);
		deleteButton.setBounds(300, 54, 180, 20);
		saveButton.setBounds(300, 76, 180, 20);
		loadButton.setBounds(300, 98, 180, 20);
		saveSerializableButton.setBounds(300, 120, 180, 20);
		loadSerializableButton.setBounds(300, 142, 180, 20);
		infoButton.setBounds(300, 164, 180, 20);
		exitButton.setBounds(300, 186, 180, 20);
		
		panel.add(titleLabel);
		panel.add(titleField);
		panel.add(firstNameLabel);
		panel.add(firstNameField);
		panel.add(lastNameLabel);
		panel.add(lastNameField);
		panel.add(publishersLabel);
		panel.add(publishersField);
		panel.add(publicationYearLabel);
		panel.add(publicationYearField);
		panel.add(priceLabel);
		panel.add(priceField);
		panel.add(gerneLabel);
		panel.add(gerneField);
		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(editButton);
		panel.add(saveSerializableButton);
		panel.add(loadSerializableButton);
		panel.add(infoButton);
		panel.add(exitButton);
		
		
		setContentPane(panel);
		
		showCurrentBook();
		
		/////JMenuBar//////////////////////////
		fromFileJMenuItem.addActionListener(this);
		fromSerializableFileJMenuItem.addActionListener(this);
		newJMenuItem.addActionListener(this);
		editJMenuItem.addActionListener(this);
		deleteJMenuItem.addActionListener(this);
		toFileJMenuItem.addActionListener(this);
		toSerializableFileJMenuItem.addActionListener(this);
		infoJMenuItem.addActionListener(this);
		exitJMenuItem.addActionListener(this);
		
		newJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		editJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		helpJMenu.add(infoJMenuItem);
		editJMenu.add(editJMenuItem);
		editJMenu.add(deleteJMenuItem);
		
		fileJMenu.add(newJMenuItem);
		
		fileJMenu.addSeparator();
		
		openJMenu.add(fromFileJMenuItem);
		openJMenu.add(fromSerializableFileJMenuItem);
		fileJMenu.add(openJMenu);
		
		saveJMenu.add(toFileJMenuItem);
		saveJMenu.add(toSerializableFileJMenuItem);
		fileJMenu.add(saveJMenu);
		
		fileJMenu.addSeparator();
		
		fileJMenu.add(exitJMenuItem);
		
		menuBar.add(fileJMenu);
		menuBar.add(editJMenu);
		menuBar.add(helpJMenu);
		
		this.setJMenuBar(menuBar);
		////////////////////////////////////////
		
		setVisible(true);
	}
	
	void showCurrentBook()
	{
		if(currentBook == null)
		{
			titleField.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			publishersField.setText("");
			publicationYearField.setText("");
			priceField.setText("");
			gerneField.setText("");
		}
		else
		{
			titleField.setText(currentBook.getTitle());
			firstNameField.setText(currentBook.getAutorFirstName());
			lastNameField.setText(currentBook.getAutorLastName());
			publishersField.setText(currentBook.getPublishers());
			publicationYearField.setText(""+currentBook.getPublicationYear());
			priceField.setText(""+currentBook.getPrice());
			gerneField.setText(currentBook.getGerne().toString());
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent event)
	{
		
		Object source = event.getSource();
		
		try 
		{
			if(source == newButton || source == newJMenuItem)
			{
				currentBook = WindowUserDialog.createNewBook(this);
			}
			if(source == deleteButton || source == deleteJMenuItem)
			{
				currentBook = null;
			}
			if(source == saveButton || source == toFileJMenuItem)
			{
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if((fileName == null) || (fileName.equals("")))
						return;
				Books.toFile(fileName, currentBook);
			}
			if(source == loadButton || source == fromFileJMenuItem)
			{
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if((fileName == null) || (fileName.equals("")))
						return;
				currentBook = Books.fromFile(fileName);
			}
			if(source == editButton || source == editJMenuItem)
			{
				if(currentBook == null)
					throw new BooksException("¯adna osoba nie zosta³a stworzona");
				WindowUserDialog.changePersonData(this, currentBook);				
			}
			if(source == infoButton || source == infoJMenuItem)
			{
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if(source == exitButton || source == exitJMenuItem)
			{
				System.exit(0);
			}
			if(source == saveSerializableButton || source == toSerializableFileJMenuItem)
			{
				String fileName = null;
				JFileChooser saveFile = new JFileChooser();
				if(saveFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					fileName = saveFile.getSelectedFile().getName();
				}
				if((fileName == null) || (fileName.equals("")))
						return;
				Books.toFileSerializable(fileName, currentBook);
			}
			if(source == loadSerializableButton || source == fromSerializableFileJMenuItem)
			{
				String fileName = null;
				JFileChooser saveFile = new JFileChooser();
				if(saveFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					fileName = saveFile.getSelectedFile().getName();
				}
				if((fileName == null) || (fileName.equals("")))
						return;
				currentBook = Books.fromFileSerializable(fileName);
			}
		}
		catch(BooksException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		showCurrentBook();
	}
}
