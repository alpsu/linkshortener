package by.aplevich.linkshortener.webapp.page.statshort;

import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.linkbytagpage.LinkByTagPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

public class StatShort extends BaseLayout {
    private String description;

    private by.aplevich.linkshortener.datamodel.Link link;
    @Inject
    private LinkService linkService;

    public StatShort(final Long linkId) {
        link = linkService.get(linkId);
        description = link.getDescription();
        Set<Teg> tegs = link.getTegs();
        add(new Label("statshort", link.getUrl()));

        TextArea<String> descr = new TextArea<String>("description", new PropertyModel<String>(this, "description"));

        descr.setEnabled(false);
        add(descr);

        add(new ListView<Teg>("list-teg", new ArrayList<Teg>(tegs)) {
            @Override
            protected void populateItem(ListItem<Teg> item) {
                Teg teg = item.getModelObject();
                Link tegLink = new Link<String>("teg") {
                    @Override
                    public void onClick() {
                        setResponsePage(new LinkByTagPage(teg.getName()));
                    }
                };
                tegLink.add(new Label("tegName", new Model<>(teg.getName())));
                item.add(tegLink);
            }
        });
//        Link<String> tagOneLink = new Link<String>("tagonelink") {
//            @Override
//            public void onClick() {
//                setResponsePage(new LinkByTagPage(link.getTagone().getName()));
//            }
//        };
//
//        add(tagOneLink.add(new Label("tagonename", link.getTagone().getName())));
    }
}
