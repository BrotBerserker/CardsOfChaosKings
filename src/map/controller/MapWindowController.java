package map.controller;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import map.model.AbstractMapObject;
import map.model.Map;
import map.model.Player;
import map.view.MapWindow;

public class MapWindowController {
	protected MapWindow view;
	protected Map map;
	protected Player player;

	protected int nextPlayerX;
	protected int nextPlayerY;

	protected final Set<AbstractMapObject> touching = new HashSet<AbstractMapObject>();

	protected MapWindowController() {
		// just for inheritance purposes
	}

	public MapWindowController(final Map map, final Player player, final boolean fullscreen) {
		this.map = map;
		this.player = player;
		view = new MapWindow(map, player, fullscreen);
		startMovementThread();
		addMovementListener();
	}

	protected void startMovementThread() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						detectCollisionsAndCalculateNewPlayerPosition();
						map.setPlayerPos(nextPlayerX, nextPlayerY);
						sleep(10);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}

	protected boolean detectCollisionsAndCalculateNewPlayerPosition() {
		nextPlayerX = Math.min(Math.max(0, map.getPlayerX() + player.getMovementX() * player.getSpeed()), map.getWidth());
		nextPlayerY = Math.min(Math.max(0, map.getPlayerY() + player.getMovementY() * player.getSpeed()), map.getHeight());

		final int newPlayerTopLeftX = map.getPlayerX() - player.getWidth() / 2 + player.getMovementX() * player.getSpeed();
		final int oldPlayerTopLeftX = map.getPlayerX() - player.getWidth() / 2;
		final int newPlayerTopLeftY = map.getPlayerY() - player.getHeight() / 2 + player.getMovementY() * player.getSpeed();
		final int oldPlayerTopLeftY = map.getPlayerY() - player.getHeight() / 2;
		final Rectangle playerRectX = new Rectangle(newPlayerTopLeftX, oldPlayerTopLeftY, player.getWidth(), player.getHeight());
		final Rectangle playerRectY = new Rectangle(oldPlayerTopLeftX, newPlayerTopLeftY, player.getWidth(), player.getHeight());

		boolean xCollision = false;
		boolean yCollision = false;
		for (final AbstractMapObject mapObject : map.getObjects()) {
			xCollision = mapObject.collides(playerRectX);
			yCollision = mapObject.collides(playerRectY);
			if ((xCollision || yCollision) && !touching.contains(mapObject)) {
				mapObject.collisionEnter(player, view, map);
				touching.add(mapObject);
			} else if ((!xCollision && !yCollision) && touching.contains(mapObject)
					&& (player.getMovementX() != 0 || player.getMovementY() != 0)) {
				mapObject.collisionLeave(player, view, map);
				touching.remove(mapObject);
			}
			if (xCollision && mapObject.isBlocking()) {
				nextPlayerX = map.getPlayerX();
			}
			if (yCollision && mapObject.isBlocking()) {
				nextPlayerY = map.getPlayerY();
			}
		}
		return false;
	}

	protected void addMovementListener() {
		view.getWindow().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					player.setMovementX(-1);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					player.setMovementX(1);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					player.setMovementY(-1);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					player.setMovementY(1);
				}
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getMovementX() == -1) {
					player.setMovementX(0);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getMovementX() == 1) {
					player.setMovementX(0);
				} else if (e.getKeyCode() == KeyEvent.VK_UP && player.getMovementY() == -1) {
					player.setMovementY(0);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN && player.getMovementY() == 1) {
					player.setMovementY(0);
				}
			}
		});
	}

	/**
	 * @return the view
	 */
	public MapWindow getView() {
		return view;
	}

}
