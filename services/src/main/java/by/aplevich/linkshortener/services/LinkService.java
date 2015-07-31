package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.datamodel.Link;

import javax.persistence.metamodel.SingularAttribute;
import javax.transaction.Transactional;
import java.util.List;

public interface LinkService {
    Link get(Long id);

    @Transactional
    void saveOrUpdate(Link link);

    @Transactional
    void delete(Link link);

    @Transactional
    void deleteAll();

    List<Link> getAllLinksByUser(Long userId);

    Long getNextId();

    String encode(int num);

    int decode(String str);


    List<Link> getAllLinksByUser(Long userId, SingularAttribute<Link, ?> attr, boolean ascending, int startRecord, int pageSize);

    Long getCount(Long raceId);
}