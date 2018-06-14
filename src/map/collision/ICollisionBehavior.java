package map.collision;

import map.model.Map;
import map.model.Player;
import map.view.MapWindow;

public interface ICollisionBehavior {

	void collisionEnter(final Player player, MapWindow view, Map map);

	void collisionLeave(final Player player, MapWindow view, Map map);

}
