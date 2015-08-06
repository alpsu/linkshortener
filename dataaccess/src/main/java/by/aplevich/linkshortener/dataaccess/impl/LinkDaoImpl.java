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
    public List<Link> getAllLinksByUser(Long userId) {
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
        BigInteger id = (BigInteger)q1.getSingleResult();
        String query2 = "SELECT setval('link_id_seq', " + id + " , false)";
        em.createNativeQuery(query2).getSingleResult();
        return id.longValue();
    }

    @Override
    public Link getById(Long id) {
        EntityManager em = getEm();
        CriteriaBuilder cBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.id), id));
        root.fetch(Link_.userAccount);
        root.fetch(Link_.tagone);
        root.fetch(Link_.tagtwo);
        root.fetch(Link_.tagthree);
        root.fetch(Link_.tagfour);
        root.fetch(Link_.tagfive);

        TypedQuery<Link> query = em.createQuery(criteria);
        List<Link> results = query.getResultList();
        return results.get(0);
    }

    @Override
    public List<Link> getAllLinksByUser(Long userId, SingularAttribute<Link, ?> attr, boolean ascending, int first, int pageSize) {
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
    public List<Link> getAllLinksByTag1(Long tagId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.tagone), tagId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }
    @Override
    public List<Link> getAllLinksByTag2(Long tagId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.tagtwo), tagId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }
    @Override
    public List<Link> getAllLinksByTag3(Long tagId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.tagthree), tagId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }
    @Override
    public List<Link> getAllLinksByTag4(Long tagId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.tagfour), tagId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }
    @Override
    public List<Link> getAllLinksByTag5(Long tagId) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Link_.tagfive), tagId));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
    }

    @Override
    public Long getCount(Long userId) {
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