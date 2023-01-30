package br.com.assessment.usecases;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.assessment.dtos.PersonRequestDTO;
import br.com.assessment.errors.AppError;
import br.com.assessment.models.Person;
import br.com.assessment.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonUseCase {

    @Autowired
    private PersonRepository repository;

    public List<Person> listAllPeople() {
        return this.repository.findAll();
    }

    public Person listSpecificPerson(PersonRequestDTO personRequest) throws ParseException, AppError {
        Optional<Person> person = this.repository.findByNameAndBirthday(personRequest.getName(),
                this.birthdayStringToDate(personRequest.getBirthday()));

        if (person.isEmpty()) {
            throw new AppError("User Not Found", HttpStatus.NOT_FOUND);
        }

        return Optional.of(person).get().orElseThrow();
    }

    public Person createPerson(PersonRequestDTO personRequest) throws ParseException, AppError {
        Person person = new Person();

        if (personRequest.getName() == null || personRequest.getBirthday() == null) {
            throw new AppError("Name or Birthday is missing!", HttpStatus.BAD_REQUEST);
        }

        person.setBirthday(this.birthdayStringToDate(personRequest.getBirthday()));
        person.setName(personRequest.getName());

        return this.repository.save(person);
    }

    public Person updatePerson(Long id, Optional<PersonRequestDTO> personRequest) throws ParseException, AppError {
        Optional<Person> user = this.repository.findById(id);

        if (user.isEmpty()) {
            throw new AppError("User Not Found", HttpStatus.NOT_FOUND);
        }

        if (personRequest.get().getBirthday() != "") {
            user.get().setBirthday(this.birthdayStringToDate(personRequest.get().getBirthday()));
        }

        if (personRequest.get().getName() != "") {
            user.get().setName(personRequest.get().getName());
        }

        return this.repository.save(user.get());
    }

    public void deletePerson(Long id) {
        this.repository.deleteById(id);
    }

    public Date birthdayStringToDate(String birthdayString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(birthdayString);
    }
}
