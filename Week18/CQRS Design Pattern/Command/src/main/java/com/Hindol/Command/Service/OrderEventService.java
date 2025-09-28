package com.Hindol.Command.Service;

import com.Hindol.Command.Entity.OrderEvent;

public interface OrderEventService {
    void pushEvent(OrderEvent orderEvent);
}
