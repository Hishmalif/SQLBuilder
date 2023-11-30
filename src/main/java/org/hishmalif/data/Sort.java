package org.hishmalif.data;

import lombok.Data;
import org.hishmalif.data.enums.SortType;

@Data
public class Sort {
    private final int id;
    private final int fieldsId;
    private SortType type;
}