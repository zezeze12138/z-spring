package com.spring.beans.factory.parsing;

import com.spring.core.io.Resource;

public class ReaderContext {

    private final Resource resource;

    public ReaderContext(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
