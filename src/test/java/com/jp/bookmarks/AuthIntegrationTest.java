package com.jp.bookmarks;

import io.dropwizard.Application;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.*;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by lavoiejp on 11/02/17.
 */
public class AuthIntegrationTest {
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yaml");

    @ClassRule
    public static final DropwizardAppRule<DropWizardTutorialConfiguration> RULE =
            new DropwizardAppRule<DropWizardTutorialConfiguration>(
                    DropWizardTutorialApplication.class,
                    CONFIG_PATH
            );

    private static final String TARGET = "https://localhost:8443";
    private static final String PATH = "/hello/secret";
    private static final String KEYSTORE_FILE = "bookmarks.keystore";
    private static final String KEYSTORE_PASSWORD = "rootroot";

    private static final HttpAuthenticationFeature AUTHENTICATION_FEATURE =
            HttpAuthenticationFeature.basic("bobby", "bobisgreat");


    private Client client;

    @BeforeClass
    public static void setupClass() throws Exception {
        Application<DropWizardTutorialConfiguration> application = RULE.getApplication();
        application.run("db", "migrate", CONFIG_PATH);

    }

    @Before
    public void setUp() {
        SslConfigurator sslConfigurator = SslConfigurator.newInstance();
        sslConfigurator.trustStoreFile(KEYSTORE_FILE);
        sslConfigurator.trustStorePassword(KEYSTORE_PASSWORD);
        SSLContext sslContext = sslConfigurator.createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void testSadPath() {
        Response response = client
                .target(TARGET)
                .path(PATH)
                .request()
                .get();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testHappyPath() {
        client.register(AUTHENTICATION_FEATURE);
        String expected = client
                .target(TARGET)
                .path(PATH)
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        assertEquals("Secret stuff", expected);
    }



}
