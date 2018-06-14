package map.editor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class ImageObjectDialog {

	private JDialog dialog;
	private GridBagConstraints cons;
	private JCheckBox checkBox;
	private JSpinner xSpinner;
	private JSpinner ySpinner;
	private JComboBox<String> comboBox;
	private JButton addButton;

	public ImageObjectDialog(final JFrame owner) {
		initDialog(owner);
		initFileComponents();
		initSizeComponents();
		initImagePreview();
		initCheckBoxListener();
		dialog.pack();
		resize(owner);
	}

	private void initCheckBoxListener() {
		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				xSpinner.setEnabled(checkBox.isSelected());
				ySpinner.setEnabled(checkBox.isSelected());
			}
		});
	}

	private void initImagePreview() {
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridheight = 2;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(5, 5, 5, 5);
		addButton = new JButton("Add");
		dialog.add(addButton, cons);
	}

	private void initSizeComponents() {
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weightx = 1;
		cons.fill = GridBagConstraints.BOTH;
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		dialog.add(panel, cons);

		cons.gridx = 0;
		cons.gridy = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(0, 0, 0, 10);
		checkBox = new JCheckBox("Custom Size");
		panel.add(checkBox, cons);

		cons.gridx = 1;
		cons.insets = new Insets(0, 0, 0, 5);
		panel.add(new JLabel("x: "), cons);

		cons.gridx = 2;
		cons.ipadx = 10;
		cons.insets = new Insets(0, 0, 0, 10);
		xSpinner = new JSpinner();
		xSpinner.setEnabled(false);
		panel.add(xSpinner, cons);

		cons.gridx = 3;
		cons.ipadx = 0;
		cons.insets = new Insets(0, 0, 0, 5);
		panel.add(new JLabel("y: "), cons);

		cons.gridx = 4;
		cons.ipadx = 10;
		cons.insets = new Insets(0, 0, 0, 10);
		ySpinner = new JSpinner();
		ySpinner.setEnabled(false);
		panel.add(ySpinner, cons);
	}

	private void initFileComponents() {
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.fill = GridBagConstraints.BOTH;
		comboBox = new JComboBox<String>();
		dialog.add(comboBox, cons);
	}

	private void initDialog(final JFrame owner) {
		dialog = new JDialog(owner, true);
		dialog.setResizable(false);
		dialog.setTitle("New Image Object");
		dialog.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.insets = new Insets(5, 5, 5, 5);
	}

	private void resize(final JFrame owner) {
		final Rectangle ownerBounds = owner.getBounds();
		final Rectangle bounds = dialog.getBounds();
		dialog.setBounds(ownerBounds.x + ownerBounds.width / 2 - bounds.width / 2, ownerBounds.y + ownerBounds.height / 2 - bounds.height / 2, bounds.width, bounds.height);
	}

	/**
	 * @return the comboBox
	 */
	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	/**
	 * @return the dialog
	 */
	public JDialog getDialog() {
		return dialog;
	}

	/**
	 * @return the addButton
	 */
	public JButton getAddButton() {
		return addButton;
	}

}
