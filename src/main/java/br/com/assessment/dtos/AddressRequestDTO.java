package br.com.assessment.dtos;

import lombok.Data;

@Data
public class AddressRequestDTO {

    private String publicPlace;
    private String zipCode;
    private Long addressNumber;
    private String city;
    private Boolean isMain;

}
