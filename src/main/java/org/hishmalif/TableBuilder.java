package org.hishmalif;

import org.hishmalif.data.enums.QueryConstants;
import org.hishmalif.data.Table;

public class TableBuilder {
    private final boolean writeSchema;

    public TableBuilder( boolean writeSchema) {
        this.writeSchema = writeSchema;
    }

    public String getTable(Table table) {
        String tableQuery;

        tableQuery = String.format("%s %s %s %s %s", // Должно быть условие на сборку ON и условий
                QueryConstants.FROM.getValue(), getTableName(table),
                QueryConstants.AS.getValue(), getAliasName(table), getCondition(table));

        return tableQuery;
    }

    public String getAliasName(Table table) {
        return table.getAlias() != null && !table.getAlias().equals("") ? table.getAlias() : table.getName();
    }

    private String getTableName(Table table) {
        if (table.getSchema() != null && writeSchema) {
            return String.format("%s.%s", table.getSchema(), table.getName());
        } else {
            return table.getName();
        }
    }

    private String getCondition(Table table) { //TODO Смотреть position и condition

        return String.format("%s ", QueryConstants.ON.getValue());

    }
}