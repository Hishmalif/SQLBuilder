package org.hishmalif.data;

import lombok.*;
import org.hishmalif.data.enums.SortType;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sort {
    private final int id;
    private final int fieldId;
    private SortType type;
}