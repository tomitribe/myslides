package com.github.rmannibucau.myslides.front.show.encoder;

import com.github.rmannibucau.myslides.front.show.domain.Command;

import java.io.Writer;
import java.util.Collections;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.stream.JsonGeneratorFactory;

@ApplicationScoped
public class JsonEncoderDelegate {
    private final JsonGeneratorFactory factory = Json.createGeneratorFactory(Collections.emptyMap());

    public void encode(final Command command, final Writer writer) {
        factory.createGenerator(writer)
                    .writeStartObject()
                        .write("action", command.getAction().name())
                    .writeEnd()
                .close();
    }
}
