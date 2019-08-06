package ru.otus.application.service.router;

import ru.otus.message.Message;

public class DefaultRouter extends Router {
	@Override
	public String getAddressee(Message message) {
		String addressee = message.getTo();
		return addressee != null ? addressee : super.getAddressee(message);
	}
}
