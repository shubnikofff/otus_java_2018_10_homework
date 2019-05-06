package ru.otus.app;

import org.eclipse.jetty.server.Server;
import ru.otus.app.messageSystemContext.MessageSystemContext;
import ru.otus.app.service.AuthServiceImplementation;
import ru.otus.app.service.DBServiceImplementation;
import ru.otus.app.service.FrontendServiceImplementation;
import ru.otus.dao.Dao;
import ru.otus.dao.HibernateDao;
import ru.otus.dao.HibernateSessionFactoryBuilder;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.service.AuthService;
import ru.otus.service.DBService;
import ru.otus.service.FrontendService;
import ru.otus.webServer.ServerBuilder;

public class Application {
	private static Application instance = new Application();

	private final MessageSystem messageSystem = new MessageSystem();

	private final MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);

	private FrontendService frontendService;

	private Server webServer;

	public static Application getInstance() {
		return instance;
	}

	private Application() {
		createAuthService();
		createDbService();
		createFrontendService();
		createWebServer();
	}

	private void createAuthService() {
		final Address address = new Address("Auth Service");
		messageSystemContext.setAuthAddress(address);
		AuthService authService = new AuthServiceImplementation(address, messageSystemContext);
		authService.registerInMessageSystem();
	}

	private void createDbService() {
		final HibernateSessionFactoryBuilder sessionFactoryBuilder = new HibernateSessionFactoryBuilder("hibernate.cfg.xml")
				.withAnnotatedClasses(User.class, ru.otus.model.Address.class, Phone.class);
		final Dao<User> dao = new HibernateDao<>(sessionFactoryBuilder);

		final Address address = new Address("DB Service");
		messageSystemContext.setDbAddress(address);
		DBService dbService = new DBServiceImplementation(address, dao, messageSystemContext);
		dbService.registerInMessageSystem();
	}

	private void createFrontendService() {
		final Address address = new Address("Frontend Service");
		messageSystemContext.setFrontendAddress(address);
		frontendService = new FrontendServiceImplementation(address, messageSystemContext);
		frontendService.registerInMessageSystem();
	}

	private void createWebServer() {
		webServer = new ServerBuilder()
				.withResourceHandler("/static")
				.withContextHandler(frontendService)
				.build(8080);
	}

	public void start() throws Exception {
		messageSystem.start();
		webServer.start();
		webServer.join();
	}
}
