package map.objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import map.model.AbstractMapObject;

public class ImageObject extends RectangleMapObject {

	private static final long serialVersionUID = -4574166788687119191L;
	private final ImageIcon image;
	private String imageName;

	public ImageObject(final Rectangle bounds, final boolean blocking, final String imageName) {
		super(bounds, blocking, false);
		this.imageName = imageName;
		image = new ImageIcon("images/" + imageName);
	}

	public ImageObject(final int x, final int y, final boolean blocking, final String imageName) {
		super(new Rectangle(x, y, 0, 0), blocking, false);
		image = new ImageIcon("images/" + imageName);
		resize(new Rectangle(getOriginX(), getOriginY(), image.getIconWidth(), image.getIconHeight()));
	}

	@Override
	public void paint(final Graphics g, final int mapX, final int mapY) {
		super.paint(g, mapX, mapY);
		g.drawImage(image.getImage(), rectangle.x + mapX, rectangle.y + mapY, rectangle.width, rectangle.height, null);
	}

	@Override
	public AbstractMapObject getNewInstance() {
		return new ImageObject(rectangle, blocking, imageName);
	}

}
