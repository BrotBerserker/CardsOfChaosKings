package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonImageThread extends Thread {

	private String animationFolder;

	private JButton button;

	private boolean ssj = false;

	private boolean running = true;

	private static final int DELAY = 200;

	/**
	 * Constructor.
	 *
	 * @param animationFolder
	 * @param button
	 */
	public ButtonImageThread(final String animationFolder, final JButton button) {
		this.animationFolder = animationFolder;
		this.button = button;
	}

	@Override
	public void run() {
		String status = "idle";
		while (running) {
			if (ssj) {
				status = "ssj";
			} else {
				status = "idle";
			}
			try {
				ButtonImages.addImageToButton(button, new ImageIcon("images/" + animationFolder + "/" + status + "1.jpg"));
				sleep(DELAY);
				ButtonImages.addImageToButton(button, new ImageIcon("images/" + animationFolder + "/" + status + "2.jpg"));
				sleep(DELAY);
				ButtonImages.addImageToButton(button, new ImageIcon("images/" + animationFolder + "/" + status + "3.jpg"));
				sleep(DELAY);
				ButtonImages.addImageToButton(button, new ImageIcon("images/" + animationFolder + "/" + status + "2.jpg"));
				sleep(DELAY);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the animationFolder
	 */
	public String getAnimationFolder() {
		return animationFolder;
	}

	/**
	 * @param animationFolder
	 *            the animationFolder to set
	 */
	public void setAnimationFolder(final String animationFolder) {
		this.animationFolder = animationFolder;
	}

	/**
	 * @return the ssj
	 */
	public boolean isSsj() {
		return ssj;
	}

	/**
	 * @param ssj
	 *            the ssj to set
	 */
	public void setSsj(final boolean ssj) {
		this.ssj = ssj;
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(final boolean running) {
		this.running = running;
	}

}
