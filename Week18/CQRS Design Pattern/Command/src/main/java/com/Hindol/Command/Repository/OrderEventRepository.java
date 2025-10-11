package com.Hindol.Command.Repository;

import com.Hindol.Command.Event.OrderEvent;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderEventRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public void addEvent(OrderEvent event) {
        mapper.save(event);
    }
}
