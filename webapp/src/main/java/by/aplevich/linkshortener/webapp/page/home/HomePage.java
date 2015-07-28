package by.aplevich.linkshortener.webapp.page.home;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.login.LoginPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import javax.inject.Inject;

public class HomePage extends BaseLayout {
    UserAccount user;

//    private String longurl;
//    private String description;
//    private String tagOne;
//    private String tagTwo;
//    private String tagThree;
//    private String tagFour;
//    private String tagFive;

    @Inject
    LinkService linkService;

    public HomePage() {
        super();

        final Form<Link> urlform = new Form<Link>("urlform", new CompoundPropertyModel<Link>(new Link()));

        final TextField<String> longInput = new TextField<>("url");
        urlform.add(longInput);

        final TextField<String> codeInput = new TextField<>("code");
        urlform.add(codeInput);



        urlform.add(new SubmitLink("longinputbtn") {
            @Override
            public void onSubmit() {
                super.onSubmit();

                user = BasicAuthenticationSession.get().getUser();

                String value = longInput.getValue();
                if (value.contains("http://localhost:8081/")) {
                    value.replace("http://localhost:8081/", "");
                }
                if (value.contains("http://127.0.0.1:8081/")) {
                    value.replace("http://127.0.0.1:8081/", "");
                }
                // TODO check URL in base if exist - get code
                int shortUrlid = linkService.decode(value);

                Link link = linkService.get(Long.valueOf(shortUrlid));
//                if ((link != null) && (user == null)) {
//                    //setResponsePage(new StatPage(link));
//                }

                if ((user == null) && (value != null)) {
                    setResponsePage(new LoginPage());
                } else {
                    Long id = linkService.getNextId();
                    Link linkNew = new Link();
                    linkNew.setUrl(value);
                    String code = linkService.encode(id.intValue());
                    linkNew.setCode(code);
                    linkNew.setUserAccount(user);
                    linkNew.setQuantity(0);
                    linkNew.setDescription("description");
                    linkService.saveOrUpdate(linkNew);
                    codeInput.setModel(new Model<>(code));
                }
            }
        });
        add(urlform);
    }


    @Override
    protected IModel<String> getPageTitle() {
        return new ResourceModel("p.home.title");
    }
}