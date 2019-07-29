package ru.otus.application.service;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.domain.service.MessageProcessor;
import ru.otus.domain.service.MessageSystem;
import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageSystemImplementation implements MessageSystem {
	private final List<MessageWorker> messageWorkers = new CopyOnWriteArrayList<>();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final MessageProcessor messageProcessor;

	public MessageSystemImplementation(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void run() {
		while (true) {
			for (MessageWorker worker : messageWorkers) {
				try {
					Message message = worker.takeMessage();
					while (message != null) {
						messageProcessor.process(message, worker);
						message = worker.takeMessage();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addWorker(MessageWorker messageWorker) {
		messageWorkers.add(messageWorker);
	}


//	private static final int PROCESS_DELAY = 100;
//
//	private ExecutorService executor = Executors.newSingleThreadExecutor();
//	private Logger logger = Logger.getLogger(MessageSystemImplementation.class.getName());
//
//	public void start(int port) throws IOException {
//		executor.submit(this::processMessage);
//
//		try (ServerSocket serverSocket = new ServerSocket(port)) {
//			logger.info("Socket server started on port " + serverSocket.getLocalPort());
//			while (!executor.isShutdown()) {
//				Socket socket = serverSocket.accept();
//				logger.info("Accepted socket on port: " + socket.getPort());
//				try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//					String msg = reader.readLine();
//					System.out.println(msg);
//				}
//			}
//		}
//	}
//
//	private void processMessage() {
//		while (true) {
//			try {
//				Thread.sleep(PROCESS_DELAY);
//			} catch (InterruptedException e) {
//				logger.log(Level.SEVERE, e.getMessage());
//			}
//		}
//	}
}
