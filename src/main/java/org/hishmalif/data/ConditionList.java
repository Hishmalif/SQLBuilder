package org.hishmalif.data;

import lombok.Data;
import org.hishmalif.data.enums.ConditionType;

import java.util.LinkedList;
import java.util.List;

@Data
public class ConditionList {
    private final ConditionType type;
    private List<Condition> conditions = new LinkedList<>();
}