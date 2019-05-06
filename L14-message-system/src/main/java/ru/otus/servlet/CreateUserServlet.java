package ru.otus.servlet;

import ru.otus.service.FrontendService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends HttpServlet {
	private FrontendService frontendService;

	public CreateUserServlet(FrontendService frontendService) {
		this.frontendService = frontendService;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		frontendService.createUser(request);
		response.sendRedirect("list");
	}
}
