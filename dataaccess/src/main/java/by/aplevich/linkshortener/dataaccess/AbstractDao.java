package by.aplevich.linkshortener.dataaccess;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public interface AbstractDao<ID, Entity> {

    Entity getById(ID id);

    List<Entity> getAll();

    void delete(ID key);

    void deleteAll();

    void delete(List<ID> ids);

    Entity insert(Entity entity);

    Entity update(Entity entity);

    List<Entity> getAllByFieldRestriction(final SingularAttribute<? super Entity, ?> attribute, final Object value);
}