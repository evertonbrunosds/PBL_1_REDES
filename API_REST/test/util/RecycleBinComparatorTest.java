package util;

import java.util.Map.Entry;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static util.Constants.*;

public class RecycleBinComparatorTest {

    private final String idSmaller = "0";
    private final String idBigger = "1";
    private final String usageSmaller = "0.25";
    private final String usageBigger = "0.50";
    private final String isPriorityFalse = "FALSE";
    private final String isPriorityTrue = "TRUE";

    private final RecycleBinComparator comparator;

    public RecycleBinComparatorTest() {
        comparator = new RecycleBinComparator();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static Entry<String, JSONObject> gerateRecycle(
            final String id,
            final String usage,
            final String isPriority
    ) {
        final Entry<String, JSONObject> entry;
        final JSONObject recycleDataJSON = new JSONObject();
        recycleDataJSON.put(USAGE, usage);
        recycleDataJSON.put(IS_PRIORITY, isPriority);
        entry = new Entry<String, JSONObject>() {
            @Override
            public String getKey() {
                return id;
            }

            @Override
            public JSONObject getValue() {
                return recycleDataJSON;
            }

            @Override
            public JSONObject setValue(JSONObject value) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        return entry;
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityFalseIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageSmallerIsPriorityTrueIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityFalseIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idSmallerUsageBiggerIsPriorityTrueIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityFalseIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageSmallerIsPriorityTrueIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(SMALLER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityFalseIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdSmallerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdSmallerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageSmaller, isPriorityTrue);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdSmallerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityFalse);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdSmallerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idSmaller, usageBigger, isPriorityTrue);
        assertEquals(BIGGER, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdBiggerUsageSmallerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdBiggerUsageSmallerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageSmaller, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdBiggerUsageBiggerIsPriorityFalse() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityFalse);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

    @Test
    public void idBiggerUsageBiggerIsPriorityTrueIdBiggerUsageBiggerIsPriorityTrue() {
        final Entry<String, JSONObject> e1 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        final Entry<String, JSONObject> e2 = gerateRecycle(idBigger, usageBigger, isPriorityTrue);
        assertEquals(EQUAL, comparator.compare(e1, e2));
    }

}
