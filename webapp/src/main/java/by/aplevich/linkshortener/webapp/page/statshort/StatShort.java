package by.aplevich.linkshortener.webapp.page.statshort;

import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.linkbytagpage.LinkByTagPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;

public class StatShort extends BaseLayout {
    private String description;

    private by.aplevich.linkshortener.datamodel.Link link;
    @Inject
    LinkService linkService;

    public StatShort(Long linkId) {
        link = linkService.get(linkId);
        description = link.getDescription();
        add(new Label("statshort", link.getUrl()));

        TextArea<String> descr = new TextArea<String>("description", new PropertyModel<String>(this, "description"));

        descr.setEnabled(false);
        add(descr);

        Link<String> tagOneLink = new Link<String>("tagonelink") {
            @Override
            public void onClick() {
                setResponsePage(new LinkByTagPage(link.getTagone().getName()));
            }
        };

        add(tagOneLink.add(new Label("tagonename", link.getTagone().getName())));
        Link<String> tagTwoLink = new Link<String>("tagtwolink") {
            @Override
            public void onClick() {
                setResponsePage(new LinkByTagPage(link.getTagtwo().getName()));
            }
        };

        add(tagTwoLink.add(new Label("tagtwoname", link.getTagtwo().getName())));
        Link<String> tagThreeLink = new Link<String>("tagthreelink") {
            @Override
            public void onClick() {
                setResponsePage(new LinkByTagPage(link.getTagthree().getName()));
            }
        };

        add(tagThreeLink.add(new Label("tagthreename", link.getTagthree().getName())));
        Link<String> tagFourLink = new Link<String>("tagfourlink") {
            @Override
            public void onClick() {
                setResponsePage(new LinkByTagPage(link.getTagfour().getName()));
            }
        };

        add(tagFourLink.add(new Label("tagfourname", link.getTagfour().getName())));
        Link<String> tagFiveLink = new Link<String>("tagfivelink") {
            @Override
            public void onClick() {
                setResponsePage(new LinkByTagPage(link.getTagfive().getName()));
            }
        };

        add(tagFiveLink.add(new Label("tagfivename", link.getTagfive().getName())));
    }
}