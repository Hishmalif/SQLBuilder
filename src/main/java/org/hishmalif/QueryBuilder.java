package org.hishmalif;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.BuildType;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.data.enums.QueryConstants;

import java.util.List;

public class QueryBuilder implements Builder {
    protected Query query;
    private final BuildType type;
    private final boolean writeSchema;

    public QueryBuilder(final Query query, final BuildType type, final boolean writeSchema) {
        this.query = query;
        this.type = type;
        this.writeSchema = writeSchema;
    }

    public String buildQuery(final Query query) {
        this.query = query;
        String queryString = null;
        try {
            if (type != null && type.equals(BuildType.SELECT)) {
                System.out.println("hi");
                queryString = "hi";
            } else if (type != null && type.equals(BuildType.UPDATE)) {
                queryString = "update ..."; // TODO in future
            } else if (type != null && type.equals(BuildType.DELETE)) {
                queryString = "delete ..."; // TODO in future
            } else {
                throw new BuilderException();
            }
        } catch (BuilderException e) {
            e.printStackTrace();
        }
        return queryString;
    } //TODO сделать норм

    public String getShortFieldName(final int id) {
        final Field field = query.getFields().get(id);
        return String.format("%s.%s", getShortTableName(field.getTableId()), field.getName());
    }

//    public String getLongFieldName(final int id) {
//        final Field field = query.getFields().get(id);
//        return String.format("%s%s", getFunction(query), getAlias());
//    }


    public String getShortTableName(final int id) {
        final Table table = query.getTables().get(id);
        return table.getAlias() != null ? table.getAlias() : table.getName();
    }

    /**
     * Getting sort list
     */


    public String getGroupFields(final List<Integer> fieldsId) {
        final StringBuilder builder = new StringBuilder();

        if (fieldsId.size() > 1) {
            builder.append(fieldsId.get(0));
            for (int i = 1; i < fieldsId.size(); i++) {
                builder.append(",").append(getShortFieldName(query.getFields().get(i).getId())); //TODO Подумать над тем что бы передавать объект
            }
        } else if (fieldsId.size() == 1) {
            builder.append(getShortFieldName(query.getFields().get(0).getId())); //TODO Посмотреть тоже
        }

        return builder.toString();
    }

//    private String getFunctionName(final Function function) { //TODO Сделать норм
//        if (function.getType().equals(FunctionType.WINDOW)) {
//            return String.format("%s over (%s%s)", getFunctionName(filedName),
//                    getPartitionGroups(function.getGroupsId()),
//                    getSorts(function.getSortsId()));
//        }
//        return getFunctionName(filedName);
//    }

    public String getSorts(final List<Integer> sorts) {
        final StringBuilder builder = new StringBuilder();

        if (sorts == null || sorts.size() == 0 || query.getSorts() == null
                || query.getSorts().isEmpty()) {
            return "";
        }
        Sort sort = query.getSorts().get(sorts.get(0));
        builder.append(String.format("%s %s", QueryConstants.ORDER_BY.getValue(), getSortField(sort)));
        for (int i = 1; i < sorts.size(); i++) {
            sort = query.getSorts().get(sorts.get(i));
            builder.append(String.format(", %s", getSortField(sort)));
        }
        return builder.toString();
    }

    private String getSortField(final Sort sort) {
        if (sort.getType() == null) {
            return getShortFieldName(sort.getFieldsId());
        }
        return String.format("%s %s", getShortFieldName(sort.getFieldsId()), sort.getType().getValue());
    }

    /**
     * Build partition group
     */
    private String getPartitionGroups(int groupsId) {
        if (groupsId == 0 || query.getGroups() == null || query.getGroups().isEmpty()
                || !query.getGroups().containsKey(groupsId)) {
            return "";
        }
        return String.format("partition by %s", getGroupFields(query.getGroups().get(groupsId).getFieldsId()));
    }
}