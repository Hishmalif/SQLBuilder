package org.hishmalif.data;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private final int id;
    private List<Integer> fieldsId;

    public String getListFields(final Query query) {
        StringBuilder builder = new StringBuilder();

        if (getFieldsId().size() > 1) {
            builder.append(query.getFields().get(getFieldsId().get(0)).getShortFieldName(query));
            for (int i = 1; i < getFieldsId().size(); i++) {
                builder.append(",").append(query.getFields().get(getFieldsId().get(i)).getShortFieldName(query));
            }
        } else if (getFieldsId().size() == 1) {
            builder.append(query.getFields().get(getFieldsId().get(0)).getShortFieldName(query));
        }

        return builder.toString();
    }
}