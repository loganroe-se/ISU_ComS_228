package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * Tests the updatePlain() method of Wildlife and tells the user how to test main in comments.
 * @author Logan Roe
 */
class WildlifeTest {

	/**
	 * Tests updatePlain() by iterating through some cycles of a given plain.
	 * The exact expected values are then checked to see if they are right after the updates have been done.
	 * @throws FileNotFoundException
	 */
	@Test
	void testUpdatePlain() throws FileNotFoundException {
		Plain evenPublic1 = new Plain("public1-3x3.txt");
		Plain oddPublic1 = new Plain(evenPublic1.getWidth());
		
		for (int i = 1; i < 5; i++) {
			if (i % 2 == 0) {
				Wildlife.updatePlain(oddPublic1, evenPublic1);
			} else {
				Wildlife.updatePlain(evenPublic1, oddPublic1);
			}
		}
		
		Scanner scnr = new Scanner(oddPublic1.toString());
		
		assertEquals("G", scnr.next());
		assertEquals("F0", scnr.next());
		assertEquals("E", scnr.next());
		
		assertEquals("E", scnr.next());
		assertEquals("E", scnr.next());
		assertEquals("F0", scnr.next());
		
		assertEquals("F0", scnr.next());
		assertEquals("F0", scnr.next());
		assertEquals("G", scnr.next());
		
		scnr.close();
	}

	@Test
	void testMain() throws FileNotFoundException {	
		Wildlife.main(new String[0]);
		
		// Enter "2" then "public1-3x3.txt" then "5" and the output for final plain should be:
		// G  F0 E  
		// E  E  F0
		// F0 F0 G  
		// Further, entering "1" should generate a random grid.
		// Entering "3" should terminate the program.
	}

}
