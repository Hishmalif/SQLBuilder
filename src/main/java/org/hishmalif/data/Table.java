package org.hishmalif.data;

import lombok.*;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Table {
    private final int id;
    private final String name;
    private String alias;
    private String schema;
    private int position;
    private int joinCondition;

    public String getAlias() {
        return alias != null ? alias : name;
    }
}