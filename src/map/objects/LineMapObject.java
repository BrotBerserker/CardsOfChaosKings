package map.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import map.model.AbstractMapObject;

public class LineMapObject extends AbstractMapObject {

	private static final long serialVersionUID = 8562492048469673508L;

	private Line2D line;

	/**
	 * @param blocking
	 * @param drawBorders
	 * @param line
	 */
	public LineMapObject(final Line2D line, final boolean blocking, final boolean drawBorders) {
		super(blocking, drawBorders);
		this.line = line;
	}

	@Override
	public void paint(final Graphics g, final int mapX, final int mapY) {
		if (drawBorders) {
			g.drawLine((int) line.getX1() + mapX, (int) line.getY1() + mapY, (int) line.getX2() + mapX, (int) line.getY2() + mapY);
		}
	}

	@Override
	public boolean collides(final Rectangle2D r) {
		return line.intersects(r);
	}

	@Override
	public void resize(final Rectangle r) {
		line = new Line2D.Double(r.x, r.y, r.x + r.width, r.y + r.height);
	}

	@Override
	public int getOriginX() {
		return (int) line.getX1();
	}

	@Override
	public int getOriginY() {
		return (int) line.getY1();
	}

	@Override
	public AbstractMapObject getNewInstance() {
		return new LineMapObject(new Line2D.Double(), blocking, drawBorders);
	}

	@Override
	public int getWidth() {
		return line.getBounds().width;
	}

	@Override
	public int getHeight() {
		return line.getBounds().height;
	}

}
