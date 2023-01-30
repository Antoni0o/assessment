package br.com.assessment.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.assessment.dtos.AddressRequestDTO;
import br.com.assessment.errors.AppError;
import br.com.assessment.models.Address;
import br.com.assessment.models.Person;
import br.com.assessment.repositories.AddressRepository;
import br.com.assessment.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressUseCase {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Address> listAllPersonAdresses(Long personId) throws AppError {
        Optional<List<Address>> adresses = this.addressRepository.findByPerson(this.findPersonById(personId).get());

        this.findPersonById(personId);

        if (adresses.get().isEmpty()) {
            throw new AppError("Adresses Not Found!", HttpStatus.NOT_FOUND);
        }

        return adresses.get();
    }

    public Address listPersonMainAddress(Long personId) throws AppError {
        return this.addressRepository.findByIsMainAndPerson(true, this.findPersonById(personId).get());
    }

    @Transactional
    public Address createAddress(Long personId, AddressRequestDTO addressRequest) throws AppError {
        // também pode ser feito um mapper
        Address address = new Address();
        address.setAddressNumber(addressRequest.getAddressNumber());
        address.setCity(addressRequest.getCity());
        address.setPublicPlace(addressRequest.getPublicPlace());
        address.setZipCode(addressRequest.getZipCode());
        address.setIsMain(addressRequest.getIsMain());
        address.setPerson(this.findPersonById(personId).get());

        return this.addressRepository.save(address);
    }

    public Optional<Person> findPersonById(Long personId) throws AppError {
        Optional<Person> person = this.personRepository.findById(personId);

        if (Optional.of(person).isEmpty()) {
            throw new AppError("Person Not Found!", HttpStatus.NOT_FOUND);
        }

        return person;
    }
}
