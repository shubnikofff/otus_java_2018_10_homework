package ru.otus.app.messageSystemContext;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

public class MessageSystemContext {
	private final MessageSystem messageSystem;
	private final Address authAddress;
	private final Address dbAddress;
	private final Address frontendAddress;

	public MessageSystemContext(MessageSystem messageSystem, Address authAddress, Address dbAddress, Address frontendAddress) {
		this.messageSystem = messageSystem;
		this.authAddress = authAddress;
		this.dbAddress = dbAddress;
		this.frontendAddress = frontendAddress;
	}

	public MessageSystem getMessageSystem() {
		return messageSystem;
	}

	public Address getAuthAddress() {
		return authAddress;
	}

	public Address getDbAddress() {
		return dbAddress;
	}

	public Address getFrontendAddress() {
		return frontendAddress;
	}
}
