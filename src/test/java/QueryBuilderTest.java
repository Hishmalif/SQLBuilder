import org.hishmalif.builders.*;
import org.hishmalif.data.*;
import org.junit.jupiter.api.*;
import org.hishmalif.data.enums.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class QueryBuilderTest {
    private Query queryOne;
    private EntityBuilder entityBuilder;

    @BeforeEach
    protected void init() {
        //  Init tables
        Map<Integer, Table> tableMap = new HashMap<>();
        Table tableOne = new Table(1, "shops").toBuilder()
                .schema("public")
                .position(1)
                .alias("s")
                .build();
        Table tableTwo = new Table(2, "documents").toBuilder()
                .schema("public")
                .position(2)
                .alias("d")
                .joinCondition(1) //TODO Is not work now
                .build();
        tableMap.put(tableOne.getId(), tableOne);
        tableMap.put(tableTwo.getId(), tableTwo);

        //  Init fields
        Map<Integer, Field> fieldMap = new HashMap<>();
        Field fieldOne = new Field(1, "shop_id", 1).toBuilder()
                .position(1)
                .build();
        Field fieldTwo = new Field(2, "name", 2).toBuilder()
                .position(2)
                .alias("shop_name")
                .build();
        Field fieldThree = new Field(3, "amount", 2).toBuilder()
                .position(3)
                .alias("sum_amount")
                .filedFunction(new FiledFunction(FunctionType.AGGREGATE, FunctionName.SUM))
                .build();
        fieldMap.put(fieldOne.getId(), fieldOne);
        fieldMap.put(fieldTwo.getId(), fieldTwo);
        fieldMap.put(fieldThree.getId(), fieldThree);

        //   Init sorts
        Map<Integer, Sort> sortMap = new HashMap<>();
        Sort sortOne = new Sort(1, 1).toBuilder()
                .build();
        Sort sortTwo = new Sort(2, 2).toBuilder()
                .type(SortType.DESC)
                .build();
        sortMap.put(sortOne.getId(), sortOne);
        sortMap.put(sortTwo.getId(), sortTwo);

        // Init group
        Map<Integer, Group> groupMap = new HashMap<>();
        Group groupOne = new Group(1).toBuilder()
                .fieldsId(List.of(1))
                .build();
        groupMap.put(groupOne.getId(), groupOne);

        // Init conditions
        List<ConditionList> conditionLists = new ArrayList<>();
        Condition condition = new Condition(1,"=","shop_id");
        Condition condition1 = new Condition(2,"=","amount");
        Condition condition2 = new Condition(3,"=","amount");
        ConditionList conditionList = new ConditionList(ConditionType.JOIN);
        conditionList.getConditions().add(condition);
        conditionList.getConditions().add(condition1);
        conditionList.getConditions().add(condition2);
        conditionLists.add(conditionList);

        // Init query
        queryOne = new Query(1).toBuilder()
                .allFieldsAfter(true)
                .intersectType(IntersectType.NONE)
                .tables(tableMap)
                .fields(fieldMap)
                .sorts(sortMap)
                .groups(groupMap)
                .conditionsList(conditionLists)
                .build();

        // Init builders
        entityBuilder = new EntityBuilderImpl(queryOne);
    }

    @AfterEach
    protected void clear() {
        queryOne.getTables().clear();
        queryOne.getFields().clear();
        queryOne.getSorts().clear();
    }

    @Test
    @DisplayName("TestShortTableName")
    public void testShortTableName() {
        assertEquals(entityBuilder.getTableName(1), "s");
    }

    @Test
    @DisplayName("TestLongTableName")
    public void testLongTableName() {
        assertEquals(entityBuilder.getFullTableName(2), "\"public\".\"documents\" AS d");
    }

    @Test
    @DisplayName("TestSortWithOneField")
    public void testSortWithOneField() {
        assertEquals(entityBuilder.getSorts(List.of(1)), "ORDER_BY s.\"shop_id\"");
    }

    @Test
    @DisplayName("TestSortWithTwoField")
    public void testSortWithTwoField() {
        assertEquals(entityBuilder.getSorts(List.of(1,2)), "ORDER_BY s.\"shop_id\", d.\"name\" DESC");
    }

    @Test
    @DisplayName("TestShortField")
    public void testShortField() {
        assertEquals(entityBuilder.getFieldName(2), "d.\"name\"");
    }

//    @Test
//    @DisplayName("TestLongFieldName")
//    public void testLongFieldName() {
//        System.out.println(entityBuilder.getFullFieldName(1));
//    }
}