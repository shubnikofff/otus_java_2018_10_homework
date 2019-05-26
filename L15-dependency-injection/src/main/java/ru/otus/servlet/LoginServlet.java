package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.service.FrontendService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
	@Autowired
	private FrontendService frontendService;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final boolean result = frontendService.auth(request.getParameter("login"), request.getParameter("password"));

		if(result) {
			request.getSession();
			response.sendRedirect("admin");
		} else {
			response.sendRedirect("login.html");
		}
	}
}
