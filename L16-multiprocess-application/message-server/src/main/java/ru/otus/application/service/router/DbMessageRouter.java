package ru.otus.application.service.router;

import ru.otus.domain.service.MessageWorker;
import ru.otus.message.Message;
import ru.otus.message.SaveUserRequest;
import ru.otus.message.UserListRequest;

import java.util.Map;

class DbMessageRouter extends Router {

//	private Balancer balancer = new Balancer();

//	DbMessageRouter(Map<String, MessageWorker> messageWorkerMap) {
//		super(messageWorkerMap);
//	}
//
//	@Override
//	void sendMessage(Message message) {
//		if (canSendMessage(message)) {
//			messageWorkerMap.get(balancer.getAddressee()).sendMessage(message);
//		} else {
//			super.sendMessage(message);
//		}
//	}


	@Override
	String getAddressee(Message message) {
		if (canGetAddressee(message)) {

		}
		return super.getAddressee(message);
	}

	private boolean canGetAddressee(Message message) {
		return message instanceof SaveUserRequest || message instanceof UserListRequest;
	}
}
