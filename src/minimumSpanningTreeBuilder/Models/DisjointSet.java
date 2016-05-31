package minimumSpanningTreeBuilder.Models;

public class DisjointSet<T>
{
	public static DisjointSet find(DisjointSet x)
	{
		if (x.parent != x)
		{
			x.parent = DisjointSet.find(x.parent);
		}

		return x.parent;
	}

	public static boolean union(DisjointSet x, DisjointSet y)
	{
		DisjointSet xRep = DisjointSet.find(x);
		DisjointSet yRep = DisjointSet.find(y);

		if (xRep == yRep)
		{
			return false;
		}

		if (xRep.rank < yRep.rank)
		{
			xRep.parent = yRep;
		}
		else
		{
			yRep.parent = xRep;

			if (xRep.rank == yRep.rank)
			{
				++xRep.rank;
			}
		}

		return true;
	}

	private final T _data;

	public DisjointSet<T> parent;

	public int rank;

	public T getData()
	{
		return this._data;
		
	}
	
	public DisjointSet(T data)
	{
		this._data = data;
		
		this.parent = this;
	}
}
