package com.example.cardiocare;


import org.junit.Test;


import static org.junit.Assert.*;
/**
 * This class use for Unit Test
 */

public class UserMeasurementTest {
    UserMeasurementDetails details = new UserMeasurementDetails("21-05-2023", "6.00", "121", "101", "99", "Normal Pressure", "120");
    UserMeasurementDetails details2 = new UserMeasurementDetails("21-05-2020", "5.00", "120", "100", "98", "Normal Pressure", "121");

    @Test
    public void testGetDate() {
        assertEquals("21-05-2020", details2.getDate());
    }

    @Test
    public void testGetTime() {
        assertEquals("5.00", details2.getTimne());
    }

    @Test
    public void testGetSystolic() {

        assertEquals("120", details2.getSystolic());
    }

    @Test
    public void testGetDiastolic() {

        assertEquals("100", details2.getDayastolic());
    }

    @Test
    public void testGetHeartrate() {
        assertEquals("98", details2.getHeartrate());
    }

    @Test
    public void testGetComment() {
        assertEquals("Normal Pressure", details2.getComment());
    }


    @Test
    public void testGetKey() {
        assertEquals("121", details2.getKey());
    }

    /**
     * testing addData method
     */
    @Test
    public void testAddData() {
        DataTestingClass dataList = new DataTestingClass();

        dataList.addData(details);
        assertEquals(1, dataList.getData().size());

        dataList.addData(details2);
        assertEquals(2, dataList.getData().size());

        assertTrue(dataList.getData().contains(details));
        assertTrue(dataList.getData().contains(details2));
    }

    /**
     * testing deleteData method
     */
    @Test
    public void testDeleteData() {
        DataTestingClass dataList = new DataTestingClass();

        dataList.addData(details);
        assertEquals(1, dataList.getData().size());


        dataList.addData(details2);
        assertEquals(2, dataList.getData().size());

        assertTrue(dataList.getData().contains(details));
        assertTrue(dataList.getData().contains(details2));

        dataList.deleteData(details);
        assertEquals(1, dataList.getData().size());
        assertFalse(dataList.getData().contains(details));

        dataList.deleteData(details2);
        assertEquals(0, dataList.getData().size());
        assertFalse(dataList.getData().contains(details2));
    }

    /**
     * testing addData method for exceptions
     */
    @Test
    public void testAddRecordException() {

        DataTestingClass dataList = new DataTestingClass();
        dataList.addData(details);

        assertThrows(IllegalArgumentException.class, () -> dataList.addData(details));
    }

    /**
     * testing deleteData method for exceptions
     */
    @Test
    public void testDeleteRecordException() {


        DataTestingClass dataList = new DataTestingClass();

        dataList.addData(details);

        dataList.deleteData(details);

        assertThrows(IllegalArgumentException.class, () -> dataList.deleteData(details));
    }

}
