package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 * The Wildlife class performs a simulation of a grid plain with 
 * squares inhabited by badgers, foxes, rabbits, grass, or empty.
 * It also can update a Plain for a given number of cycles, by user input.
 * @author Logan Roe
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew)
	{	
		// Get the width of the old plain and convert it to a string in a Scanner.
		int width = pOld.getWidth();
		Scanner scnrOld = new Scanner(pOld.toString());
		
		// Use the Scanner variable to go through and create new life forms for each square.
		// This has to be done to ensure that the "this" statement works properly within the life form classes.
		// Otherwise, it will not always work as intended.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				switch(scnrOld.next()) {
				case "B0": 
					pOld.grid[i][j] = new Badger(pOld, i, j, 0);
					break;
				case "B1": 
					pOld.grid[i][j] = new Badger(pOld, i, j, 1);
					break;
				case "B2": 
					pOld.grid[i][j] = new Badger(pOld, i, j, 2);
					break;
				case "B3": 
					pOld.grid[i][j] = new Badger(pOld, i, j, 3);
					break;
				case "B4": 
					pOld.grid[i][j] = new Badger(pOld, i, j, 4);
					break;
				case "E": 
					pOld.grid[i][j] = new Empty(pOld, i, j);
					break;
				case "F0": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 0);
					break;
				case "F1": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 1);
					break;
				case "F2": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 2);
					break;
				case "F3": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 3);
					break;
				case "F4": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 4);
					break;
				case "F5": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 5);
					break;
				case "F6": 
					pOld.grid[i][j] = new Fox(pOld, i, j, 6);
					break;
				case "G": 
					pOld.grid[i][j] = new Grass(pOld, i, j);
					break;
				case "R0": 
					pOld.grid[i][j] = new Rabbit(pOld, i, j, 0);
					break;
				case "R1": 
					pOld.grid[i][j] = new Rabbit(pOld, i, j, 1);
					break;
				case "R2": 
					pOld.grid[i][j] = new Rabbit(pOld, i, j, 2);
					break;
				case "R3": 
					pOld.grid[i][j] = new Rabbit(pOld, i, j, 3);
					break;
				}
			}
			
			scnrOld.nextLine();
		}
		scnrOld.close();
		
		// Update each square in the grid based upon the old grid and put it in the new grid.
		// This is done by calling the next() method for each cell.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				pOld.grid[i][j].next(pNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * Input 1 will create a random grid, 2 will read from a file, and 3 will terminate.
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		// Initialize variables.
		int totalTrials = 0;
		int userIn, numCyclesRan, numCyclesToRun, width;
		Plain even, odd;
		Scanner scnr, widthScnr, numCycles, fileScnr;
		String fileName;
		
		// Start the loop.
		while (true) {
			// Keep track of the total trials done and output what the simulation does.
			totalTrials++;
			System.out.println("Simulation of Wildlife of the Plain\nkeys: 1 (random plain)  2 (file input)  3 (exit)\n\nTrial " + totalTrials + ": ");
			
			// Read in the value from the user and then determine if it is a 1, 2, or 3.
			// If the value is negative or a 0, it will wait for another input.
			scnr = new Scanner(System.in);
			userIn = scnr.nextInt();
			
			if (userIn == 1) {
				// Ask for a grid width from the user.
				System.out.println("Random Plain\nEnter grid width: ");
				widthScnr = new Scanner(System.in);
				// Generate a new plain based upon the width given.
				// Then generate random values for the grid.
				even = new Plain(widthScnr.nextInt());
				even.randomInit();
				width = even.getWidth();
				odd = new Plain(width);
				
				// Ask for the number of cycles to run.
				System.out.println("Enter the number of cycles: ");
				numCycles = new Scanner(System.in);
				numCyclesToRun = numCycles.nextInt();
				
				// Print out the original grid.
				System.out.println("\nInitial plain:\n" + even.toString());
				
				numCyclesRan = 1;
				
				// For x amount of cycles (determined by user input) call updatePlain().
				// The call differs depending on if it is the even or odd run.
				// This is done for ease of readability and use.
				while (numCyclesRan <= numCyclesToRun) {
					if (numCyclesRan % 2 == 0) {
						updatePlain(odd, even);
					} else {
						updatePlain(even, odd);
					}
					
					numCyclesRan++;
				}
				
				// Print out the appropriate grid depending on the number of cycles ran.
				if (numCyclesToRun % 2 == 0) {
					System.out.println("\nFinal plain:\n" + even.toString());
				} else {
					System.out.println("\nFinal plain:\n" + odd.toString());
				}
				
			} else if (userIn == 2) {
				// Ask for a file name from the user.
				System.out.println("Plain input from a file\nFile name: ");
				fileScnr = new Scanner(System.in);
				fileName = fileScnr.nextLine();
				
				// Create a new grid based upon the file given.
				even = new Plain(fileName);
				width = even.getWidth();
				odd = new Plain(width);
				
				// Ask for the number of cycles to run.
				System.out.println("Enter the number of cycles: ");
				numCycles = new Scanner(System.in);
				numCyclesToRun = numCycles.nextInt();
				
				// Print out the original grid.
				System.out.println("\nInitial plain:\n" + even.toString());
				
				numCyclesRan = 1;
				
				// For x amount of cycles (determined by user input) call updatePlain().
				// The call differs depending on if it is the even or odd run.
				// This is done for ease of readability and use.
				while (numCyclesRan <= numCyclesToRun) {
					if (numCyclesRan % 2 == 0) {
						updatePlain(odd, even);
					} else {
						updatePlain(even, odd);
					}
					
					numCyclesRan++;
				}
				
				// Print out the appropriate grid depending on the number of cycles ran.
				if (numCyclesToRun % 2 == 0) {
					System.out.println("\nFinal plain:\n" + even.toString());
				} else {
					System.out.println("\nFinal plain:\n" + odd.toString());
				}
				
			} else if (userIn == 3) {
				// Terminate the program by closing the scanner and breaking out of the while loop.
				scnr.close();
				break;
			}
		}
	}
}
