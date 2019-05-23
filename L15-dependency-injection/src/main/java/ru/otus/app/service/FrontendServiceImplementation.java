package ru.otus.app.service;

import ru.otus.app.messageSystemContext.MessageSystemContext;
import ru.otus.app.messages.*;
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
	private final static AtomicInteger ID_COUNTER = new AtomicInteger();
	private final Map<Integer, LinkedBlockingQueue<Message>> responseMessageMap = new HashMap<>();

	public FrontendServiceImplementation(Address address, MessageSystemContext messageSystemContext) {
		this.address = address;
		this.messageSystemContext = messageSystemContext;
	}

	@Override
	public Boolean auth(String login, String password) {
		final AuthenticateRequestMessage message = new AuthenticateRequestMessage(ID_COUNTER.incrementAndGet(), address, messageSystemContext.getAuthAddress(), login, password);
		AuthenticateResponseMessage response = (AuthenticateResponseMessage) sendRequest(message);
		return response.isAuthenticated();
	}

	@Override
	public void createUser(HttpServletRequest request) {
		final SaveUserRequestMessage message = new SaveUserRequestMessage(ID_COUNTER.incrementAndGet(), address, messageSystemContext.getDbAddress(), getUserFromRequest(request));
		messageSystemContext.getMessageSystem().sendMessage(message);
	}

	@Override
	public void putResponseMessage(int id, Message message) {
		try {
			responseMessageMap.get(id).put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getUserList() {
		final UserListRequestMessage message = new UserListRequestMessage(ID_COUNTER.incrementAndGet(), address, messageSystemContext.getDbAddress());
		UserListResponseMessage response = (UserListResponseMessage) sendRequest(message);
		return response.getUserList();
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

	private Message sendRequest(Message message) {
		LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
		int messageId = message.getId();
		responseMessageMap.put(messageId, queue);
		messageSystemContext.getMessageSystem().sendMessage(message);
		Message response = null;

		try {
			response = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		responseMessageMap.remove(messageId);
		return response;
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
