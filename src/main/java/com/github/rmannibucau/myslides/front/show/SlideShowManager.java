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
    private final Map<Long, Session> sessionsById = new HashMap<>();
    private final Map<Session, Long> slideIdBySession = new HashMap<>();

    public void register(final Session session, final Long slideId) {
        sessionsById.put(slideId, session);
        slideIdBySession.put(session, slideId);
    }

    public void deRegister(final Session session) {
        sessionsById.remove(slideIdBySession.remove(session));
    }

    public void next(final Long id) {
        doSend(id, Command.NEXT);
    }

    public void previous(final Long id) {
        doSend(id, Command.PREVIOUS);
    }

    private void doSend(final Long id, final Command cmd) {
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
