package minimumSpanningTreeBuilder.Models;

public class Point
{
	private final double _x;
	
	private final double _y;
	
	public Point(double x, double y)
	{
		this._x = x;
		this._y = y;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		Point other = (Point) obj;
		if (Double.doubleToLongBits(this._x) != Double
				.doubleToLongBits(other._x))
		{
			return false;
		}
		if (Double.doubleToLongBits(this._y) != Double
				.doubleToLongBits(other._y))
		{
			return false;
		}
		return true;
	}
	
	public double getX()
	{
		return this._x;
	}
	
	public double getY()
	{
		return this._y;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this._x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this._y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
