package labproblems.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Marius
 * This class contains all the tests related to the Student entity
 */
public class StudentTest {
    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String SERIAL_NUMBER = "sn01";
    private static final String NEW_SERIAL_NUMBER = "sn02";
    private static final String NAME = "studentName";
    private static final int GROUP = 123;

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student(SERIAL_NUMBER, NAME, GROUP);
        student.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        student=null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial numbers should be equal", SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        student.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial numbers should be equal", NEW_SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, student.getId());
    }

    @Test
    public void testSetId() throws Exception {
        student.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, student.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be the same", NAME, student.getName());
    }

    @Test
    public void testSetName() throws Exception {
        student.setName("Another Name");
        assertEquals("Names should be equal", "Another Name", student.getName());
    }

    @Test
    public void testGetGroup() throws Exception {
        assertEquals("Groups should be the same", GROUP, student.getGroup());
    }

    @Test
    public void testSetGroup() throws Exception {
        student.setGroup(456);
        assertEquals("Groups should be the same", 456, student.getGroup());
    }
}