package ru.otus.servlet;

import org.springframework.context.ApplicationContext;
import ru.otus.service.FrontendService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends HttpServlet {
	private FrontendService frontendService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		final ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
		frontendService = (FrontendService) ac.getBean("frontendService");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		frontendService.createUser(request);
		response.sendRedirect("list");
	}
}
