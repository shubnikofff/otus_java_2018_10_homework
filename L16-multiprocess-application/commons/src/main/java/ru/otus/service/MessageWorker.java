package ru.otus.service;

import ru.otus.message.Message;

public interface MessageWorker {
	void start();
	void init();
	void stop();
	void putMessage(Message message) throws InterruptedException;
	void processMessage(Message message);
	Message getMessage() throws InterruptedException;


	//SocketMessageWorker
	//
	//ClientSocketMessageWorker
	//ServerSocketMessageWorker
	//
	//Router
	//DBService
	//FrontendService



//	void sendMessage(Message message);
//	Message pollMessage();
}
