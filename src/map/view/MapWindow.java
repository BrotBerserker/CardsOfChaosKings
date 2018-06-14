package map.view;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import map.model.Map;
import map.model.Player;

public class MapWindow {
	protected JFrame window;
	protected MapPanel mapPanel;
	protected Player player;

	protected MapWindow() {
		//just for inheritance purposes
	}

	public MapWindow(final Map map, final Player player, final boolean fullscreen) {
		this.player = player;
		initWindow();
		initMapPanel(map);
		if (fullscreen) {
			window.setUndecorated(true);
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
		window.setVisible(true);

		// key binding for spacebar actions 
		window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "spacebarAction");
	}

	protected void initMapPanel(final Map map) {
		mapPanel = new MapPanel(map, player);
		window.add(mapPanel);
	}

	protected void initWindow() {
		window = new JFrame("Cards of Chaos Kings™ World Map");
		window.setBounds(100, 100, 1000, 800);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @return the window
	 */
	public JFrame getWindow() {
		return window;
	}

	public void showSpacebarAction(final String actionName, final Action action) {
		window.getRootPane().getActionMap().put("spacebarAction", action);
		mapPanel.setShowSpacebar(true);
	}

	public void hideSpacebarAction() {
		window.getRootPane().getActionMap().remove("spacebarAction");
		mapPanel.setShowSpacebar(false);
	}

	/**
	 * @return the mapPanel
	 */
	public MapPanel getMapPanel() {
		return mapPanel;
	}

}
