package org.hishmalif.data;

import lombok.Data;
import org.hishmalif.data.enums.SortType;

@Data
public class Sort {
    private final int id;
    private final int fieldsId;
    private SortType type;

    public String getField(final Query query) {
        return String.format(getType() != null ? ", %s %s" : ", %s%s",
                query.getFields().get(fieldsId).getShortFieldName(query), getTypeSort());
    }

    private String getTypeSort() {
        return getType() != null ? getType().getValue() : "";
    }
}
