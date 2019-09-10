package ru.otus.application.service.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.Dao;
import ru.otus.dto.LoginDto;
import ru.otus.message.AuthRequest;
import ru.otus.message.AuthResponse;
import ru.otus.message.Message;
import ru.otus.model.Admin;

@Service
public class AuthQuery extends Query {
	private final String applicationId;
	private final Dao<Admin> dao;

	public AuthQuery(@Value("${id}") String applicationId, Dao<Admin> dao) {
		this.applicationId = applicationId;
		this.dao = dao;
	}

	@Override
	public Message makeQuery(Message message) {
		if (canMakeQuery(message)) {
			AuthRequest request = (AuthRequest) message;
			final LoginDto dto = request.getDto();
			final Admin admin = dao.load(Admin.class, dto.getUsername());
			final boolean authorized = admin != null && admin.getPassword().equals(dto.getPassword());
			return new AuthResponse(message.getId(), applicationId, message.getFrom(), authorized);
		}

		return super.makeQuery(message);
	}

	private boolean canMakeQuery(Message message) {
		return message instanceof AuthRequest;
	}
}
