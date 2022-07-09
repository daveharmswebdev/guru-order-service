package com.dave.orderservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader oh1 = new OrderHeader();
        oh1.setId(1l);

        OrderHeader oh2 = new OrderHeader();
        oh2.setId(1l);

        assert oh1.equals(oh2);
    }

    @Test
    void testNotEquals() {
        OrderHeader oh1 = new OrderHeader();
        oh1.setId(1l);

        OrderHeader oh2 = new OrderHeader();
        oh2.setId(2l);

        assertFalse(oh1.equals(oh2));
    }
}