package by.aplevich.linkshortener.webapp.page.linkbytagpage;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.services.TegService;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import javax.inject.Inject;
import java.util.List;

public class LinkByTagPage extends BaseLayout {

    Teg tagTmp;
    @Inject
    LinkService linkService;
    @Inject
    TegService tegService;

    public LinkByTagPage(String tagName) {
        tagTmp = tegService.getByName(tagName);
        final List<Link> allLinksByTag = linkService.getAllLinksByTag(tagTmp.getId());
        add(new ListView<Link>("listlinks", allLinksByTag) {
            @Override
            protected void populateItem(ListItem<Link> item) {
                Link link = item.getModelObject();
                item.add((new Label("fullUrl", link.getUrl())));
            }
        });
    }
}


