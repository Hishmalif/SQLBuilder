package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum FunctionName {
    AVG("avg", true),
    COUNT("count", true),
    MAX("max", true),
    MIN("min", true),
    SUM("sum", true),
    DENSE_RANK("dense_rank", false),
    RANK("rank", false),
    ROW_NUMBER("row_number", false),
    FIRST_VALUE("first_value", true),
    LAST_VALUE("last_value", true),
    LAG("lag", true),
    LEAD("lead", true);

    private final String value;
    private final boolean needField;
}