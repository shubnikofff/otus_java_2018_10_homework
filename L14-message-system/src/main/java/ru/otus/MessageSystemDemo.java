package ru.otus;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import ru.otus.app.MessageSystemContext;
import ru.otus.app.service.AuthServiceImplementation;
import ru.otus.app.service.DBServiceImplementation;
import ru.otus.app.service.FrontendServiceImplementation;
import ru.otus.dao.Dao;
import ru.otus.dao.HibernateConfigurationBuilder;
import ru.otus.dao.HibernateDao;
import ru.otus.dao.HibernateSessionFactoryBuilder;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.server.ServerBuilder;
import ru.otus.service.*;

public class MessageSystemDemo {
	private Server server;
	private MessageSystem messageSystem;

	private MessageSystemDemo() {
		messageSystem = new MessageSystem();
		final MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);

		final Address authAddress = new Address("Auth");
		messageSystemContext.setAuthAddress(authAddress);

		final Address dbAddress = new Address("DB");
		messageSystemContext.setDbAddress(dbAddress);

		final Address frontendAddress = new Address("Frontend");
		messageSystemContext.setFrontendAddress(frontendAddress);

		AuthService authService = new AuthServiceImplementation(authAddress, messageSystemContext);
		authService.registerInMessageSystem();

		SessionFactory sessionFactory = HibernateConfigurationBuilder
				.builder()
				.setXmlConfig("hibernate.cfg.xml")
				.addAnnotatedClasses(User.class, ru.otus.model.Address.class, Phone.class)
				.getSessionFactory();
//
//		Dao dao = new HibernateDao(sessionFactory.openSession());

		final HibernateSessionFactoryBuilder sessionFactoryBuilder = new HibernateSessionFactoryBuilder("hibernate.cfg.xml")
				.withAnnotatedClasses(User.class, ru.otus.model.Address.class, Phone.class);
		final Dao<User> dao = new HibernateDao<>(sessionFactoryBuilder);

		DBService dbService = new DBServiceImplementation(dbAddress, dao, messageSystemContext);
		dbService.registerInMessageSystem();

		FrontendService frontendService = new FrontendServiceImplementation(frontendAddress, messageSystemContext);
		frontendService.registerInMessageSystem();

		server = new ServerBuilder()
				.withResourceHandler("/static")
				.withContextHandler(sessionFactory, frontendService)
				.build(8080);
	}

	private void startWebServer() throws Exception {
		server.start();
		server.join();
	}

	private void startMessageSystem() {
		messageSystem.start();
	}

	public static void main(String[] args) throws Exception {
		final MessageSystemDemo messageSystemDemo = new MessageSystemDemo();

		messageSystemDemo.startMessageSystem();
		messageSystemDemo.startWebServer();
	}
}
