package ru.otus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class FrontendMain {
	public static void main(String[] args) {
		try {
			final Socket socket = new Socket("localhost", 5050);
			final PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println("Hello from Frontend");
			printWriter.println();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
