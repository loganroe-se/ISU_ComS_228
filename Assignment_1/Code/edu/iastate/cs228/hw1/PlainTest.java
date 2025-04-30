package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * Runs numerous tests that will test all methods in Plain at least once.
 * @author Logan Roe
 */
class PlainTest {

	/**
	 * Tests the creation of a Plain from a file constructor.
	 * This is done by reading in a Plain from a file and checking 
	 * to see if the State's Plain finds are accurate to what they should be.
	 * It will also double check that all ages are being initialized properly.
	 * @throws FileNotFoundException
	 */
	@Test
	void testPlainString() throws FileNotFoundException {
		Plain testPlain = new Plain("public2-6x6.txt");
		
		// ROW 1
		assertEquals(State.FOX, ((Living) testPlain.grid[0][0]).who());
		assertEquals(0, ((Animal) testPlain.grid[0][0]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[0][1]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[0][2]).who());
		assertEquals(State.FOX, ((Living) testPlain.grid[0][3]).who());
		assertEquals(0, ((Animal) testPlain.grid[0][3]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[0][4]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[0][5]).who());
		
		// ROW 2
		assertEquals(State.BADGER, ((Living) testPlain.grid[1][0]).who());
		assertEquals(0, ((Animal) testPlain.grid[1][0]).myAge());
		assertEquals(State.FOX, ((Living) testPlain.grid[1][1]).who());
		assertEquals(0, ((Animal) testPlain.grid[1][1]).myAge());
		assertEquals(State.BADGER, ((Living) testPlain.grid[1][2]).who());
		assertEquals(0, ((Animal) testPlain.grid[1][2]).myAge());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[1][3]).who());
		assertEquals(0, ((Animal) testPlain.grid[1][3]).myAge());
		assertEquals(State.GRASS, ((Living) testPlain.grid[1][4]).who());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[1][5]).who());
		assertEquals(0, ((Animal) testPlain.grid[1][5]).myAge());
		
		// ROW 3
		assertEquals(State.RABBIT, ((Living) testPlain.grid[2][0]).who());
		assertEquals(0, ((Animal) testPlain.grid[2][0]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[2][1]).who());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[2][2]).who());
		assertEquals(0, ((Animal) testPlain.grid[2][2]).myAge());
		assertEquals(State.BADGER, ((Living) testPlain.grid[2][3]).who());
		assertEquals(0, ((Animal) testPlain.grid[2][3]).myAge());
		assertEquals(State.BADGER, ((Living) testPlain.grid[2][4]).who());
		assertEquals(0, ((Animal) testPlain.grid[2][4]).myAge());
		assertEquals(State.GRASS, ((Living) testPlain.grid[2][5]).who());
		
		// ROW 4
		assertEquals(State.BADGER, ((Living) testPlain.grid[3][0]).who());
		assertEquals(0, ((Animal) testPlain.grid[3][0]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[3][1]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[3][2]).who());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[3][3]).who());
		assertEquals(0, ((Animal) testPlain.grid[3][3]).myAge());
		assertEquals(State.FOX, ((Living) testPlain.grid[3][4]).who());
		assertEquals(0, ((Animal) testPlain.grid[3][4]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[3][5]).who());
		
		// ROW 5
		assertEquals(State.BADGER, ((Living) testPlain.grid[4][0]).who());
		assertEquals(0, ((Animal) testPlain.grid[4][0]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[4][1]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[4][2]).who());
		assertEquals(State.GRASS, ((Living) testPlain.grid[4][3]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[4][4]).who());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[4][5]).who());
		assertEquals(0, ((Animal) testPlain.grid[4][5]).myAge());
		
		// ROW 6
		assertEquals(State.GRASS, ((Living) testPlain.grid[5][0]).who());
		assertEquals(State.GRASS, ((Living) testPlain.grid[5][1]).who());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[5][2]).who());
		assertEquals(State.BADGER, ((Living) testPlain.grid[5][3]).who());
		assertEquals(0, ((Animal) testPlain.grid[5][3]).myAge());
		assertEquals(State.RABBIT, ((Living) testPlain.grid[5][4]).who());
		assertEquals(0, ((Animal) testPlain.grid[5][4]).myAge());
		assertEquals(State.EMPTY, ((Living) testPlain.grid[5][5]).who());
	}

	/**
	 * This tests whether the constructor that creates a w width grid does not initialize the values.
	 * It also checks that the width is set properly.
	 */
	@Test
	void testPlainInt() {
		Plain testPlain = new Plain(3);
		
		assertEquals(3, testPlain.getWidth());
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(null, testPlain.grid[i][j]);
			}
		}
	}

	/**
	 * Tests the getWidth() method of Plain. It ensures that the proper width is had for a variety of possible grid sizes.
	 */
	@Test
	void testGetWidth() {
		Plain testPlain;
		
		for (int i = 0; i < 9; i++) {
			testPlain = new Plain(i);
			assertEquals(i, (testPlain.getWidth()));
		}
	}

	/**
	 * Tests if the randomInit() functions randomly generates values for all slots in a Plain.
	 * This is tested for a very wide range of values.
	 */
	@Test
	void testRandomInit() {
		Plain testPlain;
		
		for (int i = 0; i < 25; i++) {
			testPlain = new Plain(3);
			testPlain.randomInit();
			
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					State tempState = ((Living) testPlain.grid[j][k]).who();
					assertTrue((tempState == State.BADGER) || (tempState == State.EMPTY) || (tempState == State.FOX) || (tempState == State.GRASS) || (tempState == State.RABBIT));
					
					if ((tempState == State.BADGER) || (tempState == State.FOX) || (tempState == State.RABBIT)) {
						int tempAge = (((Animal) testPlain.grid[j][k]).myAge());
						assertEquals(0, tempAge);
					}
				}
			}
		}
	}

	/**
	 * Tests the toString() method of Plain. It ensures that the toString() method properly converts a Plain to a string value.
	 * This is done by using a predetermined grid layout and checking if the string values match the expected values.
	 * @throws FileNotFoundException
	 */
	@Test
	void testToString() throws FileNotFoundException {
		Plain testPlain = new Plain("public1-3x3.txt");
		Scanner scnr = new Scanner(testPlain.toString());
		
		assertEquals("G", scnr.next());
		assertEquals("B0", scnr.next());
		assertEquals("F0", scnr.next());
		
		assertEquals("F0", scnr.next());
		assertEquals("F0", scnr.next());
		assertEquals("R0", scnr.next());
		
		assertEquals("F0", scnr.next());
		assertEquals("E", scnr.next());
		assertEquals("G", scnr.next());
		
		scnr.close();
	}

	/**
	 * Reads and then writes a given grid to a file. This file is then read here to check the values to ensure the writing was done properly.
	 * This is done by using a predetermined grid layout and checking if the string values match the expected values.
	 * @throws FileNotFoundException
	 */
	@Test
	void testWrite() throws FileNotFoundException {
		Plain testPlain = new Plain("public1-3x3.txt");
		testPlain.write("JUnitTesting.txt");
		
		Plain testPlain2 = new Plain("JUnitTesting.txt");
		Scanner scnr = new Scanner(testPlain2.toString());
		
		assertEquals("G", scnr.next());
		assertEquals("B0", scnr.next());
		assertEquals("F0", scnr.next());
		
		assertEquals("F0", scnr.next());
		assertEquals("F0", scnr.next());
		assertEquals("R0", scnr.next());
		
		assertEquals("F0", scnr.next());
		assertEquals("E", scnr.next());
		assertEquals("G", scnr.next());
		
		scnr.close();
	}

}
