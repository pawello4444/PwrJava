import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/*
 * Program: Aplikacja okienkowa z GUI, kt�ra umo�liwia zarz�dzanie
 *          grupami obiekt�w klasy Person.
 *    Plik: GroupOfPeople.java
 *
 *   Autor: Pawe� Rogalinski
 *    Data: pazdziernik 2017 r.
 */

/*
 *  Typ wyliczeniowy GroupType reprezentuje typy kolekcji,
 *  kt�re mog� by� wykorzystane do tworzenia grupy os�b.
 *  w programie mo�na wybra� dwa rodzaje kolekcji: listy i zbiory.
 *  Ka�dy rodzaj kolekcji mo�e by� implementowany przy pomocy
 *  r�nych klas:
 *      Listy: klasa Vector, klasa ArrayList, klasa LinnkedList;
 *     Zbiory: klasa TreeSet, klasa HashSet.
 */
enum GroupType {
	VECTOR("Lista   (klasa Vector)"),
	ARRAY_LIST("Lista   (klasa ArrayList)"),
	LINKED_LIST("Lista   (klasa LinkedList)"),
	HASH_SET("Zbi�r   (klasa HashSet)"),
	TREE_SET("Zbi�r   (klasa TreeSet)");

	String typeName;

	private GroupType(String type_name) {
		typeName = type_name;
	}


	@Override
	public String toString() {
		return typeName;
	}


	public static GroupType find(String type_name){
		for(GroupType type : values()){
			if (type.typeName.equals(type_name)){
				return type;
			}
		}
		return null;
	}


	// Metoda createCollection() dla wybranego typu grupy
	// tworzy kolekcj� obiekt�w klasy Person implementowan�
	// za pomoc� w�a�ciwej klasy z pakietu Java Collection Framework.
	public Collection<Person> createCollection() throws PersonException {
		switch (this) {
		case VECTOR:      return new Vector<Person>();
		case ARRAY_LIST:  return new ArrayList<Person>();
		case HASH_SET:    return new HashSet<Person>();
		case LINKED_LIST: return new LinkedList<Person>();
		case TREE_SET:    return new TreeSet<Person>();
		default:          throw new PersonException("Podany typ kolekcji nie zosta� zaimplementowany.");
		}
	}

}  // koniec klasy enum GroupType




/*
 * Klasa GroupOfPeople reprezentuje grupy os�b, kt�re s� opisane za pomoc�
 * trzech atrybutow: name, type oraz collection:
 *     name - nazwa grupy wybierana przez u�ytkownika
 *            (musi zawiera� niepusty ci�g znak�w).
 *     type - typ kolekcji, kt�ra ma by� u�yta do zapami�tania
 *            danych os�b nale��cych do tej grupy.
 *     collection - kolekcja obiekt�w klasy Person, w kt�rej
 *                  pami�tane s� dane os�b nale��cych do tej grupy.
 *                  (Musi by� to obiekt utworzony za pomoc� metody
 *                  createCollection z typu wyliczeniowego GroupType).
 */
public class GroupOfPeople implements Iterable<Person>, Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private GroupType type;
	private Collection<Person> collection;


	public GroupOfPeople(GroupType type, String name) throws PersonException {
		setName(name);
		if (type==null){
			throw new PersonException("Nieprawid�owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}


	public GroupOfPeople(String type_name, String name) throws PersonException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new PersonException("Nieprawid�owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) throws PersonException {
		if ((name == null) || name.equals(""))
			throw new PersonException("Nazwa grupy musi by� okre�lona.");
		this.name = name;
	}


	public GroupType getType() {
		return type;
	}


	public void setType(GroupType type) throws PersonException {
		if (type == null) {
			throw new PersonException("Typ kolekcji musi by� okre�lny.");
		}
		if (this.type == type)
			return;
		// Gdy nast�puje zmiana typu kolekcji to osoby zapami�tane
		// w dotychczasowej kolekcji musz� zosta� przepisane do nowej
		// kolekcji, kt�ra jest implementowana, przy pomocy
		// klasy w�a�ciwej dla nowego typu kolekcji.
		Collection<Person> oldCollection = collection;
		collection = type.createCollection();
		this.type = type;
		for (Person person : oldCollection)
			collection.add(person);
	}


	public void setType(String type_name) throws PersonException {
		for(GroupType type : GroupType.values()){
			if (type.toString().equals(type_name)) {
				setType(type);
				return;
			}
		}
		throw new PersonException("Nie ma takiego typu kolekcji.");
	}


	// Zamiast gettera getCollection zosta�y zaimplementowane
	// niezb�dne metody delegowane z interfejsu Collection,
	// kt�re umo�liwiaj� wykonanie wszystkich operacji na
	// kolekcji os�b.

	public boolean add(Person e) {
		return collection.add(e);
	}

	public Iterator<Person> iterator() {
		return collection.iterator();
	}

	public int size() {
		return collection.size();
	}


	// Poni�ej zosta�y zaimplementowane metody umo�liwiaj�ce
	// sortowanie listy os�b wed�ug poszczeg�lnych atrybut�w.
	// UWAGA: sortowanie jest mo�liwe tylko dla kolekcji typu Lista.
	public void sortName() throws PersonException {
		if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
			throw new PersonException("Kolekcje typu SET nie mog� by� sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystana
		// metoda compareTo b�d�ca implementacj� interfejsu
		// Comparable w klasie Person.
		Collections.sort((List<Person>)collection);
	}

	public void sortBirthYear() throws PersonException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new PersonException("Kolekcje typu SET nie mog� by� sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), kt�ra implementuje
		// interfejs Comparator i zawiera tylko jedn� metod� compare.
		Collections.sort((List<Person>) collection, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				if (o1.getBirthYear() < o2.getBirthYear())
					return -1;
				if (o1.getBirthYear() > o2.getBirthYear())
					return 1;
				return 0;
			}

		});
	}

	public void sortJob() throws PersonException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new PersonException("Kolekcje typu SET nie mog� by� sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), kt�ra implementuje
		// interfejs Comparator i zawiera tylko jedn� metod� compare.
		Collections.sort((List<Person>) collection, new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getJob().toString().compareTo(o2.getJob().toString());
			}

		});
	}


	@Override
	public String toString() {
		return name + "  [" + type + "]";
	}


	public static void printToFile(PrintWriter writer, GroupOfPeople group) {
		writer.println(group.getName());
		writer.println(group.getType());
		for (Person person : group.collection)
			Person.printToFile(writer, person);
	}


	public static void printToFile(String file_name, GroupOfPeople group) throws PersonException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, group);
		} catch (FileNotFoundException e){
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		}
	}


	public static GroupOfPeople readFromFile(BufferedReader reader) throws PersonException{
		try {
			String group_name = reader.readLine();
			String type_name = reader.readLine();
			GroupOfPeople groupOfPeople = new GroupOfPeople(type_name, group_name);

			Person person;
			while((person = Person.readFromFile(reader)) != null)
				groupOfPeople.collection.add(person);
			return groupOfPeople;
		} catch(IOException e){
			throw new PersonException("Wyst�pi� b��d podczas odczytu danych z pliku.");
		}
	}


	public static GroupOfPeople readFromFile(String file_name) throws PersonException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return GroupOfPeople.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new PersonException("Wyst�pi� b��d podczas odczytu danych z pliku.");
		}
	}


	//#######################################################################
	//#######################################################################
	//
	// Poni�ej umieszczono cztery pomocnicze metody do tworzenia specjalnych
	// grup, kt�re s� wynikiem wykonania wybranych operacji na dw�ch grupach
	// �r�d�owych. Mo�liwe s� nast�puj�ce operacje:
	//   SUMA  - grupa os�b zawieraj�ca wszystkie osoby z grupy pierwszej
	//           oraz wszystkie osoby z grupy drugiej;
	//   ILICZYN - grupa os�b, kt�re nale�� zar�wno do grupy pierwszej jak i
	//             do grupy drugiej;
	//   RӯNICA - grupa os�b, kt�re nale�� do grupy pierwszej
	//             i nie ma ich w grupie drugiej
	//   RӯNICA SYMETRYCZNA - grupa os�b, kt�re nale�� do grupy pierwszej
	//             i nie ma ich w grupie drugiej oraz te osoby, kt�re nale��
	//             do grupy drugiej i nie ma w grupie pierwszej.
	//
	//   Nazwa grupy specjalnej jest tworzona wed�ug nast�puj�cego wzorca"
	//          ( nazwa1 NNN nazwa2 )
	//   gdzie
	//         nazwa1 - nazwa pierwszej grupy os�b,
	//         nazwa2 - nazwa drugiej grupy os�b,
	//         NNN - symbol operacji wykonywanej na grupach os�b:
	//                   "OR"  - dla operacji typu SUMA,
	//                   "AND" - dla operacji typu ILOCZYN,
	//                   "SUB" - dla operacji typu R��ica,
	//                   "XOR" - dla operacji typu R�NICA SYMETRYCZNA.
	//
	//   Typ grupy specjalnej zale�y od typu grup �r�d�owych i jest wybierany
	//   wed�ug nast�puj�cych regu�:
	//  	 - Je�li obie grupy �r�d�owe s� tego samego rodzaju (lista lub zbi�r)
	//  	   to grupa wynikpwa ma taki typ jak pierwsza grupa �r�d�owa.
	//       - Je�li grupy �r�d�owe r�ni� si� rodzajem (jedna jest list�, a druga zbioerm)
	//         to grupa wynikowa ma taki sam typ jak grupa �r�d�owa, kt�ra jest zbiorem.
	//
	//   Ilustruje to poni�sza tabelka
	//       |=====================================================================|
	//       |   grupy �r�d�owe    |   grupa  |  uwagi dodatkowe                   |
	//       | pierwsza |  druga   | wynikowa |                                    |
	//       |==========|==========|==========|====================================|
	//       |  lista   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
	//       |  lista   |  zbi�r   |   zbi�r  | typ dziedziczony z grupy drugiej   |
    //       |  zbi�r   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  zbi�r   |  zbi�r   |   zbi�r  | typ dziedziczony z grupy pierwszej |
	//       =======================================================================
	//
	//##################################################################################
	//##################################################################################

	public static GroupOfPeople createGroupUnion(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
		String name = "(" + g1.name + " OR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfPeople group = new GroupOfPeople(type, name);
		group.collection.addAll(g1.collection);
		group.collection.addAll(g2.collection);
		return group;
	}



	public static GroupOfPeople createGroupDifference(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
		String name = "(" + g1.name + " SUB " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfPeople group = new GroupOfPeople(type, name);

		//##############################################################################
		//#                                                                            #
		//# Tu nale�y dopisa� instrukcje kt�re wyznacz� r�nic� dw�ch                  #
		//#      grup �r�d�owych                                                       #
		//#   Do grupy nale�y doda� te osoby, kt�re nale�� do grupy pierwszej          #
		//#     i nie nale�� do grupy drugiej;                                         #
		//#                                                                            #
		//##############################################################################

		return group;
	}


	public static GroupOfPeople createGroupSymmetricDiff(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
		String name = "(" + g1.name + " XOR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}

		GroupOfPeople group = new GroupOfPeople(type, name);

		//##############################################################################
		//#                                                                            #
		//# Tu nale�y dopisa� instrukcje kt�re wyznacz� r�nic� symetryczn� dw�ch      #
		//#      grup �r�d�owych                                                       #
		//#   Do grupy nale�y doda� te osoby, kt�re nale�� tylko do grupy pierwszej    #
		//#     lub nale�� tylko do grupy drugiej;                                     #
		//#                                                                            #
		//##############################################################################

		return group;
	}

} // koniec klasy GroupOfPeople
