/*
 *   Program: Aplikacja dzia³aj¹ca w oknie konsoli, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Books.
 *    Plik: BooksConsoleApp.java
 *          
 *   Autor: Pawe³ Twardawa
 *    Data: pazdziernik 2017 r.
 */
package programLab2;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class BooksConsoleApp {
	
	private static final String GREETING_MESSAGE = 
			"Program Books - wersja konsolowa\n" + 
	        "Autor: Pawe³ Twardawa\n" + 
			"Data:  paŸdziernik 2017 r.\n";
	
	private static final String MENU = 
			" MENU G£ÓWNE \n"
			+ "[1] Podaj dane nowej ksi¹¿ki \n"
			+ "[2] Usuñ dane ksi¹¿ki \n"
			+ "[3] Modyfikuj dane ksi¹¿ki \n"
			+ "[4] Wczytaj dane z pliku \n"
			+ "[5] Zapisz dane do pliku \n"
			+ "[6] Zapisz do pliku binarnego \n"
			+ "[7] Wczytaj dane z pliku binarnego \n"
			+ "[0] Zakoñcz program \n";
	
	private static final String CHANGE_MENU = 
			" CO ZMIENIÆ ? \n"
			+ "[1] Tytu³ \n"
			+ "[2] Imiê autora  \n"
			+ "[3] Nazwisko autora \n"
			+ "[4] Wydawnictwo \n"
			+ "[5] Rok wydania \n"
			+ "[6] Gatunek \n"
			+ "[7] Cena \n"
			+ "[0] Powrót do menu g³ównego \n";
	
	private static ConsoleUserDialog UI = new ConsoleUserDialog();
	private Books currentBook  = null;
	
	void showCurrentBook()
	{
		showBook(currentBook);
	}
	
	static void showBook(Books book)
	{
		StringBuilder sb = new StringBuilder();
		
		if( book != null)
		{
			sb.append("AKTUALNA KSI¥¯KA: \n");
			sb.append("Tytu³: " + book.getTitle() + "\n");
			sb.append("Imiê autora: "+ book.getAutorFirstName() + "\n");
			sb.append("Nazwisko autora " + book.getAutorLastName() + "\n");
			sb.append("Gatunek literacki " + book.getGerne().toString() + " \n");
			sb.append("Wydawnictwo: " + book.getPublishers() + "\n");
			sb.append("Rok wydania: "+ book.getPublicationYear()+ "\n");
			sb.append("Cena: " + book.getPrice() + "\n");
			
		}
		else
		{
			sb.append("Brak danych ksi¹¿ki \n");
		}
		UI.printMessage(sb.toString());
	}
	
	static Books createNewBook()
	{
		String title = UI.enterString("Podaj tytu³: ");
		String firstName = UI.enterString("Podaj Imiê autora: ");
		String lastName = UI.enterString("Podaj nazwisko autora: ");
		UI.printMessage("Dozwolone gatunki: " + Arrays.deepToString(Gernes.values()));
		String gerne = UI.enterString("Podaj gatunek: ");
		String publisher = UI.enterString("Podaj wydawnictwo: ");
		int year  = UI.enterInt("Podaj rok wydania: ");
		float price = UI.enterFloat("Podaj cenê: ");
		
		Books books;
		try
		{
			books = new Books(title, firstName, lastName);
			books.setPublishers(publisher);
			books.setPublicationYear(year);
			books.setPrice(price);
			books.setGerne(gerne);
		}
		catch(BooksException e)
		{
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return books;
	}
	
	static void changeBookData(Books books)
	{
		while(true)
		{
			UI.clearConsole();
			showBook(books);
			
			try
			{
				switch(UI.enterInt(CHANGE_MENU + "===>"))
				{
				case 1: 
					books.setTitle(UI.enterString("Podaj tytu³: "));
					break;
				case 2:
					books.setAutorFirstName(UI.enterString("Podaj Imiê autora: "));
					break;
				case 3:
					books.setAutorLastName(UI.enterString("Podaj nazwisko autora: "));
					break;
				case 4:
					books.setPublishers(UI.enterString("Podaj wydawnictwo: "));
					break;
				case 5:
					books.setPublicationYear(UI.enterInt("Podaj rok wydania: "));
					break;
				case 6:
					UI.printMessage("Dozwolone gatunki: " + Arrays.deepToString(Gernes.values()));
					books.setGerne(UI.enterString("Podaj gatunek: "));
					break;
				case 7:
					books.setPrice(UI.enterFloat("Podaj cenê: "));
					break;
				case 0 : 
					return;
				}
				
			}
			catch(BooksException e)
			{
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	public void runMainLoop()
	{
		UI.printErrorMessage(GREETING_MESSAGE);
		
		while(true)
		{
			UI.clearConsole();
			showCurrentBook();
			
			try
			{
				switch(UI.enterInt(MENU + "===>"))
				{
				case 1:
					currentBook = createNewBook();
					break;
				case 2:
					currentBook = null;
					UI.printMessage("Dane ksi¹¿ki zosta³y usuniête.");
					break;
				case 3:
					if(currentBook == null)
					{
						throw new BooksException("¯adna osoba nie zosta³a utworzona.");
					}
					changeBookData(currentBook);
					break;
				case 4:
					String fileName = UI.enterString("Podaj nazwê pliku: ");
					currentBook = Books.fromFile(fileName);
					UI.printInfoMessage("Dane aktualnej osoby zosta³y wczytane z pliku "+ fileName);
					break;
				case 5:
					String file_Name = UI.enterString("Podaj nazwê pliku: ");
					Books.toFile(file_Name, currentBook);
					UI.printInfoMessage("Dane zosta³y zapisane.");
					break;
				case 6:
					String file_Name1 = UI.enterString("Podaj nazwê pliku: ");
					Books.toFileSerializable(file_Name1, currentBook);
					UI.printInfoMessage("Dane zosta³y zapisane.");
					break;
				case 7:
					String fileName1 = UI.enterString("Podaj nazwê pliku: ");
					currentBook = Books.fromFileSerializable(fileName1);
					UI.printInfoMessage("Dane aktualnej osoby zosta³y wczytane z pliku "+ fileName1);
					break;
				case 0:
					UI.printInfoMessage("\nProgram zakoñczy³ dzia³anie. \n");
					System.exit(0);
				}
			}
			catch(BooksException e)
			{
				UI.printErrorMessage(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		BooksConsoleApp application = new BooksConsoleApp();
		application.runMainLoop();
	}
	
	

}
