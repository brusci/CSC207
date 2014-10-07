/**
 * 
 */
package w3lab;

/**
 * This Book class represents a Book with Authors, ISBN, Price, Title.
 * 
 * @author Robert & Angel
 * @version 1.0, September 2014
 */
public class Book {
	/**
	 * @param title The book's title name
	 * @param authors The class authors stored inside class book.
	 * @param ISBN The unique international code to identify books.
	 * @param price The Canadian price of each book.
	 */
	private String title;
	private Author[] authors;
	private String ISBN;
	private double price;
	
	Book(String title, Author[] authors, String ISBN, double price){
		/**
		 * The Book initialization method, includes all pertinent information
		 * about the instance variables of the Book class.
		 */
		this.title = title;
		this.authors = authors;
		this.ISBN = ISBN;
		this.price = price;
	}
	
	// Setters and Getters for title, authors, ISBN, and price
	// were automatically generated by eclipse
	// i.e. Source>Generate Getters and Setters...
	// Then, Check all the instance variables
	// click OK
	
	// Setters and Getters below.
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	} 

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//Setters and Getters above.

	public String toString(){
		/**
		 * Overrides Java's default toString() Method,
		 * this requires us to use public modifier.
		 * 
		 * Note, this method calls Author toString() method,
		 * which simply returns a first and last name, formatted.
		 */
		String str = "";
		for (Author item : authors){
			String s = item.toString();
			str += s + "\n"; //concatenates each author with a new line after each author.
		}
		return String.format("%s (%s, $%.2f) is written by:\n%s", title, ISBN, price, str);
	}

	public static void main(String[] args) {
		/*Simple test application, creates two author objects
		// puts them into an array
		// then creates a book object and stores the array
		// finally we print the Books toString() method
		///
		// The Book has a parameter of Author[], the syntax requires
		// we store Author objects in an array {} before we
		// are allowed to create a book object
		// i.e. Book(Author[] authors) would disallow: 
		// Author object1 = new Author("Winder", "Russel");
		// Book object2 = new Book(object1);
		// But would allow:
		// Author object1 = new Author("Winder", "Russel");
		// Author[] authors = {object1};
		// Book object2 = new Book(authors);
		// or
		// Author[] authors = {new Author("Roberts", "Graham")};
		// Book object2 = new Book(authors);
		*/
		Author[] authors = {new Author("Russel", "Winder"), new Author("Graham", "Roberts")};
		Book object = new Book("Developing Java Software", authors, "978-0470090251", 79.75);
		String book_rep = object.toString();
		System.out.print(book_rep);
		}
}