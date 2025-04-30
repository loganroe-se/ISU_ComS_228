package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 * Sorts the given array of points based upon the insertion sort algorithm.
 * @author Logan Roe
 */
public class InsertionSorter extends AbstractSorter 
{	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  input array of points
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "InsertionSort";
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		for (int i = 1; i < this.points.length; i++) {
			// Store a temporary point.
			Point temp = this.points[i];
			int j = i - 1;
			
			// If the current point is less than the temp point (this is known by calling the comparator from AbstractSorter)
			// then move the current point to the left one in the array of points.
			while (j > -1 && (this.pointComparator.compare(temp, this.points[j]) < 0)) {
				this.points[j + 1] = this.points[j];
				j--;
			}
			
			this.points[j + 1] = temp;
		}
	}		
}
