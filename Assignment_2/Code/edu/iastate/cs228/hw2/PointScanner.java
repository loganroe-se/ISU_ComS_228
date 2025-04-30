package edu.iastate.cs228.hw2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 * @author Logan Roe
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  
	
	private Algorithm sortingAlgorithm;    
	
	protected long scanTime;
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copies
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{		
		// If the array pts is invalid, throw an exception.
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		
		// Create a new array of points given the length of pts.
		points = new Point[pts.length];
		
		// Copy all of the values of pts into the new points array.
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
		
		// Store the sorting algorithm parameter in the instance variable sortingAlgorithm.
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName The name of the file to read from.
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		int width = 0;
		
		// Create a File from the inputFileName and put it in a scanner.
		File readPoints = new File(inputFileName);
		Scanner scnrWidth = new Scanner(readPoints);

		// Determine the width of the file (i.e. how many coordinates the file contains).
		while(scnrWidth.hasNext()) {
			width++;
			scnrWidth.next();
		}
		
		scnrWidth.close();
		
		// If the file contains an odd number of values (meaning that a point would be left without a y-coordinate) then throw an exception.
		if (width % 2 == 1) {
			throw new InputMismatchException();
		}
		
		// Create a new scanner so that the previous operations do not affect our current place in the file.
		Scanner scnr = new Scanner(readPoints);
		
		// Create a new array of Points of the necessary sized based upon the number of values in the file.
		points = new Point[width / 2];
		
		// Add the values of the file as Points into the new array.
		for (int i = 0; i < (width / 2); i++) {
			points[i] = new Point(scnr.nextInt(), scnr.nextInt());
		}
		
		// Store the type of sorting algorithm being used.
		sortingAlgorithm = algo;
		
		scnr.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 */
	public void scan()
	{
		// Initialize variables.
		AbstractSorter aSorter; 
		long startTime, finalTimeX, finalTimeY;
		Point medianX, medianY;
		
		// Based upon the sorting algorithm being used, create a new sorted of that type.
		switch(sortingAlgorithm) {
			case SelectionSort:
				aSorter = new SelectionSorter(points);
				break;
			case InsertionSort:
				aSorter = new InsertionSorter(points);
				break;
			case MergeSort:
				aSorter = new MergeSorter(points);
				break;
			default:
				aSorter = new QuickSorter(points);
				break;
		}
		
		
		// Sorting by x-coordinates first whilst keeping track of the time it takes.
		// Then obtain the median value for the x-coordinates.
		aSorter.setComparator(0);
		startTime = System.nanoTime();
		aSorter.sort();
		finalTimeX = (System.nanoTime() - startTime);
		medianX = aSorter.getMedian();
		
		// Sorting by y-coordinates whilst keeping track of the time it takes.
		// Then obtain the median value for the y-coordinates.
		aSorter.setComparator(1);
		startTime = System.nanoTime();
		aSorter.sort();
		finalTimeY = (System.nanoTime() - startTime);
		medianY = aSorter.getMedian();
		
		// Create a new point with the median value for the x- and y-coordinates found above.
		medianCoordinatePoint = new Point(medianX.getX(), medianY.getY());
		
		// Add up the sort times of the x- and y-coordinates to get a final time for this sorting method.
		scanTime = finalTimeX + finalTimeY;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * @return String Returns the formatted statistics string.
	 */
	public String stats()
	{
		String stats = "";
		
		// Based upon the sorting algorithm used, store a string value that includes
		// the sorting algorithm, the length of the points array, and the time it took.
		switch(sortingAlgorithm) {
			case SelectionSort:
				stats = "SelectionSort    " + points.length + "    " + scanTime;
				break;
			case InsertionSort:
				stats = "InsertionSort    " + points.length + "    " + scanTime;
				break;
			case MergeSort:
				stats = "MergeSort        " + points.length + "    " + scanTime;
				break;
			case QuickSort:
				stats = "QuickSort        " + points.length + "    " + scanTime;
				break;
		}
		
		// Return the string of statistics.
		return stats; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 * @return String Returns the Point at the median in a string format.
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")"; 
	}

	
	/**
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().
	 * 
	 * @param outputFileName The name of the file to write the MCP to.
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile(String outputFileName) throws FileNotFoundException
	{
		// Creates a PrintWriter for the file with the file name handed in as a parameter.
		PrintWriter fileOut = new PrintWriter(outputFileName);
		
		// Print the MCP to the file given the format in toString().
		fileOut.println(toString());
		
		fileOut.close();
	}	

	

		
}
