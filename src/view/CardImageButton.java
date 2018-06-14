package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;

public class CardImageButton extends JButton {
	/** */
	private static final long serialVersionUID = -6265623531654981951L;

	private String description;

	private String title;

	private static final String FONT = "Arial";

	private static final Color TEXT_COLOR = Color.BLACK;

	private static final Color NUMBER_COLOR = Color.BLUE;

	public CardImageButton() {
		this("", "", "");
	}

	public CardImageButton(final String file, final String title, final String text) {
		super(new ImageIcon(file));
		this.description = text;
		this.title = title;
		setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		final int startX = 20;
		final int startY = 143;
		int x = startX;
		int y = startY;
		g.setColor(TEXT_COLOR);

		// Title
		g.setFont(new Font(FONT, Font.BOLD, 15));
		g.drawString(title, g.getClipBounds().width / 2 - g.getFontMetrics().stringWidth(title) / 2, y += g.getFontMetrics().getHeight());

		y += g.getFontMetrics().getHeight() + 7;

		// Description
		g.setFont(new Font(FONT, Font.PLAIN, 13));
		final String[] words = description.split(" ");

		// Für jedes Wort
		for (int i = 0; i < words.length; i++) {
			// Verschiebe x um die Breite des letzten Wortes nach recht
			if (i != 0) {
				x += g.getFontMetrics().stringWidth(words[i - 1]) + 3;
			}
			// Ist x + die Breite des nächsten Wortes außerhalb des Textbereichs? -> Zeilenumbruch
			if (x + g.getFontMetrics().stringWidth(words[i]) > g.getClipBounds().width - 20) {
				y += g.getFontMetrics().getHeight();
				x = startX;
			}
			try {
				// Zahlen in bunt schreiben
				Integer.parseInt(words[i]);
				g.setColor(NUMBER_COLOR);
				g.drawString(words[i], x, y);
				g.setColor(TEXT_COLOR);
			} catch (final Exception e) {
				// Wörter nicht bunt
				g.drawString(words[i], x, y);
			}
		}
	}

	/**
	 * @return the text
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setDescription(final String text) {
		this.description = text;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

}
