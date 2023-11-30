package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum SortType {
    ASC("asc"),
    DESC("desc");

    private final String value;
}