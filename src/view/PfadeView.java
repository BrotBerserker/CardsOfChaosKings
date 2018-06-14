package view;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Pfad;
import model.SpielerListe;
import controller.PfadPanelController;

public class PfadeView {
	private JDialog dialog;
	private GridBagConstraints cons;
	private JTabbedPane tabbedPane;

	public PfadeView(final JFrame owner) {
		initDialog(owner);
		initTabbedPane(owner);
		dialog.setVisible(true);
	}

	private void initTabbedPane(final JFrame owner) {
		tabbedPane = new JTabbedPane();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.fill = GridBagConstraints.BOTH;
		for (final Pfad pfad : SpielerListe.PFADE) {
			tabbedPane.addTab(pfad.getName(), new PfadPanelController(pfad, owner).getPanel());
		}
		dialog.add(tabbedPane, cons);
	}

	private void initDialog(final JFrame owner) {
		dialog = new JDialog() {
			/**
			 *
			 */
			private static final long serialVersionUID = -1594972829304423249L;

			@Override
			public void paint(final Graphics g) {
				super.paint(g);
				pack();
				dialog.setBounds(owner.getBounds().width / 2 - dialog.getBounds().width / 2 + owner.getBounds().x, owner.getBounds().height / 2 - dialog.getBounds().height / 2 + owner.getBounds().y,
						dialog.getBounds().width, dialog.getBounds().height);
			}
		};
		dialog.setModal(true);
		dialog.setTitle("Fortschritt");
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
	}
}
