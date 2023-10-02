package com.ispc.gymapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    User user;

    @Before
    public void setUp(){
        user = new User(68d,165);
    }
    @Test
    public void calculateIMC() {
        double expected = 24.98;
        System.out.println(user);
        assertEquals(expected,user.getIMC(), 1.0);
    }

    @Test
    public void shouldBeMeters(){
        Integer height = user.getHeight();
        double expected =  1.65d;
        assertEquals(expected, height / 100d, 1.0);
    }
}