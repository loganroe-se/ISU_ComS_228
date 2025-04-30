package edu.iastate.cs228.hw1;

/**
 * The Grass class is one of the five Living objects that make up a Plain.
 * Grass extends Living so Grass is a subclass of Living.
 * @author Logan Roe
 */
public class Grass extends Living 
{
	/**
	 * Creates a Grass instance with a given plain, row, and column that all get passed up the hierarchy to Living via super(). 
	 * @param p: plain the Living object resides in
	 * @param r: row position
	 * @param c: column position
	 */
	public Grass (Plain p, int r, int c) 
	{
		super(p, r, c); 
	}
	
	/**
	 * Grass occupies this square.
	 * @return State Returns GRASS as the State of this current square.
	 */
	public State who()
	{
		return State.GRASS; 
	}
	
	/**
	 * Determines the next life stage that will occupy the given square.
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
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
		// Empty if at least three times as many Rabbits as Grasses in the neighborhood;
		// otherwise, Rabbit if there are at least three Rabbits in the neighborhood;
		// otherwise, Grass.
		if (population[4] >= (population[3] * 3)) {
			pNew.grid[row][column] = new Empty(currPlain, row, column);
			return new Empty(currPlain, row, column);
		} else if (population[4] >= 3) {
			pNew.grid[row][column] = new Rabbit(currPlain, row, column, 0);
			return new Rabbit(currPlain, row, column, 0);
		} else {
			pNew.grid[row][column] = new Grass(currPlain, row, column);
			return new Grass(currPlain, row, column);
		}
	}
}
