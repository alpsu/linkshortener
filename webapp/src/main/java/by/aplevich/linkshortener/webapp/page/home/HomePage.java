package by.aplevich.linkshortener.webapp.page.home;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.services.TegService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.login.LoginPage;
import by.aplevich.linkshortener.webapp.page.shorturl.ShortUrlPage;
import by.aplevich.linkshortener.webapp.page.statshort.StatShort;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;
import java.util.List;

public class HomePage extends BaseLayout {
    public static String HTTP_NUMBER_URL = "http://127.0.0.1:8081/r/";
    public static String HTTP_LOCAL_URL = "http://localhost:8081/r/";
    private final TextArea<String> descr;
    private final TextField<String> tagOne;
    private final TextField<String> tagTwo;
    private final TextField<String> tagThree;
    private final TextField<String> tagFour;
    private final TextField<String> tagFive;
    private UserAccount user;
    private String tmp;
    private Long id;
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
    private boolean isEditing;
    private Teg tegone;
    private Teg tegtwo;
    private Teg tegthree;
    private Teg tegfour;
    private Teg tegfive;

    public HomePage(final PageParameters parameters) {
        super();
        if (parameters.getNamedKeys().contains("id")) {
            id = parameters.get("id").toLong();
        }
        final Form urlform = new Form("urlform", new CompoundPropertyModel(this));

        TextField<String> longInput = new TextField<>("url");
        if (parameters.getNamedKeys().contains("url")) {
            url = parameters.get("url").toString();
            longInput.setEnabled(false);
            isEditing = true;
        }
        urlform.add(longInput);


        descr = new TextArea<String>("description");
        if (parameters.getNamedKeys().contains("desk")) {
            description = parameters.get("desk").toString();
        }
        urlform.add(descr);

        tagOne = new TextField<String>("tag1");
        if (parameters.getNamedKeys().contains("tag1")) {
            tag1 = parameters.get("tag1").toString();
        }
        urlform.add(tagOne);
        tagTwo = new TextField<String>("tag2");
        if (parameters.getNamedKeys().contains("tag2")) {
            tag2 = parameters.get("tag2").toString();
        }
        urlform.add(tagTwo);
        tagThree = new TextField<String>("tag3");
        if (parameters.getNamedKeys().contains("tag3")) {
            tag3 = parameters.get("tag3").toString();
        }
        urlform.add(tagThree);
        tagFour = new TextField<String>("tag4");
        if (parameters.getNamedKeys().contains("tag4")) {
            tag4 = parameters.get("tag4").toString();
        }
        urlform.add(tagFour);
        tagFive = new TextField<String>("tag5");
        if (parameters.getNamedKeys().contains("tag5")) {
            tag5 = parameters.get("tag5").toString();
        }
        urlform.add(tagFive);

        urlform.add(new SubmitLink("longinputbtn") {
                        @Override
                        public void onSubmit() {
                            super.onSubmit();

                            if (!isEditing) {
                                user = BasicAuthenticationSession.get().getUser();

                                String value = longInput.getValue();
                                if (!value.contains("http://")) {
                                    if (!value.contains("https://")) {
                                        value = "http://" + value;
                                    }
                                }
                                if (user != null) {
                                    checkUrlInBase(value);
                                }

                                if (value.contains(HTTP_LOCAL_URL)) {
                                    tmp = value.replace(HTTP_LOCAL_URL, "");
                                }
                                if (value.contains(HTTP_NUMBER_URL)) {
                                    tmp = value.replace(HTTP_NUMBER_URL, "");
                                }

                                int shortUrlid;
                                if (tmp != null) {
                                    shortUrlid = linkService.decode(tmp);
                                    isShortUrl = true;
                                } else {
                                    shortUrlid = linkService.decode(value);
                                }

                                Link link = null;
                                try {
                                    link = linkService.get(Long.valueOf(shortUrlid));
                                } catch (Exception e) {
                                    setResponsePage(HomePage.class, new PageParameters());
                                }

                                if (link != null) {
                                    UserAccount uFromLink = link.getUserAccount();
                                    if ((user != null) && (user.equals(uFromLink))) {
                                        PageParameters pageParameters = new PageParameters();
                                        pageParameters.add("url", link.getUrl());
                                        pageParameters.add("id", link.getId());
                                        pageParameters.add("desk", link.getDescription());
                                        pageParameters.add("tag1", link.getTagone().getName());
                                        pageParameters.add("tag2", link.getTagtwo().getName());
                                        pageParameters.add("tag3", link.getTagthree().getName());
                                        pageParameters.add("tag4", link.getTagfour().getName());
                                        pageParameters.add("tag5", link.getTagfive().getName());
                                        setResponsePage(HomePage.class, pageParameters);
                                    } else {
                                        setResponsePage(new StatShort(link.getId()));
                                    }
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
                                        createDescr(linkNew);
                                        setResponsePage(new ShortUrlPage(HTTP_NUMBER_URL + code));
                                    }
                                }
                            } else {
                                Link linkFromBase = linkService.get(id);
                                createDescr(linkFromBase);
                                setResponsePage(HomePage.class, new PageParameters());
                            }
                        }
                    }

        );

        add(urlform);
    }

    private void createDescr(final Link linkTmp) {
        linkTmp.setDescription(descr.getValue());
        createTags(tagOne.getValue(), tagTwo.getValue(), tagThree.getValue(), tagFour.getValue(), tagFive.getValue());
        linkTmp.setTagone(tegone);
        linkTmp.setTagtwo(tegtwo);
        linkTmp.setTagthree(tegthree);
        linkTmp.setTagfour(tegfour);
        linkTmp.setTagfive(tegfive);
        linkService.saveOrUpdate(linkTmp);
    }

    private void createTags(final String tagOneName, final String tagTwoName, final String tagThreeName, final String tagFourName, final String tagFiveName) {
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
                setResponsePage(new ShortUrlPage(HTTP_NUMBER_URL + one.getCode()));
            }
        }
    }

    @Override
    protected IModel<String> getPageTitle() {
        return new ResourceModel("p.home.title");
    }
}
