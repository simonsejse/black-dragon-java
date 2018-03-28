package dk.acto.blackdragon.service;

import io.vavr.collection.List;

public interface ModelFactory<T> {
    List<T> parse (String string);
}
