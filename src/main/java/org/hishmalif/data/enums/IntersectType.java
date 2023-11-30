package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum IntersectType {
    NONE("none"),
    INTERSECT("intersect"),
    EXCEPT("except"),
    UNION("union"),
    UNION_ALL("union_all"),
    SUBQUERY("subquery"),
    CTE("cte");

    private final String value;
}