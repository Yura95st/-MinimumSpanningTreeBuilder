package minimumSpanningTreeBuilder.Models;

public class Edge
{
	public Point u;

	public Point v;

	public double weight;

	public Edge(Point u, Point v)
	{
		this.u = u;
		this.v = v;
		this.weight = Math.pow(this.u.x - this.v.x, 2)
				+ Math.pow(this.u.y - this.v.y, 2);
	}
}
