package com.jp.bookmarks;

import com.jp.bookmarks.auth.HelloAuthenticator;
import com.jp.bookmarks.core.User;
import com.jp.bookmarks.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardTutorialApplication extends Application<DropWizardTutorialConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropWizardTutorialApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropWizardTutorial";
    }

    @Override
    public void initialize(final Bootstrap<DropWizardTutorialConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<DropWizardTutorialConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DropWizardTutorialConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final DropWizardTutorialConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new HelloResource());
        environment.jersey().register(AuthFactory.binder(
                new BasicAuthFactory<>(
                        new HelloAuthenticator(configuration.getPassword()),
                        "SECURITY REALM",
                        User.class
                )));
    }

}
