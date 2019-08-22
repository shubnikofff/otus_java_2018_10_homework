package ru.otus;

import ru.otus.message.Message;
import ru.otus.message.UserListRequest;
import ru.otus.service.MessageWorker;
import ru.otus.service.SocketWorker;

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

			final MessageWorker socketMessageWorker = new SocketWorker(socket);
			socketMessageWorker.start();

			int count = 0;
			while (count < 50) {
				socketMessageWorker.putMessage(new UserListRequest(args[0]));
				Thread.sleep(500);
				count++;
			}

			while (true) {
				Message message = socketMessageWorker.getMessage();
				if(message != null) {
					System.out.println("Received message from: " + message.getFrom());
				}
				Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
