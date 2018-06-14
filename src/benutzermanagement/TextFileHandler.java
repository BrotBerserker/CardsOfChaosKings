package benutzermanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Pfade;

/**
 * Verwaltet Benutzer in einer Textdatei.
 *
 * @author Roman Schmidt
 *
 */
public class TextFileHandler {

	private BufferedReader reader;
	private FileWriter writer;

	/**
	 * Konstruktor.
	 *
	 * @throws IOException
	 *             Falls die Datei nicht existiert.
	 */
	public TextFileHandler() throws IOException { // TODO Datei im Konstruktor übergeben oder so
		reader = new BufferedReader(new FileReader("benutzer.txt"));
	}

	/**
	 * Liest alle Benutzer aus der Textdatei.
	 *
	 * @return Liste der Benutzer
	 * @throws IOException
	 *             Falls das Lesen fehlschlägt.
	 */
	public List<Benutzer> readBenutzers() throws IOException {
		final List<Benutzer> list = new ArrayList<Benutzer>();
		String line;
		String[] split;
		Map<String, Integer> pfadExp;// = new HashMap<String, Integer>();
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			pfadExp = new HashMap<String, Integer>();
			split = line.split(";");
			int position = 2;
			for (final Pfade pfad : Pfade.values()) {
				pfadExp.put(pfad.getText(), Integer.parseInt(split[position++]));
			}
			if (split.length == Pfade.values().length + 2) {
				list.add(new Benutzer(split[0], split[1], pfadExp));
			}
		}
		return list;
	}

	/**
	 * Schreibt einen einzelnen Benutzer in die Textdate.
	 *
	 * @param benutzer
	 *            Der Benutzer
	 * @throws IOException
	 *             Falls das Schreiben fehlschlägt.
	 */
	private void writeBenutzer(final Benutzer benutzer) throws IOException {
		final Map<String, Integer> pfadExp = benutzer.getPfadExp();
		final StringBuilder builder = new StringBuilder();
		builder.append(benutzer.getNickname() + ";" + benutzer.getPasswort());
		for (final Pfade pfad : Pfade.values()) {
			builder.append(";" + pfadExp.get(pfad.getText()));
		}
		writer.write(builder.toString() + "\n");
		writer.flush();
	}

	/**
	 * Schreibt die Benutzerliste in die Textdatei.
	 *
	 * @param benutzerliste
	 *            Benutzerliste
	 * @throws IOException
	 *             Falls das Schreiben fehlschlägt.
	 */
	public void writeBenutzers(final List<Benutzer> benutzerliste) throws IOException {
		writer = new FileWriter("benutzer.txt", false);
		for (final Benutzer benutzer : benutzerliste) {
			writeBenutzer(benutzer);
		}
	}
}
