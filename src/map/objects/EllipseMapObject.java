package map.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import map.model.AbstractMapObject;

public class EllipseMapObject extends AbstractMapObject {

	private static final long serialVersionUID = 6904845891502228922L;

	private Ellipse2D ellipse;

	public EllipseMapObject(final Ellipse2D ellipse, final boolean blocking, final boolean drawBorders) {
		super(blocking, drawBorders);
		this.ellipse = ellipse;
	}

	@Override
	public void paint(final Graphics g, final int mapX, final int mapY) {
		if (drawBorders) {
			g.drawOval(ellipse.getBounds().x + mapX, ellipse.getBounds().y + mapY, ellipse.getBounds().width, ellipse.getBounds().height);
		}
	}

	@Override
	public boolean collides(final Rectangle2D r) {
		return ellipse.intersects(r);
	}

	@Override
	public void resize(final Rectangle r) {
		ellipse = new Ellipse2D.Double(r.x, r.y, r.width, r.height);
	}

	@Override
	public int getOriginX() {
		return ellipse.getBounds().x;
	}

	@Override
	public int getOriginY() {
		return ellipse.getBounds().y;
	}

	@Override
	public AbstractMapObject getNewInstance() {
		return new EllipseMapObject(new Ellipse2D.Double(), blocking, drawBorders);
	}

	@Override
	public int getWidth() {
		return (int) ellipse.getWidth();
	}

	@Override
	public int getHeight() {
		return (int) ellipse.getHeight();
	}

}
