 package edu.iastate.cs228.hw2;

/**
 * This class creates the Point for the array of points in abstractSorter.
 * It can determine if points are equal to each other, whether to compare to x or y points, and how to points compare.
 * @author Logan Roe
 */

public class Point implements Comparable<Point>
{
	private int x; 
	private int y;
	
	public static boolean xORy;
	
	
	/**
	 * Constructor that creates a Point with default values 0, 0.
	 */
	public Point()
	{
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructor that creates a Point at a given x and y coordinate.
	 * @param x
	 * @param y
	 */
	public Point(int x, int y)
	{
		this.x = x;  
		this.y = y;   
	}
	
	/**
	 * A constructor that copies one point over to a new point.
	 * @param p
	 */
	public Point(Point p) { // copy constructor
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Returns the x coordinate of the current Point.
	 * @return x
	 */
	public int getX()   
	{
		return x;
	}
	
	/**
	 * Returns the y coordinate of the current Point.
	 * @return y
	 */
	public int getY()
	{
		return y;
	}
	
	/** 
	 * Set the value of the static instance variable xORy. 
	 * True means that x is the current coordinate being compared and false means that y is.
	 * @param xORy
	 */
	public static void setXorY(boolean xORy)
	{
		Point.xORy = xORy; 
	}
	
	
	/**
	 * Determines if two points are equal or not by comparing the current point and the passed in obj point.
	 * @param obj 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
    
		Point other = (Point) obj;
		return x == other.x && y == other.y;   
	}

	/**
	 * Compare this point with a second point q depending on the value of the static variable xORy 
	 * @param 	q 
	 * @return  -1  if (xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) 
	 *                || (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))
	 * 		    0   if this.x == q.x && this.y == q.y)  
	 * 			1	otherwise 
	 */
	public int compareTo(Point q)
	{
		if ((xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {
			return -1;
		} else if (this.equals(q)) {
			return 0;
		} else {
			return 1;
		}
	}
	
	
	/**
	 * Output a point in the standard form (x, y). 
	 */
	@Override
    public String toString() 
	{
		return "(" + this.x + ", " + this.y + ")";  
	}
}
