package br.com.realtech.ancora.controllers;

import br.com.realtech.ancora.dtos.address.request.UpsertAddressRequest;
import br.com.realtech.ancora.dtos.address.response.AddressResponse;
import br.com.realtech.ancora.services.address.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping()
    public ResponseEntity<AddressResponse> getAddressByUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(addressService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AddressResponse> createOrUpdateAddress(@PathVariable UUID userId, @Valid @RequestBody UpsertAddressRequest request) {
        return new ResponseEntity<>(addressService.saveAddress(userId, request), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID userId, @Valid @RequestBody UpsertAddressRequest request) {
        return new ResponseEntity<>(addressService.updateAddress(userId, request), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID userId) {
        addressService.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
