package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 * Sorts the given array of points based upon the quick sort algorithm.
 * @author Logan Roe
 */
public class QuickSorter extends AbstractSorter
{
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of points
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		super.algorithm = "QuickSort"; 
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.
	 * This is done by calling quickSortRec with the beginning and ending values of the Points array.
	 */
	@Override 
	public void sort()
	{
		quickSortRec(0, this.points.length - 1);
	}
	
	
	/**
	 * Operates on the sub-array of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the sub-array
	 * @param last   ending index of the sub-array
	 */
	private void quickSortRec(int first, int last)
	{
		if (first >= last) {
			return;
		}
		
		// Find the partition based upon the first and last variables by calling partition().
		int part = partition(first, last);
		
		// Recursive calls with updated first/last values based upon the partition found above.
		quickSortRec(first, part - 1);
		quickSortRec(part + 1, last);
	}
	
	
	/**
	 * Operates on the sub-array of points[] with indices between first and last.
	 * 
	 * @param first The beginning of the sub-array that is currently being sorted.
	 * @param last The end of the sub-array that is currently being sorted.
	 * @return int The value that is currently the partition in the array.
	 */
	private int partition(int first, int last)
	{
		// Starts at the last index of the array of Points for the pivot.
		Point pivot = this.points[last];
		int i = first - 1;
		
		// Compares points and, if the point at j is less than or equal to the pivot point, swap the points at i and j.
		for (int j = first; j < last; j++) {
			if ((this.pointComparator.compare(this.points[j], pivot)) <= 0) {
				i++;
				this.swap(i, j);
			}
		}
		
		// Swap the value at i + 1 and the last value (the pivot).
		this.swap(i + 1, last);
		
		// Return the partition value.
		return i + 1; 
	}	
}
