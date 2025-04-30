package edu.iastate.cs228.hw1;

/**
 * The Fox class is one of the three Animals and one of the five Living objects that make up a Plain.
 * Fox extends Animal so Fox is a subclass of Animal.
 * @author Logan Roe
 */
public class Fox extends Animal 
{
	/**
	 * Creates a Fox instance with a given plain, row, column, and age that all get passed up the hierarchy to Animal via super(). 
	 * @param p: plain the Living object resides in
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) 
	{
		super(p, r, c, a);
	}
	
	/**
	 * A Fox occupies this square.
	 * @return State Returns FOX as the State of this current square.
	 */
	public State who()
	{ 
		return State.FOX; 
	}
	
	/**
	 * Determines the next life stage that will occupy the given square.
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	@Override
	public Living next(Plain pNew)
	{
		// Gather necessary info on the neighborhood via census, the age, the plain, the row, and the column.
		// Note: Population is in this order: Badger [0], Empty [1], Fox [2], Grass[3], Rabbit[4]
		int[] population = new int[5];
		this.census(population);
		Plain currPlain = this.plain;
		int age = this.age;
		int row = this.row;
		int column = this.column;
		
		// Use the criteria found in the project description to determine what will be next in this square.
		// If the Fox is currently at age 6, return Empty;
		// otherwise, Badger, if there are more Badgers than Foxes in the neighborhood;
		// otherwise, Empty, if Badgers and Foxes together out-number Rabbits in the neighborhood;
		// otherwise, Fox (the fox will live on).
		if (age == 6) {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		} else if (population[0] > population[2]) {
			pNew.grid[row][column] = new Badger(currPlain, row, column, 0);
			return new Badger(currPlain, row, column, 0);
		} else if ((population[0] + population[2]) > population[4]) {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		} else {
			pNew.grid[row][column] = new Fox(currPlain, row, column, age + 1);
			return new Fox(currPlain, row, column, age + 1);
		}
	}
}
