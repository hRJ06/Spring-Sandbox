package com.Hindol.AOP.Service.Implementation;

import com.Hindol.AOP.Aspect.LogAnnotation;
import com.Hindol.AOP.Service.ShipmentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShipmentServiceImplementation implements ShipmentService {
    @Override
    @LogAnnotation
    public String orderPackage(Long orderId) {
        try {
            log.info("Processing the order");
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            log.error("Error occurred while processing the order", ex);
        }
        return "Order has been processed successfully, orderId - " + orderId;
    }

    @Override
    @Transactional
    public String trackPackage(Long orderId) {
        try {
            log.info("Tracking the order");
            Thread.sleep(500);
            throw new RuntimeException("Exception occurred during trackPackage");
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
