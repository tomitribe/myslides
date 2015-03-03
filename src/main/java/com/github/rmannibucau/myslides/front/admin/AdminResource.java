package com.github.rmannibucau.myslides.front.admin;

import com.github.rmannibucau.myslides.front.show.SlideShowManager;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import static javax.ejb.LockType.READ;

@Singleton
@Lock(READ)
// @RolesAllowed("**")
@Path("slideshow")
public class AdminResource {
    @Inject
    private SlideShowManager slideShowManager;

    @HEAD
    @Path("actions/next/{id}")
    public void next(@PathParam("id") final String id) {
        slideShowManager.next(id);
    }

    @HEAD
    @Path("actions/previous/{id}")
    public void previous(@PathParam("id") final String id) {
        slideShowManager.previous(id);
    }
}
