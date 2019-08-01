package ru.otus;

import ru.otus.application.service.SocketMessageWorker;
import ru.otus.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontendMain {
	public static void main(String[] args) throws InterruptedException {
		final Logger logger = Logger.getLogger(args[0]);
		logger.info("App " + args[0] + " is running on port: " + args[1]);

		try(final ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]))) {
			Socket socket = null;
			while (socket == null) {
				socket = serverSocket.accept();
				logger.log(Level.SEVERE, "Accepted connection on " + socket.getPort());
			}

			final SocketMessageWorker socketMessageWorker = new SocketMessageWorker(socket);
			socketMessageWorker.start();

			int count = 0;
			while (count < 10) {
				socketMessageWorker.sendMessage(new Message(args[0], "DB#1"));
				Thread.sleep(1000);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
