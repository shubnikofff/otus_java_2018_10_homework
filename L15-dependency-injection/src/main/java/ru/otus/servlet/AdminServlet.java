package ru.otus.servlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.template.TemplateProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AdminServlet extends HttpServlet {
	private static final String TEMPLATE_FILE_NAME = "admin.ftl";

	private TemplateProcessor templateProcessor = new TemplateProcessor();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_FILE_NAME, new HashMap<>(0)));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
