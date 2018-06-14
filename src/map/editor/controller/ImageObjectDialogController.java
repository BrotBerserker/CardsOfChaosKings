package map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import map.editor.model.ImageFileHandler;
import map.editor.view.ImageObjectDialog;
import map.objects.ImageObject;

public class ImageObjectDialogController {

	private ImageObjectDialog view;
	private List<File> availableImageFiles;

	private ImageObject selectedImage;

	public ImageObjectDialogController(final JFrame owner) {
		view = new ImageObjectDialog(owner);
		fillComboBox();
		addButtonListener();
	}

	private void addButtonListener() {
		view.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final File file = availableImageFiles.get(view.getComboBox().getSelectedIndex());
				selectedImage = new ImageObject(50, 50, true, "/effektIcons/" + file.getName());
				view.getDialog().dispose();
			}
		});
	}

	private void fillComboBox() {
		availableImageFiles = ImageFileHandler.getAvailableImageFiles();
		for (final File file : availableImageFiles) {
			view.getComboBox().addItem(file.getName());
		}
	}

	public ImageObject show() {
		view.getDialog().setVisible(true);
		return selectedImage;
	}

}
