package com.Hindol.Command.Event.Publisher;

import com.Hindol.Command.Event.OrderEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderEventPublisherFactory {
    private final Map<String, OrderEventPublisherStrategy> strategies;

    public OrderEventPublisherStrategy getPublisher(OrderEventType type) {
        return strategies.get(type.name());
    }
}
