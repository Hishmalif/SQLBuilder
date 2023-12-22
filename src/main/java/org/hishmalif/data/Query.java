package org.hishmalif.data;

import lombok.*;
import org.hishmalif.data.enums.IntersectType;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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