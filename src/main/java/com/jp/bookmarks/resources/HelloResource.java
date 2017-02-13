package com.jp.bookmarks.resources;

import com.jp.bookmarks.core.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lavoiejp on 11/02/17.
 */

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello World!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secret")
    @UnitOfWork
    public String getHelloSecured(@Auth User user) {
        return "Secret stuff";
    }
}
