package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum FunctionType {
    AGGREGATE("aggregate"),
    WINDOW("window");

    private final String value;
}