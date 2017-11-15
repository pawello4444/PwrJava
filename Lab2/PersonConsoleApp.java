import java.util.Arrays;

/*
 * Program: Aplikacja dzia³aj¹ca w oknie konsoli, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java
 *          
 *   Autor: Pawe³ Rogalinski
 *    Data: pazdziernik 2017 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja konsolowa\n" + 
	        "Autor: Pawe³ Rogalinski\n" + 
			"Data:  paŸdziernik 2017 r.\n";

	private static final String MENU = 
			"    M E N U   G £ Ó W N E  \n" +
			"1 - Podaj dane nowej osoby \n" +
			"2 - Usuñ dane osoby        \n" +
			"3 - Modyfikuj dane osoby   \n" +
			"4 - Wczytaj dane z pliku   \n" +
			"5 - Zapisz dane do pliku   \n" +
			"0 - Zakoñcz program        \n";	
	
	private static final String CHANGE_MENU = 
			"   Co zmieniæ?     \n" + 
	        "1 - Imiê           \n" + 
			"2 - Nazwisko       \n" + 
	        "3 - Rok urodzenia  \n" + 
			"4 - Stanowisko     \n" +
	        "0 - Powrót do menu g³ównego\n";

	
	/* 
	 * ConsoleUserDialog to pomocnicza klasa zawieraj¹ca zestaw
	 * prostych metod do realizacji dialogu z u¿ytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	private static ConsoleUserDialog UI = new ConsoleUserDialog();
	
	
	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie g³ównej pêtli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;
	
	
	/*
	 *  Metoda runMainLoop wykonuje g³ówn¹ petlê aplikacji.
	 *  UWAGA: Ta metoda zawiera nieskoñczon¹ pêtlê,
	 *         w której program siê zatrzymuje a¿ do zakoñczenia
	 *         dzia³ania za pomoc¹ metody System.exit(0); 
	 */
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);

		while (true) {
			UI.clearConsole();
			showCurrentPerson();

			try {
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1:
					// utworzenie nowej osoby
					currentPerson = createNewPerson();
					break;
				case 2:
					// usuniêcie danych aktualnej osoby.
					currentPerson = null;
					UI.printInfoMessage("Dane aktualnej osoby zosta³y usuniête");
					break;
				case 3:
					// zmiana danych dla aktualnej osoby
					if (currentPerson == null) throw new PersonException("¯adna osoba nie zosta³a utworzona.");
					changePersonData(currentPerson);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazwê pliku: ");
					currentPerson = Person.readFromFile(file_name);
					UI.printInfoMessage("Dane aktualnej osoby zosta³y wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnej osoby do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazwê pliku: ");
					Person.printToFile(file_name, currentPerson);
					UI.printInfoMessage("Dane aktualnej osoby zosta³y zapisane do pliku " + file_name);
				}

					break;
				case 0:
					// zakoñczenie dzia³ania programu
					UI.printInfoMessage("\nProgram zakoñczy³ dzia³anie!");
					System.exit(0);
				} // koniec instrukcji switch
			} catch (PersonException e) { 
				// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
				// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec pêtli while
	}
	
	
	/*
	 *  Metoda wyœwietla w oknie konsoli dane aktualnej osoby 
	 *  pamiêtanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		showPerson(currentPerson);
	} 

	
	/* 
	 * Metoda wyœwietla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			sb.append("Aktualna osoba: \n");
			sb.append( "      Imiê: " + person.getFirstName() + "\n" );
			sb.append( "  Nazwisko: " + person.getLastName() + "\n" );
			sb.append( "   Rok ur.: " + person.getBirthYear() + "\n" );
			sb.append( "Stanowisko: " + person.getJob() + "\n");
		} else
			sb.append( "Brak danych osoby" + "\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Meoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wype³nia atrybuty wczytanymi danymi.
	 * Walidacja poprawnoœci danych odbywa siê w konstruktorze i setterach
	 * klasy Person. Jeœli zostan¹ wykryte niepoprawne dane 
	 * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imiê: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie wartoœci wszystkich atrybutów.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
			// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczytaæ nowe dane dla poszczególnych atrybutów 
	 * obiekty person i zmienia je poprzez wywo³anie odpowiednich setterów z klasy Person.
	 * Walidacja poprawnoœci wczyranych danych odbywa siê w setterach
	 * klasy Person. Jeœli zostan¹ wykryte niepoprawne dane 
	 * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imiê: "));
					break;
				case 2:
					person.setLastName(UI.enterString("Podaj nazwisko: "));
					break;
				case 3:
					person.setBirthYear(UI.enterString("Podaj rok ur.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
					person.setJob(UI.enterString("Podaj stanowisko: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
			} catch (PersonException e) {     
				// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
				// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // koniec klasy PersonConsoleApp
