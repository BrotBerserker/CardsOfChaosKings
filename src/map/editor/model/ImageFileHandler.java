package map.editor.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFileHandler {

	private static final String IMAGE_FOLDER = "images/effektIcons";

	public static List<File> getAvailableImageFiles() {
		final List<File> files = new ArrayList<File>();
		for (final File f : new File(IMAGE_FOLDER).listFiles()) {
			if (!f.getName().endsWith("png")) {
				continue;
			}
			files.add(f);
		}
		return files;

	}

}
