package org.hishmalif.data;

import org.hishmalif.data.enums.BuildType;
import lombok.Data;

import java.util.List;

@Data
public class BuildObject {
    private final BuildType type;
    private boolean writeSchema;
    private final List<Query> queryArray;
}