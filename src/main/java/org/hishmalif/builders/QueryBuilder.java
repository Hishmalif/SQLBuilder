package org.hishmalif.builders;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.BuildType;

import java.util.List;
import java.util.StringJoiner;

public class QueryBuilder implements Builder {
    protected Query query;
    private final BuildType type;
    private final boolean writeSchema;
    private final EntityBuilder entityBuilder;

    public QueryBuilder(Query query, BuildType type, boolean writeSchema) {
        this.query = query;
        this.type = type;
        this.writeSchema = writeSchema;
        entityBuilder = new EntityBuilderImpl(query);
    }

    @Override
    public String build() {
//        final StringBuilder queryString = new StringBuilder();
//
//        if (type.equals(BuildType.SELECT)) {
//            queryString.append(BuildType.SELECT.getValue()).append(" ");
//            queryString.append(getListSeparated(query.getFields().values().stream()
//                    .filter(field -> field.getPosition() > 0)
//                    .sorted(Comparator.comparingInt(Field::getPosition))
//                    .map(this::getFullFieldName)
//                    .toList()));
//            queryString.append("\n").append(QueryConstants.FROM.getValue()).append(" ");
//            query.getTables().values().forEach(table -> queryString.append(getLongTableName(table.getId())).append(" "));
//            queryString.append("\n").append(QueryConstants.WHERE.getValue()).append(" ");
//            queryString.append(getListSeparated(query.getFilters().values().stream()
//                    .filter(filter -> filter.getPosition() > 0)
//                    .sorted(Comparator.comparingInt(Filter::getPosition))
//                    .map(this::getFullFilter)
//                    .toList()));
//            queryString.append("\n").append(QueryConstants.ORDER_BY.getValue()).append(" ");
//            queryString.append(getListSeparated(query.getSorts().values().stream()
//                    .filter(sort -> sort.getPosition() > 0)
//                    .sorted(Comparator.comparingInt(Sort::getPosition))
//                    .map(this::getFullSort)
//                    .toList()));
//        } else if (type.equals(BuildType.UPDATE)) {
//            queryString.append(BuildType.UPDATE.getValue()).append(" "); //TODO in future
//
//        } else if (type.equals(BuildType.DELETE)) {
//            queryString.append(BuildType.DELETE.getValue()).append(" "); //TODO in future
//
//        } else {
//            throw new IllegalArgumentException("Incorrect type of build");
//        }
//        return queryString.toString();
        return null;
    } //TODO сделать норм


    /**
     * Returns a comma-separated string representation of the elements in the given list.
     *
     * @param list The list of strings.
     * @return The comma-separated string.
     */
    protected static String getListSeparated(List<String> list) {
        StringJoiner joiner = new StringJoiner(", ");
        list.forEach(joiner::add);
        return joiner.toString();
    }
}