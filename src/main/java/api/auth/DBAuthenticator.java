package api.auth;

import com.jp.bookmarks.core.User;
import com.jp.bookmarks.db.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import com.google.common.base.Optional;

/**
 * Created by lavoiejp on 13/02/17.
 */
public class DBAuthenticator implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public DBAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        return userDAO.findByUsernamePassword(credentials.getUsername(), credentials.getPassword());
    }
}
