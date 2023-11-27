package org.hishmalif.data;

import org.hishmalif.data.enums.FunctionName;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.SQLBuilder;
import lombok.Data;

import java.util.List;

@Data
public class Function {
    private final FunctionType type;
    private final FunctionName name;
    private int groupsId;
    private List<Integer> sortsId;

    public String getFunctionName(final Query query, final String filedName) {
        if (getType().equals(FunctionType.WINDOW)) {
            return String.format("%s over (%s%s)", getFunctionName(filedName), getGroups(query), getSorts(query));
        }
        return getFunctionName(filedName);
    }

    /**
     * Build function name. (With field name)
     */
    private String getFunctionName(final String filedName) {
        if (getName().isNeedField()) {
            return String.format("%s(%s)", getName().getValue(), filedName);
        } else {
            return String.format("%s()", getName().getValue());
        }
    }

    /**
     * Build partition by
     */
    private String getGroups(final Query query) {
        if (groupsId == 0 || query.getGroups() == null || query.getGroups().isEmpty()
                || !query.getGroups().containsKey(groupsId)) {
            return "";
        }
        return String.format("partition by %s", query.getGroups().get(groupsId).getListFields(query));
    }

    private String getSorts(final Query query) {
        final SQLBuilder builder = new SQLBuilder(query);

        if (sortsId == null || query.getSorts() == null || query.getSorts().isEmpty()) {
            return "";
        }
        return String.format("order by %s", builder.buildSort(sortsId));
    }
}