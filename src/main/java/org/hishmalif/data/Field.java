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

//    public String getLongFieldName(final Query query) {
//        return String.format("%s%s",getFunction(query), getAlias());
//    }

    /**
     * Build alias if it not null
     */
    private String getAlias() {
        if (alias == null) {
            return "";
        }
        return " as " + alias;
    }

    /**
     * Build function if not null
     */
//    private String getFunction(final Query query) {
//        if (function != null) {
//            return function.getFunctionName(query, getShortFieldName(query));
//        } else {
//            return getShortFieldName(query);
//        }
//    }
}