package ru.otus.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.dao.HibernateDao;
import ru.otus.model.User;
import ru.otus.server.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ListUserServlet extends HttpServlet {
	private static final String VARIABLE_USER_LIST = "userList";
	private static final String TEMPLATE_FILE_NAME = "list.ftl";

	private SessionFactory sessionFactory;
	private TemplateProcessor templateProcessor;

	public ListUserServlet(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		templateProcessor = new TemplateProcessor();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		try (Session session = sessionFactory.openSession()) {
			final HibernateDao<User> userHibernateDao = new HibernateDao<>(session);
			final List<User> userList = userHibernateDao.getAll(User.class);

			final HashMap<String, Object> variables = new HashMap<>();
			variables.put(VARIABLE_USER_LIST, userList);

			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(templateProcessor.getPage(TEMPLATE_FILE_NAME, variables));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
