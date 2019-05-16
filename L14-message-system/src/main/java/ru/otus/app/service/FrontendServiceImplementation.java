package ru.otus.app.service;

import ru.otus.app.messageSystemContext.MessageSystemContext;
import ru.otus.app.messages.AuthenticateRequestMessage;
import ru.otus.app.messages.AuthenticateResponseMessage;
import ru.otus.app.messages.SaveUserRequestMessage;
import ru.otus.app.messages.UserListRequestMessage;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Message;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.service.FrontendService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FrontendServiceImplementation implements FrontendService {
	private Address address;
	private MessageSystemContext messageSystemContext;
	private volatile List<User> userList;
	private final static AtomicInteger idCounter = new AtomicInteger();
	private final Map<Integer, LinkedBlockingQueue<Message>> messageMap = new HashMap<>();

	public FrontendServiceImplementation(Address address, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public boolean auth(String login, String password) {
		var ref = new Object() {
			Boolean auth = null;
		};

		int messageId = idCounter.incrementAndGet();
		final AuthenticateRequestMessage message = new AuthenticateRequestMessage(messageId, address, messageSystemContext.getAuthAddress(), login, password);
		LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
		messageMap.put(messageId, queue);
		messageSystemContext.getMessageSystem().sendMessage(message);


		Thread thread = new Thread(() -> {
			while (ref.auth == null) {
				try {
					AuthenticateResponseMessage response = (AuthenticateResponseMessage)queue.take();
					ref.auth = response.isAuthenticated();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		messageMap.remove(messageId);
		return ref.auth;
	}

	@Override
	public void createUser(HttpServletRequest request) {
		final SaveUserRequestMessage message = new SaveUserRequestMessage(address, messageSystemContext.getDbAddress(), getUserFromRequest(request));
		messageSystemContext.getMessageSystem().sendMessage(message);
	}

	@Override
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public void putResponseMessage(int id, Message message) {
		try {
			messageMap.get(id).put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getUserList() {
		userList = null;
		final UserListRequestMessage message = new UserListRequestMessage(address, messageSystemContext.getDbAddress());
		messageSystemContext.getMessageSystem().sendMessage(message);

		while (userList == null) {
			Thread.onSpinWait();
		}

		return userList;
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

	private User getUserFromRequest(HttpServletRequest request) {
		final User user = new User();

		String name = request.getParameter("name");
		if (name != null) {
			user.setName(name);
		}

		try {
			user.setAge(Integer.parseInt(request.getParameter("age")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		user.setAddress(new ru.otus.model.Address(request.getParameter("address")));

		final String[] phones = request.getParameterValues("phone");
		for (String phone : phones) {
			if (!phone.equals("")) {
				user.addPhone(new Phone(phone));
			}
		}

		return user;
	}
}
