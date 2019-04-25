package ru.otus.servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.dao.HibernateDao;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends HttpServlet {
	private SessionFactory sessionFactory;

	public CreateUserServlet(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		final User user = new User();

		String name = req.getParameter("name");
		if (name != null) {
			user.setName(name);
		}

		try {
			user.setAge(Integer.parseInt(req.getParameter("age")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		user.setAddress(new Address(req.getParameter("address")));

		final String[] phones = req.getParameterValues("phone");
		for (String phone : phones) {
			if (!phone.equals("")) {
				user.addPhone(new Phone(phone));
			}
		}

		try(Session session = sessionFactory.openSession()) {
			final HibernateDao<User> userHibernateDao = new HibernateDao<>(session);
			userHibernateDao.save(user);
		}

		resp.sendRedirect("list");
	}
}
