package by.aplevich.linkshortener.dataaccess;

import by.aplevich.linkshortener.datamodel.Teg;

import java.util.List;

public interface TegDao extends AbstractDao<Long, Teg>{
    Teg getByName(String name);

    List<Teg> getAllTegs();
}
