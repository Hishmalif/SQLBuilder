package org.hishmalif.builders;

import org.hishmalif.data.*;
import org.hishmalif.data.enums.BuildType;
import org.hishmalif.data.enums.QueryConstants;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public class QueryBuilder implements Builder {
    protected Query query;
    private final BuildType type;
    private final EntityBuilder entityBuilder;
    private final StringBuilder queryString = new StringBuilder();

    public QueryBuilder(Query query, BuildType type) {
        this.query = query;
        this.type = type;
        entityBuilder = new EntityBuilderImpl(query);
    }

    @Override
    public String build() {
        if (type.equals(BuildType.SELECT)) {
            return selectQuery();
        } else if (type.equals(BuildType.UPDATE)) {
            return "update in future"; //TODO in future
        } else if (type.equals(BuildType.DELETE)) {
            return "delete in future"; //TODO in future
        } else {
            throw new IllegalArgumentException("Incorrect type of build");
        }
    }

    private String selectQuery() {
        queryString.append(BuildType.SELECT).append(" ");
        queryString.append(getListSeparated(query.getFields().values().parallelStream()
                .filter(f -> f.getPosition() > 0)
                .sorted(Comparator.comparingInt(Field::getPosition))
                .map(f -> entityBuilder.getFullFieldName(f.getId()))
                .toList())).append("\n");
        queryString.append(QueryConstants.FROM).append(" ");
        query.getTables().values().parallelStream().forEach(t -> {
            if (t.getPosition() == 0) {
                queryString.append(entityBuilder.getFullTableName(t.getId())).append("\n");
            } else if (t.getPosition() > 0) {
                queryString.append(joinTables(t)).append("\n");//TODO Join where causes
            }
        });
        queryString.append(getWhereCondition());
        return queryString.toString();
    }

    private String joinTables(Table table) {
        return QueryConstants.JOIN + " " +
                entityBuilder.getFullTableName(table.getId()) + " " +
                QueryConstants.ON + " " +
                getConditions(table.getJoinCondition()) + "\n";
    }

    private String getConditions(int id) {
        if (query.getConditionsList() != null && !query.getConditionsList().isEmpty()) {

        }
        return null;
    }

    private String getConditions() {
        if (query.getConditionsList() != null && !query.getConditionsList().isEmpty()) {

        }
        return null;
    }

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