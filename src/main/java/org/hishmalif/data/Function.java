package org.hishmalif.data;

import org.hishmalif.data.enums.FunctionName;
import org.hishmalif.data.enums.FunctionType;
import org.hishmalif.SQLBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Function {
    private final FunctionType type;
    private final FunctionName name;
    private int groupsId;
    private List<Integer> sortsId;

//    public String getFunctionName(final Query query, final String filedName) {
//        if (getType().equals(FunctionType.WINDOW)) {
//            return String.format("%s over (%s%s)", getFunctionName(filedName), getGroups(query), getSorts(query));
//        }
//        return getFunctionName(filedName);
//    }

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
}