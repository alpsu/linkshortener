package by.aplevich.linkshortener.webapp.page.shorturl;

import by.aplevich.linkshortener.webapp.page.BaseLayout;
import org.apache.wicket.markup.html.basic.Label;

public class ShortUrlPage extends BaseLayout {
    private String fullShortUrl;

    public ShortUrlPage(final String fullShortUrl) {
        this.fullShortUrl = fullShortUrl;
        Label fullShort = new Label("fullshorturl", fullShortUrl);
        add(fullShort);
    }
}
