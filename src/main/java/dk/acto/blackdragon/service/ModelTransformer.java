package dk.acto.blackdragon.service;

import io.vavr.collection.List;

public interface ModelTransformer<T, R> {
    R transform (List<T> model);
}
