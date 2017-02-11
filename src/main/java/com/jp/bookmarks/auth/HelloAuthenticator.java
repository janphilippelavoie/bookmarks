package com.jp.bookmarks.auth;

import com.google.common.base.Optional;
import com.jp.bookmarks.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by lavoiejp on 11/02/17.
 */
public class HelloAuthenticator implements Authenticator<BasicCredentials, User> {

    private String password;

    public HelloAuthenticator(String password) {
        this.password = password;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (password.equals(credentials.getPassword())) {
            return Optional.of(new User());
        } else {
            return Optional.absent();
        }
    }
}
