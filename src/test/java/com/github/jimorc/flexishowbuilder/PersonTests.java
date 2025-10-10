package com.github.jimorc.flexishowbuilder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PersonTests {
    @Test
    void testPerson() {
        Person p = new Person("John", "Doe");
        assertEquals("John", p.getFirstName());
        assertEquals("Doe", p.getLastName());
        assertEquals("John Doe", p.getFullName());
        assertEquals("John D.", p.getFirstPlusInitial());
    }
}