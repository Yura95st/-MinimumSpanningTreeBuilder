package minimumSpanningTreeBuilder.Models;

// an event is either a site or circle event for the sweep line to process
public class Event implements Comparable<Event>
{
	
	// a circle event is when the point is a vertex of the voronoi
	// diagram/parabolas
	public static int CIRCLE_EVENT = 1;
	
	// a site event is when the point is a site
	public static int SITE_EVENT = 0;
	
	public Parabola arc; // only if circle event

	public Point p;

	public int type;
	
	public Event(Point p, int type)
	{
		this.p = p;
		this.type = type;
		this.arc = null;
	}
	
	@Override
	public int compareTo(Event other)
	{
		return this.p.compareTo(other.p);
	}

}
