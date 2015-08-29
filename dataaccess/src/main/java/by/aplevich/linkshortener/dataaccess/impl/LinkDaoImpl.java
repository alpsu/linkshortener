package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Link_;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigInteger;
import java.util.List;

@Repository
public class LinkDaoImpl extends AbstractDaoImpl<Long, Link> implements LinkDao {
    protected LinkDaoImpl() {
        super(Link.class);
    }

    @Override
    public List<Link> getAllLinksByUser(final Long userId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.userAccount), userId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }

    public Long getNextId() {
        EntityManager em = getEm();
        Query q1 = em.createNativeQuery("SELECT nextval('link_id_seq')");
        BigInteger id = (BigInteger) q1.getSingleResult();
        String query2 = "SELECT setval('link_id_seq', " + id + " , false)";
        em.createNativeQuery(query2).getSingleResult();
        return id.longValue();
    }

    @Override
    public Link getById(final Long id) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.id), id));
        root.fetch(Link_.userAccount);
        //root.fetch(Link_.tegs);
        criteria.distinct(true);
        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results.get(0);
    }

    @Override
    public Link get(final Long id) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.id), id));
        TypedQuery<Link> query = getEm().createQuery(criteria);
        Link singleResult = query.getSingleResult();
        return singleResult;
    }

    @Override
    public List<Link> getAllLinksByUser(final Long userId, final SingularAttribute<Link, ?> attr, final boolean ascending, final int first, final int pageSize) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.userAccount), userId));
        criteria.orderBy(new OrderImpl(root.get(attr), ascending));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Link> results = query.getResultList();
        return results;
    }

    @Override
    public Long getCount(final Long userId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.userAccount), userId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return Long.valueOf(results.size());
    }
}
