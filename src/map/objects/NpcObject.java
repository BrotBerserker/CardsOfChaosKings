package map.objects;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import map.collision.ICollisionBehavior;
import map.controller.DuelWindowController;
import map.model.Map;
import map.model.Player;
import map.view.MapWindow;

public class NpcObject extends ImageObject {

	private static final long serialVersionUID = 881832138239188877L;

	@SuppressWarnings("unused")
	private final String npcName;

	private final String deck;

	public NpcObject(final int x, final int y, final String imageName, final String npcName, final String deck) {
		//		super(bounds, true, "npcs/" + imageName);
		super(x, y, true, "npcs/" + imageName);
		this.npcName = npcName;
		this.deck = deck;
		initializeCollisionBehavior();
	}

	private void initializeCollisionBehavior() {
		setCollisionBehavior(new ICollisionBehavior() {
			@Override
			public void collisionEnter(final Player player, final MapWindow view, final Map map) {
				view.showSpacebarAction("wasdalos", new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(final ActionEvent e) {
						new Thread() {
							@Override
							public void run() {
								System.out.println(Thread.currentThread());
								new DuelWindowController("Ritter", deck, view);
							}
						}.start();

					}
				});
			}

			@Override
			public void collisionLeave(final Player player, final MapWindow view, final Map map) {
				view.hideSpacebarAction();
			}
		});
	}

}
