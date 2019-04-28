package com.microcore.common.xss;

import org.apache.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 * @author chenshun
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpRequest);
		if("OPTIONS".equals(httpRequest.getMethod())){
			chain.doFilter(request, response);
			return;
		};
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}