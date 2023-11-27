package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum QueryConstants {
    SELECT("select"),
    AS("as"),
    OVER("over"),
    PARTITION_BY("partition by"),
    FROM("from"),
    ON("on"),
    WHERE("where"),
    AND("and"),
    OR("or"),
    LOWER("lower"),
    GROUP_BY("group by"),
    ORDER_BY("order by"),
    LIMIT("limit");

    private final String value;
}