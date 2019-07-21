package ru.otus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DbServerMain {
	public static void main(String[] args) {
		try {
			final Socket socket = new Socket("localhost", 5050, null, 5051);
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("Hello from DB server");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
