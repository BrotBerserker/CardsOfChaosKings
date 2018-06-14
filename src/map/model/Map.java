package map.model;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Map implements Serializable {

	private static final long serialVersionUID = 3719966584932457900L;

	private int width;
	private int height;
	private ImageIcon image;

	private int playerX;
	private int playerY;
	private List<AbstractMapObject> objects = new ArrayList<AbstractMapObject>();

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * @param width
	 * @param height
	 * @param image
	 */
	public Map(final int width, final int height, final ImageIcon image) {
		this.width = width;
		this.height = height;
		this.image = image;
		playerX = width / 2;
		playerY = height / 2;
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addMapObject(final AbstractMapObject obj) {
		objects.add(obj);
		propertyChangeSupport.firePropertyChange("objectAdded", null, obj);
	}

	public void removeLastMapObject() {
		if (objects.size() > 0) {
			objects.remove(objects.size() - 1);
			propertyChangeSupport.firePropertyChange("objectRemoved", null, objects);
		}
	}

	public void removeAllMapObjects() {
		objects.clear();
		propertyChangeSupport.firePropertyChange("allObjectsRemoved", null, objects);
	}

	public void changeMapObjectSize(final AbstractMapObject obj, final int newX, final int newY) {
		obj.resize(new Rectangle(obj.getOriginX(), obj.getOriginY(), newX, newY));
		propertyChangeSupport.firePropertyChange("objectResized", null, obj);
	}

	public void changeMapObjectPosition(final AbstractMapObject obj, final int newX, final int newY) {
		obj.resize(new Rectangle(newX, newY, obj.getWidth(), obj.getHeight()));
		propertyChangeSupport.firePropertyChange("objectRepositioned", null, obj);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * @return the image
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(final ImageIcon image) {
		this.image = image;
	}

	/**
	 * @return the objects
	 */
	public List<AbstractMapObject> getObjects() {
		return objects;
	}

	/**
	 * @return the playerX
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * @return the playerY
	 */
	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerPos(final int x, final int y) {
		propertyChangeSupport.firePropertyChange("playerX", playerX, x);
		propertyChangeSupport.firePropertyChange("playerY", playerY, y);
		playerX = x;
		playerY = y;
	}

}
