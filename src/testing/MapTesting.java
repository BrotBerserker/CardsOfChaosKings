package testing;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

import map.controller.MapWindowController;
import map.model.Map;
import map.model.Player;
import map.objects.EllipseMapObject;
import map.objects.LineMapObject;
import map.objects.NpcObject;
import map.objects.RectangleMapObject;

/**
 * @author RomSch
 *
 */
public class MapTesting {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Map map = new Map(2000, 1500, new ImageIcon("maps/map1.jpg"));
		map.addMapObject(new NpcObject(300, 150, "yula.png", "Yula", "Magier"));
		map.addMapObject(new NpcObject(220, 450, "pete.png", "Pete", "Jäger"));

		// final boolean show = true;
		// map.addMapObject(new MapObject(new Rectangle(390, 530, 50, 50), true, show));
		// map.addMapObject(new MapObject(new Rectangle(415, 580, 150, 30), true, show));
		// map.addMapObject(new MapObject(new Rectangle(585, 600, 150, 30), true, show));
		map.addMapObject(new RectangleMapObject(new Rectangle(800, 800, 100, 100), true, true));
		map.addMapObject(new LineMapObject(new Line2D.Double(900, 800, 1000, 900), true, true));
		map.addMapObject(new EllipseMapObject(new Ellipse2D.Double(850, 700, 100, 100), true, true));

		final Player player = new Player("character", 27, 31, false);
		new MapWindowController(map, player, false);
	}
}
