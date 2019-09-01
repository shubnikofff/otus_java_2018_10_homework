package ru.otus.application.service.router;

import org.springframework.stereotype.Service;
import ru.otus.application.configuration.ApplicationProperties;
import ru.otus.message.Message;
import ru.otus.message.PingRequest;
import ru.otus.message.SaveUserRequest;
import ru.otus.message.UserListRequest;

@Service
public class DbRouter extends Router {
	private final ApplicationProperties applicationProperties;
	private int lastIndex = 0;

	public DbRouter(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Override
	public String getAddressee(Message message) {
		if (canGetAddressee(message)) {
			final int index = lastIndex + 1 < applicationProperties.getDbServerIds().size() ? lastIndex + 1 : 0;
			lastIndex = index;
			return applicationProperties.getDbServerIds().get(index);
		}
		return super.getAddressee(message);
	}

	private boolean canGetAddressee(Message message) {
		return message instanceof SaveUserRequest || message instanceof UserListRequest || message instanceof PingRequest;
	}
}
