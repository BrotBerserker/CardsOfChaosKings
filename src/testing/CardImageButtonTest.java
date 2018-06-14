package testing;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

import view.CardImageButton;

/**
 * @author RomSch
 *
 */
public class CardImageButtonTest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(500, 400, 500, 400);
		window.setLayout(new GridBagLayout());

		// final CardImageButton butt = new CardImageButton("", "", "");
		// window.add(butt);
		window.add(new CardImageButton("images/feuerelementar.jpg", "Feuerelementar", "Verursacht 4 Runden lang 3 Schaden pro Runde"));
		// window.add(new CardImageButton("images/magier/ssjkarte.jpg", "Wutausbruch",
		// "Erhöht die SSJ-Punkte sofort um 15"));
		window.add(new CardImageButton("images/blendung.jpg", "Blendung", "Lässt den Gegner 5 neue Karten ziehen und macht ihn 3 Runden lang blind"));

		window.setVisible(true);
		// butt.setTitle("asgsa");
		// butt.setDescription("was dal os");
		// // ButtonImages.addImageToButton(butt, "feuer.jpg");
		// final SchadenKarte karte = new SchadenKarte("asd", 5, 5, "sturmelementar.jpg");
		// ButtonImages.addKarteToButton(butt, karte);
	}

	// static class SuperButton extends JButton {
	//
	// /** */
	// private static final long serialVersionUID = -6265623531654981951L;
	//
	// private String text;
	//
	// private String title;
	//
	// public SuperButton(final String file, final String title, final String text) {
	// super(new ImageIcon(file));
	// this.text = text;
	// this.title = title;
	// setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
	// }
	//
	// @Override
	// public void paint(final Graphics g) {
	// super.paint(g);
	// final int x = 15;
	// int y = 135;
	// final String font = "Arial";
	//
	// g.setColor(Color.WHITE);
	// g.setFont(new Font(font, Font.BOLD, 14));
	// g.drawString(title, g.getClipBounds().width / 2 - g.getFontMetrics().stringWidth(title) / 2, y +=
	// g.getFontMetrics().getHeight());
	//
	// y += 7;
	// g.setFont(new Font(font, Font.PLAIN, 13));
	// for (final String line : text.split("\n")) {
	// g.drawString(line, x, y += g.getFontMetrics().getHeight());
	// }
	// }
	// }
}
