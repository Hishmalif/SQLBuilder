package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;

@Getter
@AllArgsConstructor
public enum FunctionName {
    @SerializedName("avg")
    AVG("avg", true),
    @SerializedName("count")
    COUNT("count", true),
    @SerializedName("max")
    MAX("max", true),
    @SerializedName("min")
    MIN("min", true),
    @SerializedName("sum")
    SUM("sum", true),
    @SerializedName("dense_rank")
    DENSE_RANK("dense_rank", false),
    @SerializedName("rank")
    RANK("rank", false),
    @SerializedName("row_number")
    ROW_NUMBER("row_number", false),
    @SerializedName("first_value")
    FIRST_VALUE("first_value", true),
    @SerializedName("last_value")
    LAST_VALUE("last_value", true),
    @SerializedName("lag")
    LAG("lag", true),
    @SerializedName("lead")
    LEAD("lead", true);

    private final String value;
    private final boolean needField;
}