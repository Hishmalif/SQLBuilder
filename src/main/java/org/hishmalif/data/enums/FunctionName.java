package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum FunctionName {
    AVG(true),
    COUNT(true),
    MAX(true),
    MIN(true),
    SUM(true),
    DENSE_RANK(false),
    RANK(false),
    ROW_NUMBER(false),
    FIRST_VALUE(true),
    LAST_VALUE(true),
    LAG(true),
    LEAD(true);

    private final boolean needField;
}