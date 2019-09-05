package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.application.Application;
import ru.otus.dto.UserDto;
import ru.otus.message.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class HelloController {
	private final Application application;

	public HelloController(Application application) {
		this.application = application;
	}

	@GetMapping("/")
	String greeting(Model model) throws InterruptedException {
//		Message request = new AuthRequest(application.generateMessageId(), application.getId(), "admin", "admin1");
//		AuthResponse response = (AuthResponse) application.sendMessage(request);

		final UserDto bill = new UserDto("Bill", 35, "Uuslinna 6-11", new ArrayList<>(Arrays.asList("+37253849797", "+79103937868")));

		final SaveUserRequest message = new SaveUserRequest(application.generateMessageId(), application.getId(), bill);
		SaveUserResponse response = (SaveUserResponse) application.sendMessage(message);

		model.addAttribute("response", response.getId());
		return "hello";
	}
}
