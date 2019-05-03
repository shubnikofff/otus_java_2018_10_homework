package ru.otus.app;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

public class MessageSystemContext {
	private final MessageSystem messageSystem;

	private Address authAddress;
	private Address dbAddress;
	private Address frontendAddress;

	public MessageSystemContext(MessageSystem messageSystem) {
		this.messageSystem = messageSystem;
	}

	public MessageSystem getMessageSystem() {
		return messageSystem;
	}

	public Address getAuthAddress() {
		return authAddress;
	}

	public void setAuthAddress(Address authAddress) {
		this.authAddress = authAddress;
	}

	public Address getDbAddress() {
		return dbAddress;
	}

	public void setDbAddress(Address dbAddress) {
		this.dbAddress = dbAddress;
	}

	public Address getFrontendAddress() {
		return frontendAddress;
	}

	public void setFrontendAddress(Address frontendAddress) {
		this.frontendAddress = frontendAddress;
	}
}
