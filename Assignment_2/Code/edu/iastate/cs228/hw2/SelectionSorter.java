package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 * Sorts the given array of points based upon the selection sort algorithm.
 * @author Logan Roe
 */
public class SelectionSorter extends AbstractSorter
{
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  input array of points
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "SelectionSort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		for (int i = 0; i < this.points.length - 1; i++) {
			// Determine the minIndex of the point that will be compared to.
			int minIndex = i;
			
			// Iterate through all of the necessary points in the array and compare that point with the point at the minIndex.
			// If the point at the minIndex is greater than the other point, update the minIndex value.
			for (int j = i + 1; j < this.points.length; j++) {
				if (this.pointComparator.compare(this.points[j], this.points[minIndex]) < 0) {
					minIndex = j;
				}
			}
			
			// Swap the Point at i and the minIndex.
			this.swap(i, minIndex);
		}
	}	
}
