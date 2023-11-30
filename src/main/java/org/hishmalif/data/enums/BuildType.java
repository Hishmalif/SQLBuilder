package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum BuildType {
    SELECT("select"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;
}