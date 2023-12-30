package org.hishmalif.builders;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.FunctionName;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.data.enums.QueryConstants;

import java.util.Map;
import java.util.List;

public class EntityBuilderImpl implements EntityBuilder {
    private final Map<Integer, Table> tables;
    private final Map<Integer, Sort> sorts;
    private final Map<Integer, Group> groups;
    private final Map<Integer, Field> fields;
    private final List<ConditionList> conditions;

    public EntityBuilderImpl(Query query) {
        tables = query.getTables();
        sorts = query.getSorts();
        groups = query.getGroups();
        fields = query.getFields();
        conditions = query.getConditionsList();
    }

    /**
     * Retrieves the short table name based on the given table id.
     *
     * @param id The table id.
     * @return The short table name.
     * @throws IllegalArgumentException if the list of tables is empty or if the table id is incorrect.
     */
    @Override
    public String getTableName(int id) {
        if (id > 0 && tables != null && !tables.isEmpty() && tables.containsKey(id)) {
            return tables.get(id).getAlias();
        } else if (tables == null || tables.isEmpty()) {
            throw new IllegalArgumentException("List of tables is empty!");
        } else {
            throw new IllegalArgumentException("Incorrect table id!");
        }
    }

    /**
     * Returns the full table name as a string representation.
     *
     * @param id The ID of the table.
     * @return The full table name.
     * @throws IllegalArgumentException If the table ID is incorrect or if the list of tables is empty.
     */
    @Override
    public String getFullTableName(int id) {
        if (id > 0 && tables != null && !tables.isEmpty() && tables.containsKey(id)) {
            Table table = tables.get(id);
            String schema = table.getSchema() != null ? "\"" + table.getSchema() + "\"." : "";
            QueryConstants asValue = QueryConstants.AS;
            String tableName = getTableName(id);

            return String.format("%s\"%s\" %s %s", schema, table.getName(), asValue, tableName);
        } else if (tables == null || tables.isEmpty()) {
            throw new IllegalArgumentException("List of tables is empty!");
        } else {
            throw new IllegalArgumentException("Incorrect table id!");
        }
    }

    /**
     * Returns the name of the field with the given id.
     *
     * @param id The id of the field.
     * @return The name of the field.
     * @throws IllegalArgumentException if the list of fields is empty or the field id is incorrect.
     */
    @Override
    public String getFieldName(int id) {
        if (fields != null && !fields.isEmpty() && fields.containsKey(id)) {
            Field field = fields.get(id);
            return field.getName(getTableName(field.getTableId()));
        } else if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("List of fields is empty!");
        } else {
            throw new IllegalArgumentException("Incorrect field id!");
        }
    }

    /**
     * Retrieves the full field name based on the given field ID.
     * If the field has a function, it retrieves the short field name.
     * Otherwise, it retrieves the long field name.
     *
     * @param id The field ID.
     * @return The full field name.
     * @throws IllegalArgumentException If the list of fields is empty or if the field ID is incorrect.
     */
    @Override
    public String getFullFieldName(int id) {
        String fullName = "";
        if (fields != null && !fields.isEmpty() && fields.containsKey(id)) {
            Field field = fields.get(id);
            FiledFunction function = field.getFiledFunction();

            if (field.getFiledFunction() != null) {
                fullName = getFieldName(field.getId());
            } else {
                fullName = getLongFieldName(field);
            }
            return fullName + field.getAlias();
        } else if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("List of fields is empty!");
        } else {
            throw new IllegalArgumentException("Incorrect field id!");
        }
    }

    /**
     * Retrieves the sort fields as a formatted string.
     *
     * @param sorts The list of sort IDs.
     * @return The formatted string of sort fields.
     */
    @Override
    public String getSorts(List<Integer> sorts) {
        if (sorts != null && !sorts.isEmpty() && this.sorts != null && !this.sorts.isEmpty()) {
            return String.format("%s %s", QueryConstants.ORDER_BY,
                    QueryBuilder.getListSeparated(this.sorts.values().parallelStream()
                            .filter(sort -> sorts.contains(sort.getId()))
                            .map(this::getSortField)
                            .toList()));
        } else if (sorts == null || sorts.size() == 0) {
            throw new IllegalArgumentException("Incorrect integer list of sort!");
        } else {
            throw new IllegalArgumentException("Sort list is empty!");
        }
    }

    /**
     * Retrieves the names of the groups based on the provided list of field IDs.
     *
     * @param fieldsId the list of field IDs
     * @return the names of the groups separated by commas
     * @throws IllegalArgumentException if the list of fields is empty
     */
    @Override
    public String getGroups(List<Integer> fieldsId) {
        if (fieldsId != null && !fieldsId.isEmpty() && fields != null && !fields.isEmpty()) {
            return QueryBuilder.getListSeparated(fields.values().parallelStream()
                    .filter(field -> fieldsId.contains(field.getId()))
                    .map(field -> getFieldName(field.getId()))
                    .toList());
        } else {
            throw new IllegalArgumentException("List of fields is empty!");
        }
    }

    @Override
    public String getConditions(int id) {
        if (conditions != null && !conditions.isEmpty() && conditions.containsKey(id)) {
            Condition condition = conditions.get(id);

            return conditions.get(id).toString();//TODO!!!FIXME
        } else if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("List of conditions is empty!");
        } else {
            throw new IllegalArgumentException("Incorrect condition id!");
        }
    }

    /**
     * Getting sort field name
     * Retrieves the sort field name based on the given Sort object.
     *
     * @param sort The Sort object containing the sort information.
     * @return The sort field name.
     */
    private String getSortField(Sort sort) {
        String fieldName = getFieldName(sort.getFieldId());

        if (sort.getType() == null) {
            return fieldName;
        }
        return String.format("%s %s", fieldName, sort.getType());
    }

    /**
     * Returns a long field name based on the given field.
     * If the field's function type is WINDOW, the field name will include additional information.
     *
     * @param field The field for which to generate the long field name.
     * @return The long field name.
     */
    private String getLongFieldName(final Field field) {
        FiledFunction function = field.getFiledFunction();

        if (function.getType().equals(FunctionType.WINDOW)) {
            return String.format("%s %s (%s %s)", getFunction(field),
                    QueryConstants.OVER,
                    getPartitionGroups(function.getGroupsId()),
                    getSorts(function.getSortsId()));
        }
        return getFunction(field);
    }

    /**
     * Gets the partition groups based on the provided id.
     *
     * @param id The id of the group.
     * @return The partition groups.
     * @throws IllegalArgumentException If the id is invalid or the groups list is empty.
     */
    private String getPartitionGroups(final int id) {
        if (id > 0 && groups != null && !groups.isEmpty() && groups.containsKey(id)) {
            return String.format("%s %s", QueryConstants.PARTITION_BY,
                    getGroups(groups.get(id).getFieldsId()));
        } else if (id <= 0 || (groups != null && !groups.containsKey(id))) {
            throw new IllegalArgumentException("Incorrect group id!");
        } else {
            throw new IllegalArgumentException("Groups list is empty!");
        }
    }

    /**
     * Returns the function as a string representation.
     *
     * @param field The field object.
     * @return The function as a string.
     */
    private String getFunction(Field field) {
        FunctionName functionName = field.getFiledFunction().getName();
        String tableName = getTableName(field.getTableId());

        return String.format("%s(%s)", functionName, functionName.isNeedField() ? field.getName(tableName) : "");
    }
}