package com.Hindol.DynamoDB.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

import java.io.Serializable;

@DynamoDBDocument
@Data
public class Address implements Serializable {
    @DynamoDBAttribute
    private String city;
    @DynamoDBAttribute
    private String state;
    @DynamoDBAttribute
    private Long pinCode;
}
