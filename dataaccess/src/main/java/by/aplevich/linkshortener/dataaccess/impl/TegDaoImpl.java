package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.TegDao;
import by.aplevich.linkshortener.datamodel.Teg;
import org.springframework.stereotype.Repository;

@Repository
public class TegDaoImpl extends AbstractDaoImpl<Long, Teg> implements TegDao{
    protected TegDaoImpl(Class<Teg> tegClass) {
        super(tegClass);
    }
}
