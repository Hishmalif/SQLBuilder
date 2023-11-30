package org.hishmalif.data;

import lombok.Data;
import org.hishmalif.data.enums.IntersectType;

import java.util.Map;

@Data
public class Query {
    private final int id;
    private boolean allFieldsAfter;
    private IntersectType intersectType;
    private Map<Integer, Field> fields;
    private Map<Integer, Table> tables;
    private Map<Integer, Condition> conditions;
    private Map<Integer, Group> groups;
    private Map<Integer, Sort> sorts;
}