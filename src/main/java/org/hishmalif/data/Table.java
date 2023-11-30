package org.hishmalif.data;

import lombok.Data;

@Data
public class Table {
    private final int id;
    private final String name;
    private String alias;
    private String schema;
    private int position;
    private int joinCondition;

    public String getLongTableName(boolean writeSchema) {
        if (writeSchema && schema != null) {
            return String.format("%s.%s", getSchema(), getName());
        } else {
            return getName();
        }
    }
}