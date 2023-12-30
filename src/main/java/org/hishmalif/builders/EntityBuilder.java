package org.hishmalif.builders;

import org.hishmalif.data.enums.ConditionType;

import java.util.List;

public interface EntityBuilder {
    String getTableName(int id);
    String getFullTableName(int id);
    String getFieldName(int id);
    String getFullFieldName(int id);
    String getSorts(List<Integer> sorts);
    String getGroups(List<Integer> fieldsId);
    String getConditions(int id);

}