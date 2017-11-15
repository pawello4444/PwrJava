/* Pawe³ Twardawa pazdziernik 2017 */
package programLab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;

public enum GroupType {

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

	public Collection<Books> createCollection() throws BooksException {
		switch (this) {
		case VECTOR:      return new Vector<Books>();
		case ARRAY_LIST:  return new ArrayList<Books>();
		case HASH_SET:    return new HashSet<Books>();
		case LINKED_LIST: return new LinkedList<Books>();
		case TREE_SET:    return new TreeSet<Books>();
		default:          throw new BooksException("Podany typ kolekcji nie zosta³ zaimplementowany.");
		}
	}


}
