package ru.otus.messageSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MessageSystem {
	private final static Logger LOGGER = Logger.getLogger(MessageSystem.class.getName());

	private final List<Thread> workers = new ArrayList<>();
	private final Map<Address, LinkedBlockingQueue<Message>> messagesMap = new ConcurrentHashMap<>();

	public void addAddressee(Addressee addressee) {
		String name = "MS-worker-" + addressee.getAddress().getId();
		final LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
		messagesMap.put(addressee.getAddress(), queue);

		final Thread thread = new Thread(() -> {
			while (true) {
				try {
					final Message message = queue.take();
					message.exec(addressee);
				} catch (InterruptedException e) {
					LOGGER.log(Level.INFO, "Thread interrupted. Finishing: " + name);
					return;
				}
			}
		});

		thread.setName(name);
		thread.start();
		workers.add(thread);
	}

	public void sendMessage(Message message) {
		LOGGER.log(Level.INFO, "Send message from " + message.getFrom() + " to " + message.getTo());
		messagesMap.get(message.getTo()).add(message);
	}

	public void dispose() {
		workers.forEach(Thread::interrupt);
	}
}
