package ru.otus.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements javax.servlet.Filter {
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		if (httpServletRequest.getSession(false) == null) {
			httpServletResponse.sendRedirect("/index.html");
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	@Override
	public void destroy() {
	}
}
