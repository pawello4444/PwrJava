import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


/* 
 *  Program: Operacje na obiektach klasy Person
 *     Plik: Person.java
 *           definicja typu wyliczeniowego Job
 *           definicja klasy PersonException
 *           definicja publicznej klasy Person
 *           
 *    Autor: Pawel Rogalinski
 *     Data:  pazdziernik 2017 r.
 */


/* 
 *  Typ wyliczeniowy PersonJob reprezentuje przyk³adowe stanowiska, 
 *  które mo¿e zajmowaæ osoba. Klasa zosta³a zaimplementowana
 *  tak, by mog³a byæ rozszerzana o dodatkowe stanowiska.
 *  W tym celu wystarczy do zdefiniowanej listy dodaæ kolejne
 *  wywo³anie konstruktora. 
 */
enum PersonJob {
	UNKNOWN("-------"), 
	GUEST("Goœæ"), 
	STUDENT("Student"), 
	TEACHER("Nauczyciel"), 
	MANAGER("Kierownik"), 
	DIRECTOR("Dyrektor");

	String jobName;

	private PersonJob(String job_name) {
		jobName = job_name;
	}

	
	@Override
	public String toString() {
		return jobName;
	}
	
}  // koniec klasy enum Job


/*
 * Klasa PersonException jest klas¹ wyj¹tków dedykowan¹ do zg³aszania b³êdów 
 * wystêpuj¹cych przy operacjach na obiektach klasy Person.
 */
class PersonException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersonException(String message) {
		super(message);
	}
	
} // koniec klasy PersonException


/*
 * Klasa Person reprezentuje osoby, które s¹ opisane za pomoc¹
 * czterech atrybutow: imiê, nazwisko, rok urodzenia, stanowisko.
 * W klasie przyjêto ograniczenia:
 *   - pola firstName oraz lastName musz¹ zawieraæ niepusty ci¹g znaków
 *   - pole birthYear musi zawieraæ liczbê z przedzia³u [1900-2030]
 *     lub 0 (0 oznacza niezdefiniowany rok urodzenia.
 *   - pole job musi zawieraæ wy³¹cznie jedn¹ z pozycji zdefiniowanych
 *     w typie wyliczeniowym enum PersonJob. 
 *     
 * Powy¿sze ograniczenia s¹ kontrolowane i w przypadku próby nadania
 * niedozwolonej wartoœci, któremuœ z atrybutów jest zg³aszany wyj¹tek
 * zawieraj¹cy stosowny komunikat.
 */
public class Person {
	
	private String firstName;
	private String lastName;
	private int birthYear;
	private PersonJob job;
 
	
	public Person(String first_name, String last_name) throws PersonException {
		setFirstName(first_name);
		setLastName(last_name);
		job = PersonJob.UNKNOWN;
	}

	
	public String getFirstName() {
		return firstName;
	}

	
	public void setFirstName(String first_name) throws PersonException {
		if ((first_name == null) || first_name.equals(""))
			throw new PersonException("Pole <Imiê> musi byæ wype³nione.");
		this.firstName = first_name;
	}

	
	public String getLastName() {
		return lastName;
	}

	
	public void setLastName(String last_name) throws PersonException {
		if ((last_name == null) || last_name.equals(""))
			throw new PersonException("Pole <Nazwisko> musi byæ wype³nione.");
		this.lastName = last_name;
	}

	
	public int getBirthYear() {
		return birthYear;
	}

	
	public void setBirthYear(int birth_year) throws PersonException {
		if ((birth_year!=0) && (birth_year < 1900 || birth_year > 2030))
			throw new PersonException("Rok urodzenia musi byæ w przedziale [1900 - 2030].");
		this.birthYear = birth_year;
	}
	
	
	public void setBirthYear(String birth_year) throws PersonException {
		if (birth_year == null || birth_year.equals("")){  // pusty ³añcuch znaków oznacza rok niezdefiniowany
			setBirthYear(0);
			return;
		}
		try { 
			setBirthYear(Integer.parseInt(birth_year));
		} catch (NumberFormatException e) {
			throw new PersonException("Rok urodzenia musi byæ liczb¹ ca³kowit¹.");
		}
	}

	
	public PersonJob getJob() {
		return job;
	}

	
	public void setJob(PersonJob job){
		this.job = job;
	}
	
	
	public void setJob(String job_name) throws PersonException {
		if (job_name == null || job_name.equals("")) {  // pusty ³añcuch znaków oznacza stanowisko niezdefiniowane
			this.job = PersonJob.UNKNOWN;
			return;
		}
		for(PersonJob job : PersonJob.values()){
			if (job.jobName.equals(job_name)) {
				this.job = job;
				return;
			}
		}
		throw new PersonException("Nie ma takiego stanowiska.");
	}

	
	@Override
	public String toString() {  
		return firstName + " " + lastName;
	}
	
	
	public static void printToFile(PrintWriter writer, Person person){
		writer.println(person.firstName + "#" + person.lastName + 
				"#" + person.birthYear + "#" + person.job);
	}
	
	
	public static void printToFile(String file_name, Person person) throws PersonException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, person);
		} catch (FileNotFoundException e){
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		}
	}
	
	
	public static Person readFromFile(BufferedReader reader) throws PersonException{
		try {
			String line = reader.readLine();
			String[] txt = line.split("#");
			Person person = new Person(txt[0], txt[1]);
			person.setBirthYear(txt[2]);
			person.setJob(txt[3]);	
			return person;
		} catch(IOException e){
			throw new PersonException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
	
	public static Person readFromFile(String file_name) throws PersonException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return Person.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new PersonException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
}  // koniec klasy Person
