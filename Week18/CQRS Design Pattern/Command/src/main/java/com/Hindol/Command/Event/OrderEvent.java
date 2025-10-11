package com.Hindol.Command.Event;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@DynamoDBTable(tableName = "order_event")
@Data
@Builder
public class OrderEvent implements Serializable {
    @DynamoDBHashKey(attributeName = "order_id")
    private Long orderId;

    @DynamoDBRangeKey(attributeName = "timestamp")
    private String timestamp;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private OrderEventType eventType;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson
    private Map<String, Object> payload;
}
