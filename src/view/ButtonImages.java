/**
 *
 */
package view;

import java.awt.Insets;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import model.Karte;

/**
 * @author Sigi
 *
 */
public class ButtonImages {

	public static void addKarteToButton(final CardImageButton button, final Karte karte) {
		button.setTitle(karte.getName());
		button.setDescription(karte.toString());
		addImageToButton(button, new ImageIcon("images/" + karte.getImgPath()));
	}

	public static void addImageToButton(final JButton button, final String name) {
		addImageToButton(button, new ImageIcon("images/" + name));
		if (button instanceof CardImageButton) {
			((CardImageButton) button).setTitle("");
			((CardImageButton) button).setDescription("");
		}
	}

	public static void addImageToButton(final JButton button, final Icon img) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				button.setSize(1, 1);
				if (!new File(img.toString()).exists()) {
					ButtonImages.addImageToButton(button, "leer.jpg");
				} else {
					button.setIcon(img);
				}
				button.setContentAreaFilled(false);
				button.setIconTextGap(0);
				button.setMargin(new Insets(0, 0, 0, 0));
				button.updateUI();
			}
		});
	}

	// public static void startImageThread(final String folder, final JButton button, final int spieler) {
	// new Thread() {
	// @Override
	// public void run() {
	// final int delay = 200;
	// String status = "idle";
	// while (DuelWindowController.ingameAnimationenLaufen || MainWindowController.ingameAnimationenLaufen) {
	// if (DuelWindowController.ssj[spieler] || MainWindowController.ssj[spieler]) {
	// status = "ssj";
	// } else {
	// status = "idle";
	// }
	// try {
	// ButtonImages.addImageToButton(button, new ImageIcon("images/" + folder + "/" + status + "1.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButton(button, new ImageIcon("images/" + folder + "/" + status + "2.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButton(button, new ImageIcon("images/" + folder + "/" + status + "3.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButton(button, new ImageIcon("images/" + folder + "/" + status + "2.jpg"));
	// sleep(delay);
	// } catch (final InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// };
	// }.start();
	// }

	// public static void startImageThreadOhneInvokeLater(final String folder, final JButton button) {
	// new Thread() {
	// @Override
	// public void run() {
	// final int delay = 200;
	// while (DuelWindowController.auswahlAnimationenLaufen || MainWindowController.auswahlAnimationenLaufen) {
	// try {
	// ButtonImages.addImageToButtonOhneInvokeLater(button, new ImageIcon("images/" + folder + "/idle1.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButtonOhneInvokeLater(button, new ImageIcon("images/" + folder + "/idle2.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButtonOhneInvokeLater(button, new ImageIcon("images/" + folder + "/idle3.jpg"));
	// sleep(delay);
	// ButtonImages.addImageToButtonOhneInvokeLater(button, new ImageIcon("images/" + folder + "/idle2.jpg"));
	// sleep(delay);
	// } catch (final InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// };
	// }.start();
	// }

	// static void addImageToButtonOhneInvokeLater(final JButton button, final Icon img) {
	// button.setSize(1, 1);
	// button.setIcon(img);
	// button.setContentAreaFilled(false);
	// button.setIconTextGap(0);
	// button.setMargin(new Insets(0, 0, 0, 0));
	// button.updateUI();
	// }
}
