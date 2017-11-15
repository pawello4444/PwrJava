/* Pawe³ Twardawa pazdziernik 2017 */
package programLab3;

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


import java.util.*;
import java.lang.*;


public class GroupOfBook implements java.lang.Iterable<Books>, Serializable, Observer {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private GroupType type;
	private Collection<Books> collection;
	private GroupOfBook gr1;
	private GroupOfBook gr2;
	private int function;
	//////////////////////////////////////////////////////////////////////////
	public void setGroup(GroupOfBook g1, GroupOfBook g2)
	{
		this.gr1 = g1;
		this.gr2 = g2;
	}
	
	public GroupOfBook getGr1()
	{
		return this.gr1;
	}
	
	public GroupOfBook getGr2()
	{
		return this.gr2;
	}
	
	public void setFunction(int func) 
	{
		this.function = func;
	}
	public int getFunction()
	{
		return this.function;
	}
	////////////////////////////////////////////////////////////////////////
	public void setName(String name) throws BooksException
	{
		if((name == null) || name.equals(""))
			throw new BooksException("Nazwa grupy musi byæ okreslona.");
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public GroupOfBook(GroupType type, String name) throws BooksException
	{
		setName(name);
		if(type == null) {
			throw new BooksException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
		this.function = 0;
		this.gr1 = null;
		this.gr2 = null;
	}
	
	public GroupOfBook(String type_name, String name) throws BooksException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new BooksException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
		this.function = 0;
		this.gr1 = null;
		this.gr2 = null;
	
	}
	
	public GroupType getType() {
		return type;
	}
	
	public void setType(GroupType type) throws BooksException {
		if (type == null) {
			throw new BooksException("Typ kolekcji musi byæ okreœlny.");
		}
		if (this.type == type)
			return;
		
		Collection<Books> oldCollection = collection;
		collection = type.createCollection();
		this.type = type;
		for (Books book : oldCollection)
			collection.add(book);
	}
	
	public void setType(String type_name) throws BooksException {
		for(GroupType type : GroupType.values()){
			if (type.toString().equals(type_name)) {
				setType(type);
				return;
			}
		}
		throw new BooksException("Nie ma takiego typu kolekcji.");
	}
	
	public boolean add(Books e) {
		return collection.add(e);
	}
	
	public Iterator<Books> iterator() {
		return collection.iterator();
	}

	public int size() {
		return collection.size();
	}
	
	public void sortTitle() throws BooksException {
		if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
			throw new BooksException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}

		Collections.sort((List<Books>)collection);
	}
	
	public void sortAutorFirstName() throws BooksException{
		if(type == GroupType.HASH_SET || type == GroupType.TREE_SET)
		{
			throw new BooksException("Kolekcje typu SET nie moga byc sortowane." );
		}
		
		Collections.sort((List<Books>)collection, new Comparator<Books>() {
			@Override
			public int compare(Books o1, Books o2)
			{
				return o1.getAutorFirstName().compareTo(o2.getAutorFirstName());
			}
		});
	}
	
	public void sortAutorLastName() throws BooksException{
		if(type == GroupType.HASH_SET || type == GroupType.TREE_SET)
		{
			throw new BooksException("Kolekcje typu SET nie moga byc sortowane." );
		}
		
		Collections.sort((List<Books>)collection, new Comparator<Books>() {
			@Override
			public int compare(Books o1, Books o2)
			{
				return o1.getAutorLastName().compareTo(o2.getAutorLastName());
			}
		});
	}
	
	public void sortPublishers() throws BooksException{
		if(type == GroupType.HASH_SET || type == GroupType.TREE_SET)
		{
			throw new BooksException("Kolekcje typu SET nie moga byc sortowane." );
		}
		
		Collections.sort((List<Books>)collection, new Comparator<Books>() {
			@Override
			public int compare(Books o1, Books o2)
			{
				return o1.getPublishers().compareTo(o2.getPublishers());
			}
		});
	}
	
	public void sortPublicationYear() throws BooksException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new BooksException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		
		Collections.sort((List<Books>) collection, new Comparator<Books>() {

			@Override
			public int compare(Books o1, Books o2) {
				if (o1.getPublicationYear() < o2.getPublicationYear())
					return -1;
				if (o1.getPublicationYear() > o2.getPublicationYear())
					return 1;
				return 0;
			}

		});
	}
	public void sortPrice() throws BooksException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new BooksException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		
		Collections.sort((List<Books>) collection, new Comparator<Books>() {

			@Override
			public int compare(Books o1, Books o2) {
				if (o1.getPrice() < o2.getPrice())
					return -1;
				if (o1.getPrice() > o2.getPrice())
					return 1;
				return 0;
			}

		});
	}
	
	public void sortGerne() throws BooksException{
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new BooksException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		Collections.sort((List<Books>) collection, new Comparator<Books>() {

			@Override
			public int compare(Books o1, Books o2) {
				return o1.getGerne().toString().compareTo(o2.getGerne().toString());
			}

		});
	}
	
	@Override
	public String toString() {
		return name + "  [" + type + "]";
	}
	public static void printToFile(PrintWriter writer, GroupOfBook group) {
		writer.println(group.getName());
		writer.println(group.getType());
		for (Books book : group.collection)
			Books.toFile(writer, book);
	}


	public static void printToFile(String file_name, GroupOfBook group) throws BooksException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, group);
		} catch (FileNotFoundException e){
			throw new BooksException("Nie odnaleziono pliku " + file_name);
		}
	}


	public static GroupOfBook readFromFile(BufferedReader reader) throws BooksException{
		try {
			String group_name = reader.readLine();
			String type_name = reader.readLine();
			GroupOfBook groupOfPeople = new GroupOfBook(type_name, group_name);

			Books book;
			book = Books.fromFile(reader);
				groupOfPeople.collection.add(book);
			return groupOfPeople;
		} catch(IOException e){
			throw new BooksException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}


	public static GroupOfBook readFromFile(String file_name) throws BooksException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return GroupOfBook.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new BooksException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new BooksException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}

	public static GroupOfBook createGroupOr(GroupOfBook g1,GroupOfBook g2) throws BooksException {
		String name = "(" + g1.name + " OR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfBook group = new GroupOfBook(type, name);
		group.collection.addAll(g1.collection);
		group.collection.addAll(g2.collection);
		group.setGroup(g1, g2);
		group.setFunction(1);
		return group;
	}
	/*
	public void createGroupUnion()
	{
		this.collection.clear();
		String name = "(" + this.gr1.name + " OR " + this.gr2.name +")";
		GroupType type;
		if (this.gr2.collection instanceof Set && !(this.gr1.collection instanceof Set) ){
			type = this.gr2.type;
		} else {
			type = this.gr1.type;
		}
		this.collection.addAll(this.gr1.collection);
		this.collection.addAll(this.gr2.collection);
	}
*/
	public static GroupOfBook createGroupAnd(GroupOfBook g1,GroupOfBook g2) throws BooksException {
		String name = "(" + g1.name + " AND " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfBook group = new GroupOfBook(type, name);
		
		Collection<Books> tmp = group.type.createCollection();
		tmp.addAll(g2.collection);
		
		for(Books book : g1.collection)
		{
			if(tmp.remove(book))
				group.collection.add(book);
		}
		group.setGroup(g1, g2);
		group.setFunction(2);
		return group;
	}
	
	
	public static GroupOfBook createGroupSub(GroupOfBook g1,GroupOfBook g2) throws BooksException {
		String name = "(" + g1.name + " SUB " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfBook group = new GroupOfBook(type, name);

		Collection<Books> tmp = group.type.createCollection();
		
		tmp.addAll(g1.collection);
		for(Books book : g2.collection)
		{
			tmp.remove(book);
				
		}
		group.setGroup(g1, g2);
		group.setFunction(3);
		group.collection.addAll(tmp);
		return group;
	}
	
	public static GroupOfBook createGroupXor(GroupOfBook g1,GroupOfBook g2) throws BooksException {
		String name = "(" + g1.name + " XOR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}

		GroupOfBook group = new GroupOfBook(type, name);
		
		group.collection.addAll(g1.collection);
		
		Collection<Books> tmp2 = group.type.createCollection();
		tmp2.addAll(g2.collection);
		
		for(Books book : g2.collection)
		{
			group.collection.remove(book);
				
		}
		
		for(Books book : g1.collection)
		{
			tmp2.remove(book);
				
		}
		group.collection.addAll(tmp2);
		group.setGroup(g1, g2);
		group.setFunction(4);
		return group;
	}

	@Override
	public void notifyObserver() throws BooksException {
		GroupOfBook book;
		switch(this.function)
		{
		case 1:
			//this.createGroupUnion();
			this.collection.clear();
			//this.collection.addAll((GroupOfBook.createGroupOr(this.gr1, this.gr2)).collection);
			book = GroupOfBook.createGroupOr(this.gr1, this.gr2);
			this.collection = book.collection;
			this.setName(book.name);
			this.setType(book.type);
			//this.collection = (GroupOfBook.createGroupOr(this.gr1, this.gr2)).collection;
			return;
		case 2:
			this.collection.clear();
			book = GroupOfBook.createGroupAnd(this.gr1, this.gr2);
			this.collection = book.collection;
			this.setName(book.name);
			this.setType(book.type);
			return;
		case 3:
			book = GroupOfBook.createGroupSub(this.gr1, this.gr2);
			this.collection = book.collection;
			this.setName(book.name);
			this.setType(book.type);
			return;
		case 4:
			book = GroupOfBook.createGroupXor(this.gr1, this.gr2);
			this.collection = book.collection;
			this.setName(book.name);
			this.setType(book.type);
			return;
		}
		
	}

}
