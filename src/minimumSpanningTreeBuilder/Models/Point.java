package minimumSpanningTreeBuilder.Models;

// a point in 2D, sorted by y-coordinate
public class Point implements Comparable<Point>
{
	
	public double x;

	public double y;
	
	public Point(double x0, double y0)
	{
		this.x = x0;
		this.y = y0;
	}
	
	@Override
	public int compareTo(Point other)
	{
		if (this.y == other.y)
		{
			if (this.x == other.x)
			{
				return 0;
			}
			else if (this.x > other.x)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else if (this.y > other.y)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Point))
		{
			return false;
		}

		Point other = (Point) obj;

		return other != null && this.x == other.x && this.y == other.y;
	}
	
	@Override
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
}
