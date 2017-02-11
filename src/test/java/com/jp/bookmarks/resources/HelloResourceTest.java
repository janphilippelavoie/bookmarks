package com.jp.bookmarks.resources;

import com.google.common.base.Optional;
import com.jp.bookmarks.core.User;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

/**
 * Created by lavoiejp on 11/02/17.
 */
public class HelloResourceTest {

    private static final HttpAuthenticationFeature AUTHENTICATION_FEATURE = HttpAuthenticationFeature.basic("username", "password");
    private static final Authenticator<BasicCredentials, User> AUTHENTICATOR = new Authenticator<BasicCredentials, User>() {
        @Override
        public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
            return Optional.of(new User());
        }
    };

    @ClassRule
    public static final ResourceTestRule RULE =
            ResourceTestRule.builder()
                    .addProvider(AuthFactory.binder(
                            new BasicAuthFactory<>(
                                    AUTHENTICATOR,
                                    "realm",
                                    User.class
                            )))
                    .setTestContainerFactory(
                            new GrizzlyWebTestContainerFactory()
                    )
                    .addResource(new HelloResource())
                    .build();

    @BeforeClass
    public static void setupClass() {
        RULE.getJerseyTest().client().register(AUTHENTICATION_FEATURE);
    }


    @Test
    public void testGetHello() {
        String actual = RULE
                .getJerseyTest()
                .target("/hello")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals("Hello World!", actual);
    }

    @Test
    public void testGetSecrets() {
        String actual = RULE
                .getJerseyTest()
                .target("/hello/secret")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals("Secret stuff", actual);
    }

}