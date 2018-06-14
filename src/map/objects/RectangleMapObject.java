package map.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import map.model.AbstractMapObject;

public class RectangleMapObject extends AbstractMapObject {

	private static final long serialVersionUID = 8955634381688484986L;

	protected Rectangle rectangle;

	/**
	 * @param bounds
	 * @param blocking
	 * @param drawBorders
	 */
	public RectangleMapObject(final Rectangle bounds, final boolean blocking, final boolean drawBorders) {
		super(blocking, drawBorders);
		this.rectangle = bounds;
	}

	@Override
	public void paint(final Graphics g, final int mapX, final int mapY) {
		if (drawBorders) {
			g.drawRect(rectangle.x + mapX, rectangle.y + mapY, rectangle.width, rectangle.height);
		}
	}

	@Override
	public boolean collides(final Rectangle2D r) {
		return rectangle.intersects(r);
	}

	@Override
	public void resize(final Rectangle r) {
		this.rectangle = r;
	}

	@Override
	public int getOriginX() {
		return rectangle.x;
	}

	@Override
	public int getOriginY() {
		return rectangle.y;
	}

	@Override
	public AbstractMapObject getNewInstance() {
		return new RectangleMapObject(new Rectangle(), blocking, drawBorders);
	}

	@Override
	public int getWidth() {
		return rectangle.width;
	}

	@Override
	public int getHeight() {
		return rectangle.height;
	}

}
