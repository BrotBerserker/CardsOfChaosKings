package map.editor.view;

import java.awt.Graphics;

import map.model.Map;
import map.model.Player;
import map.view.MapPanel;

public class MapEditorPanel extends MapPanel {

	private static final long serialVersionUID = 888100631526438384L;

	public MapEditorPanel(final Map map, final Player player) {
		super(map, player);
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		//		g.draw3DRect(3, 3, 104, 104, true);
		//		g.draw3DRect(4, 4, 102, 102, true);
		//		g.draw3DRect(5, 5, 100, 100, true);
		//		g.drawImage(new ImageIcon("images/gui/rectangle.png").getImage(), 6, 6, 99, 99, null);
	}

}
