//package ru.otus.servlet;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.otus.application.service.FrontendService;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Component
//public class HelloServlet extends HttpServlet {
//	@Autowired
//	private FrontendService frontendService;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		final HttpSession session = req.getSession();
//		resp.getOutputStream().println("Hello from container " + System.getenv("ID"));
//	}
//}
