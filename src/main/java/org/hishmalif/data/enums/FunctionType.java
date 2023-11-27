package org.hishmalif.data.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.google.gson.annotations.SerializedName;

@Getter
@AllArgsConstructor
public enum FunctionType {
    @SerializedName("aggregate")
    AGGREGATE("aggregate"),
    @SerializedName("window")
    WINDOW("window");

    private final String value;
}