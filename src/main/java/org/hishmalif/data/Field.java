package org.hishmalif.data;

import lombok.*;
import org.hishmalif.data.enums.QueryConstants;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Field {
    private final int id;
    private final String name;
    private final int tableId;
    private String alias;
    private int position;
    private FiledFunction filedFunction;

    /**
     * Returns the alias of the object, if not null.
     *
     * @return The alias of the object, or an empty string if the alias is null.
     */
    public String getAlias() {
        if (alias == null) {
            return "";
        }
        return String.format(" %s %s ", QueryConstants.AS, alias);
    }

    public String getName(final String table) {
        return String.format("%s.\"%s\"", table, name);
    }
}