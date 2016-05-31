package minimumSpanningTreeBuilder.Models;

public class Parabola
{
	public static Parabola getLeftChild(Parabola p)
	{
		if (p == null)
		{
			return null;
		}
		Parabola par = p.getLeft();
		while (!par.isLeaf)
		{
			par = par.getRight();
		}
		return par;
	}
	
	public static Parabola getLeftParent(Parabola p)
	{
		Parabola par = p.parent;
		Parabola pLast = p;
		while (par.getLeft() == pLast)
		{
			if (par.parent == null)
			{
				return null;
			}
			pLast = par;
			par = par.parent;
		}
		return par;
	}
	
	public static Parabola getRightChild(Parabola p)
	{
		if (p == null)
		{
			return null;
		}
		Parabola par = p.getRight();
		while (!par.isLeaf)
		{
			par = par.getLeft();
		}
		return par;
	}
	
	public static Parabola getRightParent(Parabola p)
	{
		Parabola par = p.parent;
		if (par == null)
		{
			return null;
		}
		
		Parabola pLast = p;
		while (par.getRight() == pLast)
		{
			if (par.parent == null)
			{
				return null;
			}
			pLast = par;
			par = par.parent;
		}
		return par;
	}
	
	public VoronoiEdge edge;
	
	public Event event;
	
	public boolean isLeaf;
	
	public Parabola parent;
	
	private Parabola _left;
	
	private final Point _point;
	
	private Parabola _right;
	
	public Parabola()
	{
		this._point = null;
		this.isLeaf = false;
	}
	
	public Parabola(Point point)
	{
		this._point = point;
		this.isLeaf = true;
	}
	
	public Parabola getLeft()
	{
		return this._left;
	}
	
	public Point getPoint()
	{
		return this._point;
	}
	
	public Parabola getRight()
	{
		return this._right;
	}
	
	public void setLeft(Parabola left)
	{
		this._left = left;
		left.parent = this;
	}
	
	public void setRight(Parabola right)
	{
		this._right = right;
		right.parent = this;
	}
}
