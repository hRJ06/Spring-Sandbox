package com.Hindol.Command.Service.Implementation;

import com.Hindol.Command.Event.OrderEvent;
import com.Hindol.Command.Event.Publisher.OrderEventPublisherFactory;
import com.Hindol.Command.Repository.OrderEventRepository;
import com.Hindol.Command.Service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventServiceImplementation implements OrderEventService {

    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisherFactory publisherFactory;

    @Override
    public void pushEvent(OrderEvent orderEvent) {
        orderEventRepository.addEvent(orderEvent);
        log.info("ORDER event saved for orderId={}", orderEvent.getOrderId());

        var publisher = publisherFactory.getPublisher(orderEvent.getEventType());
        publisher.publish(orderEvent);

        log.info("Kafka event published for eventType={}", orderEvent.getEventType());
    }
}
