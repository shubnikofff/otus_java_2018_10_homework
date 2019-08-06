package ru.otus;

import ru.otus.service.SocketMessageWorker;
import ru.otus.message.Message;
import ru.otus.message.UserListRequest;

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
			while (count < 50) {
				socketMessageWorker.sendMessage(new UserListRequest(args[0]));
				Thread.sleep(1000);
				count++;
			}

			while (true) {
				Message message = socketMessageWorker.pollMessage();
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
