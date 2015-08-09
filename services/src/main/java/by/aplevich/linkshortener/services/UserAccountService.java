package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.datamodel.UserAccount;

import javax.transaction.Transactional;

public interface UserAccountService {
    UserAccount get(Long id);

    @Transactional
    void createNewUser(UserAccount user);

    @Transactional
    void updateUser(UserAccount user);

    @Transactional
    void deteteUser(Long id);

    @Transactional
    void deleteAll();

    UserAccount getUserByLogin(String userLogin);
}
