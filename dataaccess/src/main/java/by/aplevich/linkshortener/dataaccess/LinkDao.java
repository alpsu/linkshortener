package by.aplevich.linkshortener.dataaccess;

import by.aplevich.linkshortener.datamodel.Link;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public interface LinkDao extends AbstractDao<Long, Link> {
    List<Link> getAllLinksByUser(Long userId);

    Link getById(Long id);

    Long getNextId();

    List<Link> getAllLinksByUser(Long userId, SingularAttribute<Link, ?> attr, boolean ascending, int first, int pageSize);

    List<Link> getAllLinksByTag1(Long tagId);
    List<Link> getAllLinksByTag2(Long tagId);
    List<Link> getAllLinksByTag3(Long tagId);
    List<Link> getAllLinksByTag4(Long tagId);
    List<Link> getAllLinksByTag5(Long tagId);

    Long getCount(Long userId);
}
