package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;

@Getter
@AllArgsConstructor
public enum SortType {
    @SerializedName("asc")
    ASC("asc"),
    @SerializedName("desc")
    DESC("desc");

    private final String value;
}