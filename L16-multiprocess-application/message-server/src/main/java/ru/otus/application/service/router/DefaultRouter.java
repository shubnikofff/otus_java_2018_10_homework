package ru.otus.application.service.router;

import org.springframework.stereotype.Service;
import ru.otus.message.Message;

@Service
public class DefaultRouter extends Router {
	@Override
	public String getAddressee(Message message) {
		String addressee = message.getTo();
		return addressee != null ? addressee : super.getAddressee(message);
	}
}
