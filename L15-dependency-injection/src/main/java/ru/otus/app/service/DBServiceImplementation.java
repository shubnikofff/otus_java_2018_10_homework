package ru.otus.app.service;

import ru.otus.app.messageSystemContext.MessageSystemContext;
import ru.otus.dao.Dao;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.User;
import ru.otus.service.DBService;

import java.util.List;

public class DBServiceImplementation implements DBService {
	private Address address;
	private Dao<User> dao;
	private MessageSystemContext messageSystemContext;

	public DBServiceImplementation(Address address, Dao<User> dao, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.dao = dao;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public void saveUser(User user) {
		dao.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return dao.getAll(User.class);
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
