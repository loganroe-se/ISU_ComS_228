package edu.iastate.cs228.hw1;

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
/**
 * The plain is represented as a square grid of size width x width.
 * Each slot in the grid is filled with a Living object: Badger, Fox, Rabbit, Grass, or Empty.
 * Badger, Fox, and Rabbit all have ages, whereas Grass and Empty do not.
 * @author Logan Roe
 */
public class Plain 
{
	// grid size: width X width
	private int width; 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	/**
	 * Default constructor which will read a grid in from a file.
	 * @param inputFileName The file that the grid is read in from.
	 * @throws FileNotFoundException
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{		
		// Create a File from the inputFileName and put it in a scanner.
		File readGrid = new File(inputFileName);
		Scanner scnrWidth = new Scanner(readGrid);
		
		// Find the width by using a temporary scanner.
		Scanner tempScnr = new Scanner(scnrWidth.nextLine());

		while(tempScnr.hasNext()) {
			width++;
			tempScnr.next();
		}
		
		scnrWidth.close();
		
		// Create a new scanner so that the previous operations do not affect our current place in the file.
		Scanner scnr = new Scanner(readGrid);
		
		// Create a grid based upon the width just found.
		grid = new Living[width][width];
		
		// Assign each grid value with the appropriate value from the text file.
		// This is done by comparing, using a switch statement, all of the potential string values.
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				switch(scnr.next()) {
					case "B0": 
						grid[i][j] = new Badger(this, i, j, 0);
						break;
					case "B1": 
						grid[i][j] = new Badger(this, i, j, 1);
						break;
					case "B2": 
						grid[i][j] = new Badger(this, i, j, 2);
						break;
					case "B3": 
						grid[i][j] = new Badger(this, i, j, 3);
						break;
					case "B4": 
						grid[i][j] = new Badger(this, i, j, 4);
						break;
					case "E": 
						grid[i][j] = new Empty(this, i, j);
						break;
					case "F0": 
						grid[i][j] = new Fox(this, i, j, 0);
						break;
					case "F1": 
						grid[i][j] = new Fox(this, i, j, 1);
						break;
					case "F2": 
						grid[i][j] = new Fox(this, i, j, 2);
						break;
					case "F3": 
						grid[i][j] = new Fox(this, i, j, 3);
						break;
					case "F4": 
						grid[i][j] = new Fox(this, i, j, 4);
						break;
					case "F5": 
						grid[i][j] = new Fox(this, i, j, 5);
						break;
					case "F6": 
						grid[i][j] = new Fox(this, i, j, 6);
						break;
					case "G": 
						grid[i][j] = new Grass(this, i, j);
						break;
					case "R0": 
						grid[i][j] = new Rabbit(this, i, j, 0);
						break;
					case "R1": 
						grid[i][j] = new Rabbit(this, i, j, 1);
						break;
					case "R2": 
						grid[i][j] = new Rabbit(this, i, j, 2);
						break;
					case "R3": 
						grid[i][j] = new Rabbit(this, i, j, 3);
						break;
				}
			}
			scnr.nextLine();
		}
		
		// Close the input file.
		scnr.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing any of the values. They remain as null.
	 * @param w width of the grid 
	 */
	public Plain(int w)
	{
		grid = new Living[w][w];
		width = w;
	}
	
	/**
	 * Returns the width of the Plain that called this method.
	 * @return width of the Plain/grid
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Initializes a Plain at random where every Animal will start at age 0. 
	 */
	public void randomInit()
	{
		Random generator = new Random(); 
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				int randInt = generator.nextInt(5);
				
				switch(randInt) {
				case 0:
					grid[i][j] = new Badger(this, i, j, 0);
					break;
				case 1:
					grid[i][j] = new Empty(this, i, j);
					break;
				case 2:
					grid[i][j] = new Fox(this, i, j, 0);
					break;
				case 3:
					grid[i][j] = new Grass(this, i, j);
					break;
				case 4:
					grid[i][j] = new Rabbit(this, i, j, 0);
					break;
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String strGrid = "";
		int age = 0;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				Living temp = grid[i][j];
				State tempWho = temp.who();
				
				switch(tempWho) {
					case BADGER:
						age = ((Animal) temp).myAge();
						strGrid = strGrid.concat("B" + age + " ");
						break;
					case EMPTY:
						strGrid = strGrid.concat("E  ");
						break;
					case FOX:
						age = ((Animal) temp).myAge();
						strGrid = strGrid.concat("F" + age + " ");
						break;
					case GRASS:
						strGrid = strGrid.concat("G  ");
						break;
					case RABBIT:
						age = ((Animal) temp).myAge();
						strGrid = strGrid.concat("R" + age + " ");
						break;
				}
			}
			
			strGrid = strGrid.concat("\n");
		}
		
		return strGrid; 
	}
	

	/**
	 * Write the plain grid to an output file in grid format.
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		PrintWriter fileOut = new PrintWriter(outputFileName);
		
		fileOut.print(toString());
		
		fileOut.close();
	}			
}
