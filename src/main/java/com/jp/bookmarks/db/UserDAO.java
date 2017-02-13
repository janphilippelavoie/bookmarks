package com.jp.bookmarks.db;

import com.jp.bookmarks.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import com.google.common.base.Optional;

/**
 * Created by lavoiejp on 12/02/17.
 */
public class UserDAO extends AbstractDAO<User>  {
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        Query query = namedQuery("com.jp.bookmarks.core.User.findAll");
        return list(query);
    }

    public Optional<User> findByUsernamePassword(String username, String password) {
        Query query = namedQuery("com.jp.bookmarks.core.User.findByUsernamePassword")
                .setParameter("username", username)
                .setParameter("password", password);
        return Optional.fromNullable(uniqueResult(query));
    }
}
