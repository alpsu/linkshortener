package by.aplevich.linkshortener.dataaccess;

import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.UserAccount;

import java.util.List;

public interface LinkDao extends AbstractDao<Long, Link>{
    List<Link> getAllLinksByUser(Long userId);
}