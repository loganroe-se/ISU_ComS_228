package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Grass class to ensure it works properly.
 * @author Logan Roe
 */
class GrassTest {

	/**
	 * Tests the who() method of Badger to ensure it works for various row/column positions.
	 */
	@Test
	void testWho() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Grass testGrass = new Grass(testPlain, i, j);
				assertEquals(State.GRASS, testGrass.who());

			}
		}
	}

	/**
	 * Tests the next() method of Grass using a plain that is made separately for each test.
	 * The separate plains are designed to test each individual criteria at a minimum of one time.
	 */
	@Test
	void testNext() {
		Plain testPlain = new Plain(2);
		Plain newTestPlain = new Plain(2);
		
		// Tests the first criteria: Empty if at least three times as many Rabbits as Grasses in the neighborhood
		testPlain.grid[0][0] = new Grass(testPlain, 0, 0);
		testPlain.grid[0][1] = new Rabbit(testPlain, 0, 1, 0);
		testPlain.grid[1][0] = new Rabbit(testPlain, 1, 0, 1);
		testPlain.grid[1][1] = new Rabbit(testPlain, 1, 1, 2);
		assertEquals(State.EMPTY, (testPlain.grid[0][0].next(newTestPlain).who()));
		
		// Tests the second and third criteria: 
		// (2) otherwise, Rabbit if there are at least three Rabbits in the neighborhood
		// (3) otherwise, Grass
		
		Plain testPlain2 = new Plain(3);
		Plain newTestPlain2 = new Plain(3);
		
		// (2)
		testPlain2.grid[0][0] = new Rabbit(testPlain2, 0, 0, 0);
		testPlain2.grid[0][1] = new Grass(testPlain2, 0, 1);
		testPlain2.grid[0][2] = new Rabbit(testPlain2, 0, 2, 0);
		testPlain2.grid[1][0] = new Rabbit(testPlain2, 1, 0, 0);
		testPlain2.grid[1][1] = new Grass(testPlain2, 1, 1);
		testPlain2.grid[1][2] = new Rabbit(testPlain2, 1, 2, 0);
		testPlain2.grid[2][0] = new Empty(testPlain2, 2, 0);
		testPlain2.grid[2][1] = new Empty(testPlain2, 2, 1);
		testPlain2.grid[2][2] = new Empty(testPlain2, 2, 2);
		assertEquals(State.RABBIT, (testPlain2.grid[0][1].next(newTestPlain2).who()));
		assertEquals(0, ((Animal) newTestPlain2.grid[0][1]).myAge());

		// (3)
		testPlain2.grid[0][0] = new Rabbit(testPlain2, 0, 0, 0);
		testPlain2.grid[0][1] = new Grass(testPlain2, 0, 1);
		testPlain2.grid[0][2] = new Rabbit(testPlain2, 0, 2, 0);
		testPlain2.grid[1][0] = new Grass(testPlain2, 1, 0);
		testPlain2.grid[1][1] = new Empty(testPlain2, 1, 1);
		testPlain2.grid[1][2] = new Fox(testPlain2, 1, 2, 0);
		testPlain2.grid[2][0] = new Empty(testPlain2, 2, 0);
		testPlain2.grid[2][1] = new Rabbit(testPlain2, 2, 1, 0);
		testPlain2.grid[2][2] = new Rabbit(testPlain2, 2, 2, 1);
		assertEquals(State.GRASS, (testPlain2.grid[0][1].next(newTestPlain2).who()));
	}

	/**
	 * Makes sure that the grass constructor works properly by iterating through various row/column values.
	 */
	@Test
	void testGrass() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Living testGrass = new Grass(testPlain, i, j);
				
				assertEquals(i, testGrass.row);
				assertEquals(j, testGrass.column);
			}
		}
	}

}
