package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;

@Getter
@AllArgsConstructor
public enum BuildType {
    @SerializedName("select")
    SELECT("select"),
    @SerializedName("update")
    UPDATE("update"),
    @SerializedName("delete")
    DELETE("delete");

    private final String value;
}