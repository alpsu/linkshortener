package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.UserAccount;

import javax.transaction.Transactional;
import java.util.List;

public interface LinkService {
    Link get(Long id);

    @Transactional
    void saveOrUpdate(Link link);

    @Transactional
    void delete(Link link);

    @Transactional
    void deleteAll();

//    @Transactional
//    void deleteAllInRace(Race race);
//
    List<Link> getAllLinksByUser(Long userId);

    Long getNextId();

    String encode(int num);

    int decode(String str);


//    List<Runner> getAllRunnerByRace(Long raceId, SingularAttribute<Runner, ?> attr, boolean ascending, int startRecord, int pageSize);
//
//    Runner getWithAllByRunner(Long runnerId);
//
//    Long getCount(Long raceId);
}