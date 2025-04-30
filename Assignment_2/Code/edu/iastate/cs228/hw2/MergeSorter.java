package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 * Sorts the given array of points based upon the merge sort algorithm.
 * @author Logan Roe
 */
public class MergeSorter extends AbstractSorter
{
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of points
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "MergeSort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * It simply calls mergeSortRec to do the sorting and passes in the current points array.
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(this.points);
	}


	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. 
	 * It makes copies of the two halves of pts and recursively calls mergeSortRec on these.
	 * After this is done, the two sorted sub-arrays are merged by calling merge().
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int ptsLength = pts.length;
		
		// Return if the length of pts is less than or equal to one. (Meaning it is time to combine the two sub-arrays)
		if (ptsLength <= 1) {
			return;
		}
		
		// Finds the midpoint with a bias of keeping extra values to the left.
		// This means that with an array of seven points, the left array will have four whilst the right has three.
		int mid = 1 + (ptsLength - 1) / 2;
		Point[] ptsLeft = new Point[mid];
		Point[] ptsRight = new Point[ptsLength - mid];
		
		// Copies the values of pts from 0 to mid into the ptsLeft array.
		for (int i = 0; i < mid; i++) {
			ptsLeft[i] = pts[i];
		}
		
		// Copies the values of pts from mid to the end into the ptsRight array.
		for (int i = 0; i < (ptsLength - mid); i++) {
			ptsRight[i] = pts[i + mid];
		}
		
		// Recursive calls to get ptsLeft and ptsRight arrays all the way down to just one point in each.
		mergeSortRec(ptsLeft);
		mergeSortRec(ptsRight);
		
		// Calls merge() to combine the two sub-arrays.
		Point[] tempPts = merge(ptsLeft, ptsRight);
		
		// Stores the newly found sorted points into the pts array of Points.
		int k = 0;
		for (int j = 0; j < tempPts.length; j++) {
			pts[k] = tempPts[j];
			k++;
		}
	}


	/**
	 * Takes in two arrays of points and combines them in order from least to greatest.
	 * 
	 * @param ptsLeft One of the sub-arrays to be merged.
	 * @param ptsRight The other sub-array to be merged.
	 * @return The sorted array of points.
	 */
	private Point[] merge(Point[] ptsLeft, Point[] ptsRight) {
		// Initialize some variables and gather lengths of the incoming arrays. Also, create a new array of size ptsLeft.length + ptsRight.length.
		int leftLength = ptsLeft.length;
		int rightLength = ptsRight.length;
		Point[] pts = new Point[leftLength + rightLength];
		int i = 0;
		int j = 0;
		int k = 0;
		
		// As long as i and j are less than their respective sub-array lengths, compare the points.
		while (i < leftLength && j < rightLength) {
			// If the point from ptsLeft is less than or equal to the point from ptsRight then assign the point from ptsLeft into pts.
			// Otherwise, assign the value from ptsRight into the pts array.
			if ((this.pointComparator.compare(ptsLeft[i], ptsRight[j])) <= 0) {
				pts[k] = ptsLeft[i];
				// Keeps track of where the program is currently at in each array.
				i++;
				k++;
			} else {
				pts[k] = ptsRight[j];
				// Keeps track of where the program is currently at in each array.
				j++;
				k++;
			}
		}
		
		// If the index "i" is greater than or equal to the length of the left sub-array, then assign 
		// all of the remaining values in ptsRight to pts. Else, assign the remaining values in ptsLeft to pts.
		if (i >= leftLength) {
			while (j < rightLength) {
				pts[k] = ptsRight[j];
				j++;
				k++;
			}
		} else {
			while (i < leftLength) {
				pts[k] = ptsLeft[i];
				i++;
				k++;
			}
		}
		
		// Return the sorted and combined array of Points.
		return pts;
	}
}
