package minimumSpanningTreeBuilder.Models;

import minimumSpanningTreeBuilder.Enums.EventType;
import minimumSpanningTreeBuilder.Utils.Guard;

public class SiteEvent extends Event
{
	private final Point _point;

	public SiteEvent(Point point)
	{
		Guard.notNull(point, "point");

		this._point = point;
	}

	@Override
	public EventType getEventType()
	{
		return EventType.Site;
	}

	public Point getPoint()
	{
		return this._point;
	}

	@Override
	public double getY()
	{
		return this._point.getY();
	}

}
