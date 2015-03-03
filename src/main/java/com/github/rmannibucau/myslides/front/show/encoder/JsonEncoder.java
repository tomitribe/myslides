package com.github.rmannibucau.myslides.front.show.encoder;

import com.github.rmannibucau.myslides.front.show.domain.Command;

import java.io.IOException;
import java.io.Writer;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonEncoder implements Encoder.TextStream<Command> {
    private JsonEncoderDelegate delegate;

    @Override
    public void init(final EndpointConfig endpointConfig) { // use it to keep json caching and not build it each time
        final BeanManager beanManager = CDI.current().getBeanManager();
        delegate = JsonEncoderDelegate.class.cast(
                beanManager.getReference(beanManager.resolve(beanManager.getBeans(JsonEncoderDelegate.class)), JsonEncoderDelegate.class, null));
    }

    @Override
    public void encode(final Command command, final Writer writer) throws EncodeException, IOException {
        delegate.encode(command, writer);
    }

    @Override
    public void destroy() {
        // no-op
    }
}
