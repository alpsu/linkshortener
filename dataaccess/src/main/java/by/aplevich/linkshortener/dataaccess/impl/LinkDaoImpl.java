package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import org.springframework.stereotype.Repository;

@Repository
public class LinkDaoImpl extends AbstractDaoImpl<Long, Link> implements LinkDao{
    protected LinkDaoImpl(Class<Link> linkClass) {
        super(linkClass);
    }
}
