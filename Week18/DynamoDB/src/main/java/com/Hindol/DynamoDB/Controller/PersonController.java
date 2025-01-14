package com.Hindol.DynamoDB.Controller;

import com.Hindol.DynamoDB.Entity.Person;
import com.Hindol.DynamoDB.Service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.savePerson(person));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String personId) {
        personService.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable String personId) {
        return ResponseEntity.ok(personService.findPersonById(personId));
    }

    @PutMapping
    public ResponseEntity<Person> editPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.editPerson(person));
    }

}
