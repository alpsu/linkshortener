package by.aplevich.linkshortener.webapp.page.home;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.services.TegService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.shorturl.ShortUrlPage;
import by.aplevich.linkshortener.webapp.page.login.LoginPage;
import by.aplevich.linkshortener.webapp.page.statshort.StatShort;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import javax.inject.Inject;
import java.util.List;

public class HomePage extends BaseLayout {
    private UserAccount user;

    private String url;
    private String description;
    private String tag1;
    private String tag2;
    private String tag3;
    private String tag4;
    private String tag5;

    @Inject
    private LinkService linkService;
    @Inject
    private TegService tegService;

    private boolean isUrlExist;
    private boolean isShortUrl;
    private Teg tegone;
    private Teg tegtwo;
    private Teg tegthree;
    private Teg tegfour;
    private Teg tegfive;

    public HomePage() {
        super();

        final Form urlform = new Form("urlform", new CompoundPropertyModel(this));

        final TextField<String> longInput = new TextField<>("url");
        urlform.add(longInput);

        final TextArea<String> description = new TextArea<String>("description");
        urlform.add(description);

        final TextField<String> tagOne = new TextField<>("tag1");
        urlform.add(tagOne);
        final TextField<String> tagTwo = new TextField<>("tag2");
        urlform.add(tagTwo);
        final TextField<String> tagThree = new TextField<>("tag3");
        urlform.add(tagThree);
        final TextField<String> tagFour = new TextField<>("tag4");
        urlform.add(tagFour);
        final TextField<String> tagFive = new TextField<>("tag5");
        urlform.add(tagFive);

        urlform.add(new SubmitLink("longinputbtn") {
            @Override
            public void onSubmit() {
                super.onSubmit();

                user = BasicAuthenticationSession.get().getUser();

                String value = longInput.getValue();
                if (!value.contains("http://")) {
                    value = "http://" + value;
                }
                if (user != null) {
                    checkUrlInBase(value);
                }

                if (value.contains("http://localhost:8081/")) {
                    value.replace("http://localhost:8081/", "");
                }
                if (value.contains("http://127.0.0.1:8081/")) {
                    value.replace("http://127.0.0.1:8081/", "");
                }

                int shortUrlid = linkService.decode(value);

                Link link = linkService.get(Long.valueOf(shortUrlid));
                if (link != null) {
                    isShortUrl = true;
                    setResponsePage(new StatShort(link, user));
                }

                if (!isShortUrl) {
                    if ((user == null) && (value != null)) {
                        setResponsePage(new LoginPage());
                    } else if (!isUrlExist) {
                        Long id = linkService.getNextId();
                        Link linkNew = new Link();
                        linkNew.setUrl(value);
                        String code = linkService.encode(id.intValue());
                        linkNew.setCode(code);
                        linkNew.setUserAccount(user);
                        linkNew.setQuantity(0);
                        linkNew.setDescription(description.getValue());
                        createTags(tagOne.getValue(), tagTwo.getValue(), tagThree.getValue(), tagFour.getValue(), tagFive.getValue());
                        linkNew.setTagone(tegone);
                        linkNew.setTagtwo(tegtwo);
                        linkNew.setTagthree(tegthree);
                        linkNew.setTagfour(tegfour);
                        linkNew.setTagfive(tegfive);
                        linkService.saveOrUpdate(linkNew);
                        setResponsePage(new ShortUrlPage("http//127.0.0.1/" + code));
                    }
                }
            }
        });
        add(urlform);
    }

    private void createTags(String tagOneName, String tagTwoName, String tagThreeName, String tagFourName, String tagFiveName) {
        if (tagOneName != null) {
            try {
                tegone = tegService.getByName(tagOneName);
            } catch (Exception e) {
                tegone = new Teg();
                tegone.setName(tagOneName);
                tegService.saveOrUpdate(tegone);
            }
        }
        if (tagTwoName != null) {
            try {
                tegtwo = tegService.getByName(tagTwoName);
            } catch (Exception e) {
                tegtwo = new Teg();
                tegtwo.setName(tagTwoName);
                tegService.saveOrUpdate(tegtwo);
            }
        }
        if (tagThreeName != null) {
            try {
                tegthree = tegService.getByName(tagThreeName);
            } catch (Exception e) {
                tegthree = new Teg();
                tegthree.setName(tagThreeName);
                tegService.saveOrUpdate(tegthree);
            }
        }
        if (tagFourName != null) {
            try {
                tegfour = tegService.getByName(tagFourName);
            } catch (Exception e) {
                tegfour = new Teg();
                tegfour.setName(tagFourName);
                tegService.saveOrUpdate(tegfour);
            }
        }
        if (tagFiveName != null) {
            try {
                tegfive = tegService.getByName(tagFiveName);
            } catch (Exception e) {
                tegfive = new Teg();
                tegfive.setName(tagFiveName);
                tegService.saveOrUpdate(tegfive);
            }
        }
    }

    private void checkUrlInBase(String value) {
        List<Link> links = linkService.getAllLinksByUser(user.getId());
        for (Link one : links) {
            String url = one.getUrl();
            if (url.equals(value)) {
                isUrlExist = true;
                setResponsePage(new ShortUrlPage("http//127.0.0.1:8081/" + one.getCode()));
            }
        }
    }

    @Override
    protected IModel<String> getPageTitle() {
        return new ResourceModel("p.home.title");
    }
}