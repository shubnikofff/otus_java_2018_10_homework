package ru.otus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class FrontendMain {
	public static void main(String[] args) {
		try {
			final Socket socket = new Socket("localhost", 5050);
			final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("Hello from " + args[0]);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
