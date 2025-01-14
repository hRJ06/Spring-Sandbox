package com.Hindol.DynamoDB.Repository;

import com.Hindol.DynamoDB.Entity.Person;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PersonRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public Person addPerson(Person person) {
        mapper.save(person);
        return person;
    }

    public Person findPersonByPersonId(String personId) {
        return mapper.load(Person.class, personId);
    }

    public void deletePerson(String personId) {
        Person person = new Person();
        person.setPersonId(personId);
        mapper.delete(person, buildDeleteExpression(personId));
    }

    public Person editPerson(Person person) {
        mapper.save(person, buildSaveExpression(person));
        return person;
    }

    private DynamoDBSaveExpression buildSaveExpression(Person person) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("personId", new ExpectedAttributeValue(new AttributeValue().withS(person.getPersonId())));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }

    private DynamoDBDeleteExpression buildDeleteExpression(String personId) {
        DynamoDBDeleteExpression dynamoDBDeleteExpression = new DynamoDBDeleteExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("personId", new ExpectedAttributeValue(new AttributeValue().withS(personId)));
        dynamoDBDeleteExpression.setExpected(expectedMap);
        return dynamoDBDeleteExpression;
    }
}
