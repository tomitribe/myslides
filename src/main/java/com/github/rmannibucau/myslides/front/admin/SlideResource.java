package com.github.rmannibucau.myslides.front.admin;

import com.github.rmannibucau.myslides.front.admin.domain.UserSlide;
import com.github.rmannibucau.myslides.service.SlideService;
import com.github.rmannibucau.myslides.service.domain.Slide;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import static javax.ejb.LockType.READ;

@Singleton
@Lock(READ)
//@RolesAllowed("**")
@Path("slide")
public class SlideResource {
    @Inject
    private SlideService slideService;

    @Inject
    private DtoMapper mapper;

    @GET
    @Path("user/{user}")
    public List<UserSlide> slides(@PathParam("user") final String user) {
        return slideService.findByUser(user)
                .stream()
                .map(mapper::toUserSlide)
                .collect(Collectors.toList());
    }
    @GET
    @Path("{id}")
    public UserSlide slide(@PathParam("id") final Long id) {
        return mapper.toUserSlide(slideService.findById(id));
    }

    @POST
    @Path("user/new")
    public UserSlide createOrUpdate(final UserSlide userSlide) {
        Slide s = slideService.findById(userSlide.getId());
        final Slide slide = mapper.copySlide(s != null ? s : new Slide(), userSlide);
        if (s == null) {
            userSlide.setId(slideService.newSlide(slide).getId());
        }
        return userSlide;
    }
}
