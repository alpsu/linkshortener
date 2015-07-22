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

    public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
    public static final int BASE = ALPHABET.length();

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

    @Override
    public String encode(int num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALPHABET.charAt(num % BASE));
            num = num / BASE;
        }
        return str.toString();
    }
    @Override
    public  int decode(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    @Override
    public Long getNextId() {
        return dao.getNextId();
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
//
//    @Override
//    public String createShortUrl() {
//        final char[] arr = new char[]{'a','b','c','d','e','f',
//                'g','h','i','j','k','l',
//                'm','n','o','p','r','s',
//                't','u','v','w','x','y',
//                'z','A','B','C','D','E',
//                'G','H','I','J','K','L',
//                'M','N','O','P','R','S',
//                'T','U','V','W','X','Y',
//                'Z','F','1','2','3','4',
//                '5','6','7','8','9','0'};
//        StringBuffer url = new StringBuffer();
//        Random random = new Random();
//        for(int i = 0; i < 7; i++)
//        {
//            url.append(arr[random.nextInt(arr.length-1)]);
//        }
//        return url.toString();
//    }
}