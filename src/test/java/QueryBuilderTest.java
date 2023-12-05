import org.hishmalif.Builder;
import org.hishmalif.data.*;
import org.junit.jupiter.api.*;
import org.hishmalif.data.enums.*;
import org.hishmalif.QueryBuilder;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QueryBuilderTest {
    private Query queryOne;
    private QueryBuilder builder;

    @BeforeEach
    protected void init() {
        // Init query
        queryOne = new Query(1);
        queryOne.setIntersectType(IntersectType.NONE);
        queryOne.setAllFieldsAfter(true);

        // Init tables
        final Map<Integer, Table> tableMap = new HashMap<>();
        Table tableOne = new Table(1, "shops");
        tableOne.setSchema("public");
        tableOne.setPosition(1);
        tableOne.setAlias("s");
        Table tableTwo = new Table(2, "documents");
        tableTwo.setSchema("public");
        tableTwo.setPosition(2);
        tableTwo.setAlias("d");
        tableTwo.setJoinCondition(1); //TODO Is not work now
        tableMap.put(tableOne.getId(), tableOne);
        tableMap.put(tableTwo.getId(), tableTwo);
        queryOne.setTables(tableMap);

        // Init fields
        final Map<Integer, Field> fieldMap = new HashMap<>();
        Field fieldOne = new Field(1, "shop_id", 1, 2);
        Field fieldTwo = new Field(2, "name", 3, 1);
        fieldTwo.setAlias("shop_name");
        Field fieldThree = new Field(3, "amount", 2, 2);
        fieldThree.setAlias("sum_amount");
        fieldThree.setFunction(new Function(FunctionType.AGGREGATE, FunctionName.SUM));
        fieldMap.put(fieldOne.getId(), fieldOne);
        fieldMap.put(fieldTwo.getId(), fieldTwo);
        fieldMap.put(fieldThree.getId(), fieldThree);
        queryOne.setFields(fieldMap);

        // Init sorts
        final Map<Integer, Sort> sortMap = new HashMap<>();
        Sort sortOne = new Sort(1, 1);
        sortOne.setType(SortType.DESC);
        Sort sortTwo = new Sort(2, 2);
        sortTwo.setType(SortType.ASC);
        sortMap.put(sortOne.getId(), sortOne);
        sortMap.put(sortTwo.getId(), sortTwo);
        queryOne.setSorts(sortMap);

        // Init group
        final Map<Integer, Group> groupMap = new HashMap<>();
        Group groupOne = new Group(1);
        groupOne.setFieldsId(List.of(1));
        groupMap.put(groupOne.getId(), groupOne);
        queryOne.setGroups(groupMap);

        // Init builder
        builder = new QueryBuilder(queryOne, BuildType.SELECT, true);
    }

    @AfterEach
    protected void clear() {
        queryOne.getTables().clear();
        queryOne.getFields().clear();
        queryOne.getSorts().clear();
    }

    @Test
    @DisplayName("Test build sorts for two fields")
    protected void testBuildSortsForTwoField() {
        //Arrange
        final List<Integer> sortsId = queryOne.getSorts().keySet().stream().toList();
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        assertEquals("order by d.shop_id desc, s.name asc", result);
    }

    @Test
    @DisplayName("Test build sort for one field")
    protected void testBuildSortForOneField() {
        //Arrange
        final List<Integer> sortsId = List.of(queryOne.getSorts().get(1).getId());
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        assertEquals("order by d.shop_id desc", result);
    }

    @Test
    @DisplayName("Test build sorts for two fields without type")
    protected void testBuildSortsForTwoFieldWithoutType() {
        //Arrange
        queryOne.getSorts().values().forEach(sort -> sort.setType(null));
        final List<Integer> sortsId = queryOne.getSorts().keySet().stream().toList();
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        assertEquals("order by d.shop_id, s.name", result);
    }

    @Test
    @DisplayName("Test build sort for one field without type")
    protected void testBuildSortsForOneFieldWithoutType() {
        //Arrange
        final List<Integer> sortsId = queryOne.getSorts().values().stream()
                .filter(sort -> sort.getId() == 1)
                .map(sort -> {
                    sort.setType(null);
                    return sort.getId();
                })
                .toList();
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        assertEquals("order by d.shop_id", result);
    }

    @Test
    @DisplayName("Test build sort without sort list")
    protected void testBuildSortWithoutList() {
        //Arrange
        final List<Integer> sortsId = new ArrayList<>();
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        assertEquals("", result);
    }

    @Test
    @DisplayName("Test a short name from a field")
    public void testShortNameFromFiled() {
        //Act
        String shortName = builder.getShortFieldName(queryOne.getFields().get(1));
        // Assert
        assertEquals("d.shop_id", shortName);
    }

    @Test
    @DisplayName("Test a short name from a incorrect field")
    public void testShortNameFromIncorrectField() {
        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                builder.getShortFieldName(queryOne.getFields().get(161101)));
        // Assert
        assertEquals("Incorrect field id!", exception.getMessage());
    }

    @Test
    @DisplayName("Test building select query object")
    public void testBuildSelectQuery() {
        queryOne.getFields().put(4, new Field(4, "*", 4, 1));
        queryOne.getFields().get(4).setFunction(new Function(FunctionType.WINDOW, FunctionName.COUNT));
        queryOne.getFields().get(4).getFunction().setSortsId(List.of(1));
        queryOne.getFields().get(4).getFunction().setGroupsId(1);
        System.out.println(builder.build());
    }
}