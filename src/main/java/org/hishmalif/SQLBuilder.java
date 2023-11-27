package org.hishmalif;

import org.hishmalif.data.Query;
import org.hishmalif.data.Sort;
import org.hishmalif.data.enums.QueryConstants;

import java.util.List;

public class SQLBuilder {
    private final Query query;

    public SQLBuilder(final Query query) {
        this.query = query;
    }


    /**
     * Get sorting list
     */
    public String buildSort(List<Integer> sorts) {
        StringBuilder builder = new StringBuilder();

        if (!sorts.isEmpty() || !query.getSorts().isEmpty()) {
            Sort sort = query.getSorts().get(sorts.get(0));
            builder.append(String.format("%s %s", QueryConstants.ORDER_BY.getValue(), sort.getField(query)));

            for (int i = 1; i < sorts.size(); i++) {
                sort = query.getSorts().get(sorts.get(i));
                builder.append(String.format(", %s", sort.getField(query)));
            }
        }
        return builder.toString();
    }
}