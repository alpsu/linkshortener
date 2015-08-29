package by.aplevich.linkshortener.servlet;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectServlet extends HttpServlet {

    private static final int NUM_SYMBOLS_FOR_DELETE = 3;
    private static final String PATH_TO_HOME = "http://127.0.0.1:8081/wicket/home";
    @Autowired
    private LinkService linkService;

    @Override
    public void init() throws ServletException {
        //super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        String value = request.getRequestURI().substring(NUM_SYMBOLS_FOR_DELETE);
        Link link = null;
        try {
            link = linkService.get(Long.valueOf(linkService.decode(value)));
        } catch (Exception e) {
            response.sendRedirect(response.encodeRedirectURL(PATH_TO_HOME));
        }
        if (link != null) {
            final String url = link.getUrl();

            if (!StringUtils.isEmpty(url)) {
                int quantity = link.getQuantity();
                link.setQuantity(++quantity);
                linkService.saveOrUpdate(link);
                response.sendRedirect(response.encodeRedirectURL(url));
            }
        }
    }
}
