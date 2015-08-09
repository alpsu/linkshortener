package by.aplevich.linkshortener.webapp.app;

import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.services.UserAccountService;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

import javax.inject.Inject;

public class BasicAuthenticationSession extends AuthenticatedWebSession {
    public static final String ROLE_SIGNED_IN = "SIGNED_IN";

    private UserAccount user;
    private Roles resultRoles;

    @Inject
    private UserAccountService userAccountService;
    @Inject
    private LinkService linkService;

    public BasicAuthenticationSession(final Request request) {
        super(request);
        Injector.get().inject(this);

    }

    public static BasicAuthenticationSession get() {
        return (BasicAuthenticationSession) Session.get();
    }

    @Override
    public boolean authenticate(final String userLogin, final String password) {
        boolean authenticationResult = false;
        final UserAccount account = userAccountService.getUserByLogin(userLogin);
        if (account != null && account.getPassword().equals(password)) {
            this.user = account;
            authenticationResult = true;
        }
        return authenticationResult;
    }

    @Override
    public Roles getRoles() {
        if (isSignedIn() && (resultRoles == null)) {
            resultRoles = new Roles(ROLE_SIGNED_IN);
        }
        return resultRoles;
    }

    @Override
    public void signOut() {
        super.signOut();
        user = null;
    }

    public UserAccount getUser() {
        return user;
    }
}
