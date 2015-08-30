package by.aplevich.linkshortener.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectFilter implements Filter {
    public void init(FilterConfig config)
            throws ServletException {
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String value = req.getRequestURI();
        int num = value.lastIndexOf('/');
        String tmp = value.substring(++num);
        if (!tmp.contains(".")) {
            chain.doFilter(request, response);
            return;
        }
    }

    public void destroy() {
      /* Called before the Filter instance is removed
      from service by the web container*/
    }
}