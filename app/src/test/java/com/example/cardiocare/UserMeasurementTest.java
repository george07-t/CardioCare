package com.example.cardiocare;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserMeasurementTest {
    UserMeasurementDetails details = new UserMeasurementDetails("21-05-2020", "5.00", "120", "100", "98", "Normal Pressure", "121");

    @Test
    public void testGetDate() {
        assertEquals("21-05-2020", details.getDate());
    }

    @Test
    public void testGetTime() {
        assertEquals("5.00", details.getTimne());
    }

    @Test
    public void testGetSystolic() {

        assertEquals("120", details.getSystolic());
    }

    @Test
    public void testGetDiastolic() {

        assertEquals("100", details.getDayastolic());
    }

    @Test
    public void testGetHeartrate() {
        assertEquals("98", details.getHeartrate());
    }

    @Test
    public void testGetComment() {
        assertEquals("Normal Pressure", details.getComment());
    }


    @Test
    public void testGetKey() {
        assertEquals("121", details.getKey());
    }

}
