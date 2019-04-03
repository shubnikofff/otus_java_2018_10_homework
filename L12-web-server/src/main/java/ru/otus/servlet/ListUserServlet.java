package ru.otus.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.dao.HibernateDao;
import ru.otus.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListUserServlet extends HttpServlet {
	private SessionFactory sessionFactory;

	public ListUserServlet(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		try (Session session = sessionFactory.openSession()) {
			final HibernateDao<User> userHibernateDao = new HibernateDao<>(session);
			final List<User> userList = userHibernateDao.getAll(User.class);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<head>");
			writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/list.css\">");
			writer.println("<title>User list</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<div class=\"link-container\"><a href=\"/admin\">< Back to admin page</a></div>");
			writer.println("<table>");
			writer.println("<tr>");
			writer.println("<th>Id</th><th>Name</th><th>Age</th><th>Address</th><th>Phones</th>");
			writer.println("</tr>");

			userList.forEach(user -> {
				writer.println("<tr><td>" + user.getId() + "</td>");
				writer.println("<td>" + user.getName() + "</td>");
				writer.println("<td>" + user.getAge() + "</td>");
				writer.println("<td>" + user.getAddress() + "</td>");
				writer.println("<td>" + user.getPhones() + "</td>");
				writer.println("</tr>");
			});

			writer.println("</table>");
			writer.println("</body>");
			writer.println("</html>");
		}
	}
}
