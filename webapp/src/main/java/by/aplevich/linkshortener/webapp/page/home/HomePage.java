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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage extends BaseLayout {
    public static final String HTTP_NUMBER_URL = "http://127.0.0.1:8081/r/";
    //public static final String HTTP_NUMBER_URL = "http://127.0.0.1:8081/";
    public static final String HTTP_LOCAL_URL = "http://localhost:8081/r/";
    //public static final String HTTP_LOCAL_URL = "http://localhost:8081/";
    private final TextArea<String> descr;
    private final TextArea<String> tag;
    private final TextField<String> longInput;

    @Inject
    private LinkService linkService;
    @Inject
    private TegService tegService;

    private UserAccount user;
    private String tmp;
    private Long idNum;
    private String url;
    private String description;
    private String tags;
    private Set<Teg> tmpTegs;

    private boolean isUrlExist;
    private boolean isShortUrl;
    private boolean isEditing;

    public HomePage(final PageParameters parameters) {
        super();
        if (parameters.getNamedKeys().contains("idNum")) {
            idNum = parameters.get("idNum").toLong();
        }
        final Form urlform = new Form("urlform", new CompoundPropertyModel(this));

        longInput = new TextField<>("url");
        if (parameters.getNamedKeys().contains("url")) {
            url = parameters.get("url").toString();
            longInput.setEnabled(false);
            isEditing = true;
        }
        urlform.add(longInput);


        descr = new TextArea<>("description");
        if (parameters.getNamedKeys().contains("desk")) {
            description = parameters.get("desk").toString();
        }
        urlform.add(descr);

        tag = new TextArea<>("tags");
        if (parameters.getNamedKeys().contains("tags")) {
            tags = parameters.get("tags").toString();
        }
        urlform.add(tag);

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
                                // засунь в проверку isUrlExist
                                if (!isUrlExist) {
                                    try {
                                        link = linkService.getById(Long.valueOf(shortUrlid));
                                    } catch (Exception e) {
                                        setResponsePage(HomePage.class, new PageParameters());
                                    }
                                }

                                if (link != null) {
                                    UserAccount uFromLink = link.getUserAccount();
                                    if ((user != null) && (user.equals(uFromLink))) {
                                        PageParameters pageParameters = new PageParameters();
                                        pageParameters.add("url", link.getUrl());
                                        pageParameters.add("idNum", link.getId());
                                        pageParameters.add("desk", link.getDescription());
                                        String convertedTags = convertTegsToString(link);
                                        pageParameters.add("tags", convertedTags);
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
                                Link linkFromBase = linkService.get(idNum);
                                createDescr(linkFromBase);
                                setResponsePage(HomePage.class, new PageParameters());
                            }
                        }
                    }

        );

        add(urlform);
    }

    private String convertTegsToString(final Link link) {
        Set<Teg> tags = link.getTegs();
        StringBuffer str = new StringBuffer();
        for (Teg tmp : tags) {
            str.append(tmp.getName()).append(' ');
        }
        return str.toString().trim();
    }

    private void createDescr(final Link linkTmp) {
        linkTmp.setDescription(descr.getValue());
        tmpTegs = createTags(tag.getValue());
        linkTmp.setTegs(tmpTegs);
        linkService.saveOrUpdate(linkTmp);
    }

    private Set<Teg> createTags(final String value) {
        Set<Teg> tegs = new HashSet<>();
        if ((value != null) && (!value.equals(""))) {
            String tmpValue = value.trim();
            String[] temp;
            temp = tmpValue.split(" ");

            for (String tmpStr : temp) {
                try {
                    Teg teg = tegService.getByName(tmpStr);
                    tegs.add(teg);
                } catch (Exception e) {
                    Teg teg = new Teg();
                    teg.setName(tmpStr);
                    tegService.saveOrUpdate(teg);
                    tegs.add(teg);
                }
            }
        }
        return tegs;
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
