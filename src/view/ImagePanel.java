package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Roman Schmidt
 *
 */
public class ImagePanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 4792690664161978939L;
	private Image image;

	/**
	 * @param image
	 */
	public ImagePanel(final Image image) {
		this.image = image;
		repaint();
	}

	@Override
	protected void paintComponent(final Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} else {
			super.paintComponent(g);
		}
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(final Image image) {
		this.image = image;
		repaint();
	}

}
