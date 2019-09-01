package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.application.Application;
import ru.otus.message.PingResponse;

@Controller
public class HelloController {
	private final Application application;

	public HelloController(Application application) {
		this.application = application;
	}

	@GetMapping("/")
	String greeting(Model model) throws InterruptedException {
		final PingResponse response = application.sendPing();
		model.addAttribute("response", response.getText());
		return "hello";
	}
}
