package minimumSpanningTreeBuilder.Models;

// represents the beach line
// can either be a site that is the center of a parabola
// or can be a vertex that bisects two sites
public class Parabola
{
	
	public static int IS_FOCUS = 0;

	public static int IS_VERTEX = 1;
	
	// returns the closest left site (focus of parabola)
	public static Parabola getLeft(Parabola p)
	{
		return Parabola.getLeftChild(Parabola.getLeftParent(p));
	}

	// returns closest site (focus of another parabola) to the left
	public static Parabola getLeftChild(Parabola p)
	{
		if (p == null)
		{
			return null;
		}
		Parabola child = p.child_left;
		while (child.type == Parabola.IS_VERTEX)
		{
			child = child.child_right;
		}
		return child;
	}

	// returns the closest parent on the left
	public static Parabola getLeftParent(Parabola p)
	{
		Parabola parent = p.parent;
		if (parent == null)
		{
			return null;
		}
		Parabola last = p;
		while (parent.child_left == last)
		{
			if (parent.parent == null)
			{
				return null;
			}
			last = parent;
			parent = parent.parent;
		}
		return parent;
	}

	// returns closest right site (focus of parabola)
	public static Parabola getRight(Parabola p)
	{
		return Parabola.getRightChild(Parabola.getRightParent(p));
	}
	
	// returns closest site (focus of another parabola) to the right
	public static Parabola getRightChild(Parabola p)
	{
		if (p == null)
		{
			return null;
		}
		Parabola child = p.child_right;
		while (child.type == Parabola.IS_VERTEX)
		{
			child = child.child_left;
		}
		return child;
	}

	// returns the closest parent on the right
	public static Parabola getRightParent(Parabola p)
	{
		Parabola parent = p.parent;
		if (parent == null)
		{
			return null;
		}
		Parabola last = p;
		while (parent.child_right == last)
		{
			if (parent.parent == null)
			{
				return null;
			}
			last = parent;
			parent = parent.parent;
		}
		return parent;
	}

	public Parabola child_left;
	
	public Parabola child_right;
	
	public VoronoiEdge edge; // if is vertex

	public Event event; // a parabola with a focus can disappear in a circle event

	public Parabola parent;

	public Point point; // if is focus
	
	public int type;
	
	public Parabola()
	{
		this.type = Parabola.IS_VERTEX;
	}
	
	public Parabola(Point p)
	{
		this.point = p;
		this.type = Parabola.IS_FOCUS;
	}
	
	public void setLeftChild(Parabola p)
	{
		this.child_left = p;
		p.parent = this;
	}
	
	public void setRightChild(Parabola p)
	{
		this.child_right = p;
		p.parent = this;
	}
	
	@Override
	public String toString()
	{
		if (this.type == Parabola.IS_FOCUS)
		{
			return "Focus at " + this.point;
		}
		else
		{
			return "Vertex/Edge beginning at " + this.edge.start;
		}
	}

}
