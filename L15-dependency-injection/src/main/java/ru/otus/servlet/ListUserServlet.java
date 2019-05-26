package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.model.User;
import ru.otus.service.FrontendService;
import ru.otus.template.TemplateProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ListUserServlet extends HttpServlet {
	private static final String VARIABLE_USER_LIST = "userList";
	private static final String TEMPLATE_FILE_NAME = "list.ftl";

	@Autowired
	private FrontendService frontendService;

	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final List<User> userList = frontendService.getUserList();
		final HashMap<String, Object> variables = new HashMap<>();
		variables.put(VARIABLE_USER_LIST, userList);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_FILE_NAME, variables));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
