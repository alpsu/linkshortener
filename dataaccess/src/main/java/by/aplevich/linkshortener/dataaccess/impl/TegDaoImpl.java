package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.TegDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Link_;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.datamodel.Teg_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TegDaoImpl extends AbstractDaoImpl<Long, Teg> implements TegDao {
    protected TegDaoImpl() {
        super(Teg.class);
    }

    @Override
    public Teg getByName(final String name) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Teg> criteria = cBuilder.createQuery(Teg.class);
        Root<Teg> root = criteria.from(Teg.class);

        criteria.select(root);
        criteria.where(cBuilder.equal(root.get(Teg_.name), name));

        TypedQuery<Teg> query = getEm().createQuery(criteria);

        Teg result = query.getSingleResult();
        return result;
    }

    @Override
    public List<Teg> getAllTegs() {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Teg> criteria = cBuilder.createQuery(Teg.class);
        Root<Teg> root = criteria.from(Teg.class);

        criteria.select(root);

        TypedQuery<Teg> query = getEm().createQuery(criteria);
        List<Teg> result = query.getResultList();
        return result;
    }

    @Override
    public List<Link> getAllLinksByTag(Teg tag) {
        CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

        CriteriaQuery<Link> criteria = cBuilder.createQuery(Link.class);
        Root<Link> root = criteria.from(Link.class);

        criteria.select(root);
        criteria.where(cBuilder.isMember(tag, root.get(Link_.tegs)));

        TypedQuery<Link> query = getEm().createQuery(criteria);
        List<Link> results = query.getResultList();
        return results;
//        return new ArrayList<Link>();
    }
}
