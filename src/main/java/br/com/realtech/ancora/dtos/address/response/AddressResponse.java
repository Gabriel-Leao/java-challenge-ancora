package br.com.realtech.ancora.dtos.address.response;

import br.com.realtech.ancora.entities.Address;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddressResponse {
    private final UUID id;
    private final String street;
    private final String number;
    private final String complement;
    private final String city;
    private final String state;
    private final String postalCode;

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.city = address.getCity();
        this.state = address.getState();
        this.postalCode = address.getPostalCode();
    }
}
