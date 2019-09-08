package ru.otus.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.otus.application.Application;
import ru.otus.dto.UserDto;
import ru.otus.message.*;
import ru.otus.web.form.CreateUserForm;
import ru.otus.web.form.LoginForm;

import java.util.Objects;

@Controller
public class UserController {
	private final Application application;

	public UserController(Application application) {
		this.application = application;
	}

	@GetMapping("/")
	String userList(Model model) throws InterruptedException {
		final UserListRequest request = new UserListRequest(application.generateMessageId(), application.getId());
		final UserListResponse response = (UserListResponse) application.sendMessage(request);
		model.addAttribute("users", response.getUserDtoList());
		return "list";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	String login(LoginForm loginForm) throws InterruptedException {
		final Message authRequest = new AuthRequest(application.generateMessageId(), application.getId(), loginForm.getUsername(), loginForm.getPassword());
		final AuthResponse authResponse = (AuthResponse) application.sendMessage(authRequest);

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
	String create(CreateUserForm userForm) throws InterruptedException {
		final UserDto userDto = new UserDto(null, userForm.getName(), Integer.parseInt(userForm.getAge()), userForm.getAddress(), userForm.getPhones());
		final SaveUserRequest message = new SaveUserRequest(application.generateMessageId(), application.getId(), userDto);
		application.sendMessage(message);
		return "redirect:/";
	}
}
