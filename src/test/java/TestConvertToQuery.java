import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hishmalif.data.*;
import org.hishmalif.data.enums.FunctionName;
import org.hishmalif.data.enums.FunctionType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConvertToQuery {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final List<Integer> groupOneFieldsId = new ArrayList<>();
    private final List<Integer> groupTwoFieldsId = new ArrayList<>();
    private final Map<Integer, Table> tableMap = new HashMap<>();
    private final Map<Integer, Field> fieldMap = new HashMap<>();
    private final Map<Integer, Group> groupMap = new HashMap<>();
    private BuildObject object;
    private Query query;
    private Field fieldOne;
    private Field fieldTwo;
    private Function functionOne;
    private Function functionTwo;
    private Sort sortOne;
    private Sort sortTwo;
    private Table tableOne;
    private Table tableTwo;
    private Table tableThree;
    private Condition conditionOne;
    private Condition conditionTwo;
    private Group groupOne;
    private Group groupTwo;

    @Before
    public void init() {
        // init tables
        tableOne = new Table(1, "slot");
        tableOne.setAlias("s");
        tableOne.setPosition(1);
        tableOne.setSchema("public");
        tableTwo = new Table(2, "event");
        tableTwo.setAlias("e");
        tableTwo.setPosition(2);
        tableTwo.setSchema("public");
        tableThree = new Table(3, "object");
        tableThree.setAlias("o");
        tableThree.setPosition(3);
        tableThree.setSchema("public");
        tableMap.put(tableOne.getId(), tableOne);
        tableMap.put(tableTwo.getId(), tableTwo);
        tableMap.put(tableThree.getId(), tableThree);

        // init fields
        fieldOne = new Field(1, "name", 1, 1);
        fieldOne.setAlias("Name");
        fieldTwo = new Field(2, "id", 2, 2);
        fieldTwo.setAlias("Id");
        fieldMap.put(fieldOne.getId(), fieldOne);
        fieldMap.put(fieldTwo.getId(), fieldTwo);

        // init groups
        groupOne = new Group(1);
        groupTwo = new Group(2);
        groupOneFieldsId.add(1);
        groupOneFieldsId.add(2);
        groupOne.setFieldsId(groupOneFieldsId);
        groupTwo.setFieldsId(groupTwoFieldsId);
        groupMap.put(groupOne.getId(), groupOne);
        groupMap.put(groupTwo.getId(), groupTwo);

        // init functions
        functionOne = new Function(FunctionType.WINDOW, FunctionName.MAX);
        functionOne.setGroupsId(1);
        fieldTwo.setFunction(functionOne);


        groupOne.setFieldsId(groupOneFieldsId);


        // init query
        query = new Query(1);
        query.setFields(fieldMap);
        query.setTables(tableMap);
        query.setAllFieldsAfter(false); //TODO Пока не используется
        query.setGroups(groupMap);
    }

    @Test
    public void checkGroupBuilding() {

    }
}