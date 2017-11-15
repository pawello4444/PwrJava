/*
 *    Program: Aplikacja okienkowa z GUI, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Books.
 *    Plik: WindowsUserDialog.java
 *          
 *   Autor: Pawe³ Twardawa
 *    Data: pazdziernik 2017 r.
 */
package programLab3;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WindowUserDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private Books book;
	
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
	JComboBox<Gernes> gernesBox = new JComboBox<Gernes>(Gernes.values());
	
	JButton OkButton = new JButton("OK");
	JButton CancelButton = new JButton("Anuluj");
	
	private WindowUserDialog(Window parent, Books books)
	{
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(260, 300);
		setLocationRelativeTo(parent);
		
		this.book = books;
		
		if( books == null)
		{
			setTitle("Nowa ksi¹¿ka");
		}
		else
		{
			setTitle(books.toString());
			titleField.setText(books.getTitle());
			firstNameField.setText(books.getAutorFirstName());
			lastNameField.setText(books.getAutorLastName());
			publishersField.setText(books.getPublishers());
			publicationYearField.setText(""+books.getPublicationYear());
			priceField.setText(""+books.getPrice());
			gernesBox.setSelectedItem(books.getGerne());
		}
		
		OkButton.addActionListener(this);
		CancelButton.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		titleLabel.setBounds(10, 10, 110, 20);
		firstNameLabel.setBounds(10, 40, 110, 20);
		lastNameLabel.setBounds(10, 70, 110, 20);
		publishersLabel.setBounds(10, 100, 110, 20);
		publicationYearLabel.setBounds(10, 130, 110, 20);
		priceLabel.setBounds(10, 160, 110, 20);
		gerneLabel.setBounds(10, 190, 110, 20);
		
		titleField.setBounds(120, 10, 110, 20);
		firstNameField.setBounds(120, 40, 110, 20);
		lastNameField.setBounds(120, 70, 110, 20);
		publishersField.setBounds(120, 100, 110, 20);
		publicationYearField.setBounds(120, 130, 110, 20);
		priceField.setBounds(120, 160, 110, 20);
		gernesBox.setBounds(120, 190, 110, 20);
		
		OkButton.setBounds(10, 220, 100, 20);
		CancelButton.setBounds(130, 220, 100, 20);
		
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
		panel.add(gernesBox);
		panel.add(OkButton);
		panel.add(CancelButton);
		
		setContentPane(panel);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		Object source = event.getSource();
		
		if(source == OkButton)
		{
			try
			{
				if(book == null)
				{
					book = new Books(titleField.getText(), firstNameField.getText(), lastNameField.getText() );
				}
				else
				{
					book.setTitle(titleField.getText());
					book.setAutorFirstName(firstNameField.getText());
					book.setAutorLastName(lastNameField.getText());
				}
				book.setPublishers(publishersField.getText());
				book.setPublicationYear(publicationYearField.getText());
				book.setPrice(priceField.getText());
				book.setGerne((Gernes)gernesBox.getSelectedItem());
				dispose();
			}
			catch(BooksException e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(source == CancelButton)
		{
			dispose();
		}
	}
	
	public static Books createNewBook(Window parent)
	{
		WindowUserDialog dialog = new WindowUserDialog(parent, null);
		return dialog.book;
	}
	
	public static void changePersonData(Window parent, Books books)
	{
		new WindowUserDialog(parent, books);
	}
}
