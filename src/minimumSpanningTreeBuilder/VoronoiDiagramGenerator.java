package minimumSpanningTreeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import minimumSpanningTreeBuilder.Models.Event;
import minimumSpanningTreeBuilder.Models.Parabola;
import minimumSpanningTreeBuilder.Models.ParabolaEvent;
import minimumSpanningTreeBuilder.Models.Point;
import minimumSpanningTreeBuilder.Models.SiteEvent;
import minimumSpanningTreeBuilder.Models.VoronoiEdge;
import minimumSpanningTreeBuilder.Utils.Guard;

public class VoronoiDiagramGenerator
{
	private double _currentY; // current y-coord of sweep line

	private final List<VoronoiEdge> _edges;

	private final PriorityQueue<Event> _events;

	private Parabola _root; // binary search tree represents beach line

	public VoronoiDiagramGenerator()
	{
		this._edges = new ArrayList<VoronoiEdge>();
		this._events = new PriorityQueue<Event>();
	}

	public List<VoronoiEdge> generateDiagram(Iterable<Point> points,
		double width, double height)
	{
		Guard.notNull(points, "points");
		Guard.moreThanZero(width, "width");
		Guard.moreThanZero(height, "height");

		this._edges.clear();
		this._events.clear();

		for (Point p : points)
		{
			this._events.add(new SiteEvent(p));
		}

		while (!this._events.isEmpty())
		{
			Event e = this._events.remove();
			this._currentY = e.getY();

			switch (e.getEventType())
			{
				case Site:
				{
					this.addParabola(((SiteEvent) e).getPoint());
					break;
				}

				case Circle:
				{
					this.removeParabola((ParabolaEvent) e);
					break;
				}
			}
		}

		this._currentY = width + height;

		this.endEdges(this._root); // close off any dangling edges

		// get rid of those crazy inifinte lines
		for (VoronoiEdge e : this._edges)
		{
			if (e.neighbour != null)
			{
				e.start = e.neighbour.end;
				e.neighbour = null;
			}
		}

		return this._edges;
	}

	// processes site event
	private void addParabola(Point point)
	{
		// base case
		if (this._root == null)
		{
			this._root = new Parabola(point);
			return;
		}

		// find parabola on beach line right above p
		Parabola par = this.getParabolaByX(point.getX());
		if (par.event != null)
		{
			this._events.remove(par.event);
			par.event = null;
		}

		// create new dangling edge; bisects parabola focus and p
		Point start =
				new Point(point.getX(), this.getY(par.getPoint(), point.getX()));

		VoronoiEdge el = new VoronoiEdge(start, par.getPoint(), point);
		VoronoiEdge er = new VoronoiEdge(start, point, par.getPoint());

		el.neighbour = er;
		er.neighbour = el;

		par.edge = el;
		par.isLeaf = false;

		// replace original parabola par with p0, p1, p2
		Parabola p0 = new Parabola(par.getPoint());
		Parabola p1 = new Parabola(point);
		Parabola p2 = new Parabola(par.getPoint());

		par.setLeft(p0);
		par.setRight(new Parabola());
		par.getRight().edge = er;

		par.getRight().setLeft(p1);
		par.getRight().setRight(p2);

		this.checkCircleEvent(p0);
		this.checkCircleEvent(p2);
	}

	// first thing we learned in this class :P
	private int ccw(Point a, Point b, Point c)
	{
		double area2 =
				(b.getX() - a.getX()) * (c.getY() - a.getY())
				- (b.getY() - a.getY()) * (c.getX() - a.getX());
		if (area2 < 0)
		{
			return -1;
		}
		else if (area2 > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	// adds circle event if foci a, b, c lie on the same circle
	private void checkCircleEvent(Parabola b)
	{
		Parabola lp = Parabola.getLeftParent(b);
		Parabola rp = Parabola.getRightParent(b);

		if (lp == null || rp == null)
		{
			return;
		}

		Parabola a = Parabola.getLeftChild(lp);
		Parabola c = Parabola.getRightChild(rp);

		if (a == null || c == null || a.getPoint() == c.getPoint())
		{
			return;
		}

		if (this.ccw(a.getPoint(), b.getPoint(), c.getPoint()) != 1)
		{
			return;
		}

		// edges will intersect to form a vertex for a circle event
		Point start = this.getEdgeIntersection(lp.edge, rp.edge);
		if (start == null)
		{
			return;
		}

		// compute radius
		double dx = b.getPoint().getX() - start.getX();
		double dy = b.getPoint().getY() - start.getY();

		double d = Math.sqrt((dx * dx) + (dy * dy));

		if (start.getY() + d < this._currentY)
		{
			return; // must be after sweep line
		}

		// add circle event
		ParabolaEvent e =
				new ParabolaEvent(new Point(start.getX(), start.getY() + d));
		e.arch = b;
		b.event = e;
		this._events.add(e);
	}

	// end all unfinished edges
	private void endEdges(Parabola p)
	{
		if (p.isLeaf)
		{
			return;
		}

		double x = this.getXofEdge(p);
		p.edge.end = new Point(x, p.edge.f * x + p.edge.g);
		this._edges.add(p.edge);

		this.endEdges(p.getLeft());
		this.endEdges(p.getRight());
	}

	// returns intersection of the lines of with vectors a and b
	private Point getEdgeIntersection(VoronoiEdge a, VoronoiEdge b)
	{

		if (b.f == a.f && b.g != a.g)
		{
			return null;
		}

		double x = (b.g - a.g) / (a.f - b.f);
		double y = a.f * x + a.g;

		return new Point(x, y);
	}

	// returns parabola above this x coordinate in the beach line
	private Parabola getParabolaByX(double xx)
	{
		Parabola par = this._root;
		double x = 0;

		while (!par.isLeaf)
		{
			x = this.getXofEdge(par);
			if (x > xx)
			{
				par = par.getLeft();
			}
			else
			{
				par = par.getRight();
			}
		}
		return par;
	}

	// returns current x-coordinate of an unfinished edge
	private double getXofEdge(Parabola par)
	{
		// find intersection of two parabolas

		Parabola left = Parabola.getLeftChild(par);
		Parabola right = Parabola.getRightChild(par);

		Point p = left.getPoint();
		Point r = right.getPoint();

		double dp = 2 * (p.getY() - this._currentY);
		double a1 = 1 / dp;
		double b1 = -2 * p.getX() / dp;
		double c1 =
				(p.getX() * p.getX() + p.getY() * p.getY() - this._currentY
						* this._currentY)
						/ dp;

		double dp2 = 2 * (r.getY() - this._currentY);
		double a2 = 1 / dp2;
		double b2 = -2 * r.getX() / dp2;
		double c2 =
				(r.getX() * r.getX() + r.getY() * r.getY() - this._currentY
						* this._currentY)
						/ dp2;

		double a = a1 - a2;
		double b = b1 - b2;
		double c = c1 - c2;

		double disc = b * b - 4 * a * c;
		double x1 = (-b + Math.sqrt(disc)) / (2 * a);
		double x2 = (-b - Math.sqrt(disc)) / (2 * a);

		double ry;
		if (p.getY() > r.getY())
		{
			ry = Math.max(x1, x2);
		}
		else
		{
			ry = Math.min(x1, x2);
		}

		return ry;
	}

	// find corresponding y-coordinate to x on parabola with focus p
	private double getY(Point p, double x)
	{
		// determine equation for parabola around focus p
		double dp = 2 * (p.getY() - this._currentY);
		double a1 = 1 / dp;
		double b1 = -2 * p.getX() / dp;
		double c1 =
				(p.getX() * p.getX() + p.getY() * p.getY() - this._currentY
						* this._currentY)
						/ dp;
		return (a1 * x * x + b1 * x + c1);
	}

	// process circle event
	private void removeParabola(ParabolaEvent e)
	{

		// find p0, p1, p2 that generate this event from left to right
		Parabola p1 = e.arch;

		Parabola xl = Parabola.getLeftParent(p1);
		Parabola xr = Parabola.getRightParent(p1);

		Parabola p0 = Parabola.getLeftChild(xl);
		Parabola p2 = Parabola.getRightChild(xr);

		// remove associated events since the points will be altered
		if (p0.event != null)
		{
			this._events.remove(p0.event);
			p0.event = null;
		}
		if (p2.event != null)
		{
			this._events.remove(p2.event);
			p2.event = null;
		}

		Point p =
				new Point(e.getPoint().getX(), this.getY(p1.getPoint(), e
					.getPoint().getX())); // new vertex

		// end edges!
		xl.edge.end = p;
		xr.edge.end = p;
		this._edges.add(xl.edge);
		this._edges.add(xr.edge);

		// start new bisector (edge) from this vertex on which ever original
		// edge is higher in tree
		Parabola higher = new Parabola();
		Parabola par = p1;
		while (par != this._root)
		{
			par = par.parent;
			if (par == xl)
			{
				higher = xl;
			}
			if (par == xr)
			{
				higher = xr;
			}
		}
		higher.edge = new VoronoiEdge(p, p0.getPoint(), p2.getPoint());

		// delete p1 and parent (boundary edge) from beach line
		Parabola gparent = p1.parent.parent;
		if (p1.parent.getLeft() == p1)
		{
			if (gparent.getLeft() == p1.parent)
			{
				gparent.setLeft(p1.parent.getRight());
			}
			if (gparent.getRight() == p1.parent)
			{
				gparent.setRight(p1.parent.getRight());
			}
		}
		else
		{
			if (gparent.getLeft() == p1.parent)
			{
				gparent.setLeft(p1.parent.getLeft());
			}
			if (gparent.getRight() == p1.parent)
			{
				gparent.setRight(p1.parent.getLeft());
			}
		}

		this.checkCircleEvent(p0);
		this.checkCircleEvent(p2);
	}
}
