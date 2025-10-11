package com.Hindol.Command.Service;

import com.Hindol.Command.Event.OrderEvent;

public interface OrderEventService {
    void pushEvent(OrderEvent orderEvent);
}
