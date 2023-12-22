package org.hishmalif.data;

import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {
    private final int id;
    private List<Integer> fieldsId;
}