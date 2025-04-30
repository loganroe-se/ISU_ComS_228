package edu.iastate.cs228.hw1;

/**
 * A parent class for Badger, Fox, and Rabbit that houses the ages for each of these animals.
 * @author Logan Roe
 */
public abstract class Animal extends Living implements MyAge
{
	// age of the animal 
	protected int age;

	/**
	 * A constructor for animals that calls Living via super() but stores age here. The rest, plain, row, and column, get passed, via super(), to Living.
	 * @param p Plain in which the animal resides.
	 * @param r Row in which the animal is on.
	 * @param c Column in which the animal is on.
	 * @param a Age of the animal.
	 */
	public Animal(Plain p, int r, int c, int a) {
		super(p, r, c);
		age = a;
	}
	
	@Override
	/**
	 * Returns the age of the animal that calls it.
	 * @return age of the animal 
	 */
	public int myAge()
	{
		return age; 
	}
}
