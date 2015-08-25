package by.aplevich.linkshortener.webapp.page.stat;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Link_;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.services.LinkService;
import by.aplevich.linkshortener.webapp.app.BasicAuthenticationSession;
import by.aplevich.linkshortener.webapp.page.BaseLayout;
import by.aplevich.linkshortener.webapp.page.home.HomePage;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
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
    private LinkService linkService;

    private UserAccount user = BasicAuthenticationSession.get().getUser();

    public StatPage() {
        LinkDataProvider linkDataProvider = new LinkDataProvider();
        DataView<Link> dataView = new DataView<Link>("list", linkDataProvider, 20) {
            @Override
            protected void populateItem(final Item<Link> item) {
                Link link = item.getModelObject();

                item.add(new Label("fullurl", link.getUrl()));
                item.add(new Label("shorturl", HomePage.HTTP_NUMBER_URL + link.getCode()));
                item.add(new Label("quantity", link.getQuantity()));
            }
        };
        add(dataView);
        add(new PagingNavigator("paging", dataView));
        add(new OrderByBorder<SingularAttribute<Link, ?>>("sortByUrl", Link_.url, linkDataProvider));
        add(new OrderByBorder<SingularAttribute<Link, ?>>("sortByCode", Link_.code, linkDataProvider));
        add(new OrderByBorder<SingularAttribute<Link, ?>>("sortByQuantity", Link_.quantity, linkDataProvider));
    }

    private class LinkDataProvider extends SortableDataProvider<Link, SingularAttribute<Link, ?>> {

        public LinkDataProvider() {
            super();
            setSort(Link_.code, SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends Link> iterator(final long first, final long count) {
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
        public IModel<Link> model(final Link link) {
            return new CompoundPropertyModel<Link>(link);
        }
    }
}
