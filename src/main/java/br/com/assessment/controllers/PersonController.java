package br.com.assessment.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.assessment.dtos.PersonRequestDTO;
import br.com.assessment.errors.AppError;
import br.com.assessment.models.Person;
import br.com.assessment.usecases.PersonUseCase;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonUseCase useCase;

    @GetMapping
    public List<Person> listAllPeople() {
        try {
            return useCase.listAllPeople();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/specific")
    public Person listSpecificPerson(@RequestBody PersonRequestDTO personRequest) {
        try {
            return useCase.listSpecificPerson(personRequest);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody PersonRequestDTO personRequest) {
        try {
            return useCase.createPerson(personRequest);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Person updatePerson(
            @PathVariable Long id,
            @RequestBody Optional<PersonRequestDTO> personRequest) {
        try {
            return useCase.updatePerson(id, personRequest);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id) {
        try {
            useCase.deletePerson(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
