package by.aplevich.linkshortener.webapp.app;

import by.aplevich.linkshortener.webapp.page.home.HomePage;
import by.aplevich.linkshortener.webapp.page.login.LoginPage;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("wicketWebApplicationBean")
public class WicketWebApplication extends AuthenticatedWebApplication {

    @Inject
    private ApplicationContext applicationContext;

    public static final String HOME_URL = "/home";
    public static final String LOGIN_URL = "/login";

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, getApplicationContext()));

        final BeanValidationConfiguration beanValidationConfiguration = new BeanValidationConfiguration();
        beanValidationConfiguration.configure(this);

        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
        getStoreSettings().setMaxSizePerSession(Bytes.kilobytes(500));
        getStoreSettings().setInmemoryCacheSize(50);
        if (RuntimeConfigurationType.DEPLOYMENT.equals(getConfigurationType())) {
            getDebugSettings().setDevelopmentUtilitiesEnabled(false);
            getDebugSettings().setComponentUseCheck(false);
            getDebugSettings().setAjaxDebugModeEnabled(false);
            getMarkupSettings().setStripComments(true);
            getMarkupSettings().setCompressWhitespace(true);
            getMarkupSettings().setStripWicketTags(true);
        } else {
            getComponentPostOnBeforeRenderListeners().add(new StatelessChecker());
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            getDebugSettings().setComponentUseCheck(true);
            getDebugSettings().setAjaxDebugModeEnabled(true);
            getMarkupSettings().setStripComments(false);
            getMarkupSettings().setCompressWhitespace(true);
            getMarkupSettings().setStripWicketTags(true);
        }
        mountPage(HOME_URL, HomePage.class);
        mountPage(LOGIN_URL, LoginPage.class);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return BasicAuthenticationSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
         return LoginPage.class;
    }

    @Override
    public final Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}