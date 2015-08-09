package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.datamodel.Teg;

import javax.transaction.Transactional;
import java.util.List;

public interface TegService {
    Teg get(Long id);

    Teg getByName(String name);

    @Transactional
    void saveOrUpdate(Teg teg);

    @Transactional
    void delete(Teg teg);

    @Transactional
    void deleteAll();

    List<Teg> getAllTegs();

    //List<Place> getAllPlaces(SingularAttribute<Place, ?> attr, boolean ascending, int startRecord, int pageSize);
}
