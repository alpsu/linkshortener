package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Link_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        //root.fetch(Link_.userAccount);
        //root.fetch(Link_.tagone);
       // root.fetch(Link_.tagtwo);
        //root.fetch(Link_.tagthree);
        //root.fetch(Link_.tagfour);
        //root.fetch(Link_.tagfive);

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
}
