package com.github.rmannibucau.myslides.service;

import com.github.rmannibucau.myslides.service.domain.Slide;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;

import static javax.ejb.LockType.READ;

@Singleton
@Lock(READ)
@ValidateOnExecution
public class SlideService {
    @PersistenceContext(unitName = "slides")
    private EntityManager em;

    @Resource
    private SessionContext sessionContext;

    public Slide newSlide(final Slide slide) {
        slide.setUser(sessionContext.getCallerPrincipal().getName());
        em.persist(slide);
        em.flush();
        return slide;
    }

    public Slide findById(final long id) {
        return em.find(Slide.class, id);
    }

    public Collection<Slide> findByUser(@NotNull final String user) {
        final List<Slide> slides = em.createNamedQuery("Slide.findByUser", Slide.class).setParameter("user", user).getResultList();
        return slides == null ? Collections.<Slide>emptyList() : slides;
    }
}
