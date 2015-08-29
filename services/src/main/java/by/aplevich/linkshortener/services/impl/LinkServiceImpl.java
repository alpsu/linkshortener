package by.aplevich.linkshortener.services.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.services.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
    public static final int BASE = ALPHABET.length();
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkServiceImpl.class);
    @Inject
    private LinkDao dao;

    @Override
    public Link get(final Long id) {
        return dao.get(id);
    }

    public Link getById(final Long id) {
        return dao.getById(id);
    }

    @Override
    public void saveOrUpdate(final Link link) {
        if (link.getId() == null) {
            LOGGER.debug("Save new: {}", link);
            dao.insert(link);
        } else {
            LOGGER.debug("Update: {}", link);
            dao.update(link);
        }
    }

    @Override
    public void delete(final Link link) {
        LOGGER.debug("Delete link: {}", link);
        dao.delete(link.getId());
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Delete all links:");
        dao.deleteAll();
    }

    @Override
    public List<Link> getAllLinksByUser(final Long userId) {
        LOGGER.debug("Get all links by userId: {}", userId);
        return dao.getAllLinksByUser(userId);
    }

    @Override
    public String encode(int num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALPHABET.charAt(num % BASE));
            num = num / BASE;
        }
        return str.toString();
    }
    @Override
    public int decode(final String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    @Override
    public Long getNextId() {
        return dao.getNextId();
    }

    @Override
    public List<Link> getAllLinksByUser(final Long userId, final SingularAttribute<Link, ?> attr, final boolean ascending, final int startRecord, final int pageSize) {
        LOGGER.debug("Get all links by user: {}", userId);
        return  dao.getAllLinksByUser(userId, attr, ascending, startRecord, pageSize);
    }

    @Override
    public Long getCount(final Long userId) {
        return dao.getCount(userId);
    }
}
