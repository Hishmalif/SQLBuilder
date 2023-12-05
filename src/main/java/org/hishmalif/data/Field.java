package org.hishmalif.data;

import lombok.Data;

@Data
public class Field {
    private final int id;
    private final String name;
    private String alias;
    private final int position;
    private final int tableId;
    private Function function;

    /**
     * Returns the alias of the object, if not null.
     *
     * @return The alias of the object, or an empty string if the alias is null.
     */
    public String getAlias() {
        if (alias == null) {
            return "";
        }
        return " as " + alias;
    }
}