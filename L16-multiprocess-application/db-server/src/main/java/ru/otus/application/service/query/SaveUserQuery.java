package ru.otus.application.service.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.Dao;
import ru.otus.dto.CreateUserDto;
import ru.otus.message.Message;
import ru.otus.message.SaveUserRequest;
import ru.otus.message.SaveUserResponse;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveUserQuery extends Query {
	private final String applicationId;
	private final Dao<User> dao;

	public SaveUserQuery(@Value("${id}") String applicationId, Dao<User> dao) {
		this.applicationId = applicationId;
		this.dao = dao;
	}

	@Override
	public Message makeQuery(Message message) {
		if (canMakeQuery(message)) {
			SaveUserRequest request = (SaveUserRequest) message;
			final CreateUserDto dto = request.getDto();
			final List<Phone> phoneList = dto.getPhones().stream().map(Phone::new).collect(Collectors.toList());
			final long id = (long) dao.save(new User(dto.getName(), dto.getAge(), new Address(dto.getAddress()), phoneList));
			return new SaveUserResponse(message.getId(), applicationId, message.getFrom(), id);
		}

		return super.makeQuery(message);
	}

	private boolean canMakeQuery(Message message) {
		return message instanceof SaveUserRequest;
	}
}
