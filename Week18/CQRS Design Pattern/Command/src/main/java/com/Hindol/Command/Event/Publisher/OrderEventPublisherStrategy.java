package com.Hindol.Command.Event.Publisher;

import com.Hindol.Command.Event.OrderEvent;

public interface OrderEventPublisherStrategy {
    void publish(OrderEvent event);
}
