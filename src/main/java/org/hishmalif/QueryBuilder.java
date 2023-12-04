package org.hishmalif;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.BuildType;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.data.enums.QueryConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder implements Builder {
    protected Query query;
    private final BuildType type;
    private final boolean writeSchema;

    public QueryBuilder(final Query query, final BuildType type, final boolean writeSchema) {
        this.query = query;
        this.type = type;
        this.writeSchema = writeSchema;
    }

    public String build() {
        final StringBuilder queryString = new StringBuilder();

        if (type != null && type.equals(BuildType.SELECT)) {
            queryString.append(BuildType.SELECT.getValue()).append(" ");


            queryString.append("\n").append(QueryConstants.FROM.getValue()).append(" ");

        } else if (type != null && type.equals(BuildType.UPDATE)) {
            queryString.append(BuildType.UPDATE.getValue()).append(" "); //TODO in future
        } else if (type != null && type.equals(BuildType.DELETE)) {
            queryString.append(BuildType.DELETE.getValue()).append(" "); //TODO in future
        } else {
            throw new IllegalArgumentException("Incorrect type of build");
        }
        return queryString.toString();
    } //TODO сделать норм

    public String getFullFieldName(final Field field) {
        if (field.getFunction() == null) {
            return getShortFieldName(field);
        }
        return getLongFieldName(field);
    }

    public String getShortFieldName(final Field field) {
        if (field != null && query.getFields().containsValue(field)) {
            return String.format("%s.%s", getShortTableName(field.getTableId()), field.getName());
        }
        throw new IllegalArgumentException("Incorrect field id!");
    }

    private String getLongFieldName(final Field field) {
        final Function function = field.getFunction();

        if (function.getType().equals(FunctionType.WINDOW)) {
            return String.format("%s %s (%s%s)", getFunction(field),
                    QueryConstants.OVER.getValue(),
                    getPartitionGroups(function.getGroupsId()),
                    getSorts(function.getSortsId()));
        }
        return getFunction(field);
    }

    private String getFunction(final Field field) {
        final Function function = field.getFunction();

        if (function.getName().isNeedField()) {
            return String.format("%s(%s)", function.getName().getValue(), getShortFieldName(field));
        }
        return String.format("%s()", function.getName().getValue());
    }

    public String getSorts(final List<Integer> sorts) {
        final StringBuilder builder = new StringBuilder();
        final Map<Integer, Sort> sortMap = query.getSorts();

        if (sorts == null || sorts.size() == 0 || sortMap == null || sortMap.isEmpty()) {
            return "";
        }
        Sort sort = sortMap.get(sorts.get(0));
        builder.append(String.format("%s %s", QueryConstants.ORDER_BY.getValue(), getSortField(sort)));
        for (int i = 1; i < sorts.size(); i++) {
            sort = sortMap.get(sorts.get(i));
            builder.append(String.format(", %s", getSortField(sort)));
        }
        return builder.toString();
    }

    public String getShortTableName(final int id) { //TODO Сделать проверку на корректность id
        final Table table = query.getTables().get(id);
        return table.getAlias() != null ? table.getAlias() : table.getName();
    }

    public String getGroupFields(final List<Integer> fieldsId) {
        final StringBuilder builder = new StringBuilder();
        final Map<Integer, Field> fieldMap = query.getFields();

        if (fieldsId.size() > 0 && fieldMap != null && !fieldMap.isEmpty()) {
            builder.append(getShortFieldName(fieldMap.get(fieldsId.get(0))));
            if (fieldsId.size() > 1) {
                for (int i = 1; i < fieldsId.size(); i++) {
                    builder.append(", ").append(getShortFieldName(fieldMap.get(fieldsId.get(i))));
                }
            }
        }
        return builder.toString();
    }

    /**
     * Getting sort field name
     */
    private String getSortField(final Sort sort) {
        final Field field = query.getFields().get(sort.getId());
        if (sort.getType() == null) {
            return getShortFieldName(field);
        }
        return String.format("%s %s", getShortFieldName(field), sort.getType().getValue());
    }

    /**
     * Build partition group
     */
    private String getPartitionGroups(int groupsId) {
        if (groupsId <= 0 || query.getGroups() == null || query.getGroups().isEmpty() || !query.getGroups().containsKey(groupsId)) {
            return "";
        }
        return String.format("%s %s", QueryConstants.PARTITION_BY.getValue(), getGroupFields(query.getGroups().get(groupsId).getFieldsId()));
    }
}