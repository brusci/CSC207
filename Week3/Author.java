/**
 * 
 */
package w3lab;

/**
 * This Author class represents an Author of a copyrighted product.
 * 
 * @author Robert & Angel
 * @version 1.0, September 2014
 */
public class Author {
	/**
	 * @param first_name the author's first name
	 * @param last_name the author's last name
	 */
	
	String first_name;
	String last_name;
	
	Author(String first_name, String last_name){
	    this.first_name = first_name;
	    this.last_name = last_name;
	}
	
	String getFirstName(){
		return first_name;
	}
	
	void setFirstName(String first_name){
		this.first_name = first_name;
	}
	
	String getLastName(){
		return last_name;
	}
	
	void setLastName(String last_name){
		this.last_name = last_name;
	}
	
	public String toString(){
		return String.format("%s, %s", last_name, first_name);
	}
	
	public static void main(String[] args) {
		// Tests the Author class
		Author object = new Author("Winder", "Russel");
		String test = object.getFirstName();
		String test2 = object.getLastName();
		System.out.printf("%s, %s", test, test2);
		object.toString();
	}

}
