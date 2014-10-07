package new_package;

public class W4Lab {
	public static void main (String []args){
		Organism lobster = new Arthropod("Homarus gammarus", 0, 0, "north", 2, 10); /*
		/note that on the hand out, 'north' and 2 are swapped, but that's because we had the order of the variables
		 * in our constructor slightly different from the one in the hand out 
		 */
		System.out.println(lobster);
		for (int i = 0; i<20; i++){ 
			lobster.move();
			System.out.println(lobster);
		}
		
		/*
		 * The for loop starts at i = 0 (an integer), and will add one to i at each iteration (hence the i++)
		 * and will keep iterating so long as i < 20 (i.e. from i = 0 to i = 19, which is 20 iterations)
		 */

	}
	
}
