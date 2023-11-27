package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;

@Getter
@AllArgsConstructor
public enum IntersectType {
    @SerializedName("none")
    NONE("none"),
    @SerializedName("intersect")
    INTERSECT("intersect"),
    @SerializedName("except")
    EXCEPT("except"),
    @SerializedName("union")
    UNION("union"),
    @SerializedName("union_all")
    UNION_ALL("union_all"),
    @SerializedName("subquery")
    SUBQUERY("subquery"),
    @SerializedName("cte")
    CTE("cte");

    private final String value;
}