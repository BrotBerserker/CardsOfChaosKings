package map.editor.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import map.model.Map;

public class MapFileHandler {

	private static File currentFile;

	public static void saveMap(final Map map) {
		if (currentFile != null) {
			try {
				new ObjectOutputStream(new FileOutputStream(currentFile)).writeObject(map);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			saveMapAs(map);
		}
	}

	public static void saveMapAs(final Map map) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Map files (*.map)", "map"));
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getName().endsWith(".map")) {
				file = new File(file + ".map");
			}
			currentFile = file;
			try {
				new ObjectOutputStream(new FileOutputStream(file)).writeObject(map);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map loadMap() {
		final JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("Map files (*.map)", "map"));
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				currentFile = fc.getSelectedFile();
				final Map map = (Map) new ObjectInputStream(new FileInputStream(fc.getSelectedFile())).readObject();
				return map;
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
