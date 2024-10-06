package com.Hindol.AOP.Service.Implementation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ShipmentServiceImplementationTest {
    @Autowired
    private ShipmentServiceImplementation shipmentServiceImplementation;

    @Test
    void aopTestOrderPackage() {
        String orderString = shipmentServiceImplementation.orderPackage(-1L);
        log.info(orderString);
    }

    @Test
    void aopTestTrackPackage() {
        shipmentServiceImplementation.trackPackage(4L);
    }

}