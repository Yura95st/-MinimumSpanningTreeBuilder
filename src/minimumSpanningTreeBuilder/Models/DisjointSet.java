package minimumSpanningTreeBuilder.Models;

import minimumSpanningTreeBuilder.Utils.Guard;

public class DisjointSet<T>
{
	public static DisjointSet find(DisjointSet x)
	{
		Guard.notNull(x, "x");

		if (x._parent != x)
		{
			x._parent = DisjointSet.find(x._parent);
		}

		return x._parent;
	}

	public static boolean union(DisjointSet x, DisjointSet y)
	{
		Guard.notNull(x, "x");
		Guard.notNull(y, "y");

		DisjointSet xRep = DisjointSet.find(x);
		DisjointSet yRep = DisjointSet.find(y);

		if (xRep == yRep)
		{
			return false;
		}

		if (xRep._rank < yRep._rank)
		{
			xRep._parent = yRep;
		}
		else
		{
			yRep._parent = xRep;

			if (xRep._rank == yRep._rank)
			{
				++xRep._rank;
			}
		}

		return true;
	}

	private final T _data;

	private DisjointSet<T> _parent;

	private int _rank;

	public DisjointSet(T data)
	{
		Guard.notNull(data, "data");

		this._data = data;

		this._parent = this;
	}

	public T getData()
	{
		return this._data;
	}

	public DisjointSet<T> getParent()
	{
		return this._parent;
	}

	public int getRank()
	{
		return this._rank;
	}
}
