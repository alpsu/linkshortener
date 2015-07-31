package by.aplevich.linkshortener.webapp.page;


import by.aplevich.linkshortener.webapp.page.home.HomePage;
import by.aplevich.linkshortener.webapp.page.language.LanguagePanel;
import by.aplevich.linkshortener.webapp.page.login.LoginPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BaseLayout extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("headerTitle", getPageTitle()));
        add(new FeedbackPanel("feedbackpanel"));
        add(new LoginPanel("login-panel"));
        add(new LanguagePanel("language-panel"));

        Link homeLink = new Link<String>("link-home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage(new PageParameters()));
            }
        };
        add(homeLink.add(new Label("linkhome", new ResourceModel("p.home"))));
    }

    protected IModel<String> getPageTitle() {
        return new Model<String>(getClass().getSimpleName());
    }
}