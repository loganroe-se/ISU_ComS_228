package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the Badger class to ensure it works properly.
 * @author Logan Roe
 */
public class BadgerTest {

	/**
	 * Tests the who() method of Badger to ensure it works for various row/column positions and age values.
	 */
	@Test
	public void testWho() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int age = 0; age <= 4; age++) {
					Badger testBadger = new Badger(testPlain, i, j, age);
					assertEquals(State.BADGER, testBadger.who());
				}
			}
		}
	}

	/**
	 * Tests the next() method of Badger using a plain that is made separately for each test.
	 * The separate plains are designed to test each individual criteria at a minimum of one time.
	 */
	@Test
	public void testNext() {
		Plain testPlain = new Plain(2);
		Plain newTestPlain = new Plain(2);
		
		// Tests the first criteria: Empty if the Badger is currently at age 4.
		testPlain.grid[0][0] = new Badger(testPlain, 0, 0, 4);
		testPlain.grid[0][1] = new Badger(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Badger(testPlain, 1, 0, 1);
		testPlain.grid[1][1] = new Badger(testPlain, 1, 1, 2);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));
		
		// Tests the second, third, and fourth criteria: 
		// (2) otherwise, Fox, if there is only one Badger but there are more than one Fox in the neighborhood
		// (3) otherwise, Empty, if Badgers and Foxes together out-number Rabbits in the neighborhood
		// (4) otherwise, Badger (the badger will live on)
		
		// (2)
		testPlain.grid[0][0] = new Badger(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Fox(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Fox(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Empty(testPlain, 1, 1);
		assertEquals(State.FOX, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());

		// (3)
		testPlain.grid[0][0] = new Badger(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Fox(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Rabbit(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Grass(testPlain, 1, 1);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));
		
		// (4)
		testPlain.grid[0][0] = new Badger(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Rabbit(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Grass(testPlain, 1, 0);
		testPlain.grid[1][1] = new Empty(testPlain, 1, 1);
		assertEquals(State.BADGER, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(1, ((Animal) newTestPlain.grid[0][0]).myAge());
	}

	/**
	 * Makes sure that the badger constructor works properly by iterating through various row/column values and ages.
	 */
	@Test
	public void testBadger() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 4; k++) {
					Living testBadger = new Badger(testPlain, i, j, k);
					
					assertEquals(i, testBadger.row);
					assertEquals(j, testBadger.column);
					assertEquals(k, ((Animal) testBadger).myAge());
				}
			}
		}
	}
}
