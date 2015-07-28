package by.aplevich.linkshortener.webapp.page.user;

import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.UserAccountService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import javax.inject.Inject;
import java.util.Arrays;

public class UserEditPage extends BaseLayout {
    @Inject
    private UserAccountService userService;

    public UserEditPage(UserAccount userAccount) {
        super();
        Form<UserAccount> form = new Form<>("form", new CompoundPropertyModel<UserAccount>(userAccount));

        final TextField<String> tfName = new TextField<>("name");
        tfName.add(new PropertyValidator<String>());
        tfName.setLabel(new ResourceModel("login.name"));
        form.add(tfName);

        final TextField<String> tfLogin = new TextField<>("login");
        tfLogin.add(new PropertyValidator<String>());
        tfLogin.setLabel(new ResourceModel("login.login"));
        form.add(tfLogin);

        final PasswordTextField tfPassword = new PasswordTextField("password");
        tfPassword.add(new PropertyValidator<String>());
        tfPassword.setLabel(new ResourceModel("login.password"));
        form.add(tfPassword);

        final PasswordTextField tfConfPassword = new PasswordTextField("cpassword");
        tfConfPassword.add(new PropertyValidator<String>());
        tfConfPassword.setLabel(new ResourceModel("login.cpassword"));
        form.add(tfConfPassword);

        form.add(new SubmitLink("sumbit-link") {
                     @Override
                     public void onSubmit() {
                         super.onSubmit();

                         String value = tfLogin.getValue();
                         if (userService.getUserByLogin(value) == null) {
                             userService.createNewUser(userAccount);

                             AuthenticatedWebSession.get().signIn(userAccount.getLogin(), userAccount.getPassword());
                             setResponsePage(Application.get().getHomePage());
                         } else {
                             BasicAuthenticationSession.get().error(new StringResourceModel("error.user.login", this, null).getString());
                             UserEditPage page = new UserEditPage(userAccount);
                             setResponsePage(page);
                         }
                     }

                     @Override
                     public void onError() {
                         super.onError();
                     }
                 }
        );

        add(form);
        form.add(new
                        EqualPasswordInputValidator(tfPassword, tfConfPassword)
        );
    }

    @Override
    protected IModel<String> getPageTitle() {
        return new ResourceModel("userEdit.caption");
    }
}