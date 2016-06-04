package minimumSpanningTreeBuilder.Models;

import minimumSpanningTreeBuilder.Utils.Guard;

public class Edge implements Comparable<Edge>
{
	private final Point _u;

	private final Point _v;

	private final double _weight;

	public Edge(Point u, Point v)
	{
		Guard.notNull(u, "u");
		Guard.notNull(v, "v");

		this._u = u;
		this._v = v;

		this._weight =
			Math.pow(this._u.getX() - this._v.getX(), 2)
				+ Math.pow(this._u.getY() - this._v.getY(), 2);
	}

	@Override
	public int compareTo(Edge other)
	{
		return (int) Math.signum(this._weight - other._weight);
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

		if (Double.doubleToLongBits(this._weight) != Double
				.doubleToLongBits(other._weight))
		{
			return false;
		}

		if (this._u == null)
		{
			if (other._u != null && other._v != null)
			{
				return false;
			}
		}
		else if (!this._u.equals(other._u) && !this._u.equals(other._v))
		{
			return false;
		}

		if (this._v == null)
		{
			if (other._v != null && other._u != null)
			{
				return false;
			}
		}
		else if (!this._v.equals(other._v) && !this._v.equals(other._u))
		{
			return false;
		}

		return true;
	}

	public Point getU()
	{
		return this._u;
	}

	public Point getV()
	{
		return this._v;
	}

	public double getWeight()
	{
		return this._weight;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result =
			prime * result + ((this._u == null) ? 0 : this._u.hashCode())
				+ ((this._v == null) ? 0 : this._v.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this._weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
