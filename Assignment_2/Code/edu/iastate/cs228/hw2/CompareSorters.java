package edu.iastate.cs228.hw2;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 * @author Logan Roe
 */
public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// Tell the user what the program is and what the key values are and what each value does.
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning\n\nkeys:  1 (random integers)  2 (file input)  3 (exit)");
		
		// Initialize some scanners for each sorting algorithm type.
		PointScanner[] scanners = new PointScanner[4]; 
		
		// Initialize variables.
		int totalTrials = 0;
		int userIn, numPts;
		Scanner scnrIn, ptsScnr, fileScnr;
		String fileName;
		Point[] pts;
		
		// Start the infinite loop.
		while (true) {
			// Tell the user what trial they are on and wait for a value user input.
			totalTrials++;
			System.out.print("Trial " + totalTrials + ": ");
			
			// Obtain the integer value entered by the user.
			scnrIn = new Scanner(System.in);
			userIn = scnrIn.nextInt();
			
			// Determine what the integer value the user provided is and act accordingly.
			if (userIn == 1) {
				// Tell the user to enter a number of points that will be randomly generated.
				System.out.print("Enter number of random points: ");
				ptsScnr = new Scanner(System.in);
				numPts = ptsScnr.nextInt();
				
				// Call the generateRandomPoints() to generate random values for each of the points.
				Random randGen = new Random();
				pts = generateRandomPoints(numPts, randGen);
				
				// Call each sorting algorithm for the same set of points and store each of the results in their respective PointScanners.
				scanners[0] = new PointScanner(pts, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(pts, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(pts, Algorithm.MergeSort);
				scanners[3] = new PointScanner(pts, Algorithm.QuickSort);
			} else if (userIn == 2) {
				// Tell the user that the points will be taken from a file and have the user provide a file name.
				System.out.print("Points from a file\nFile name: ");
				
				// Obtain the file name that was handed in by the user.
				fileScnr = new Scanner(System.in);
				fileName = fileScnr.nextLine();
				
				// Call each sorting algorithm for the same set of points and store each of the results in their respective PointScanners.
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
			} else if (userIn == 3) {
				// Terminate the program by closing the scanner and breaking out of the while loop.
				scnrIn.close();
				break;
			} else {
				// Tell the user that an invalid number was entered and to try again with a different value.
				// Continue the while loop so the below code does not execute and cause errors.
				System.out.println("Invalid number. Please try again.");
				continue;
			}
			
			// Run a scan() for each of the PointScanners.
			for (int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
			}
			
			// Set up the table for all of the statistics.
			System.out.println("\nalgorithm        size  time (ns)\n---------------------------------");
			
			// Print out the statistics obtained for each of the sorting algorithms.
			for (int i = 0; i < scanners.length; i++) {
				System.out.println(scanners[i].stats());
			}
			
			// Print the bottom of the table.
			System.out.println("---------------------------------");
		}
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		// Throws an exception if there is not at least one point.
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		
		// Creates a new array of points given the number of points.
		Point[] randPts = new Point[numPts];
		
		// Randomly generates values for the randPts array within the range [-50,50]� [-50,50].
		for (int i = 0; i < numPts; i++) {
			randPts[i] = new Point((rand.nextInt(101) - 50), (rand.nextInt(101) - 50));
		}
		
		// Return the array of random points.
		return randPts;
	}
	
}
