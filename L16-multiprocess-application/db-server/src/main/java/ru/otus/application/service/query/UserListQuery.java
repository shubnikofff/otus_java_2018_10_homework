package ru.otus.application.service.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.Dao;
import ru.otus.dto.UserDto;
import ru.otus.message.Message;
import ru.otus.message.UserListRequest;
import ru.otus.message.UserListResponse;
import ru.otus.model.Phone;
import ru.otus.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserListQuery extends Query {
	private final String applicationId;
	private final Dao<User> dao;

	public UserListQuery(@Value("${id}") String applicationId, Dao<User> dao) {
		this.applicationId = applicationId;
		this.dao = dao;
	}

	@Override
	public Message makeQuery(Message message) {
		if (canMakeQuery(message)) {
			final List<UserDto> userDtoList = dao.getAll(User.class).stream().map(user -> {
				final List<String> phoneList = user.getPhones().stream().map(Phone::getNumber).collect(Collectors.toList());
				return new UserDto(user.getId(), user.getName(),user.getAge(), user.getAddress().toString(), phoneList);
			}).collect(Collectors.toList());
			return new UserListResponse(message.getId(), applicationId, message.getFrom(), userDtoList);
		}

		return super.makeQuery(message);
	}

	private boolean canMakeQuery(Message message) {
		return message instanceof UserListRequest;
	}
}
