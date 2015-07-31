package by.aplevich.linkshortener.webapp.page.stat;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Link_;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Iterator;

public class StatPage extends BaseLayout {
    @Inject
    LinkService linkService;

    UserAccount user  = BasicAuthenticationSession.get().getUser();

    public StatPage() {
        LinkDataProvider linkDataProvider = new LinkDataProvider();
        DataView<Link> dataView = new DataView<Link>("list", linkDataProvider, 20) {
            @Override
            protected void populateItem(Item<Link> item) {
                Link link = item.getModelObject();

                item.add(new Label("fullurl", link.getUrl()));
                item.add(new Label("shorturl", "http://127.0.0.1:8081/" + link.getCode()));
                item.add(new Label("quantity", link.getQuantity()));
            }
        };
        add(dataView);
        add(new PagingNavigator("paging", dataView));
    }

    private class LinkDataProvider extends SortableDataProvider<Link, SingularAttribute<Link, ?>> {

        public LinkDataProvider() {
            super();
            setSort(Link_.url, SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends Link> iterator(long first, long count) {
            SingularAttribute<Link, ?> sortParam = getSort().getProperty();
            SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
            boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
            return linkService.getAllLinksByUser(user.getId(), sortParam, ascending, (int) first, (int) count).iterator();
        }

        @Override
        public long size() {
            return linkService.getCount(user.getId());
        }

        @Override
        public IModel<Link> model(Link link) {
            return new CompoundPropertyModel<Link>(link);
        }
    }
}
