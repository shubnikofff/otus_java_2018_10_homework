package ru.otus.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();

		writer.println("<html>");
		writer.println("<head>");
		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/admin.css\">");
		writer.println("<title>Admin page</title>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println("<div>");
		writer.println("<a href=\"create.html\">Create user</a>");
		writer.println("</div>");
		writer.println("<div>");
		writer.println("<a href=\"list\">User list</a>");
		writer.println("</div>");
		writer.println("</body>");
		writer.println("</html>");
	}
}
