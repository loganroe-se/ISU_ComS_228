package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Empty class to ensure it works properly.
 * @author Logan Roe
 */
class EmptyTest {

	/**
	 * Tests the who() method of Badger to ensure it works for various row/column positions.
	 */
	@Test
	void testWho() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Empty testEmpty = new Empty(testPlain, i, j);
				assertEquals(State.EMPTY, testEmpty.who());

			}
		}
	}

	/**
	 * Tests the next() method of Empty using a plain that is made separately for each test.
	 * The separate plains are designed to test each individual criteria at a minimum of one time.
	 */
	@Test
	void testNext() {
		Plain testPlain = new Plain(2);
		Plain newTestPlain = new Plain(2);
		
		// Tests the first criteria: Rabbit, if more than one neighboring Rabbit.
		testPlain.grid[0][0] = new Empty(testPlain, 0, 0);
		testPlain.grid[0][1] = new Rabbit(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Rabbit(testPlain, 1, 0, 1);
		testPlain.grid[1][1] = new Fox(testPlain, 1, 1, 2);
		assertEquals(State.RABBIT, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());
		
		// Tests the second, third, fourth, and fifth criteria: 
		// (2) otherwise, Fox, if more than one neighboring Fox
		// (3) otherwise, Badger, if more than one neighboring Badger
		// (4) otherwise, Grass, if at least one neighboring Grass
		// (5) otherwise, Empty
		
		// (2)
		testPlain.grid[0][0] = new Empty(testPlain, 0, 0);
		testPlain.grid[0][1] = new Rabbit(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Fox(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Fox(testPlain, 1, 1, 0);
		assertEquals(State.FOX, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());

		// (3)
		testPlain.grid[0][0] = new Empty(testPlain, 0, 0);
		testPlain.grid[0][1] = new Badger(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Badger(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Fox(testPlain, 1, 1, 0);
		assertEquals(State.BADGER, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());
		
		// (4)
		testPlain.grid[0][0] = new Empty(testPlain, 0, 0);
		testPlain.grid[0][1] = new Grass(testPlain, 0, 1);
		testPlain.grid[1][0] = new Badger(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Fox(testPlain, 1, 1, 0);
		assertEquals(State.GRASS, (testPlain.grid[0][0].next(newTestPlain).who()));
		
		// (5)
		testPlain.grid[0][0] = new Empty(testPlain, 0, 0);
		testPlain.grid[0][1] = new Fox(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Rabbit(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Empty(testPlain, 1, 1);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));
	}

	/**
	 * Makes sure that the grass constructor works properly by iterating through various row/column values.
	 */
	@Test
	void testEmpty() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Living testEmpty = new Empty(testPlain, i, j);
				
				assertEquals(i, testEmpty.row);
				assertEquals(j, testEmpty.column);
			}
		}
	}

}
