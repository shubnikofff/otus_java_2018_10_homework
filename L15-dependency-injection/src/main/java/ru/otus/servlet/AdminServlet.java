package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.template.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AdminServlet extends HttpServlet {
	private static final String TEMPLATE_FILE_NAME = "admin.ftl";

	@Autowired
	private TemplateProcessor templateProcessor;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(templateProcessor.getPage(TEMPLATE_FILE_NAME, new HashMap<>(0)));
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
