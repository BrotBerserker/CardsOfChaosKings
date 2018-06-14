package chat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Enthält Methoden, um zufällige Chatnachrichten zu bekommen (z.B. Beleidigungn). Die Nachrichten werden aus einer
 * Textdatei gelesen.
 *
 * @author Jana Eschwaltrup
 *
 */
public class ChatAusruf {
	List<String> beleidigungen = new ArrayList<String>();
	List<String> antworten = new ArrayList<String>();
	List<String> niederlage = new ArrayList<String>();
	List<String> sieg = new ArrayList<String>();
	List<String> sir = new ArrayList<String>();
	Random random = new Random();
	String temp;

	private static ChatAusruf instance;

	/**
	 * Main zum testen.
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println(ChatAusruf.getInstance().getSieg());
	}

	/**
	 * Gibt eine zufällige Beleidigung zurück.
	 *
	 * @return
	 */
	public String getBeleidigung() {
		return beleidigungen.get(random.nextInt(beleidigungen.size()));
	}

	/**
	 * Gibt einen zufälligen Konter zurück.
	 *
	 * @return
	 */
	public String getAntwort() {
		return antworten.get(random.nextInt(antworten.size()));
	}

	/**
	 * Gibt einen zufälligen Kommentar zu einer Niederlage zurück.
	 *
	 * @return
	 */
	public String getNiederlage() {
		return niederlage.get(random.nextInt(niederlage.size()));
	}

	/**
	 * Gibt einen zufälligen Kommentar zu einem Sieg zurück.
	 *
	 * @return
	 */
	public String getSieg() {
		return sieg.get(random.nextInt(sieg.size()));
	}

	/**
	 * Gibt einen zufälligen Like-A-Sir-Spruch zurück.
	 *
	 * @return
	 */
	public String getSir() {
		return sir.get(random.nextInt(sir.size()));
	}

	/**
	 * Constructor. Liest die Nachrichten aus der Textdatei.
	 *
	 * @throws IOException
	 *             Falls die Textdatei nicht gelesen werden kann
	 */
	private ChatAusruf() throws IOException {
		final FileReader fr = new FileReader("txts/antworten.txt");
		final BufferedReader br = new BufferedReader(fr);
		while (true) {
			temp = br.readLine();
			if (temp == null) {
				break;
			}
			antworten.add(temp);
		}
		br.close();
		final FileReader fr2 = new FileReader("txts/beleidigungen.txt");
		final BufferedReader br2 = new BufferedReader(fr2);
		while (true) {
			temp = br2.readLine();
			if (temp == null) {
				break;
			}
			beleidigungen.add(temp);
		}
		br2.close();
		final FileReader fr3 = new FileReader("txts/niederlage.txt");
		final BufferedReader br3 = new BufferedReader(fr3);

		while (true) {
			temp = br3.readLine();
			if (temp == null) {
				break;
			}
			niederlage.add(temp);
		}
		br3.close();
		final FileReader fr4 = new FileReader("txts/sieg.txt");
		final BufferedReader br4 = new BufferedReader(fr4);

		while (true) {
			temp = br4.readLine();
			if (temp == null) {
				break;
			}
			sieg.add(temp);
		}
		br4.close();
		final FileReader fr5 = new FileReader("txts/sir.txt");
		final BufferedReader br5 = new BufferedReader(fr5);

		while (true) {
			temp = br5.readLine();
			if (temp == null) {
				break;
			}
			sir.add(temp);
		}
		br5.close();

	}

	/**
	 * Gibt die Singleton-Instanz zurück.
	 * 
	 * @return
	 */
	public static ChatAusruf getInstance() {
		if (instance == null) {
			try {
				instance = new ChatAusruf();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
