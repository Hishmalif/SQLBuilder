import org.hishmalif.QueryBuilder;
import org.hishmalif.data.*;
import org.junit.jupiter.api.*;
import org.hishmalif.data.enums.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Field fieldTwo = new Field(2, "name", 2, 1);
        fieldTwo.setAlias("shop_name");
        Field fieldThree = new Field(3, "amount", 3, 2);
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
        Assertions.assertEquals("order by d.shop_id desc, s.name asc", result);
    }

    @Test
    @DisplayName("Test build sort for one field")
    protected void testBuildSortForOneField() {
        //Arrange
        final List<Integer> sortsId = List.of(queryOne.getSorts().get(1).getId());
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        Assertions.assertEquals("order by d.shop_id desc", result);
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
        Assertions.assertEquals("order by d.shop_id, s.name", result);
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
        Assertions.assertEquals("order by d.shop_id", result);
    }

    @Test
    @DisplayName("Test build sort without sort list")
    protected void testBuildSortWithoutList() {
        //Arrange
        final List<Integer> sortsId = new ArrayList<>();
        // Act
        final String result = builder.getSorts(sortsId);
        // Assert
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Getting a short name from a field")
    public void getShortNameFromFiled() {
        //Arrange

        // Act

        // Assert

    }

    @Test
    @DisplayName("Getting sorting list with two fields")
    public void getSortListWithTwoFields() {

    }
}