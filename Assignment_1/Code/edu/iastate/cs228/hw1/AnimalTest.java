package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Running basic tests for each method of Animal.
 * @author Logan Roe
 */
public class AnimalTest {

	/**
	 * Tests the Animal constructor for each Animal type: Badger, Fox, and Rabbit. All having different ages and row/column values.
	 */
	@Test
	public void testAnimal() {
		Plain testPlain = new Plain(3);
		Living testBadger = new Badger(testPlain, 0, 0, 2);
		Living testFox = new Fox(testPlain, 0, 1, 1);
		Living testRabbit = new Rabbit(testPlain, 0, 2, 3);
		
		assertEquals(0, testBadger.row);
		assertEquals(0, testBadger.column);
		assertEquals(2, ((Animal) testBadger).myAge());
		
		assertEquals(0, testFox.row);
		assertEquals(1, testFox.column);
		assertEquals(1, ((Animal) testFox).myAge());
		
		assertEquals(0, testRabbit.row);
		assertEquals(2, testRabbit.column);
		assertEquals(3, ((Animal) testRabbit).myAge());
	}

	/**
	 * Tests the myAge() method of Animal. It tests a lot of different ages for all of the different animals to make 
	 * sure it returns the right value no matter the circumstance.
	 */
	@Test
	public void testMyAge() {
		Plain testPlain = new Plain(3);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int age = 0; age <= 4; age++) {
					Badger testBadger = new Badger(testPlain, i, j, age);
					assertEquals(age, testBadger.myAge());
					
					Fox testFox = new Fox(testPlain, i, j, age);
					assertEquals(age, testFox.myAge());
					
					Rabbit testRabbit = new Rabbit(testPlain, i, j, age);
					assertEquals(age, testRabbit.myAge());
				}
			}
		}
	}

}
