package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * Runs basic tests for each method of Living at least once.
 * @author Logan Roe
 */
public class LivingTest {

	/**
	 * Tests if the constructor in Living works properly for a variety of different cases and Living objects.
	 */
	@Test
	public void testLiving() {
		Plain testPlain = new Plain(3);
		Living testBadger = new Badger(testPlain, 0, 0, 2);
		Living testFox = new Fox(testPlain, 0, 1, 1);
		Living testRabbit = new Rabbit(testPlain, 0, 2, 3);
		Living testGrass = new Grass(testPlain, 1, 0);
		Living testEmpty = new Empty(testPlain, 1, 1);
		
		assertEquals(0, testBadger.row);
		assertEquals(0, testBadger.column);
		assertEquals(2, ((Animal) testBadger).myAge());
		
		assertEquals(0, testFox.row);
		assertEquals(1, testFox.column);
		assertEquals(1, ((Animal) testFox).myAge());
		
		assertEquals(0, testRabbit.row);
		assertEquals(2, testRabbit.column);
		assertEquals(3, ((Animal) testRabbit).myAge());
		
		assertEquals(1, testGrass.row);
		assertEquals(0, testGrass.column);
		
		assertEquals(1, testEmpty.row);
		assertEquals(1, testEmpty.column);
	}

	/**
	 * Extensively tests census() by going through 4 rows of a 6 X 6 grid and testing each value of those four rows.
	 * @throws FileNotFoundException
	 */
	@Test
	public void testCensus() throws FileNotFoundException {
		Plain testPlain = new Plain("public2-6x6.txt");
		int[] testPopulation = new int[5];
		
		// Note: testPopulation is in this order: Badger [0], Empty [1], Fox [2], Grass[3], Rabbit[4]
		
		// Row 1
		testPlain.grid[0][0].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(1, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(0, testPopulation[4]);
		assertEquals(4, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[0][1].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(0, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[0][2].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[0][3].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[0][4].census(testPopulation);
		assertEquals(0, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[0][5].census(testPopulation);
		assertEquals(0, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(4, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		// Row 2
		testPlain.grid[1][0].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[1][1].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[1][2].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(2, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[1][3].census(testPopulation);
		assertEquals(3, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[1][4].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[1][5].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		// Row 3
		testPlain.grid[2][0].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[2][1].census(testPopulation);
		assertEquals(3, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[2][2].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(3, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[2][3].census(testPopulation);
		assertEquals(3, testPopulation[0]);
		assertEquals(1, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(3, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[2][4].census(testPopulation);
		assertEquals(2, testPopulation[0]);
		assertEquals(1, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(3, testPopulation[4]);
		assertEquals(9, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[2][5].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(1, testPopulation[1]);
		assertEquals(1, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		// I did not include rows 4-5 as this would be enough data to know whether census works properly or not.
		// However, I did row 6 to test more edge cases.
		
		// Row 6
		testPlain.grid[5][0].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(1, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(0, testPopulation[4]);
		assertEquals(4, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[5][1].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(0, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[5][2].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(2, testPopulation[3]);
		assertEquals(0, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
	
		testPlain.grid[5][3].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(3, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(1, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[5][4].census(testPopulation);
		assertEquals(1, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(1, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(6, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
		
		testPlain.grid[5][5].census(testPopulation);
		assertEquals(0, testPopulation[0]);
		assertEquals(2, testPopulation[1]);
		assertEquals(0, testPopulation[2]);
		assertEquals(0, testPopulation[3]);
		assertEquals(2, testPopulation[4]);
		assertEquals(4, (testPopulation[0] + testPopulation[1] + testPopulation[2] + testPopulation[3] + testPopulation[4]));
	}

	/**
	 * Tests the who() method of Living for a very wide range of potential values.
	 */
	@Test
	public void testWho() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int age = 0; age <= 3; age++) {
					Badger testBadger = new Badger(testPlain, i, j, age);
					assertEquals(State.BADGER, testBadger.who());
					
					Fox testFox = new Fox(testPlain, i, j, age);
					assertEquals(State.FOX, testFox.who());
					
					Rabbit testRabbit = new Rabbit(testPlain, i, j, age);
					assertEquals(State.RABBIT, testRabbit.who());
				}
				
				Grass testGrass = new Grass(testPlain, i, j);
				assertEquals(State.GRASS, testGrass.who());
				
				Empty testEmpty = new Empty(testPlain, i, j);
				assertEquals(State.EMPTY, testEmpty.who());
			}
		}
	}
	
	/**
	 * Tests the next() method of Living for the entirety of a grid for one cycle.
	 * @throws FileNotFoundException
	 */
	@Test
	public void testNext() throws FileNotFoundException {
		// Note: Trial2 is the same as Trial 2 in the spec sheet.
		Plain testPlain = new Plain("Trial2.txt");
		Plain newTestPlain = new Plain("Trial2.txt");
		
		// ONE CYCLE
		// ROW 1
		testPlain.grid[0][0].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[0][0].who());
		
		testPlain.grid[0][1].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[0][1].who());
		
		testPlain.grid[0][2].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[0][2].who());
		
		testPlain.grid[0][3].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[0][3].who());
		assertEquals(1, ((Animal) newTestPlain.grid[0][3]).myAge());
		
		testPlain.grid[0][4].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[0][4].who());
		
		testPlain.grid[0][5].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[0][5].who());
		
		
		
		// ROW 2
		testPlain.grid[1][0].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[1][0].who());
		
		testPlain.grid[1][1].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[1][1].who());
		
		testPlain.grid[1][2].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[1][2].who());
		assertEquals(1, ((Animal) newTestPlain.grid[1][2]).myAge());
		
		testPlain.grid[1][3].next(newTestPlain);
		assertEquals(State.BADGER, newTestPlain.grid[1][3].who());
		assertEquals(1, ((Animal) newTestPlain.grid[1][3]).myAge());
		
		testPlain.grid[1][4].next(newTestPlain);
		assertEquals(State.BADGER, newTestPlain.grid[1][4].who());
		assertEquals(0, ((Animal) newTestPlain.grid[1][4]).myAge());
		
		testPlain.grid[1][5].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[1][5].who());
		assertEquals(1, ((Animal) newTestPlain.grid[1][5]).myAge());
		
		
		
		// ROW 3
		testPlain.grid[2][0].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[2][0].who());
		
		testPlain.grid[2][1].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[2][1].who());
		assertEquals(0, ((Animal) newTestPlain.grid[2][1]).myAge());
		
		testPlain.grid[2][2].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[2][2].who());
		assertEquals(0, ((Animal) newTestPlain.grid[2][2]).myAge());
		
		testPlain.grid[2][3].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[2][3].who());
		assertEquals(1, ((Animal) newTestPlain.grid[2][3]).myAge());
		
		testPlain.grid[2][4].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[2][4].who());
		
		testPlain.grid[2][5].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[2][5].who());
		assertEquals(0, ((Animal) newTestPlain.grid[2][5]).myAge());
		
		
		
		// ROW 4
		testPlain.grid[3][0].next(newTestPlain);
		assertEquals(State.FOX, newTestPlain.grid[3][0].who());
		assertEquals(0, ((Animal) newTestPlain.grid[3][0]).myAge());
		
		testPlain.grid[3][1].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[3][1].who());
		assertEquals(0, ((Animal) newTestPlain.grid[3][1]).myAge());
		
		testPlain.grid[3][2].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[3][2].who());
		assertEquals(1, ((Animal) newTestPlain.grid[3][2]).myAge());
		
		testPlain.grid[3][3].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[3][3].who());
		assertEquals(1, ((Animal) newTestPlain.grid[3][3]).myAge());
		
		testPlain.grid[3][4].next(newTestPlain);
		assertEquals(State.BADGER, newTestPlain.grid[3][4].who());
		assertEquals(1, ((Animal) newTestPlain.grid[3][4]).myAge());
		
		testPlain.grid[3][5].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[3][5].who());
		assertEquals(1, ((Animal) newTestPlain.grid[3][5]).myAge());
		
		
		
		// ROW 5
		testPlain.grid[4][0].next(newTestPlain);
		assertEquals(State.FOX, newTestPlain.grid[4][0].who());
		assertEquals(1, ((Animal) newTestPlain.grid[4][0]).myAge());
		
		testPlain.grid[4][1].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[4][1].who());
		assertEquals(0, ((Animal) newTestPlain.grid[4][1]).myAge());
		
		testPlain.grid[4][2].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[4][2].who());
		assertEquals(1, ((Animal) newTestPlain.grid[4][2]).myAge());
		
		testPlain.grid[4][3].next(newTestPlain);
		assertEquals(State.RABBIT, newTestPlain.grid[4][3].who());
		assertEquals(0, ((Animal) newTestPlain.grid[4][3]).myAge());
		
		testPlain.grid[4][4].next(newTestPlain);
		assertEquals(State.FOX, newTestPlain.grid[4][4].who());
		assertEquals(0, ((Animal) newTestPlain.grid[4][4]).myAge());
		
		testPlain.grid[4][5].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[4][5].who());
		
		
		
		// ROW 6
		testPlain.grid[5][0].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[5][0].who());
		
		testPlain.grid[5][1].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[5][1].who());
		
		testPlain.grid[5][2].next(newTestPlain);
		assertEquals(State.FOX, newTestPlain.grid[5][2].who());
		assertEquals(1, ((Animal) newTestPlain.grid[5][2]).myAge());
		
		testPlain.grid[5][3].next(newTestPlain);
		assertEquals(State.FOX, newTestPlain.grid[5][3].who());
		assertEquals(1, ((Animal) newTestPlain.grid[5][3]).myAge());
		
		testPlain.grid[5][4].next(newTestPlain);
		assertEquals(State.GRASS, newTestPlain.grid[5][4].who());
		
		testPlain.grid[5][5].next(newTestPlain);
		assertEquals(State.EMPTY, newTestPlain.grid[5][5].who());
	}
}
