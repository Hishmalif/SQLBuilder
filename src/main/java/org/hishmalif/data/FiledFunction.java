package org.hishmalif.data;

import lombok.*;
import org.hishmalif.data.enums.FunctionName;
import org.hishmalif.data.enums.FunctionType;

import java.util.List;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FiledFunction {
    private final FunctionType type;
    private final FunctionName name;
    private int groupsId;
    private List<Integer> sortsId;
}