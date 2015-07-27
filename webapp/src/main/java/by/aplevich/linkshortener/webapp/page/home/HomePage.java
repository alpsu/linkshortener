package by.aplevich.linkshortener.webapp.page.home;

import by.aplevich.linkshortener.webapp.page.BaseLayout;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import javax.inject.Inject;

public class HomePage extends BaseLayout {
//
//    @Override
//    protected void onInitialize() {
//        super.onInitialize();
//    }

    public HomePage() {
        super();

        final TextField<String> longUrl = new TextField<String>("longinput");
        longUrl.setLabel(new ResourceModel("input.long"));
        add(longUrl);
    }

    @Override
    protected IModel<String> getPageTitle() {
        return new ResourceModel("p.home.title");
    }
}