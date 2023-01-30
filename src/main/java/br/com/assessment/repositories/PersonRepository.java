package br.com.assessment.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import br.com.assessment.models.Person;

public interface PersonRepository extends ListCrudRepository<Person, Long> {
    Optional<Person> findByNameAndBirthday(String name, Date birthday);
}
