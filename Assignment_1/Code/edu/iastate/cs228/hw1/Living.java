package edu.iastate.cs228.hw1;

/**
 * Living refers to the life form occupying a square in a plain grid. It is a 
 * superclass of Empty, Grass, and Animal, the latter of which is in turn a superclass
 * of Badger, Fox, and Rabbit. Living has two abstract methods as well.
 * @author Logan Roe
 */
public abstract class Living 
{
	protected Plain plain; // the plain in which the life form resides
	protected int row;     // location of the square on which 
	protected int column;  // the life form resides
	
	// constants to be used as indices. 
	protected static final int BADGER = 0; 
	protected static final int EMPTY = 1; 
	protected static final int FOX = 2; 
	protected static final int GRASS = 3; 
	protected static final int RABBIT = 4; 
	
	public static final int NUM_LIFE_FORMS = 5; 
	
	// life expectancies 
	public static final int BADGER_MAX_AGE = 4; 
	public static final int FOX_MAX_AGE = 6; 
	public static final int RABBIT_MAX_AGE = 3; 
	
	/**
	 * Creates a Living instance with a given plain, row, and column. 
	 * The type of Living object that lives here is dependent on what subclass' constructor called it.
	 * @param p: plain the Living object resides in
	 * @param r: row position
	 * @param c: column position
	 */
	public Living(Plain p, int r, int c) {
		plain = p;
		row = r;
		column = c;
	}
	
	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a plain.
	 * Takes into account if the square is on the edge so that no matter if it is a 2 X 2, 2 X 3, 3 X 2, or a 3 X 3,
	 * the correct population values will be found.
	 * @param population counts of all life forms
	 */
	protected void census(int population[ ])
	{		
		// Make sure all parts of population[] are 0.
		population[0] = 0;
		population[1] = 0;
		population[2] = 0;
		population[3] = 0;
		population[4] = 0;
		
		// Get the width of the plain and initialize some variables.
		int plainWidth = plain.getWidth();
		int rowStart, rowEnd, colStart, colEnd;
		State tempWho;
		
		// Find the indices for the upcoming for loops. These indices are based upon if the neighborhood is a 2x2, 2x3, 3x2, or 3x3.
		// The first part of the if statement deals with the edge cases when the square is at the very top or very bottom row of a grid.
		// The second part of the if statement (the first else if) deals with the edge cases for the very left and very right sides of the grid.
		// The else handles everything else that isn't an "edge" case.
		if (row == 0 || row == plainWidth - 1) {
			if (row == 0) {
				rowStart = 0;
				rowEnd = 1;
			} else {
				rowStart = plainWidth - 2;
				rowEnd = plainWidth - 1;
			}
			
			if (column == 0) {
				colStart = 0;
				colEnd = 1;
			} else if (column == plainWidth - 1) {
				colStart = plainWidth - 2;
				colEnd = plainWidth - 1;
			} else {
				colStart = column - 1;
				colEnd = column + 1;
			}			
		} else if (column == 0 || column == plainWidth - 1) {
			if (column == 0) {
				colStart = 0;
				colEnd = 1;
			} else {
				colStart = plainWidth - 2;
				colEnd = plainWidth - 1;
			}
			
			if (row == 0) {
				rowStart = 0;
				rowEnd = 1;
			} else if (row == plainWidth - 1) {
				rowStart = plainWidth - 2;
				rowEnd = plainWidth - 1;
			} else {
				rowStart = row - 1;
				rowEnd = row + 1;
			}
		} else {
			rowStart = row - 1;
			rowEnd = row + 1;
			colStart = column - 1;
			colEnd = column + 1;
		}
		
		// Iterate through all of the required rows/columns (as found above). Then increase the count for WHO is in that square.
		for(int rowIdx = rowStart; rowIdx <= rowEnd; rowIdx++) {
			for(int colIdx = colStart; colIdx <= colEnd; colIdx++) {
				tempWho = plain.grid[rowIdx][colIdx].who();
				
				switch (tempWho) {
					case BADGER: 
						population[0] += 1;	
						break;
					case EMPTY: 
						population[1] += 1;	
						break;
					case FOX: 
						population[2] += 1;
						break;
					case GRASS: 
						population[3] += 1;	
						break;
					case RABBIT: 
						population[4] += 1;	
						break;
				}
			}
		}
	}

	/**
	 * Gets the identity of the life form on the square.
	 * @return State Returns the State of who is in the square: 
	 * 			State.Badger, State.Empty, State.Fox, State.Grass, or State.Rabbit.
	 */
	public abstract State who();
	
	/**
	 * Determines the life form on the square in the next cycle.
	 * See the subclasses: Badger, Fox, Rabbit, Grass, and Empty for more information as to how each works.
	 * @param  pNew  plain of the next cycle
	 * @return Living The new living object that will occupy the square in the next cycle.
	 */
	public abstract Living next(Plain pNew);  
}
