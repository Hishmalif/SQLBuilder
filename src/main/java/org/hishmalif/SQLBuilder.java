package org.hishmalif;

import org.hishmalif.builders.Builder;

public class SQLBuilder {
    Builder builder;

    public SQLBuilder(Builder builder) {
        this.builder = builder;
    }

    public String build() {
        return builder.build();
    }
}