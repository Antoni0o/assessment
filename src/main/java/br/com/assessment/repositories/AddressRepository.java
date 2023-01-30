package br.com.assessment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import br.com.assessment.models.Address;
import br.com.assessment.models.Person;

public interface AddressRepository extends ListCrudRepository<Address, Long> {
    Optional<List<Address>> findByPerson(Person person);

    Address findByIsMainAndPerson(Boolean isMain, Person person);
}
