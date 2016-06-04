package minimumSpanningTreeBuilder.Models;

import minimumSpanningTreeBuilder.Enums.EventType;
import minimumSpanningTreeBuilder.Utils.Guard;

public class ParabolaEvent extends Event
{
	public Parabola arch;

	private final Point _point;

	public ParabolaEvent(Point point)
	{
		Guard.notNull(point, "point");

		this._point = point;
	}

	@Override
	public EventType getEventType()
	{
		return EventType.Circle;
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
