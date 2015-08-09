package by.aplevich.linkshortener.webapp.page.login;

import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.app.WicketWebApplication;
import by.aplevich.linkshortener.webapp.page.stat.StatPage;
import by.aplevich.linkshortener.webapp.page.user.UserEditPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

import javax.servlet.http.HttpServletRequest;

public class LoginPanel extends Panel {

    public LoginPanel(final String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Link("login-btn") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                boolean isLoginPage = LoginPage.class.equals(getPage().getClass());
                boolean isLogged = BasicAuthenticationSession.get().isSignedIn();
                setVisible(!(isLogged || isLoginPage));
            }

            @Override
            public void onClick() {
                setResponsePage(new LoginPage());
            }
        });

        add(new Link("logout-btn") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(BasicAuthenticationSession.get().isSignedIn());
            }

            @Override
            public void onClick() {
                final HttpServletRequest servletReq = (HttpServletRequest) getRequest().getContainerRequest();
                servletReq.getSession().invalidate();
                getSession().invalidate();
                getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(WicketWebApplication.HOME_URL));
            }
        });

        UserAccount user = BasicAuthenticationSession.get().getUser();
        add(new Label("user-name", new Model(user != null ? user.getName() : null)));
        add(new Link("create-user-btn") {
            @Override
            public void onClick() {
                setResponsePage((new UserEditPage(new UserAccount())));
            }
        });
        Link statLink = new Link<String>("stat") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(BasicAuthenticationSession.get().isSignedIn());
            }

            @Override
            public void onClick() {
                setResponsePage(new StatPage());
            }
        };
        add(statLink.add(new Label("statname", new ResourceModel("p.stat"))));
    }
}
