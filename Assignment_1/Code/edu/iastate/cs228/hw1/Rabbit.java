package edu.iastate.cs228.hw1;

/**
 * The Rabbit class is one of the three Animals and one of the five Living objects that make up a Plain.
 * Rabbit extends Animal so Rabbit is a subclass of Animal.
 * @author Logan Roe
 */
public class Rabbit extends Animal 
{	
	/**
	 * Creates a Rabbit instance with a given plain, row, column, and age that all get passed up the hierarchy to Animal via super(). 
	 * @param p: plain the Living object resides in  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) 
	{
		super(p, r, c, a); 
	}
		
	/**
	 * A Rabbit occupies this square.
	 * @return State Returns RABBIT as the State of this current square.
	 */
	public State who()
	{
		return State.RABBIT; 
	}
	
	/**
	 * Determines the next life stage that will occupy the given square.
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle 
	 * @return Living  new life form occupying the same square
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
		// If the Rabbit's current age is 3, return Empty;
		// otherwise, Empty if there is no Grass in the neighborhood (the rabbit needs food);
		// otherwise, Fox if in the neighborhood there are at least as many Foxes and Badgers combined as Rabbits, and furthermore, if there are more Foxes than Badgers;
		// otherwise, Badger if there are more Badgers than Rabbits in the neighborhood;
		// otherwise, Rabbit (the rabbit will live on).
		if (age == 3) {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		} else if (population[3] == 0) {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		} else if (((population[0] + population[2]) >= population[4]) && (population[2] > population[0])) {
			pNew.grid[row][column] = new Fox(currPlain, row, column, 0);
			return new Fox(currPlain, row, column, 0);
		} else if (population[0] > population[4]) {
			pNew.grid[row][column] = new Badger(currPlain, row, column, 0);
			return new Badger(currPlain, row, column, 0);
		} else {
			pNew.grid[row][column] = new Rabbit(currPlain, row, column, age + 1);
			return new Rabbit(currPlain, row, column, age + 1);
		}
	}
}
