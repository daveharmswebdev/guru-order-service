package com.dave.orderservice.repositories;

import com.dave.orderservice.domain.OrderHeader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomerName("New Customer");
        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrderHeader);
        assertNotNull(savedOrderHeader.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrderHeader.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
    }
}
