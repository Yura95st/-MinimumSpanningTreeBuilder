package minimumSpanningTreeBuilder.Models;

public class Edge implements Comparable<Edge>
{
	public Point u;
	
	public Point v;
	
	public double weight;
	
	public Edge(Point u, Point v)
	{
		this.u = u;
		this.v = v;
		this.weight = Math.pow(this.u.getX() - this.v.getX(), 2)
			+ Math.pow(this.u.getY() - this.v.getY(), 2);
	}
	
	@Override
	public int compareTo(Edge other)
	{
		return (int) Math.signum(this.weight - other.weight);
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
		
		Edge other = (Edge) obj;
		
		if (Double.doubleToLongBits(this.weight) != Double
				.doubleToLongBits(other.weight))
		{
			return false;
		}
		
		if (this.u == null)
		{
			if (other.u != null && other.v != null)
			{
				return false;
			}
		}
		else if (!this.u.equals(other.u) && !this.u.equals(other.v))
		{
			return false;
		}
		
		if (this.v == null)
		{
			if (other.v != null && other.u != null)
			{
				return false;
			}
		}
		else if (!this.v.equals(other.v) && !this.v.equals(other.u))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.u == null) ? 0 : this.u.hashCode()) + ((this.v == null) ? 0 : this.v.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this.weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
