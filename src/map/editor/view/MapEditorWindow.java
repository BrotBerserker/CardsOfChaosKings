package map.editor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import map.model.Map;
import map.model.Player;
import map.view.MapWindow;

public class MapEditorWindow extends MapWindow {

	private GridBagConstraints cons;
	private JMenuBar menuBar;
	private JMenuItem rectangleItem;
	private JMenuItem ellipseItem;
	private JMenuItem lineItem;
	private JMenuItem newMapItem;
	private JMenuItem loadMapItem;
	private JMenuItem saveMapItem;
	private JMenuItem saveMapAsItem;
	private JMenuItem imageObjectItem;
	private JMenuItem npcItem;
	private JMenuItem removeAllCollidersIteam;

	public MapEditorWindow(final Map map, final Player player, final boolean fullscreen) {
		this.player = player;
		initWindow();
		initMenuBar();
		initMapPanel(map);
		if (fullscreen) {
			window.setUndecorated(true);
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
		window.setVisible(true);

		// key binding for spacebar actions
		window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "spacebarAction");
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		newMapItem = new JMenuItem("New Map...");
		loadMapItem = new JMenuItem("Open...");
		saveMapItem = new JMenuItem("Save");
		saveMapAsItem = new JMenuItem("Save as...");
		fileMenu.add(newMapItem);
		fileMenu.addSeparator();
		fileMenu.add(loadMapItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMapItem);
		fileMenu.add(saveMapAsItem);
		menuBar.add(fileMenu);

		final JMenu editMenu = new JMenu("Edit");
		removeAllCollidersIteam = new JMenuItem("Remove all colliders");
		editMenu.add(removeAllCollidersIteam);
		menuBar.add(editMenu);

		final JMenu addObjectMenu = new JMenu("Add Object");
		final JMenu colliderMenu = new JMenu("Collider");
		rectangleItem = new JMenuItem("Rectangle");
		ellipseItem = new JMenuItem("Ellipse");
		lineItem = new JMenuItem("Line");
		imageObjectItem = new JMenuItem("Image Object...");
		npcItem = new JMenuItem("NPC...");
		colliderMenu.add(rectangleItem);
		colliderMenu.add(ellipseItem);
		colliderMenu.add(lineItem);
		addObjectMenu.add(colliderMenu);
		addObjectMenu.addSeparator();
		addObjectMenu.add(imageObjectItem);
		addObjectMenu.add(npcItem);
		menuBar.add(addObjectMenu);

		final JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem("Manual"));
		helpMenu.add(new JMenuItem("About..."));
		menuBar.add(helpMenu);

		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		window.add(menuBar, cons);
	}

	@Override
	protected void initMapPanel(final Map map) {
		mapPanel = new MapEditorPanel(map, player);
		cons.gridy = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.weighty = 1;
		window.add(mapPanel, cons);
	}

	@Override
	protected void initWindow() {
		super.initWindow();
		window.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
	}

	/**
	 * @return the rectangleItem
	 */
	public JMenuItem getRectangleItem() {
		return rectangleItem;
	}

	/**
	 * @return the ellipseItem
	 */
	public JMenuItem getEllipseItem() {
		return ellipseItem;
	}

	/**
	 * @return the lineItem
	 */
	public JMenuItem getLineItem() {
		return lineItem;
	}

	/**
	 * @return the newMapItem
	 */
	public JMenuItem getNewMapItem() {
		return newMapItem;
	}

	/**
	 * @return the loadMapItem
	 */
	public JMenuItem getLoadMapItem() {
		return loadMapItem;
	}

	/**
	 * @return the saveMapItem
	 */
	public JMenuItem getSaveMapItem() {
		return saveMapItem;
	}

	/**
	 * @return the saveMapAsItem
	 */
	public JMenuItem getSaveMapAsItem() {
		return saveMapAsItem;
	}

	/**
	 * @return the imageObjectItem
	 */
	public JMenuItem getImageObjectItem() {
		return imageObjectItem;
	}

	/**
	 * @return the npcItem
	 */
	public JMenuItem getNpcItem() {
		return npcItem;
	}

	/**
	 * @return the removeAllCollidersIteam
	 */
	public JMenuItem getRemoveAllCollidersItem() {
		return removeAllCollidersIteam;
	}

}
