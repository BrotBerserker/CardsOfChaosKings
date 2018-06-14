package testing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import networking.ChatClient;

/**
 * @author RomSch
 *
 */
public class ChatClientTester {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new ChatClientTester();
	}

	/**
	 * Constructor.
	 *
	 */
	public ChatClientTester() {
		System.out.println("Client" + new Random().nextInt(5000));
		final ChatClient client = new ChatClient("localhost", 2345);
		client.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				System.out.println(evt.getNewValue());
			}
		});
		try {
			client.sendText(new Scanner(System.in).nextLine());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
