package com.dave.orderservice.repositories;

import com.dave.orderservice.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("test product");
        product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setName("customer");
        Customer savedCustomer = customerRepository.save(customer);
        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

//        orderHeader.setOrderLines(Set.of(orderLine));
//        orderLine.setOrderHeader(orderHeader);

        orderHeader.addOrderLine(orderLine);

        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);

        orderHeaderRepository.flush();

        assertNotNull(savedOrderHeader);
        assertNotNull(savedOrderHeader.getId());
        assertNotNull(savedOrderHeader.getOrderLines());
        assertEquals(savedOrderHeader.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrderHeader.getId());

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setName("customer");
        orderHeader.setCustomer(customer);
        Customer savedCustomer = customerRepository.save(customer);
        orderHeader.setCustomer(savedCustomer);
        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrderHeader);
        assertNotNull(savedOrderHeader.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrderHeader.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }
}
