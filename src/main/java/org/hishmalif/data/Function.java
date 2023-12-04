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
}