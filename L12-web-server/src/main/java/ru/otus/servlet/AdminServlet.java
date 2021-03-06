package ru.otus.servlet;

import ru.otus.service.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AdminServlet extends HttpServlet {
	private static final String TEMPLATE_FILE_NAME = "admin.ftl";

	private TemplateProcessor templateProcessor = new TemplateProcessor();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_FILE_NAME, new HashMap<>(0)));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
