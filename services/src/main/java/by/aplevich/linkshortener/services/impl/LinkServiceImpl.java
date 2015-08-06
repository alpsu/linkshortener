package by.aplevich.linkshortener.services.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.services.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkServiceImpl.class);

    public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
    public static final int BASE = ALPHABET.length();

    @Inject
    private LinkDao dao;

    @Override
    public Link get(Long id) {
        return dao.getById(id);
    }

    @Override
    public void saveOrUpdate(Link link) {
        if (link.getId() == null) {
            LOGGER.debug("Save new: {}", link);
            dao.insert(link);
        } else {
            LOGGER.debug("Update: {}", link);
            dao.update(link);
        }
    }

    @Override
    public void delete(Link link) {
        LOGGER.debug("Delete link: {}", link);
        dao.delete(link.getId());
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Delete all links:");
        dao.deleteAll();
    }

    @Override
    public List<Link> getAllLinksByUser(Long userId) {
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
    public  int decode(String str) {
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
    public List<Link> getAllLinksByUser(Long userId, SingularAttribute<Link, ?> attr, boolean ascending, int startRecord, int pageSize) {
        LOGGER.debug("Get all links by user: {}", userId);
        return  dao.getAllLinksByUser(userId, attr, ascending, startRecord, pageSize);
    }

    @Override
    public List<Link> getAllLinksByTag(Long tagId) {
        LOGGER.debug("Get all links by tagId: {}", tagId);
        List<Link> result = new ArrayList<Link>();
        result.addAll(dao.getAllLinksByTag1(tagId));
        result.addAll(dao.getAllLinksByTag2(tagId));
        result.addAll(dao.getAllLinksByTag3(tagId));
        result.addAll(dao.getAllLinksByTag4(tagId));
        result.addAll(dao.getAllLinksByTag5(tagId));
        return result;
    }

    @Override
    public Long getCount(Long userId) {
        return dao.getCount(userId);
    }
}