package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Rabbit class to ensure it works properly.
 * @author Logan Roe
 */
class RabbitTest {

	/**
	 * Tests the who() method of Rabbit to ensure it works for various row/column positions and age values.
	 */
	@Test
	void testWho() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int age = 0; age <= 4; age++) {
					Rabbit testRabbit = new Rabbit(testPlain, i, j, age);
					assertEquals(State.RABBIT, testRabbit.who());
				}
			}
		}
	}

	/**
	 * Tests the next() method of Rabbit using a plain that is made separately for each test.
	 * The separate plains are designed to test each individual criteria at a minimum of one time.
	 */
	@Test
	void testNext() {
		Plain testPlain = new Plain(2);
		Plain newTestPlain = new Plain(2);
		
		// Tests the first criteria: Empty if the Rabbit is currently at age 3.
		testPlain.grid[0][0] = new Rabbit(testPlain, 0, 0, 3);
		testPlain.grid[0][1] = new Rabbit(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Rabbit(testPlain, 1, 0, 1);
		testPlain.grid[1][1] = new Rabbit(testPlain, 1, 1, 2);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));
		
		// Tests the second, third, fourth, and fifth criteria: 
		// (2) otherwise, Empty if there is no Grass in the neighborhood (the rabbit needs food)
		// (3) otherwise, Fox if in the neighborhood there are at least as many Foxes and Badgers combined as Rabbits, and furthermore, if there are more Foxes than Badgers
		// (4) otherwise, Badger if there are more Badgers than Rabbits in the neighborhood
		// (5) otherwise, Rabbit (the rabbit will live on)
		
		// (2)
		testPlain.grid[0][0] = new Rabbit(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Fox(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Empty(testPlain, 1, 0);
		testPlain.grid[1][1] = new Badger(testPlain, 1, 1, 0);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));

		// (3)
		testPlain.grid[0][0] = new Rabbit(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Fox(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Fox(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Grass(testPlain, 1, 1);
		assertEquals(State.FOX, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());
		
		// (4)
		testPlain.grid[0][0] = new Rabbit(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Badger(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Badger(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Grass(testPlain, 1, 1);
		assertEquals(State.BADGER, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(0, ((Animal) newTestPlain.grid[0][0]).myAge());
		
		// (5)
		testPlain.grid[0][0] = new Rabbit(testPlain, 0, 0, 0);
		testPlain.grid[0][1] = new Grass(testPlain, 0, 1);
		testPlain.grid[1][0] = new Fox(testPlain, 1, 0, 0);
		testPlain.grid[1][1] = new Badger(testPlain, 1, 1, 0);
		assertEquals(State.RABBIT, (testPlain.grid[0][0].next(newTestPlain).who()));
		assertEquals(1, ((Animal) newTestPlain.grid[0][0]).myAge());
	}

	/**
	 * Makes sure that the rabbit constructor works properly by iterating through various row/column values and ages.
	 */
	@Test
	void testRabbit() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 4; k++) {
					Living testRabbit = new Rabbit(testPlain, i, j, k);
					
					assertEquals(i, testRabbit.row);
					assertEquals(j, testRabbit.column);
					assertEquals(k, ((Animal) testRabbit).myAge());
				}
			}
		}
	}

}
