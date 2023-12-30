package org.hishmalif.data;

import lombok.*;
import org.hishmalif.data.enums.IntersectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Query {
    private final int id;
    private boolean allFieldsAfter;
    private IntersectType intersectType;
    private Map<Integer, Field> fields = new HashMap<>();
    private Map<Integer, Table> tables = new HashMap<>();
    private List<ConditionList> conditionsList = new ArrayList<>();
    private Map<Integer, Group> groups = new HashMap<>();
    private Map<Integer, Sort> sorts = new HashMap<>();
}