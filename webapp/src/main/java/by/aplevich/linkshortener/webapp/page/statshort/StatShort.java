package by.aplevich.linkshortener.webapp.page.statshort;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.webapp.page.BaseLayout;

public class StatShort extends BaseLayout {
    private Link link;

    public StatShort(Link link) {
        this.link = link;
    }
}

