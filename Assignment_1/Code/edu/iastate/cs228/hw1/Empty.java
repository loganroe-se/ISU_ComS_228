package edu.iastate.cs228.hw1;

/**
 * The Empty class is one of the five Living objects that make up a Plain.
 * Empty extends Living so Empty is a subclass of Living.
 * @author Logan Roe
 */
public class Empty extends Living 
{
	/**
	 * Creates a Grass instance with a given plain, row, and column that all get passed up the hierarchy to Living via super(). 
	 * @param p: plain the Living object resides in
	 * @param r: row position
	 * @param c: column position
	 */
	public Empty (Plain p, int r, int c) 
	{
		super(p, r, c);  
	}
	
	/**
	 * Empty occupies this square.
	 * @return State Returns EMPTY as the State of this current square.
	 */
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * Determines the next life stage that will occupy the given square.
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	@Override
	public Living next(Plain pNew)
	{
		// Gather necessary info on the neighborhood via census, the plain, the row, and the column.
		// Note: Population is in this order: Badger [0], Empty [1], Fox [2], Grass[3], Rabbit[4]
		int[] population = new int[5];
		this.census(population);
		Plain currPlain = this.plain;
		int row = this.row;
		int column = this.column;
		
		// Use the criteria found in the project description to determine what will be next in this square.
		// Rabbit, if more than one neighboring Rabbit;
		// otherwise, Fox, if more than one neighboring Fox;
		// otherwise, Badger, if more than one neighboring Badger;
		// otherwise, Grass, if at least one neighboring Grass;
		// otherwise, Empty.
		if (population[4] > 1) {
			pNew.grid[row][column] = new Rabbit(currPlain, row, column, 0);
			return new Rabbit(currPlain, row, column, 0);
		} else if (population[2] > 1) {
			pNew.grid[row][column] = new Fox(currPlain, row, column, 0);
			return new Fox(currPlain, row, column, 0);
		} else if (population[0] > 1) {
			pNew.grid[row][column] = new Badger(currPlain, row, column, 0);
			return new Badger(currPlain, row, column, 0);
		} else if (population[3] >= 1) {
			pNew.grid[row][column] = new Grass(currPlain, row, column);
			return new Grass(currPlain, row, column);
		} else {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		}
	}
}
