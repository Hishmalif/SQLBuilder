package org.hishmalif.builders;

import java.util.List;

public interface EntityBuilder {
    String getTableName(int id);
    String getFullTableName(int id);
    String getFieldName(int id);
    String getFullFieldName(int id);
    String getSorts(List<Integer> sorts);
    String getGroup(List<Integer> fieldsId);
}