package ru.otus.application.service.router;

import ru.otus.message.Message;

class DefaultRouter extends Router {
	@Override
	String getAddressee(Message message) {
		String addressee = message.getTo();
		return addressee != null ? addressee : super.getAddressee(message);
	}
}
