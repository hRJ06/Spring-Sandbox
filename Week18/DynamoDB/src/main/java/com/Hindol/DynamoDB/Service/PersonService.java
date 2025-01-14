package com.Hindol.DynamoDB.Service;

import com.Hindol.DynamoDB.Entity.Person;
import com.Hindol.DynamoDB.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person savePerson(Person person) {
        return personRepository.addPerson(person);
    }

    public void deletePerson(String personId) {
        personRepository.deletePerson(personId);
    }

    public Person findPersonById(String personId) {
        return personRepository.findPersonByPersonId(personId);
    }

    public Person editPerson(Person person) {
        return personRepository.editPerson(person);
    }
}
