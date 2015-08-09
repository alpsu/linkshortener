package by.aplevich.linkshortener.webapp.app.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Component("authenticationManager")
public class AuthenticationManagerImpl implements AuthenticationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManagerImpl.class);

    @Override
    public boolean authenticate(final String username, final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<String> resolveRoles(final String username) {
        throw new RuntimeException("Not implemented");
    }
}
