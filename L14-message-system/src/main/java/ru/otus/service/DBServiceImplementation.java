package ru.otus.service;

import ru.otus.app.MessageSystemContext;
import ru.otus.dao.Dao;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.User;

import java.util.List;

public class DBServiceImplementation implements DBService{
	private Address address;
	private Dao dao;
	private MessageSystemContext messageSystemContext;

	public DBServiceImplementation(Address address, Dao dao, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.dao = dao;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public long saveUser(User user) {
		long id = (long)dao.save(user);
		return id;
	}

	@Override
	public List<User> getAllUsers() {
		return null;
	}

	@Override
	public void registerInMessageSystem() {
		messageSystemContext.getMessageSystem().addAddressee(this);
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMessageSystem() {
		return messageSystemContext.getMessageSystem();
	}
}
