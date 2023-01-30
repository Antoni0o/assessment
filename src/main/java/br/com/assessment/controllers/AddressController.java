package br.com.assessment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.assessment.dtos.AddressRequestDTO;
import br.com.assessment.errors.AppError;
import br.com.assessment.models.Address;
import br.com.assessment.usecases.AddressUseCase;

@RestController
@RequestMapping("/adresses")
public class AddressController {

    @Autowired
    private AddressUseCase useCase;

    @GetMapping("/{personId}")
    public List<Address> listAllPersonAdresses(@PathVariable Long personId) {
        try {
            return useCase.listAllPersonAdresses(personId);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/main/{personId}")
    public Address listPersonMainAddress(@PathVariable Long personId) {
        try {
            return useCase.listPersonMainAddress(personId);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/{personId}")
    public Address createAddress(@PathVariable Long personId, @RequestBody AddressRequestDTO addressRequest) {
        try {
            return useCase.createAddress(personId, addressRequest);
        } catch (AppError e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
