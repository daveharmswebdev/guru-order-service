package com.dave.orderservice.repositories;

import com.dave.orderservice.domain.OrderHeader;
import com.dave.orderservice.domain.OrderLine;
import com.dave.orderservice.domain.Product;
import com.dave.orderservice.domain.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

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
        orderHeader.setCustomerName("New Customer");

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.setOrderLines(Set.of(orderLine));
        orderLine.setOrderHeader(orderHeader);

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
        orderHeader.setCustomerName("New Customer");
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
