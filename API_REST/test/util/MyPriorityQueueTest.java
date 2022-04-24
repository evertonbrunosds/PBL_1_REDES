package util;

import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static util.Constants.*;

public class MyPriorityQueueTest {

    private final MyPriorityQueue<Entry<String, JSONObject>> mpq;

    public MyPriorityQueueTest() {
        mpq = new MyPriorityQueue<>(new RecycleBinComparator());
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mpq.clear();
    }

    @After
    public void tearDown() {
    }

    private static Entry<String, JSONObject> gerateEntry(final String id, final String usage, final String priority) {
        final JSONObject dataUser = new JSONObject();
        dataUser.put(USAGE, usage);
        dataUser.put(IS_PRIORITY, priority);
        final Entry<String, JSONObject> entry = new Entry<String, JSONObject>() {
            @Override
            public String getKey() {
                return id;
            }

            @Override
            public JSONObject getValue() {
                return dataUser;
            }

            @Override
            public JSONObject setValue(JSONObject value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return entry;
    }

    @Test
    public void add() {
        mpq.add(gerateEntry("2", "0.25", "TRUE"));
        mpq.add(gerateEntry("3", "0.25", "TRUE"));
        mpq.add(gerateEntry("4", "0.00", "TRUE"));
        mpq.add(gerateEntry("5", "0.25", "TRUE"));
        mpq.add(gerateEntry("6", "0.25", "TRUE"));
        mpq.add(gerateEntry("7", "1.0", "FALSE"));
        mpq.add(gerateEntry("8", "0.00", "FALSE"));
        mpq.add(gerateEntry("11", "0.50", "TRUE"));
        mpq.add(gerateEntry("13", "0.75", "FALSE"));
        final Iterator<Entry<String, JSONObject>> iterator = mpq.iterator();
        assertEquals("11", iterator.next().getKey());
        assertEquals("6", iterator.next().getKey());
        assertEquals("5", iterator.next().getKey());
        assertEquals("3", iterator.next().getKey());
        assertEquals("2", iterator.next().getKey());
        assertEquals("7", iterator.next().getKey());
        assertEquals("13", iterator.next().getKey());
        assertEquals("4", iterator.next().getKey());
        assertEquals("8", iterator.next().getKey());

    }

}
