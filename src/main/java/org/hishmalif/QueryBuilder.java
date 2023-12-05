package org.hishmalif;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.BuildType;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.data.enums.QueryConstants;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
            queryString.append(getListSeparated(query.getFields().values().stream()
                    .filter(field -> field.getPosition() > 0)
                    .sorted(Comparator.comparingInt(Field::getPosition))
                    .map(this::getFullFieldName)
                    .toList()));
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

    /**
     * Retrieves the full field name based on the given Field object.
     *
     * @param field The Field object containing the field information.
     * @return The full field name.
     */
    public String getFullFieldName(final Field field) {
        String fullName;
        if (field.getFunction() == null) {
            fullName = getShortFieldName(field);
        } else {
            fullName = getLongFieldName(field);
        }
        return fullName + field.getAlias();
    }

    /**
     * Retrieves the short field name based on the given Field object.
     *
     * @param field The Field object containing the field information.
     * @return The short field name.
     * @throws IllegalArgumentException if the field id is incorrect or if the field is null.
     */
    public String getShortFieldName(final Field field) {
        if (field != null && query.getFields().containsValue(field)) {
            return String.format("%s.%s", getShortTableName(field.getTableId()), field.getName());
        }
        throw new IllegalArgumentException("Incorrect field id!");
    }

    private String getLongFieldName(final Field field) {
        final Function function = field.getFunction();

        if (function.getType().equals(FunctionType.WINDOW)) {
            return String.format("%s %s (%s %s)", getFunction(field),
                    QueryConstants.OVER.getValue(),
                    getPartitionGroups(function.getGroupsId()),
                    getSorts(function.getSortsId()));
        }
        return getFunction(field);
    }

    /**
     * Retrieves the sort fields as a formatted string.
     *
     * @param sorts The list of sort IDs.
     * @return The formatted string of sort fields.
     */
    public String getSorts(final List<Integer> sorts) {
        final Map<Integer, Sort> sortMap = query.getSorts();

        if (sorts == null || sorts.size() == 0 || sortMap == null || sortMap.isEmpty()) {
            return "";
        }
        return String.format("%s %s", QueryConstants.ORDER_BY.getValue(),
                getListSeparated(sortMap.values().parallelStream()
                        .filter(sort -> sorts.contains(sort.getId()))
                        .map(this::getSortField)
                        .toList()));
    }

    public String getShortTableName(final int id) { //TODO Сделать проверку на корректность id
        final Table table = query.getTables().get(id);
        return table.getAlias() != null ? table.getAlias() : table.getName();
    }

    /**
     * Retrieves the group fields as a comma-separated string.
     *
     * @param fieldsId The list of field IDs.
     * @return The comma-separated string of group fields.
     * @throws IllegalArgumentException if the list of fields is empty.
     */
    private String getGroupFields(final List<Integer> fieldsId) {
        final Map<Integer, Field> fieldMap = query.getFields();

        if (fieldsId.size() > 0 && fieldMap != null && !fieldMap.isEmpty()) {
            return getListSeparated(fieldMap.values().parallelStream()
                    .filter(field -> fieldsId.contains(field.getId()))
                    .map(this::getShortFieldName)
                    .toList());
        } else {
            throw new IllegalArgumentException("List of fields is empty!");
        }
    }

    /**
     * Getting sort field name
     * Retrieves the sort field name based on the given Sort object.
     *
     * @param sort The Sort object containing the sort information.
     * @return The sort field name.
     */
    private String getSortField(final Sort sort) {
        final Field field = query.getFields().get(sort.getId());

        if (sort.getType() == null) {
            return getShortFieldName(field);
        }
        return String.format("%s %s", getShortFieldName(field), sort.getType().getValue());
    }

    /**
     * Build partition group.
     *
     * @param group The group number.
     * @return The partition group string.
     */
    private String getPartitionGroups(final int group) {
        final Map<Integer, Group> groups = query.getGroups();

        if (group <= 0 || groups == null || groups.isEmpty() || !groups.containsKey(group)) {
            return "";
        }
        return String.format("%s %s", QueryConstants.PARTITION_BY.getValue(),
                getGroupFields(groups.get(group).getFieldsId()));
    }

    /**
     * Returns a comma-separated string representation of the elements in the given list.
     *
     * @param list The list of strings.
     * @return The comma-separated string.
     */
    private String getListSeparated(final List<String> list) {
        StringJoiner joiner = new StringJoiner(", ");
        list.forEach(joiner::add);
        return joiner.toString();
    }

    /**
     * Returns the function as a string representation.
     *
     * @param field The field object.
     * @return The function as a string.
     */
    private String getFunction(final Field field) {
        final Function function = field.getFunction();

        if (function.getName().isNeedField()) {
            return String.format("%s(%s)", function.getName().getValue(), getShortFieldName(field));
        }
        return String.format("%s()", function.getName().getValue());
    }
}