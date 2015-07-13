package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.TegDao;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.datamodel.Teg_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TegDaoImpl extends AbstractDaoImpl<Long, Teg> implements TegDao{
    protected TegDaoImpl() {
        super(Teg.class);
    }

    @Override
    public Teg getByName(String name) {
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
}