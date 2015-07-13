package by.aplevich.linkshortener.services.impl;

import by.aplevich.linkshortener.dataaccess.LinkDao;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.services.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkServiceImpl.class);

    @Inject
    private LinkDao dao;

    @Override
    public Link get(Long id) {
        return dao.getById(id);
    }

    @Override
    public void saveOrUpdate(Link link) {
        if (link.getId() == null) {
            LOGGER.debug("Save new: {}", link);
            dao.insert(link);
        } else {
            LOGGER.debug("Update: {}", link);
            dao.update(link);
        }
    }

    @Override
    public void delete(Link link) {
        LOGGER.debug("Delete link: {}", link);
        dao.delete(link.getId());
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Delete all links:");
        dao.deleteAll();
    }

    @Override
        public List<Link> getAllLinksByUser(Long userId) {
        LOGGER.debug("Get all links by userId: {}", userId);
        return dao.getAllLinksByUser(userId);
    }
//
//    @Override
//    public void deleteAllInRace(Race race) {
//        LOGGER.debug("Deleting all runner in race: {}", race);
//        List<Runner> runners = getAllRunnerByRace(race);
//        List<Long> ids = new ArrayList<>();
//        for (Runner runner : runners) {
//            ids.add(runner.getId());
//        }
//        dao.delete(ids);
//    }

//    @Override
//    public List<Runner> getAllRunnerByRace(Long raceId, SingularAttribute<Runner, ?> attr, boolean ascending, int startRecord, int pageSize) {
//        LOGGER.debug("Get all runner in race: {}", raceId);
//        return  dao.getAllRunnersByRaceWith(raceId, attr, ascending, startRecord, pageSize);
//    }
//
//    @Override
//    public Long getCount(Long raceId) {
//        return dao.getCount(raceId);
//    }
//
//    @Override
//    public Runner getWithAllByRunner(Long runnerId) {
//        return dao.getWithAllByRunner(runnerId);
//    }
}