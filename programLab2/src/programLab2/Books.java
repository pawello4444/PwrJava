/*
 * 	  Program: Operacje na obiektach klasy Books
 * 
 *    Plik: Books.java
 *          
 *   Autor: Pawe³ Twardawa
 *    Data: pazdziernik 2017 r.
 */


package programLab2;

import java.util.*;
import java.io.*;

enum Gernes {
		THRILLER("Thriller"),
		DRAMA("Dramat"),
		ROMANCE("Romans"),
		POETRY("Poezja"),
		UNKNOWN("------");
		
		String gerne;
		
		private Gernes(String gerne)
		{
			this.gerne = gerne;
		}
		
		@Override
		public String toString()
		{
			return gerne;
		}
}

class BooksException extends Exception {

	private static final long serialVersionUID = 1L;

	public BooksException(String message) {
		super(message);
	}
}
public class Books {

	private String title;
	private String autorFirstName;
	private String autorLastName;
	private String publishers;
	private int publicationYear;
	private float price;
	private Gernes gerne;
	
	public Books(String title, String autorFirstName, String autorLastName) throws BooksException
	{
		setTitle(title);
		setAutorFirstName(autorFirstName);
		setAutorLastName(autorLastName);
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) throws BooksException
	{
		if((title == null) || title.equals(""))
		{
			throw new BooksException("Pole <Tytu³> musi byæ wype³nione.");
		}
		this.title = title;
	}
	
	public String getAutorFirstName()
	{
		return autorFirstName;
	}
	
	public void setAutorFirstName(String autorFirstName) throws BooksException
	{
		if((autorFirstName == null) || (autorFirstName.equals("")))
			throw new BooksException("Pole <imiê autora> musi byæ wype³nione.");
		this.autorFirstName = autorFirstName;		
	}
	
	public String getAutorLastName()
	{
		return autorLastName;
	}
	
	public void setAutorLastName(String autorLastName) throws BooksException
	{
		if((autorLastName == null) || (autorLastName.equals("")))
			throw new BooksException("Pole <nazwisko autora> musi byæ wype³nione.");
		this.autorLastName = autorLastName;
	}
	
	public String getPublishers()
	{
		return publishers;
	}
	
	public void setPublishers(String publishers) throws BooksException
	{
		if((publishers == null) || (publishers.equals("")))
			throw new BooksException("Pole <wydawnictwo> musi byæ wype³nione.");
		this.publishers = publishers;
	}
	
	public int getPublicationYear()
	{
		return publicationYear;
	}
	
	public void setPublicationYear(int publicationYear) throws BooksException
	{
		if(publicationYear > Calendar.getInstance().get(Calendar.YEAR))
		{
			throw new BooksException("<Rok wydania> nie mo¿e byæ wiêkszy od bie¿¹cego roku.");
			
		}
		else
		{
			this.publicationYear = publicationYear;
		}
			
	}
	
	public void setPublicationYear(String publicationYear) throws BooksException
	{
		if((publicationYear == null) || (publicationYear.equals("")))
		{
			setPublicationYear(0);
			return;
		}
		
		try
		{
			setPublicationYear(Integer.parseInt(publicationYear));
		}
		catch(NumberFormatException e)
		{
			throw new BooksException("Rok wydania musi byæ liczb¹ ca³kowit¹.");
		}
			
	}
	
	public float getPrice()
	{
		return price;
	}
	
	public void setPrice(float price) throws BooksException
	{
		if(price < 0)
			throw new BooksException("<Cena> nie mo¿e byæ mniejsza od 0.");
		this.price = price;
	}
	
	public void setPrice(String price) throws BooksException
	{
		if((price == null) || (price.equals("")))
		{
			setPrice(0);
			return;
		}
		
		try
		{
			setPrice(Float.parseFloat(price));
		}
		catch(NumberFormatException e)
		{
			throw new BooksException("Cena musi byæ liczb¹.");
		}
	}
	
	public Gernes getGerne()
	{
		return gerne;
	}
	
	public void setGerne(Gernes gerne)
	{
		this.gerne = gerne;
	}
	
	public void setGerne(String gerne) throws BooksException
	{
		if((gerne == null) || (gerne.equals("")))
		{
			this.gerne = Gernes.UNKNOWN;
			return;
		}
		for(Gernes gerneItems : Gernes.values())
		{
			if(gerneItems.gerne.equals(gerne))
			{
				this.gerne = gerneItems;
				return;
			}
		}
		throw new BooksException("Nie ma takigo rodzaju literatury.");
	}
	
	@Override
	public String toString()
	{
		return title;
	}
	
	public static void toFile(PrintWriter writer, Books books)
	{
		writer.println(books.title +","+ books.autorFirstName +","+ books.autorLastName +","+ books.publishers +","+ books.publicationYear +","+ books.gerne +","+ books.price);
	}
	
	public static void toFile(String fileName, Books books) throws BooksException
	{
		try(PrintWriter writer = new PrintWriter(fileName))
		{
			toFile(writer, books);
		}
		catch(FileNotFoundException e)
		{
			throw new BooksException("Nie odnaleziono pliku " + fileName);
		}
	}
	
	public static void toFileSerializable(String fileName, Books books) throws BooksException
	{
		try
		{
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeUTF(books.title);
			out.writeUTF(books.autorFirstName);
			out.writeUTF(books.autorLastName);
			out.writeUTF(books.publishers);
			out.writeInt(books.publicationYear);
			out.writeUTF(books.gerne.toString());
			out.writeFloat(books.price);
			out.close();
		}
		catch (IOException e)
		{
			throw new BooksException("blad");
		}
	}
	
	public static Books fromFileSerializable(String fileName) throws BooksException
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			Books books = new Books(in.readUTF(), in.readUTF(), in.readUTF());
			books.setPublishers(in.readUTF());
			books.setPublicationYear(in.readInt());
			books.setGerne(in.readUTF());
			books.setPrice(in.readFloat());
			in.close();
			return books;
			
		}
		catch(IOException e)
		{
			throw new BooksException("b³¹d odczytu");
		}
	}
	
	public static Books fromFile(BufferedReader reader) throws BooksException
	{
		try
		{
			String line = reader.readLine();
			String[] text  = line.split(",");
			Books books = new Books(text[0], text[1], text[2]);
			books.setPublishers(text[3]);
			books.setPublicationYear(text[4]);
			books.setGerne(text[5]);
			books.setPrice(text[6]);
			return books;
		}
		catch(IOException e)
		{
			throw new BooksException("Wyst¹pi³ b³¹d podczas odczytu z pliku.");
		}
	}
	
	public static Books fromFile(String fileName) throws BooksException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(fileName))))
		{
			return Books.fromFile(reader);
		}
		catch(FileNotFoundException e)
		{
			throw new BooksException(" Nie znaleziono pliku " + fileName);
		}
		catch(IOException e)
		{
			throw new BooksException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}
}
