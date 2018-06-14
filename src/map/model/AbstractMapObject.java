package map.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import map.collision.ICollisionBehavior;
import map.view.MapWindow;

public abstract class AbstractMapObject implements Serializable {

	private static final long serialVersionUID = 8389122848304472359L;

	protected boolean blocking;
	protected boolean drawBorders = false;
	private ICollisionBehavior collisionBehavior;

	public abstract void paint(final Graphics g, final int mapX, final int mapY);

	public abstract boolean collides(final Rectangle2D r);

	public abstract void resize(Rectangle r);

	public abstract int getOriginX();

	public abstract int getOriginY();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract AbstractMapObject getNewInstance();

	/**
	 * @param blocking
	 * @param drawBorders
	 */
	public AbstractMapObject(final boolean blocking, final boolean drawBorders) {
		this.blocking = blocking;
		this.drawBorders = drawBorders;
	}

	public void collisionEnter(final Player player, final MapWindow view, final Map map) {
		if (collisionBehavior != null) {
			collisionBehavior.collisionEnter(player, view, map);
		}
	}

	public void collisionLeave(final Player player, final MapWindow view, final Map map) {
		if (collisionBehavior != null) {
			collisionBehavior.collisionLeave(player, view, map);
		}
	}

	/**
	 * @return the collisionBehavior
	 */
	public ICollisionBehavior getCollisionBehavior() {
		return collisionBehavior;
	}

	/**
	 * @param collisionBehavior
	 *            the collisionBehavior to set
	 */
	public void setCollisionBehavior(final ICollisionBehavior collisionBehavior) {
		this.collisionBehavior = collisionBehavior;
	}

	/**
	 * @return the showRect
	 */
	public boolean isDrawBorders() {
		return drawBorders;
	}

	/**
	 * @param showRect
	 *            the showRect to set
	 */
	public void setDrawBorders(final boolean showRect) {
		this.drawBorders = showRect;
	}

	/**
	 * @return the blocking
	 */
	public boolean isBlocking() {
		return blocking;
	}

	/**
	 * @param blocking
	 *            the blocking to set
	 */
	public void setBlocking(final boolean blocking) {
		this.blocking = blocking;
	}

}