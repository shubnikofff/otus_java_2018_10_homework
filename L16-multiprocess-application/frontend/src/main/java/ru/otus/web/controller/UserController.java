package ru.otus.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.otus.application.Application;
import ru.otus.dto.CreateUserDto;
import ru.otus.dto.LoginDto;
import ru.otus.message.*;

import java.util.Objects;

@Controller
public class UserController {
	private final Application application;

	public UserController(Application application) {
		this.application = application;
	}

	@GetMapping("/")
	Object userList(Model model) throws InterruptedException {
		final UserListRequest request = new UserListRequest(application.generateMessageId(), application.getId());
		final Message response = application.sendMessage(request);

		if (response instanceof ServiceUnavailable) {
			return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (response instanceof UnsupportedRequest) {
			return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
		}

		final UserListResponse userListResponse = (UserListResponse) response;
		model.addAttribute("users", userListResponse.getUserDtoList());
		return "list";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	Object login(LoginDto loginDto) throws InterruptedException {
		final Message request = new AuthRequest(application.generateMessageId(), application.getId(), loginDto);
		final Message response = application.sendMessage(request);

		if (response instanceof ServiceUnavailable) {
			return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (response instanceof UnsupportedRequest) {
			return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
		}

		final AuthResponse authResponse = (AuthResponse) response;
		if (authResponse.isAuthorized()) {
			((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
					.getRequest()
					.getSession();
			return "redirect:/";
		} else {
			return "redirect:/login.html";
		}
	}

	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	Object create(CreateUserDto createUserDto) throws InterruptedException {
		final SaveUserRequest message = new SaveUserRequest(application.generateMessageId(), application.getId(), createUserDto);
		final Message response = application.sendMessage(message);

		if (response instanceof ServiceUnavailable) {
			return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
		}

		if (response instanceof UnsupportedRequest) {
			return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
		}

		return "redirect:/";
	}
}
