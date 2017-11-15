import java.util.Arrays;

/*
 * Program: Aplikacja dzia�aj�ca w oknie konsoli, kt�ra umo�liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java
 *          
 *   Autor: Pawe� Rogalinski
 *    Data: pazdziernik 2017 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja konsolowa\n" + 
	        "Autor: Pawe� Rogalinski\n" + 
			"Data:  pa�dziernik 2017 r.\n";

	private static final String MENU = 
			"    M E N U   G � � W N E  \n" +
			"1 - Podaj dane nowej osoby \n" +
			"2 - Usu� dane osoby        \n" +
			"3 - Modyfikuj dane osoby   \n" +
			"4 - Wczytaj dane z pliku   \n" +
			"5 - Zapisz dane do pliku   \n" +
			"0 - Zako�cz program        \n";	
	
	private static final String CHANGE_MENU = 
			"   Co zmieni�?     \n" + 
	        "1 - Imi�           \n" + 
			"2 - Nazwisko       \n" + 
	        "3 - Rok urodzenia  \n" + 
			"4 - Stanowisko     \n" +
	        "0 - Powr�t do menu g��wnego\n";

	
	/* 
	 * ConsoleUserDialog to pomocnicza klasa zawieraj�ca zestaw
	 * prostych metod do realizacji dialogu z u�ytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	private static ConsoleUserDialog UI = new ConsoleUserDialog();
	
	
	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie g��wnej p�tli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	
	/*
	 *  Referencja do obiektu, kt�ry zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;
	
	
	/*
	 *  Metoda runMainLoop wykonuje g��wn� petl� aplikacji.
	 *  UWAGA: Ta metoda zawiera niesko�czon� p�tl�,
	 *         w kt�rej program si� zatrzymuje a� do zako�czenia
	 *         dzia�ania za pomoc� metody System.exit(0); 
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
					// usuni�cie danych aktualnej osoby.
					currentPerson = null;
					UI.printInfoMessage("Dane aktualnej osoby zosta�y usuni�te");
					break;
				case 3:
					// zmiana danych dla aktualnej osoby
					if (currentPerson == null) throw new PersonException("�adna osoba nie zosta�a utworzona.");
					changePersonData(currentPerson);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazw� pliku: ");
					currentPerson = Person.readFromFile(file_name);
					UI.printInfoMessage("Dane aktualnej osoby zosta�y wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnej osoby do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazw� pliku: ");
					Person.printToFile(file_name, currentPerson);
					UI.printInfoMessage("Dane aktualnej osoby zosta�y zapisane do pliku " + file_name);
				}

					break;
				case 0:
					// zako�czenie dzia�ania programu
					UI.printInfoMessage("\nProgram zako�czy� dzia�anie!");
					System.exit(0);
				} // koniec instrukcji switch
			} catch (PersonException e) { 
				// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Person
				// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
				// poszczeg�lnych atrybut�w.
				// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec p�tli while
	}
	
	
	/*
	 *  Metoda wy�wietla w oknie konsoli dane aktualnej osoby 
	 *  pami�tanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		showPerson(currentPerson);
	} 

	
	/* 
	 * Metoda wy�wietla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			sb.append("Aktualna osoba: \n");
			sb.append( "      Imi�: " + person.getFirstName() + "\n" );
			sb.append( "  Nazwisko: " + person.getLastName() + "\n" );
			sb.append( "   Rok ur.: " + person.getBirthYear() + "\n" );
			sb.append( "Stanowisko: " + person.getJob() + "\n");
		} else
			sb.append( "Brak danych osoby" + "\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Meoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wype�nia atrybuty wczytanymi danymi.
	 * Walidacja poprawno�ci danych odbywa si� w konstruktorze i setterach
	 * klasy Person. Je�li zostan� wykryte niepoprawne dane 
	 * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imi�: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie warto�ci wszystkich atrybut�w.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Person
			// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
			// poszczeg�lnych atrybut�w.
			// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczyta� nowe dane dla poszczeg�lnych atrybut�w 
	 * obiekty person i zmienia je poprzez wywo�anie odpowiednich setter�w z klasy Person.
	 * Walidacja poprawno�ci wczyranych danych odbywa si� w setterach
	 * klasy Person. Je�li zostan� wykryte niepoprawne dane 
	 * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imi�: "));
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
				// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Person
				// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
				// poszczeg�lnych atrybut�w.
				// Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // koniec klasy PersonConsoleApp
