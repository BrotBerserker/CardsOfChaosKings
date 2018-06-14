package map.editor.controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import map.controller.MapWindowController;
import map.editor.model.MapFileHandler;
import map.editor.view.MapEditorWindow;
import map.model.AbstractMapObject;
import map.model.Map;
import map.model.Player;
import map.objects.EllipseMapObject;
import map.objects.ImageObject;
import map.objects.LineMapObject;
import map.objects.RectangleMapObject;

public class MapEditorWindowController extends MapWindowController {

	// TODO testmain weg
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new MapEditorWindowController(false, new Map(2000, 1500, new ImageIcon("maps/map1.jpg")));
	}

	private AbstractMapObject currentMapObject;
	private JFrame window;

	public MapEditorWindowController(final boolean fullscreen, final Map map) {
		this.map = map;
		this.player = new Player("character", 27, 31, true);
		view = new MapEditorWindow(map, player, fullscreen);
		window = view.getWindow();
		startMovementThread();
		addMovementListener();
		addMouseListeners();
		addFileListener();
		addNewColliderListeners();
		addMenuListeners();
		currentMapObject = new RectangleMapObject(new Rectangle(), true, true);
	}

	private void addMenuListeners() {
		final MapEditorWindow mew = (MapEditorWindow) view;
		mew.getRemoveAllCollidersItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK));
		mew.getRemoveAllCollidersItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				map.removeAllMapObjects();
			}
		});
	}

	private void addNewColliderListeners() {
		final MapEditorWindow mew = (MapEditorWindow) view;
		mew.getRectangleItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_MASK));
		mew.getEllipseItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_MASK));
		mew.getLineItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_MASK));
		mew.getImageObjectItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.ALT_MASK));
		mew.getRectangleItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				currentMapObject = new RectangleMapObject(new Rectangle(), true, true);
			}
		});
		mew.getEllipseItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				currentMapObject = new EllipseMapObject(new Ellipse2D.Double(), true, true);
			}
		});
		mew.getLineItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				currentMapObject = new LineMapObject(new Line2D.Double(), true, true);
			}
		});
		mew.getImageObjectItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final ImageObject image = new ImageObjectDialogController(window).show();
				if (image != null) {
					map.addMapObject(image);
					currentMapObject = image;
				}
			}
		});
	}

	private void addFileListener() {
		final MapEditorWindow mew = (MapEditorWindow) view;
		mew.getNewMapItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		mew.getLoadMapItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		mew.getSaveMapItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		mew.getSaveMapAsItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
		mew.getLoadMapItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final Map newMap = MapFileHandler.loadMap();
				if (newMap != null) {
					new MapEditorWindowController((window.getExtendedState() == (window.getExtendedState() | JFrame.MAXIMIZED_BOTH)), newMap);
					view.getWindow().dispose();
				}
			}
		});
		mew.getSaveMapItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MapFileHandler.saveMap(map);
			}
		});
		mew.getSaveMapAsItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MapFileHandler.saveMapAs(map);
			}
		});
	}

	private void addMouseListeners() {
		window.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (currentMapObject instanceof ImageObject) {
						currentMapObject = currentMapObject.getNewInstance();
					} else {
						final int x = e.getX() - getMapX();
						final int y = e.getY() - getMapY();
						currentMapObject.resize(new Rectangle(x, y, 0, 0));
						map.addMapObject(currentMapObject);
					}
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					map.removeLastMapObject();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					currentMapObject = currentMapObject.getNewInstance();
				}
			}
		});

		window.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(final MouseEvent e) {
				if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
					final int x = e.getX() - getMapX() - currentMapObject.getOriginX();
					final int y = e.getY() - getMapY() - currentMapObject.getOriginY();
					map.changeMapObjectSize(currentMapObject, x, y);
				}
			}

			@Override
			public void mouseMoved(final MouseEvent e) {
				if (currentMapObject instanceof ImageObject) {
					final int x = e.getX() - getMapX() - currentMapObject.getWidth() / 2;
					final int y = e.getY() - getMapY() - currentMapObject.getHeight();
					map.changeMapObjectPosition(currentMapObject, x, y);
				}
			}
		});
	}

	private int getMapX() {
		int mapX = (window.getWidth() - map.getWidth()) / 2 - map.getPlayerX() + map.getWidth() / 2;
		if (mapX >= 0) {
			mapX = 0;
		} else if (mapX + map.getWidth() < window.getWidth()) {
			mapX = window.getWidth() - map.getWidth();
		}
		return mapX;
	}

	private int getMapY() {
		int mapY = (window.getHeight() - map.getHeight()) / 2 - map.getPlayerY() + map.getHeight() / 2;
		if (mapY >= 0) {
			mapY = 0;
		} else if (mapY + map.getHeight() < window.getHeight()) {
			mapY = window.getHeight() - map.getHeight();
		}
		return mapY + 24; // + 24 due to the menu bar
	}

}
