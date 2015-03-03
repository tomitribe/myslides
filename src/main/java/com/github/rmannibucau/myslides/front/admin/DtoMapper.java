package com.github.rmannibucau.myslides.front.admin;

import com.github.rmannibucau.myslides.front.admin.domain.UserSlide;
import com.github.rmannibucau.myslides.service.domain.Slide;

import java.util.Date;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoMapper {
    public UserSlide toUserSlide(final Slide slide) {
        if (slide == null) {
            return null;
        }
        final UserSlide userSlide = new UserSlide();
        userSlide.setId(slide.getId());
        userSlide.setName(slide.getName());
        userSlide.setContent(slide.getContent());
        userSlide.setCreated(new Date(slide.getCreated().getTime()));
        userSlide.setUpdated(new Date(slide.getUpdated().getTime()));
        return userSlide;
    }

    public Slide copySlide(final Slide destination, final UserSlide source) {
        if (source == null) {
            return destination;
        }
        destination.setName(source.getName());
        destination.setContent(source.getContent());
        destination.setCreated(source.getCreated());
        destination.setUpdated(source.getUpdated());
        return destination;
    }
}
