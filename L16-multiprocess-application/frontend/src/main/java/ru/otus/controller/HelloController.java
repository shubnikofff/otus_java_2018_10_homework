package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.application.Application;
import ru.otus.message.AuthRequest;
import ru.otus.message.AuthResponse;
import ru.otus.message.Message;

@Controller
public class HelloController {
	private final Application application;

	public HelloController(Application application) {
		this.application = application;
	}

	@GetMapping("/")
	String greeting(Model model) throws InterruptedException {
		Message request = new AuthRequest(application.generateMessageId(), application.getId(), "admin", "admin1");
		AuthResponse response = (AuthResponse) application.sendMessage(request);
		model.addAttribute("response", response.isAuthorized());

		return "hello";
	}
}
