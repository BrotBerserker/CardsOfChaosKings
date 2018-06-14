package map.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;

public class Player {
	private String imageFolder;
	private String direction = "down";
	private int movementX = 0;
	private int movementY = 0;
	private int speed = 1;
	private int width;
	private int height;

	private int lastImage = 0;
	private int imageCount = 0;
	private boolean showRect;

	private final PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);

	/**
	 * @param imageFolder
	 */
	public Player(final String imageFolder, final int width, final int height, final boolean showRect) {
		setImageFolder(imageFolder);
		this.width = width;
		this.height = height;
		this.showRect = showRect;
	}

	public ImageIcon getImage() {
		// TODO performance
		if (movementX == 0 && movementY == 0) {
			return new ImageIcon(imageFolder + "/" + direction + "0.png");
		}
		imageCount++;
		if (imageCount == 25) {
			lastImage = (lastImage + 1) % 4;
			imageCount = 0;
		}
		return new ImageIcon(imageFolder + "/" + direction + lastImage + ".png");
	}

	private void updateDirection() {
		final String old = direction;
		if (movementX > 0) {
			direction = "right";
		} else if (movementX < 0) {
			direction = "left";
		}
		if (movementY > 0) {
			direction = "down";
		} else if (movementY < 0) {
			direction = "up";
		}
		propChangeSupport.firePropertyChange("direction", old, direction);
	}

	// TODO fire events in setters
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propChangeSupport.addPropertyChangeListener(listener);
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
	 * @return the imageFolder
	 */
	public String getImageFolder() {
		return imageFolder;
	}

	/**
	 * @param imageFolder
	 *            the imageFolder to set
	 */
	public void setImageFolder(final String imageFolder) {
		this.imageFolder = imageFolder;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @return the movementX
	 */
	public int getMovementX() {
		return movementX;
	}

	/**
	 * @param movementX
	 *            the movementX to set
	 */
	public void setMovementX(final int movementX) {
		this.movementX = movementX;
		updateDirection();
	}

	/**
	 * @return the movementY
	 */
	public int getMovementY() {
		return movementY;
	}

	/**
	 * @param movementY
	 *            the movementY to set
	 */
	public void setMovementY(final int movementY) {
		this.movementY = movementY;
		updateDirection();
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * @return the showRect
	 */
	public boolean isShowRect() {
		return showRect;
	}

	/**
	 * @param showRect
	 *            the showRect to set
	 */
	public void setShowRect(final boolean showRect) {
		this.showRect = showRect;
	}

}
