package org.hishmalif.data;

import org.hishmalif.data.enums.BuildType;

import java.util.List;

public record BuildObject(BuildType type, List<Query> queryArray) {
}