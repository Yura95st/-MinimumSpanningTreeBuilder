package minimumSpanningTreeBuilder.Models;

public class VoronoiEdge
{
	public Point direction; // edge is really a vector normal to left and right points

	public Point end;

	public VoronoiEdge neighbor; // the same edge, but pointing in the opposite direction

	public Point site_left;

	public Point site_right;
	
	public double slope;
	
	public Point start;

	public double yint;
	
	public VoronoiEdge(Point first, Point left, Point right)
	{
		this.start = first;
		this.site_left = left;
		this.site_right = right;
		this.direction = new Point(right.y - left.y, -(right.x - left.x));
		this.end = null;
		this.slope = (right.x - left.x) / (left.y - right.y);
		Point mid = new Point((right.x + left.x) / 2, (left.y + right.y) / 2);
		this.yint = mid.y - this.slope * mid.x;
	}
	
	@Override
	public String toString()
	{
		return this.start + ", " + this.end;
	}
}
