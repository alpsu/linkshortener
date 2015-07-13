package by.aplevich.linkshortener.services.impl;

import by.aplevich.linkshortener.dataaccess.TegDao;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.services.TegService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class TegServiceImpl implements TegService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TegServiceImpl.class);

    @Inject
    private TegDao dao;

    @Override
    public Teg get(Long id) {
        return dao.getById(id);
    }

    @Override
    public void saveOrUpdate(Teg teg) {
        if (teg.getId() == null) {
            LOGGER.debug("Save new: {}", teg);
            dao.insert(teg);
        } else {
            LOGGER.debug("Update: {}", teg);
            dao.update(teg);
        }
    }

    @Override
    public void delete(Teg teg) {
        LOGGER.debug("Remove: {}", teg);
        dao.delete(teg.getId());
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Remove all tegs");
        dao.deleteAll();
    }

    @Override
    public List<Teg> getAllTegs() {
        return dao.getAllTegs();
    }

//    @Override
//    public List<Place> getAllPlaces(SingularAttribute<Place, ?> attr, boolean ascending, int startRecord, int pageSize) {
//        return dao.getAllPlaces(attr,ascending,startRecord,pageSize);
//    }

    @Override
    public Teg getByName(String name) {
        return dao.getByName(name);
    }
}