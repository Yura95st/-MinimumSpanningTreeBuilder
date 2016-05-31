package minimumSpanningTreeBuilder.Models;

public class VoronoiEdge
{
	public Point direction; // edge is really a vector normal to left and right points

	public Point end;

	public VoronoiEdge neighbour; // the same edge, but pointing in the opposite direction

	public Point left;

	public Point right;
	
	public double f;
	
	public Point start;

	public double g;
	
	public VoronoiEdge(Point start, Point left, Point right)
	{
		this.start = start;
		this.left = left;
		this.right = right;
		
		this.direction = new Point(this.right.getY() - this.left.getY(),
			-(this.right.getX() - this.left.getX()));
		
		this.f = (this.right.getX() - left.getX())
				/ (left.getY() - right.getY());
		
		//Point mid = new Point((right.getX() + left.getX()) / 2, (left.getY() + right.getY()) / 2);
		//this.g = mid.getY() - this.f * mid.getX();		
		this.g = start.getY() - this.f * start.getX();
	}
}
