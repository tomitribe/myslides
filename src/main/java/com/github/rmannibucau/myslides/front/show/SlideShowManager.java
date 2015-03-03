package com.github.rmannibucau.myslides.front.show;

import com.github.rmannibucau.myslides.front.show.domain.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Session;

@ApplicationScoped
public class SlideShowManager {
    private final Map<String, Session> sessionsById = new HashMap<>();

    public void register(final Session session, final Long slideId) {
        sessionsById.put(session.getId(), session);
        session.getUserProperties().put("id", slideId);
    }

    public Session findById(final String id) {
        return sessionsById.get(id);
    }

    public Long findBySlideId(final String id) {
        return Long.class.cast(sessionsById.get(id).getUserProperties().get("id"));
    }

    public void deRegister(final Session session) {
        sessionsById.remove(session.getId());
    }

    public void next(final String id) {
        doSend(id, Command.NEXT);
    }

    public void previous(final String id) {
        doSend(id, Command.PREVIOUS);
    }

    private void doSend(final String id, final Command cmd) {
        try {
            sessionsById.get(id).getBasicRemote().sendObject(cmd);
        } catch (final EncodeException | IOException e) {
            throw new ConnectionException(e);
        }
    }

    public static class ConnectionException extends RuntimeException {
        public ConnectionException(final Exception e) {
            super(e);
        }
    }
}
