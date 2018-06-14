package map.view;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import map.model.AbstractMapObject;
import map.model.Map;
import map.model.Player;

public class MapPanel extends JPanel {

	protected static final long serialVersionUID = 4792690664161978939L;
	protected Map map;
	protected final Player player;

	protected boolean showSpacebar = false;
	protected ImageIcon spacebarImage = new ImageIcon("images/spacebar.png");

	/**
	 * @param image
	 */
	public MapPanel(final Map map, final Player player) {
		this.map = map;
		this.player = player;
		map.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				repaint();
			}
		});
		player.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(final Graphics g) {
		drawMapAndPlayer(g);
		if (showSpacebar) {
			drawSpacebar(g);
		}
	}

	protected void drawSpacebar(final Graphics g) {
		g.drawImage(spacebarImage.getImage(), getParent().getWidth() / 2 - 75, getParent().getHeight() - 50, 150, 25, null);
	}

	protected void drawMapAndPlayer(final Graphics g) {
		final ImageIcon playerIcon = player.getImage();

		// map and player x
		int mapX = (getParent().getWidth() - map.getWidth()) / 2 - map.getPlayerX() + map.getWidth() / 2;
		int playerX = getParent().getWidth() / 2 - playerIcon.getIconWidth() / 2;
		if (mapX >= 0) {
			mapX = 0;
			playerX = map.getPlayerX() - playerIcon.getIconWidth() / 2;
		} else if (mapX + map.getWidth() < getParent().getWidth()) {
			mapX = getParent().getWidth() - map.getWidth();
			playerX = map.getPlayerX() + mapX - playerIcon.getIconWidth() / 2;
		}

		// map and player y
		int mapY = (getParent().getHeight() - map.getHeight()) / 2 - map.getPlayerY() + map.getHeight() / 2;
		int playerY = getParent().getHeight() / 2 - playerIcon.getIconHeight() / 2;
		if (mapY >= 0) {
			mapY = 0;
			playerY = map.getPlayerY() - playerIcon.getIconHeight() / 2;
		} else if (mapY + map.getHeight() < getParent().getHeight()) {
			mapY = getParent().getHeight() - map.getHeight();
			playerY = map.getPlayerY() + mapY - playerIcon.getIconHeight() / 2;
		}

		// draw map
		g.drawImage(map.getImage().getImage(), mapX, mapY, map.getWidth(), map.getHeight(), null);

		// draw map objects
		for (final AbstractMapObject mapObject : map.getObjects()) {
			mapObject.paint(g, mapX, mapY);
		}

		// draw player
		g.drawImage(playerIcon.getImage(), playerX, playerY, playerIcon.getIconWidth(), playerIcon.getIconHeight(), null);
		if (player.isShowRect()) {
			g.drawRect(playerX - player.getWidth() / 2 + playerIcon.getIconWidth() / 2, playerY - player.getHeight() / 2
					+ playerIcon.getIconHeight() / 2, player.getWidth(), player.getHeight());
		}
	}

	/**
	 * @param showSpacebar
	 *            the showSpacebar to set
	 */
	public void setShowSpacebar(final boolean showSpacebar) {
		this.showSpacebar = showSpacebar;
		repaint();
	}

	/**
	 * @return the showSpacebar
	 */
	public boolean isShowSpacebar() {
		return showSpacebar;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(final Map map) {
		this.map = map;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

}
