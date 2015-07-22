package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.AbstractDao;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigInteger;
import java.util.List;

public abstract class AbstractDaoImpl<ID, Entity> implements AbstractDao<ID, Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoImpl.class);
    private final Class<Entity> entityClass;
    private EntityManager em;

    protected AbstractDaoImpl(final Class<Entity> entityClass) {
        Validate.notNull(entityClass, "entityClass could not be a null");
        this.entityClass = entityClass;
    }

    @Override
    public Entity getById(ID id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public Entity insert(final Entity entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Entity update(Entity entity) {
        entity = em.merge(entity);
        em.flush();
        return entity;
    }

    @Override
    public void delete(final ID key) {
        em.remove(em.find(getEntityClass(), key));
    }

    @Override
    public void delete(List<ID> ids) {
        em.createQuery(String.format("Delete from %s e where e.id in (:ids)", entityClass.getSimpleName()))
                .setParameter("ids", ids).executeUpdate();
    }

    @Override
    public void deleteAll() {
        final Query q1 = em.createQuery("Delete from " + getEntityClass().getSimpleName());
        q1.executeUpdate();
        em.flush();
    }

    @Override
    public List<Entity> getAll() {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Entity> query = builder.createQuery(getEntityClass());
        query.from(getEntityClass());
        return em.createQuery(query).getResultList();
    }

    @PersistenceContext
    protected void setEntityManager(final EntityManager em) {
        LOGGER.info("Set EM {} to class {}", em.hashCode(), getClass().getName());
        this.em = em;
    }

    private Class<Entity> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEm() {
        return em;
    }

    @Override
    public List<Entity> getAllByFieldRestriction(final SingularAttribute<? super Entity, ?> attribute, final Object value) {
        Validate.notNull(value, "Search attributes can't be empty. Attribute: " + attribute.getName());
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Entity> criteria = builder.createQuery(getEntityClass());
        final Root<Entity> root = criteria.from(getEntityClass());
        criteria.select(root).distinct(true);
        criteria.where(builder.equal(root.get(attribute), value));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Long getNextId() {
        Query q1 = em.createNativeQuery("SELECT nextval('link_id_seq')");
        BigInteger id = (BigInteger)q1.getSingleResult();
        String query2 = "SELECT setval('link_id_seq', " + id + " , false)";
        em.createNativeQuery(query2).getSingleResult();
        return id.longValue();
    }
}