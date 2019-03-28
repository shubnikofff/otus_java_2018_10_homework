package ru.otus.servlet;

import ru.otus.service.AuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (AuthService.auth(request.getParameter("login"), request.getParameter("password"))) {
			request.getSession();
			response.sendRedirect("/user-list");
		} else {
			response.sendRedirect("/index.html");
		}
	}
}
