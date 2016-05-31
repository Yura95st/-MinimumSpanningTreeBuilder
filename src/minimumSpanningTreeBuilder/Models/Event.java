package minimumSpanningTreeBuilder.Models;

import minimumSpanningTreeBuilder.Enums.EventType;

public abstract class Event implements Comparable<Event>
{
	@Override
	public int compareTo(Event e)
	{
		return (int) Math.signum(this.getY() - e.getY());
	}

	public abstract EventType getEventType();

	public abstract double getY();
}
