package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.application.service.FrontendService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HelloServlet extends HttpServlet {
	@Autowired
	private FrontendService frontendService;

	@Autowired
	private WebApplicationContext context;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getServletContext().getAttributeNames();
		resp.getOutputStream().println("Hello from container " + context.getId());
		resp.getOutputStream().println("Hello from container " + frontendService.getId());
	}
}
