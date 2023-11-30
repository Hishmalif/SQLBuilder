package org.hishmalif.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Group {
    private final int id;
    private List<Integer> fieldsId;
}