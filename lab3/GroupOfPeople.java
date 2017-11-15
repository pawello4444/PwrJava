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
 * Program: Aplikacja okienkowa z GUI, która umo¿liwia zarz¹dzanie
 *          grupami obiektów klasy Person.
 *    Plik: GroupOfPeople.java
 *
 *   Autor: Pawe³ Rogalinski
 *    Data: pazdziernik 2017 r.
 */

/*
 *  Typ wyliczeniowy GroupType reprezentuje typy kolekcji,
 *  które mog¹ byæ wykorzystane do tworzenia grupy osób.
 *  w programie mo¿na wybraæ dwa rodzaje kolekcji: listy i zbiory.
 *  Ka¿dy rodzaj kolekcji mo¿e byæ implementowany przy pomocy
 *  ró¿nych klas:
 *      Listy: klasa Vector, klasa ArrayList, klasa LinnkedList;
 *     Zbiory: klasa TreeSet, klasa HashSet.
 */
enum GroupType {
	VECTOR("Lista   (klasa Vector)"),
	ARRAY_LIST("Lista   (klasa ArrayList)"),
	LINKED_LIST("Lista   (klasa LinkedList)"),
	HASH_SET("Zbiór   (klasa HashSet)"),
	TREE_SET("Zbiór   (klasa TreeSet)");

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
	// tworzy kolekcjê obiektów klasy Person implementowan¹
	// za pomoc¹ w³aœciwej klasy z pakietu Java Collection Framework.
	public Collection<Person> createCollection() throws PersonException {
		switch (this) {
		case VECTOR:      return new Vector<Person>();
		case ARRAY_LIST:  return new ArrayList<Person>();
		case HASH_SET:    return new HashSet<Person>();
		case LINKED_LIST: return new LinkedList<Person>();
		case TREE_SET:    return new TreeSet<Person>();
		default:          throw new PersonException("Podany typ kolekcji nie zosta³ zaimplementowany.");
		}
	}

}  // koniec klasy enum GroupType




/*
 * Klasa GroupOfPeople reprezentuje grupy osób, które s¹ opisane za pomoc¹
 * trzech atrybutow: name, type oraz collection:
 *     name - nazwa grupy wybierana przez u¿ytkownika
 *            (musi zawieraæ niepusty ci¹g znaków).
 *     type - typ kolekcji, która ma byæ u¿yta do zapamiêtania
 *            danych osób nale¿¹cych do tej grupy.
 *     collection - kolekcja obiektów klasy Person, w której
 *                  pamiêtane s¹ dane osób nale¿¹cych do tej grupy.
 *                  (Musi byæ to obiekt utworzony za pomoc¹ metody
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
			throw new PersonException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}


	public GroupOfPeople(String type_name, String name) throws PersonException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new PersonException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) throws PersonException {
		if ((name == null) || name.equals(""))
			throw new PersonException("Nazwa grupy musi byæ okreœlona.");
		this.name = name;
	}


	public GroupType getType() {
		return type;
	}


	public void setType(GroupType type) throws PersonException {
		if (type == null) {
			throw new PersonException("Typ kolekcji musi byæ okreœlny.");
		}
		if (this.type == type)
			return;
		// Gdy nastêpuje zmiana typu kolekcji to osoby zapamiêtane
		// w dotychczasowej kolekcji musz¹ zostaæ przepisane do nowej
		// kolekcji, która jest implementowana, przy pomocy
		// klasy w³aœciwej dla nowego typu kolekcji.
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


	// Zamiast gettera getCollection zosta³y zaimplementowane
	// niezbêdne metody delegowane z interfejsu Collection,
	// które umo¿liwiaj¹ wykonanie wszystkich operacji na
	// kolekcji osób.

	public boolean add(Person e) {
		return collection.add(e);
	}

	public Iterator<Person> iterator() {
		return collection.iterator();
	}

	public int size() {
		return collection.size();
	}


	// Poni¿ej zosta³y zaimplementowane metody umo¿liwiaj¹ce
	// sortowanie listy osób wed³ug poszczególnych atrybutów.
	// UWAGA: sortowanie jest mo¿liwe tylko dla kolekcji typu Lista.
	public void sortName() throws PersonException {
		if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
			throw new PersonException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystana
		// metoda compareTo bêd¹ca implementacj¹ interfejsu
		// Comparable w klasie Person.
		Collections.sort((List<Person>)collection);
	}

	public void sortBirthYear() throws PersonException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new PersonException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), która implementuje
		// interfejs Comparator i zawiera tylko jedn¹ metodê compare.
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
			throw new PersonException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), która implementuje
		// interfejs Comparator i zawiera tylko jedn¹ metodê compare.
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
			throw new PersonException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}


	public static GroupOfPeople readFromFile(String file_name) throws PersonException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return GroupOfPeople.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new PersonException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new PersonException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}


	//#######################################################################
	//#######################################################################
	//
	// Poni¿ej umieszczono cztery pomocnicze metody do tworzenia specjalnych
	// grup, które s¹ wynikiem wykonania wybranych operacji na dwóch grupach
	// ¿ród³owych. Mo¿liwe s¹ nastêpuj¹ce operacje:
	//   SUMA  - grupa osób zawieraj¹ca wszystkie osoby z grupy pierwszej
	//           oraz wszystkie osoby z grupy drugiej;
	//   ILICZYN - grupa osób, które nale¿¹ zarówno do grupy pierwszej jak i
	//             do grupy drugiej;
	//   RÓ¯NICA - grupa osób, które nale¿¹ do grupy pierwszej
	//             i nie ma ich w grupie drugiej
	//   RÓ¯NICA SYMETRYCZNA - grupa osób, które nale¿¹ do grupy pierwszej
	//             i nie ma ich w grupie drugiej oraz te osoby, które nale¿¹
	//             do grupy drugiej i nie ma w grupie pierwszej.
	//
	//   Nazwa grupy specjalnej jest tworzona wed³ug nastêpuj¹cego wzorca"
	//          ( nazwa1 NNN nazwa2 )
	//   gdzie
	//         nazwa1 - nazwa pierwszej grupy osób,
	//         nazwa2 - nazwa drugiej grupy osób,
	//         NNN - symbol operacji wykonywanej na grupach osób:
	//                   "OR"  - dla operacji typu SUMA,
	//                   "AND" - dla operacji typu ILOCZYN,
	//                   "SUB" - dla operacji typu Ró¿ñica,
	//                   "XOR" - dla operacji typu Ró¿NICA SYMETRYCZNA.
	//
	//   Typ grupy specjalnej zale¿y od typu grup ¿ród³owych i jest wybierany
	//   wed³ug nastêpuj¹cych regu³:
	//  	 - Jeœli obie grupy ¿ród³owe s¹ tego samego rodzaju (lista lub zbiór)
	//  	   to grupa wynikpwa ma taki typ jak pierwsza grupa ¿ród³owa.
	//       - Jeœli grupy ¿ród³owe ró¿ni¹ siê rodzajem (jedna jest list¹, a druga zbioerm)
	//         to grupa wynikowa ma taki sam typ jak grupa ¿ród³owa, która jest zbiorem.
	//
	//   Ilustruje to poni¿sza tabelka
	//       |=====================================================================|
	//       |   grupy ¿ród³owe    |   grupa  |  uwagi dodatkowe                   |
	//       | pierwsza |  druga   | wynikowa |                                    |
	//       |==========|==========|==========|====================================|
	//       |  lista   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
	//       |  lista   |  zbiór   |   zbiór  | typ dziedziczony z grupy drugiej   |
    //       |  zbiór   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  zbiór   |  zbiór   |   zbiór  | typ dziedziczony z grupy pierwszej |
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
		//# Tu nale¿y dopisaæ instrukcje które wyznacz¹ ró¿nicê dwóch                  #
		//#      grup ¿ród³owych                                                       #
		//#   Do grupy nale¿y dodaæ te osoby, które nale¿¹ do grupy pierwszej          #
		//#     i nie nale¿¹ do grupy drugiej;                                         #
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
		//# Tu nale¿y dopisaæ instrukcje które wyznacz¹ ró¿nicê symetryczn¹ dwóch      #
		//#      grup ¿ród³owych                                                       #
		//#   Do grupy nale¿y dodaæ te osoby, które nale¿¹ tylko do grupy pierwszej    #
		//#     lub nale¿¹ tylko do grupy drugiej;                                     #
		//#                                                                            #
		//##############################################################################

		return group;
	}

} // koniec klasy GroupOfPeople
