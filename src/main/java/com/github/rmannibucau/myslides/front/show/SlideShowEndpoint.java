package com.github.rmannibucau.myslides.front.show;

import com.github.rmannibucau.myslides.front.show.encoder.JsonEncoder;
import com.github.rmannibucau.myslides.service.SlideService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Dependent
@ServerEndpoint(value = "/slideshow/{id}", encoders = JsonEncoder.class)
public class SlideShowEndpoint {
    @Inject
    private SlideService slides;

    @Inject 
    private SlideShowManager manager;

    @OnOpen
    public void onOpen(final Session session, @PathParam("id") final Long id) {
        manager.register(session, id);
    }

    @OnClose
    public void onClose(final Session session) {
        manager.deRegister(session);
    }
}
